package com.api.documentmanagementservice.commons.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HeaderContent {
    TENANT_ID("x-group"),
    HOSPITAL_ID("x-hospital"),
    USER("x-user");

    private final String fieldName;
}
