package com.api.documentmanagementservice.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ScanComponentSelectedPropertyDto(

        @Positive(message = "id must be a positive value")
        Long id,

        @NotBlank(message = "name cannot be blank")
        String name,

        Boolean isMandatory,
        Boolean isFreeText,
        Boolean isAddable,

        @Min(value = 0, message = "freeTextType must be a non-negative integer")
        Long freeTextType,
        Long defaultAttributeId,
        @Valid
        List<SelectedAttributeDto> selectedAttributes
) {
}
