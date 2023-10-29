package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceRequest;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceUpdateRequest;
import com.api.documentmanagementservice.model.table.UserPreference;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserPreferenceService {
    ResponseEntity<CommonResponse> createUserPreference(UserPreferenceRequest userPreferenceRequest);

    ResponseEntity<CommonResponse> updateUserPreference(UserPreferenceUpdateRequest userPreferenceUpdateRequest) throws DmsException;

    ResponseEntity<CommonResponse> getUserPreferenceType(String userPreferenceType);

    Optional<UserPreference> isUserPreferenceExist(String userPreferenceType);
}
