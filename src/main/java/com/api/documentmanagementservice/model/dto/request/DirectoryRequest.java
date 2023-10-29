package com.api.documentmanagementservice.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DirectoryRequest(
        @JsonProperty("ClusterMRN") @NotBlank(message = "ClusterMRN cannot be null or empty") String clusterMRN,
        @JsonProperty("Tenant") @NotBlank(message = "Tenant cannot be null or empty") String tenant,
        @JsonProperty("Status") @NotNull(message = "Status cannot be null") Boolean status) {
}
