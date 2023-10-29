package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SelectedScanPropertyResponse(
        Long id,
        String name,
        Boolean isMandatory,
        Boolean isFreeText,
        Boolean isAddable,
        Long freeTextType,
        Long defaultAttributeId,
        List<SelectedAttributeResponse> attributes
) {
}
