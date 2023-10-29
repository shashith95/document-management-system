package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

@Builder
public record ZippedFileDetailsDto(
        String fileName,
        String fileHash,
        String documentId,
        String filePath,
        String contentType
) {
}
