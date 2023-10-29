package com.api.documentmanagementservice.model.dto.request;

public record UserPreferenceUpdateRequest(
        Long id,
        String preferenceType,
        Long preference
) {

}
