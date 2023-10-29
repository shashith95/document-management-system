package com.api.documentmanagementservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PropertyRenameRequest(
        @NotBlank(message = "conditions cannot be null or empty") String conditions,
        @NotBlank(message = "updatedProperty cannot be null or empty") String updatedProperty
) {
}
