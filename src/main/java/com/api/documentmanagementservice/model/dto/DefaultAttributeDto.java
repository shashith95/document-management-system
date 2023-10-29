package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DefaultAttributeDto(
        Long id,
        Long componentId,
        Long propertyId,
        Long defaultAttributeId,
        String hospitalId,
        String tenantId,
        String createdUser,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime
) {
}
