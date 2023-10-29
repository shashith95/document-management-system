package com.api.documentmanagementservice.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Optional;

public record ViewComponentSettingDto(
        @Nullable Optional<Long> id,
        @Positive(message = "component must be a positive value") Long component,
        @NotEmpty(message = "selectedProperties cannot be empty") List<ViewComponentSelectedPropertyDto> selectedProperties
) {
}
