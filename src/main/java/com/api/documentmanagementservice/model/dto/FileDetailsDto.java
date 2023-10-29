package com.api.documentmanagementservice.model.dto;

public record FileDetailsDto(
        String id,
        String fileName,
        String extension,
        Integer index
) {
}
