package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.commons.mapper.CustomObjectMapper;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.FileDataDto;
import com.api.documentmanagementservice.model.dto.UploadFileDetails;
import com.api.documentmanagementservice.model.dto.ZippedFileDetailsDto;
import com.api.documentmanagementservice.model.table.DirectoryDetails;
import com.api.documentmanagementservice.model.table.FileDetails;
import com.api.documentmanagementservice.repository.DocumentDetailsRepository;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import com.api.documentmanagementservice.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.CommonContent.ID;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_DATA_RETRIEVAL_ERROR;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.REQ_REQUEST_INPUT_FILE_MISSING;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.DM_DMS_001;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadServiceImpl implements UploadService {
    private final FileOperationsService fileOperationsService;
    private final BucketService bucketService;
    private final DirectoryDetailsService directoryDetailsService;
    private final DocumentDetailsRepository documentDetailsRepository;
    private final FileDetailsRepository fileDetailsRepository;
    private final DocumentDetailsService documentDetailsService;
    private final CustomMongoService customMongoService;
    private final DocumentStorageService documentStorageService;
    private final HeaderContext headerContext;
    private final CustomObjectMapper objectMapper;

    /**
     * Handles the file upload process.
     *
     * @param request          The HTTP servlet request containing the uploaded file data.
     * @param documentDataJson The JSON string representing document data.
     * @param fileDataJson     The JSON string representing file data.
     * @param base64File       The base64-encoded file data (optional).
     * @return A ResponseEntity containing the response data.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> uploadFile(HttpServletRequest request,
                                                     String documentDataJson,
                                                     String fileDataJson,
                                                     String base64File) throws BadRequestException, DmsException, JsonProcessingException {
        log.info("File uploading process started");

        // Parse JSON strings to extract document and file data
        Map documentDataMap = objectMapper.getMapFromJsonString(documentDataJson);
        FileDataDto fileDataDto = objectMapper.getFileDataDtoFromJsonStringArray(fileDataJson);

        // Check for documentId in request, if it's not available a null will be passed
        // and a new document id will be generated in next method
        String previousDocId = documentDataMap.containsKey(ID.getContent())
                ? documentDataMap.get(ID.getContent()).toString()
                : null;

        String bucketName = bucketService.createBucketName(headerContext.getTenantId(), headerContext.getHospitalId());
        createBucketIfNotExists(bucketName);
        String documentStoragePath = createDocumentStoragePath(documentDataMap);

        // Determine the source of the file data (Multipart or Base64)
        ZippedFileDetailsDto zippedFileDetails;
        if (base64File == null) {
            zippedFileDetails = handleMultipartFileUpload(request, fileDataDto, previousDocId, bucketName, documentStoragePath);
        } else {
            zippedFileDetails = fileOperationsService.uploadAndZipBase64ToTemporaryPath(base64File, fileDataDto, bucketName, documentStoragePath);
        }

        // Prepare document storage path and upload file details
        UploadFileDetails uploadFileDetails = getUploadFileDetails(fileDataDto, documentStoragePath, zippedFileDetails.contentType());

        FileDetails savedFileDetails;
        Map<String, Object> finalResponseMap;

        log.info("Metadata saving process started");
        // Process the uploaded file and generate the response
        if (documentDataMap.containsKey(ID.getContent()) && previousDocId != null) {
            savedFileDetails = createOrUpdateTableData(documentStoragePath, bucketName, headerContext,
                    uploadFileDetails, zippedFileDetails, TRUE);
        } else {
            savedFileDetails = createOrUpdateTableData(documentStoragePath, bucketName, headerContext,
                    uploadFileDetails, zippedFileDetails, FALSE);
        }
        // Update mongo data
        Map<String, Object> transformedDocumentData = documentDataTransformations(documentDataMap);
        finalResponseMap = customMongoService.save(modifyMongoData(transformedDocumentData, zippedFileDetails.documentId()));

        finalResponseMap.put("FileData", generateFileDataResponse(savedFileDetails));
        return generateResponse(HttpStatus.CREATED, DM_DMS_001.getMessage(), DM_DMS_001.name(), finalResponseMap);
    }

    /**
     * Handles the upload of a file from a multipart request.
     *
     * @param request       The HTTP servlet request containing the uploaded file data.
     * @param fileDataDto   The DTO containing file data information.
     * @param previousDocId The previous document ID (if available).
     * @return A {@link ZippedFileDetailsDto} containing information about the uploaded and zipped file.
     */
    private ZippedFileDetailsDto handleMultipartFileUpload(HttpServletRequest request,
                                                           FileDataDto fileDataDto,
                                                           String previousDocId,
                                                           String bucketName,
                                                           String documentPath) throws BadRequestException, DmsException {
        Map<String, MultipartFile> filesMap = ((MultipartHttpServletRequest) request).getFileMap();
        validateFilesPresence(filesMap);
        log.info("Setting up multipart file from the request");
        MultipartFile multipartFile = filesMap.get(fileDataDto.key());
        return fileOperationsService.uploadAndZipToTemporaryPath(multipartFile, fileDataDto, previousDocId, bucketName, documentPath);
    }

    /**
     * Validates the presence of files in the given map.
     *
     * @param filesMap The map containing uploaded files.
     * @throws BadRequestException If no files are available in the request.
     */
    private void validateFilesPresence(Map<String, MultipartFile> filesMap) throws BadRequestException {
        if (filesMap.isEmpty()) {
            log.error("File is not available in the request");
            throw new BadRequestException(REQ_REQUEST_INPUT_FILE_MISSING.getCode(),
                    REQ_REQUEST_INPUT_FILE_MISSING.getDescription());
        } else {
            filesMap.forEach((key, file) -> {
                if (file.isEmpty() || file.getName().isBlank()) {
                    log.error("File '{}' is empty", key);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, REQ_REQUEST_INPUT_FILE_MISSING.getDescription());
                }
            });
        }
    }

    /**
     * Retrieves a document from the MongoDB database by its unique identifier.
     *
     * @param documentId The unique identifier of the document to retrieve.
     * @return A ResponseEntity containing the retrieved document data.
     * @throws DmsException If an error occurs while retrieving the document.
     */
    @Override
    public ResponseEntity<Map<String, Object>> getMongoDoc(String documentId) throws DmsException {
        Map<String, Object> dataToResponse;
        dataToResponse = customMongoService.getDocumentById(documentId);
        return ResponseEntity.ok(dataToResponse);
    }

    /**
     * Finds a document in the MongoDB database by its unique identifier and replaces it with new data.
     *
     * @param documentId   The unique identifier of the document to find and replace.
     * @param newMongoData The new data to replace the existing document data.
     * @return A ResponseEntity containing the updated document data.
     * @throws DmsException If an error occurs while finding and replacing the document.
     */
    @Override
    public ResponseEntity<Map<String, Object>> findAndReplaceDoc(String documentId, String newMongoData) throws DmsException, JsonProcessingException {
        Map<String, Object> dataToResponse;
        Map transformedMongoData = objectMapper.getMapFromJsonString(newMongoData);
        dataToResponse = customMongoService.findAndReplaceDocument(documentId, transformedMongoData);
        return ResponseEntity.ok(dataToResponse);
    }

    /**
     * Generates a list of file detail ids
     *
     * @param savedFileDetails The FileDetails object containing file-related information.
     * @return A list of file detail ids
     */
    public List<String> generateFileDataResponse(FileDetails savedFileDetails) {

        return List.of(savedFileDetails.getId());
    }

    /**
     * Creates a new bucket in the document storage service if it does not already exist.
     *
     * @param bucketName The name of the bucket to create.
     */
    public void createBucketIfNotExists(String bucketName) {
        if (Boolean.FALSE.equals(documentStorageService.isBucketExist(bucketName))) {
            log.info("Bucket {} does not exists, creating new one", bucketName);
            documentStorageService.createBucket(bucketName);
        }
    }


    /**
     * Creates the document storage path based on the provided document data.
     *
     * @param documentData A map containing document-related data, including "Category" and "Clinic" values.
     * @return The generated document storage path.
     */
    public String createDocumentStoragePath(Map<String, Object> documentData) {
        String category = (String) documentData.getOrDefault("Category", "");
        String clinic = (String) documentData.getOrDefault("Clinic", "");

        category = "N/A".equalsIgnoreCase(category) ? "" : category;

        String path;
        if ("Medical Records".equals(category) && !"N/A".equalsIgnoreCase(clinic) && !clinic.isBlank()) {
            path = "/" + category + "/" + clinic;
        } else if (!"N/A".equalsIgnoreCase(category) && !category.isBlank()) {
            path = "/" + category.replaceAll("\\s+", "");
        } else {
            path = "/";
        }

        return path.replace(" ", "");
    }

    /**
     * Creates or updates table data related to a file, including directory details, document details, and file details.
     *
     * @param path              The path where the file is stored.
     * @param bucket            The name of the bucket where the file is stored.
     * @param headerContext     The context information from the HTTP request header.
     * @param file              Upload file details including file name, index, and file type.
     * @param zippedFileDetails Zipped file details including document ID and file hash.
     * @param isUpdate          A boolean indicating whether to update an existing file or create a new one.
     *                          If {@code true}, the existing file will be updated.
     *                          If {@code false}, a new file will be created.
     * @return The file details of the created or updated file.
     */
    @Transactional
    public FileDetails createOrUpdateTableData(String path,
                                               String bucket,
                                               HeaderContext headerContext,
                                               UploadFileDetails file,
                                               ZippedFileDetailsDto zippedFileDetails,
                                               Boolean isUpdate) throws DmsException {

        Optional<Long> directoryId = directoryDetailsService.checkDirectoryExist(path, bucket, headerContext);
        String documentId = zippedFileDetails.documentId();
        String fileHash = zippedFileDetails.fileHash();

        DirectoryDetails directoryDetails = directoryId.isPresent()
                ? directoryDetailsService.getDirectoryById(directoryId.get())
                .or(() ->
                        Optional.ofNullable(directoryDetailsService.createDirectory(path, bucket, headerContext)))
                .orElseThrow(() -> new DmsException(DB_DATA_RETRIEVAL_ERROR.getCode(), DB_DATA_RETRIEVAL_ERROR.getDescription()))
                : directoryDetailsService.createDirectory(path, bucket, headerContext);

        if (Boolean.TRUE.equals(isUpdate)) {
            documentDetailsRepository.updateDocumentDetailsById(documentId, directoryDetails.getId(), documentId);
            fileDetailsRepository.deleteFileDetailsByDocumentId(documentId);
        } else {
            documentDetailsService.createDocumentDetails(directoryDetails.getId(), documentId);
        }

        return createFileDetails(fileHash, documentId, file.fileName(), (long) file.index(),
                extractFileFormatFromMimeType(file.fileType()));
    }

    /**
     * Modifies the provided MongoDB document data by removing the original document ID and replacing it with a new one.
     *
     * @param mongoData  The original MongoDB document data to be modified.
     * @param documentId The new document ID to replace the original one.
     * @return A modified map containing the updated document data with the new document ID.
     */
    public Map<String, Object> modifyMongoData(Map<String, Object> mongoData, String documentId) {
        // Create a copy of the original MongoDB document data
        Map<String, Object> modifiedMongoData = new HashMap<>(mongoData);

        // Remove the original document ID and add the new document ID
        modifiedMongoData.remove(ID.getContent());
        modifiedMongoData.put("_id", documentId);

        return modifiedMongoData;
    }

    /**
     * Extracts the file format (e.g., "pdf", "jpg") from the provided MIME type.
     *
     * @param mimeType The MIME type from which to extract the file format.
     * @return The extracted file format or an empty string if the MIME type is null.
     */
    public String extractFileFormatFromMimeType(String mimeType) {
        return Optional.ofNullable(mimeType)
                .map(type -> type.substring(type.lastIndexOf('/') + 1))
                .orElse("");
    }

    /**
     * Creates and saves a new FileDetails entity with the provided attributes.
     *
     * @param fileHash   The hash value associated with the file.
     * @param documentId The unique identifier for the document.
     * @param fileName   The name of the file.
     * @param seq        The sequence number of the file.
     * @param extension  The file extension (e.g., "pdf", "jpg").
     * @return The created FileDetails entity after saving it in the repository.
     */
    private FileDetails createFileDetails(String fileHash, String documentId, String fileName, Long seq, String extension) {
        FileDetails fileDetails = FileDetails.builder()
                .id(fileHash)
                .documentId(documentId)
                .fileName(fileName)
                .seq(seq)
                .status(true)
                .extension(extension)
                .build();

        return fileDetailsRepository.save(fileDetails);
    }

    /**
     * Transforms selected fields in the provided document data into a new map, converting their values to strings.
     *
     * @param documentData The original document data represented as a map.
     * @return A new map containing the selected fields with their values converted to strings.
     */
    private Map<String, Object> documentDataTransformations(Map<String, Object> documentData) {
        List<String> keysToTransform = Arrays.asList("Clinic", "AppointmentNumber", "Hospital", "UploaderName");

        documentData.replaceAll((key, value) -> {
            if (value == null) {
                return "N/A";
            }

            if (keysToTransform.contains(key)) {
                return value.toString();
            } else {
                return value;
            }
        });

        return documentData;
    }

    /**
     * Creates an UploadFileDetails object with information about an uploaded file.
     *
     * @param fileDataDto The FileDataDto containing information about the file.
     * @param minioPath   The path where the file is stored in the storage service (e.g., MinIO).
     * @param contentType The content type (MIME type) of the uploaded file.
     * @return An UploadFileDetails object with details about the uploaded file.
     */
    public UploadFileDetails getUploadFileDetails(FileDataDto fileDataDto, String minioPath, String contentType) {
        return UploadFileDetails.builder()
                .key(fileDataDto.key())
                .fileName(fileDataDto.name())
                .fileType(contentType)
                .path(minioPath)
                .index(fileDataDto.index()).build();
    }
}





