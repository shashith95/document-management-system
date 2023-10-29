package com.api.documentmanagementservice.model.dto;


import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FilePropertyWithAttributesRecord(
        Long id,
        String name,
        Boolean isDeletable,
        Boolean isMandatory,
        Boolean isFreeText,
        Boolean isAddable,
        Boolean hierarchyAvailable,
        Integer propertyType,
        Long defaultAttributeId,
        String createdUser,
        String hospitalId,
        String tenantId,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime,
        List<PropertyAttributeRecord> attributes) {

}
