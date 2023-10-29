package com.api.documentmanagementservice.model.dto;

import java.util.List;
import java.util.Map;

public record BranchesResult(
        List<String> properties,
        List<Map<String, Object>> conditions) {
}
