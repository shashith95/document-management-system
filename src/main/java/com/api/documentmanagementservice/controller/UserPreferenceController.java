package com.api.documentmanagementservice.controller;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceRequest;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceUpdateRequest;
import com.api.documentmanagementservice.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/user-preference")
@RequiredArgsConstructor
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @PostMapping("/preference")
    public ResponseEntity<CommonResponse> createUserPreference(@RequestBody UserPreferenceRequest userPreferenceRequest) {
        log.info("Create user preference API {}", userPreferenceRequest);
        return userPreferenceService.createUserPreference(userPreferenceRequest);
    }

    @PutMapping("/preference")
    public ResponseEntity<CommonResponse> updateUserPreference(@RequestBody UserPreferenceUpdateRequest userPreferenceUpdateRequest) throws DmsException {
        log.info("Update user preference API {}", userPreferenceUpdateRequest);
        return userPreferenceService.updateUserPreference(userPreferenceUpdateRequest);
    }

    @GetMapping("/preference")
    public ResponseEntity<CommonResponse> getUserPreferenceType(@RequestParam("userPreferenceType") String userPreferenceType) {
        log.info("Get user preference by type API {}", userPreferenceType);
        return userPreferenceService.getUserPreferenceType(userPreferenceType);
    }
}
