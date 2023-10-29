package com.api.documentmanagementservice.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public record FolderHierarchyRequest(
        Optional<Long> id,
        @NotNull(message = "File hierarchy cannot be null") List<Long> folderHierarchy,
        @NotNull(message = "isCustom cannot be null") Boolean isCustom
) {
}
