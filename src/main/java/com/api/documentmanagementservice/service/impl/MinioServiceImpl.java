package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.api.documentmanagementservice.commons.constant.ErrorCode.FILE_UPLOAD_DMS_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl {
    private final MinioClient minioClient;

    /**
     * Retrieves a document from MinIO.
     *
     * @param bucketName The name of the bucket.
     * @param filePath   The path to the file in the bucket.
     * @return An InputStream for the retrieved document.
     * @throws RuntimeException if an error occurs during retrieval.
     */
    public InputStream retrieveDocument(String bucketName, String filePath) throws DmsException {
        try {
            return minioClient.getObject(bucketName, filePath);
        } catch (Exception e) {
            log.error("Error occurred while retrieving file from Minio", e);
            throw new DmsException(FILE_UPLOAD_DMS_ERROR.getCode(), e.getMessage());
        }

    }

    /**
     * Uploads a document to MinIO.
     *
     * @param bucketName     The name of the bucket.
     * @param filePath       The path to the file in the bucket.
     * @param documentStream The InputStream of the document to upload.
     * @param tempFile       The temporary file associated with the document.
     */
    public void uploadDocument(String bucketName, String filePath, InputStream documentStream, File tempFile) throws DmsException {
        try (documentStream) {
            minioClient.putObject(bucketName, filePath, tempFile.getPath());
        } catch (Exception e) {
            log.error("Error occurred while uploading file to Minio, bucketName: {}, filePath: {}", bucketName, filePath, e);
            throw new DmsException(FILE_UPLOAD_DMS_ERROR.getCode(), e.getMessage());
        }
    }

    /**
     * Checks if a bucket exists in MinIO.
     *
     * @param bucketName The name of the bucket.
     * @return true if the bucket exists, false otherwise.
     */
    public Boolean isBucketExist(String bucketName) {
        try {
            return minioClient.bucketExists(bucketName);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException |
                 XmlPullParserException e) {
            log.error("Error occurred while checking bucket exists", e);
            return false;
        }
    }

    /**
     * Creates a new bucket in MinIO.
     *
     * @param bucketName The name of the bucket to create.
     * @throws ResponseStatusException if the bucket name is invalid.
     */
    public void createBucket(String bucketName) {
        try {
            minioClient.makeBucket(bucketName);
            log.info("Bucket created successfully: {}", bucketName);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException |
                 XmlPullParserException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
