package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

@Builder
public record UploadFileDetails(String key,
                                String fileName,
                                String fileType,
                                String path,
                                int index) {
}
