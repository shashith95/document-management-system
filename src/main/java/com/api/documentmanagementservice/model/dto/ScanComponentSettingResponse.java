package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ScanComponentSettingResponse(
        Long id,
        Long component,
        Long defaultScannerProfileId,
        String hospitalId,
        String tenantId,
        String createdUser,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime,
        List<SelectedScanPropertyResponse> selectedProperties
) {
}
