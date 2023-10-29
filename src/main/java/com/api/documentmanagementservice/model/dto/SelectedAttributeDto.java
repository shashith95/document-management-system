package com.api.documentmanagementservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record SelectedAttributeDto(
        @Positive(message = "id must be a positive value") Long id,
        @NotBlank(message = "name cannot be blank") String name
) {
}
