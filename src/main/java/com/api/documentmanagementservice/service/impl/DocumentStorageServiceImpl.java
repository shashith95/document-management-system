package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.service.DocumentStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class DocumentStorageServiceImpl implements DocumentStorageService {
    private final MinioServiceImpl minioServiceImpl;

    @Override
    public InputStream downloadDocument(String bucketName, String filePath) throws DmsException {
        return minioServiceImpl.retrieveDocument(bucketName, filePath);
    }

    @Override
    public void uploadDocument(String bucketName, String filePath, InputStream documentStream, File tempFile) throws DmsException {
        minioServiceImpl.uploadDocument(bucketName, filePath, documentStream, tempFile);
    }

    @Override
    public Boolean isBucketExist(String bucketName) {
        return minioServiceImpl.isBucketExist(bucketName);
    }

    @Override
    public void createBucket(String bucketName) {
        minioServiceImpl.createBucket(bucketName);
    }
}
