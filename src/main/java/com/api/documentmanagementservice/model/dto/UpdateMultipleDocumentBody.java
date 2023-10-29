package com.api.documentmanagementservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UpdateMultipleDocumentBody(
        @NotEmpty String[] id,

        @NotBlank String tenant,

        String deleteReason
) {
}
