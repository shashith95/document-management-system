package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ViewComponentSettingsResponse(
        Long id,
        Long component,
        String hospitalId,
        String tenantId,
        String createdUser,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime,
        List<SelectedViewPropertyResponse> selectedProperties
) {
}
