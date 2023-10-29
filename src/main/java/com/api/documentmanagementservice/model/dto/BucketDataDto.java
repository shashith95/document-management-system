package com.api.documentmanagementservice.model.dto;

public record BucketDataDto(String bucketName,
                            String path,
                            String documentId,
                            String fileName) {
}
