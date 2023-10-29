package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PropertyAttributeRecord(
        Long id,
        String name,
        Long propertyId,
        Boolean isDeletable,
        String createdUser,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime
) {
}
