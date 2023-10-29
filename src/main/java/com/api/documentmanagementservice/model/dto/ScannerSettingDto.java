package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScannerSettingDto(
        Long id,
        String name,
        String scannerName,
        Integer qualityId,
        String quality,
        Boolean isDefaultProfile,
        Integer resolution,
        String pageSize,
        String pageSizeName,
        Integer rotate,
        Integer dpi,
        String pixelType,
        String pixelTypeName,
        String hospitalId,
        String tenantId,
        String createdUser,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime
) {
}
