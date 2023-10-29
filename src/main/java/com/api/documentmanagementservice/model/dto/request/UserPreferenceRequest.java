package com.api.documentmanagementservice.model.dto.request;

public record UserPreferenceRequest(
        String preferenceType,
        Long preference
) {

}
