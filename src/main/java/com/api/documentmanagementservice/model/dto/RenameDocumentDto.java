package com.api.documentmanagementservice.model.dto;

import jakarta.validation.constraints.NotBlank;

public record RenameDocumentDto(
        @NotBlank(message = "ID cannot be blank")
        String id,

        @NotBlank(message = "Document name cannot be blank")
        String documentName
) {
}
