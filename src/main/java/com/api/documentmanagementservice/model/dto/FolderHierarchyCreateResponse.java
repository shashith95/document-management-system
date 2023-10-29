package com.api.documentmanagementservice.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record FolderHierarchyCreateResponse(
        List<Long> folderHierarchy,
        Long id,
        LocalDateTime createdDateTime,
        LocalDateTime updatedDateTime,
        String tenantId,
        String hospitalId,
        String user,
        Boolean isCustom
) {
}
