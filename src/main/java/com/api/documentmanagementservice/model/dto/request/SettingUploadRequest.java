package com.api.documentmanagementservice.model.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Optional;

public record SettingUploadRequest(

        @Nullable Optional<Long> id,
        @Positive(message = "maxFileSize must be a positive value") Float maxFileSize,
        @Positive(message = "fileSizeUnit must be a positive value") Integer fileSizeUnit,
        @NotNull(message = "isSizeLimited cannot be null") Boolean isSizeLimited,
        @NotBlank(message = "fileTypes cannot be empty") String fileTypes,
        @NotBlank(message = "fileNamingFormat cannot be empty") String fileNamingFormat
) {
}
