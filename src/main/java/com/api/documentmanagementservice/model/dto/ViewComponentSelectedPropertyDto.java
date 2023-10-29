package com.api.documentmanagementservice.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.List;

@Builder
public record ViewComponentSelectedPropertyDto(
        @Positive(message = "id must be a positive value") Long id,
        @NotBlank(message = "name cannot be blank") String name,
        @Valid @NotEmpty(message = "selectedAttributes cannot be empty") List<SelectedAttributeDto> selectedAttributes
) {
}
