package com.api.documentmanagementservice.model.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Optional;

public record ScannerSettingRequest(
        @Nullable Optional<Long> id,
        @NotBlank(message = "name cannot be blank") String name,
        @NotBlank(message = "scannerName cannot be blank") String scannerName,
        @PositiveOrZero(message = "qualityId must be a positive value") Integer qualityId,
        @NotBlank(message = "quality cannot be blank") String quality,
        @NotNull(message = "isDefaultProfile cannot be null") Boolean isDefaultProfile,
        @PositiveOrZero(message = "resolution must be a positive value") Integer resolution,
        @NotBlank(message = "pageSize cannot be blank") String pageSize,
        @NotBlank(message = "pageSizeName cannot be blank") String pageSizeName,
        @PositiveOrZero(message = "rotate must be a non-negative value") Integer rotate,
        @PositiveOrZero(message = "dpi must be a positive value") Integer dpi,
        @NotBlank(message = "pixelType cannot be blank") String pixelType,
        @NotBlank(message = "pixelTypeName cannot be blank") String pixelTypeName
) {
}
