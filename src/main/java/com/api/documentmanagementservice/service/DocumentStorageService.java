package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.DmsException;

import java.io.File;
import java.io.InputStream;

public interface DocumentStorageService {
    InputStream downloadDocument(String bucketName, String filePath) throws DmsException;

    void uploadDocument(String bucketName, String filePath, InputStream documentStream, File tempFile) throws DmsException;

    Boolean isBucketExist(String bucketName);

    void createBucket(String bucketName);
}
