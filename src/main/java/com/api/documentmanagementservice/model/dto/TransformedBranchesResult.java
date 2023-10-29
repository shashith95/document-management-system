package com.api.documentmanagementservice.model.dto;

import java.util.List;
import java.util.Map;

public record TransformedBranchesResult(Object item, List<Map<String, Object>> children) {
}
