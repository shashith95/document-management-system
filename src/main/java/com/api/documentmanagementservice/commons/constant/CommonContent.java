package com.api.documentmanagementservice.commons.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonContent {
    TENANT("Tenant"),
    STATUS("Status"),
    HOSPITAL("Hospital"),
    DELETE_REASON("DeleteReason"),
    DOCUMENT("DOCUMENT"),
    ID("_id"),
    CLUSTER_MRN("ClusterMRN");

    private final String content;
}
