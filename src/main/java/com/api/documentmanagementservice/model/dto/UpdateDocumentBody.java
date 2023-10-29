package com.api.documentmanagementservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateDocumentBody(
        @NotBlank(message = "ID cannot be blank")
        String id,

        @NotBlank(message = "Tenant cannot be blank")
        String tenant,

        String deleteReason
) {

}
