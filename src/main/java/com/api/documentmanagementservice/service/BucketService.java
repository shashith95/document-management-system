package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.model.dto.BucketDto;

public interface BucketService {

    boolean isBucketExist(String bucketName);

    String createBucket(BucketDto bucketDTO) throws BadRequestException;

    String createBucketName(String tenantId, String hospitalId);
}
