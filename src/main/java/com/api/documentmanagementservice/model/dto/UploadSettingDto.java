package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UploadSettingDto(
        Long id,
        Float maxFileSize,
        Integer fileSizeUnit,
        Boolean isSizeLimited,
        String fileTypes,
        String fileNamingFormat,
        String hospitalId,
        String tenantId,
        String createdUser,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime
) {
}
