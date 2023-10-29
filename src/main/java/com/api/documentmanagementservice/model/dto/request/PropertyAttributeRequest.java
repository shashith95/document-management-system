package com.api.documentmanagementservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PropertyAttributeRequest(
        @NotBlank(message = "Name cannot be empty")
        String name,
        @NotNull(message = "Property id cannot be null")
        Long propertyId,
        @NotNull(message = "isDeletable cannot be null")
        Boolean isDeletable
) {
}
