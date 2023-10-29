package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SelectedViewPropertyResponse(
        Long id,
        String name,
        List<SelectedAttributeResponse> attributes
) {
}
