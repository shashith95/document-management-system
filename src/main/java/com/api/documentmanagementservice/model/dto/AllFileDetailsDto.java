package com.api.documentmanagementservice.model.dto;

import java.time.LocalDateTime;

public record AllFileDetailsDto(
        Long id,
        String name,
        Boolean isDeletable,
        Boolean isMandatory,
        Boolean isFreeText,
        Boolean isAddable,
        Integer propertyType,
        String tenantId,
        String hospitalId,
        Boolean hierarchyAvailable,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime,
        String createdUser,
        Boolean status,
        Long defaultAttributeId,
        Long attributeId,
        Long attributePropertyId,
        String attributeName,
        Boolean attributeIsDeletable,
        String attributeHospitalId,
        String attributeTenantId,
        String attributeCreatedUser,
        LocalDateTime attributeCreatedDateTime,
        LocalDateTime attributeUpdatedDateTime
) {
}
