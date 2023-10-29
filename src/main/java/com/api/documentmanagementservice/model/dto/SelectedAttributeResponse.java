package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

@Builder
public record SelectedAttributeResponse(
        Long id,
        String name
) {
}
