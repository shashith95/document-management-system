package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.config.FileProperties;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.BucketDataDto;
import com.api.documentmanagementservice.model.dto.FileDataDto;
import com.api.documentmanagementservice.model.dto.UploadedFileDetailsDto;
import com.api.documentmanagementservice.model.dto.ZippedFileDetailsDto;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import com.api.documentmanagementservice.service.DocumentStorageService;
import com.api.documentmanagementservice.service.FileOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.api.documentmanagementservice.commons.constant.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileOperationServiceImpl implements FileOperationsService {
    private final FileDetailsRepository fileDetailsRepository;
    private final FileProperties fileProperties;
    private final DocumentStorageService documentStorageService;

    /**
     * Retrieves a document from Minio storage by its file hash and performs unzipping if necessary.
     *
     * @param fileHash      The hash of the file to retrieve from Minio.
     * @param headerContext The header context containing information about the request.
     * @return A ResponseEntity containing the requested file as a Resource.
     * @throws DmsException If an error occurs during the retrieval or unzipping process.
     */
    @Override
    public ResponseEntity<Resource> getDocumentFromDocumentStorage(String fileHash, HeaderContext headerContext)
            throws DmsException, IOException {

        BucketDataDto bucketDataDto = fileDetailsRepository.getBucketDataRecordById(fileHash)
                .orElseThrow(() -> new DmsException(DB_NO_DATA.getCode(),
                        "Didn't find a bucket for given fileHash: " + fileHash));

        var filePath = bucketDataDto.path() + "/" + bucketDataDto.documentId();

        log.info("Retrieving document from Minio for fileHash: {}", fileHash);
        Resource fileResource = getFileFromDocumentStorage(bucketDataDto.bucketName(), filePath);

        // Set response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                getEncodedFileName(bucketDataDto.fileName(), "UTF-8"));
        headers.add("SessionId", fileHash);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileResource);
    }

    /**
     * Retrieves a file from a specified bucket and path in the document storage service.
     * This method handles downloading and unzipping if necessary.
     *
     * @param bucketName The name of the bucket in the document storage service.
     * @param filePath   The path to the file within the specified bucket.
     * @return A Resource containing the requested file data.
     * @throws DmsException If an error occurs during file retrieval or processing.
     */
    public Resource getFileFromDocumentStorage(String bucketName, String filePath) throws DmsException {
        try (InputStream inputStream = documentStorageService.downloadDocument(bucketName, filePath);
             ZipInputStream zipInputStream = new ZipInputStream(inputStream);
             ByteArrayOutputStream unzippedOutputStream = new ByteArrayOutputStream()) {

            while (zipInputStream.getNextEntry() != null) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                    unzippedOutputStream.write(buffer, 0, bytesRead);
                }
            }

            return new ByteArrayResource(unzippedOutputStream.toByteArray());
        } catch (Exception e) {
            log.error("Error occurred while retrieving file from Minio", e);
            throw new DmsException(FILE_NOT_FOUND.getCode(), "Issue occurred while retrieving file from Minio");
        }
    }

    /**
     * Encodes the given file name using the specified encoding type.
     *
     * @param fileName   The file name to be encoded.
     * @param encodeType The encoding type to be used (e.g., "UTF-8").
     * @return The encoded file name.
     */
    private String getEncodedFileName(String fileName, String encodeType) throws DmsException {
        try {
            return URLEncoder.encode(fileName, encodeType);
        } catch (UnsupportedEncodingException e) {
            log.error("Error occurred in file name encoder", e);
            throw new DmsException(FILE_NAME_ENCODE_ERROR.getCode(), "Error occurred in file name encoder");
        }
    }

    /**
     * Uploads and zips a file from an input stream to a specified cloud storage path.
     *
     * @param fileInputStream The input stream containing the file content to be zipped and uploaded.
     * @param fileDataDto     The FileDataDto containing information about the file.
     * @param fileUploadPath  The local temporary path for storing the zipped file.
     * @param contentType     The content type of the file.
     * @param previousDocId   The previous document ID (if available).
     * @param bucketName      The name of the cloud storage bucket.
     * @param documentPath    The path within the cloud storage bucket where the zipped file will be stored.
     * @return A ZippedFileDetailsDto containing details of the uploaded and zipped file.
     * @throws DmsException If an error occurs during the zipping or uploading process.
     */
    public ZippedFileDetailsDto uploadAndZipFileToTemporaryPath(InputStream fileInputStream,
                                                                FileDataDto fileDataDto,
                                                                String fileUploadPath,
                                                                String contentType,
                                                                String previousDocId,
                                                                String bucketName,
                                                                String documentPath) throws DmsException {

        String fileHash = RandomStringUtils.randomAlphanumeric(64);
        String documentId = Objects.requireNonNullElse(previousDocId, RandomStringUtils.randomAlphanumeric(32));
        Path zipFilePath = Path.of(fileUploadPath, documentId);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            zipOutputStream.putNextEntry(new ZipEntry(zipFilePath.getFileName().toString()));

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, len);
            }

            zipOutputStream.closeEntry();

            var documentStorageUploadPath = documentPath + "/" + zipFilePath.getFileName();
            var byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            // this step is used since minio perf version(2020) is slow when uploading files as stream directly from application
            writeFileToLocalStorage(byteArrayInputStream, zipFilePath);
            documentStorageService.uploadDocument(bucketName, documentStorageUploadPath, byteArrayInputStream, zipFilePath.toFile());
            log.info("File upload completed successfully");
            return ZippedFileDetailsDto.builder()
                    .fileName(fileDataDto.name())
                    .fileHash(fileHash)
                    .documentId(documentId)
                    .filePath(zipFilePath.toString())
                    .contentType(contentType)
                    .build();
        } catch (IOException | DmsException e) {
            log.error("Error occurred while uploading and zipping file", e);
            throw new DmsException(FILE_UPLOAD_ERROR.getCode(), e.getMessage());
        } finally {
            if (zipFilePath.toFile().exists()) {
                zipFilePath.toFile().delete();
            }
        }
    }

    /**
     * Writes the content from an input stream to a local file path.
     *
     * @param documentStream The input stream containing the file content to be written.
     * @param zipFilePath    The local file path where the content will be saved.
     * @throws DmsException If an error occurs during the write operation.
     * @throws IOException  If an I/O error occurs while reading from the input stream or writing to the file.
     */
    private void writeFileToLocalStorage(InputStream documentStream, Path zipFilePath) throws DmsException, IOException {
        createTempDirIfNotExists(zipFilePath.getParent().toString());

        try (documentStream; OutputStream outputStream = new FileOutputStream(zipFilePath.toString())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = documentStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            log.info("File saved successfully at: " + zipFilePath);
        } catch (IOException e) {
            log.error("Error occurred while saving file", e);
            throw new DmsException(FILE_WRITE_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * Creates a temporary directory if it does not exist.
     *
     * @param tempDir The path to the temporary directory.
     */
    private void createTempDirIfNotExists(String tempDir) {
        File tempDirectory = new File(tempDir);
        if (!tempDirectory.exists()) {
            log.warn("Temp directory path not available, creating one");
            tempDirectory.mkdirs();
        }
    }

    /**
     * Uploads and zips a base64-encoded file to a temporary path.
     *
     * @param base64File  The base64-encoded file.
     * @param fileDataDto The FileDataDto containing information about the file.
     * @return A ZippedFileDetailsDto containing details of the uploaded and zipped file.
     */
    public ZippedFileDetailsDto uploadAndZipBase64ToTemporaryPath(String base64File,
                                                                  FileDataDto fileDataDto,
                                                                  String bucketName,
                                                                  String documentPath) throws DmsException {
        String fileUploadPath = fileProperties.getTempUploadPath();
        UploadedFileDetailsDto uploadedFileDetailsDto = createFileFromBase64(base64File);

        try (InputStream fileContent = new ByteArrayInputStream(uploadedFileDetailsDto.getFileByteArray())) {
            return uploadAndZipFileToTemporaryPath(fileContent, fileDataDto, fileUploadPath, uploadedFileDetailsDto.getContentType(),
                    null, bucketName, documentPath);
        } catch (IOException e) {
            log.error("Error occurred while uploading and zipping file", e);
            throw new DmsException(FILE_UPLOAD_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * Uploads and zips a file from a multipart file to a temporary path.
     *
     * @param multipartFile The multipart file to upload and zip.
     * @param fileDataDto   The FileDataDto containing information about the file.
     * @param previousDocId The previous document ID (if available).
     * @return A ZippedFileDetailsDto containing details of the uploaded and zipped file.
     */
    public ZippedFileDetailsDto uploadAndZipToTemporaryPath(MultipartFile multipartFile,
                                                            FileDataDto fileDataDto,
                                                            String previousDocId,
                                                            String bucketName,
                                                            String documentPath) throws DmsException {
        String fileUploadPath = fileProperties.getTempUploadPath();

        try (InputStream fileContent = multipartFile.getInputStream()) {
            return uploadAndZipFileToTemporaryPath(fileContent, fileDataDto, fileUploadPath, multipartFile.getContentType(),
                    previousDocId, bucketName, documentPath);
        } catch (IOException | DmsException e) {
            log.error("Error occurred while uploading and zipping file", e);
            throw new DmsException(FILE_UPLOAD_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * Creates UploadedFileDetailsDto from a base64-encoded file.
     *
     * @param base64File The base64-encoded file.
     * @return An UploadedFileDetailsDto containing details of the created file.
     */
    public UploadedFileDetailsDto createFileFromBase64(String base64File) {
        String uploadPath = fileProperties.getTempUploadPath();

        String[] parts = base64File.split(",");
        String[] contentTypeParts = parts[0].split(";");
        String contentType = contentTypeParts[0].split(":")[1].trim();
        String extension = contentType.split("/")[1].trim();
        String base64Image = parts[1].strip().replace("\"", "");

        byte[] byteArr = Base64.getDecoder().decode(base64Image);
        String size = String.valueOf(byteArr.length);
        String documentTmpPath = uploadPath + "/final." + extension;

        return UploadedFileDetailsDto.builder()
                .filepath(documentTmpPath)
                .extension(extension)
                .contentType(contentType)
                .size(Long.valueOf(size))
                .fileByteArray(byteArr)
                .build();
    }
}
