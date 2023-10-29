package com.api.documentmanagementservice.model.dto.request;

import com.api.documentmanagementservice.model.dto.PropertyAttributeRecord;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public record FilePropertyRequest(
        Optional<Long> id,
        @NotBlank(message = "name property cannot be null or empty") String name,
        @NotNull(message = "isDeletable property cannot be null") Boolean isDeletable,
        @NotNull(message = "isMandatory property cannot be null") Boolean isMandatory,
        @NotNull(message = "isFreeText property cannot be null") Boolean isFreeText,
        @NotNull(message = "isAddable property cannot be null") Boolean isAddable,
        @NotNull(message = "hierarchyAvailable property cannot be null") Boolean hierarchyAvailable,
        @NotNull(message = "propertyType property cannot be null") Integer propertyType,
        @NotNull(message = "propertyAttributeRecordList property cannot be null")
        Optional<List<PropertyAttributeRecord>> attributes
) {
}
