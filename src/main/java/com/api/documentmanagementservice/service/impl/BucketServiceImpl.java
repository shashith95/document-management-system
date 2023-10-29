package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.config.FileProperties;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.BucketDto;
import com.api.documentmanagementservice.model.table.BucketDetails;
import com.api.documentmanagementservice.repository.BucketDetailsRepository;
import com.api.documentmanagementservice.service.BucketService;
import com.api.documentmanagementservice.service.DocumentStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.api.documentmanagementservice.commons.constant.ErrorCode.REQ_INVALID_INPUT_FORMAT;

@Slf4j
@Service
@RequiredArgsConstructor
public class BucketServiceImpl implements BucketService {
    private final FileProperties fileProperties;
    private final BucketDetailsRepository bucketDetailsRepository;
    private final HeaderContext headerContext;
    private final DocumentStorageService documentStorageService;

    @Override
    public boolean isBucketExist(String bucketName) {
        Optional<BucketDetails> bucketDetailsOptional = bucketDetailsRepository.findByName(bucketName);
        return bucketDetailsOptional.isPresent() || isBucketExistOnMinio(bucketName);
    }

    @Override
    public String createBucket(BucketDto bucketDTO) throws BadRequestException {

        String tenantId = bucketDTO.getTenantId();
        String hospitalId = bucketDTO.getTenantId();

        if (!bucketDTO.getTenantId().equals(headerContext.getTenantId()))
            throw new BadRequestException(REQ_INVALID_INPUT_FORMAT.getCode(), "Can not get data from a different tenant");

        boolean isBucketCreated = createBucket(tenantId, hospitalId);

        return isBucketCreated ? "Bucket created successfully" : "Failed to create bucket";
    }

    public boolean createBucket(String tenantId, String hospitalId) {

        String bucketName = createBucketName(tenantId, hospitalId);
        if (isBucketExist(bucketName))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bucket already exists");

        boolean isBucketCreatedOnMinio = createBucketOnMinio(bucketName);

        if (isBucketCreatedOnMinio) {

            BucketDetails newBucket = BucketDetails.builder()
                    .name(bucketName)
                    .hospitalId(hospitalId)
                    .tenantId(tenantId)
                    .status(true)
                    .createdDateTime(LocalDateTime.now(ZoneId.of("UTC")))
                    .modifiedDateTime(LocalDateTime.now(ZoneId.of("UTC")))
                    .build();

            BucketDetails savedBucket = bucketDetailsRepository.save(newBucket);

            if (savedBucket == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while Creating bucket details");
            return true;
        } else
            return false;
    }

    public boolean isBucketExistOnMinio(String bucketName) {
        return documentStorageService.isBucketExist(bucketName);
    }

    public boolean createBucketOnMinio(String bucketName) {
        documentStorageService.createBucket(bucketName);
        log.info("Bucket created successfully: " + bucketName);
        return true;
    }

    public String createBucketName(String tenantId, String hospitalId) {
        return fileProperties.getEnvironment().toLowerCase() +
                getPart(tenantId) + getPart(hospitalId);
    }

    private String getPart(String id) {
        return switch (id.length()) {
            case 1 -> "00" + id;
            case 2 -> "0" + id;
            default -> id;
        };
    }
}
