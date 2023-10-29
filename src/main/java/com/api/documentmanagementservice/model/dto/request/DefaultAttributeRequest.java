package com.api.documentmanagementservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record DefaultAttributeRequest(
        @PositiveOrZero(message = "Component Id should be a positive value")
        @JsonProperty(defaultValue = "0")
        Long componentId,
        @NotNull(message = "Property id cannot be null")
        Long propertyId,
        @NotNull(message = "Default attribute id cannot be null")
        Long defaultAttributeId
) {
}
