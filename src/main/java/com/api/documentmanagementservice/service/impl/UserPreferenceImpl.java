package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceRequest;
import com.api.documentmanagementservice.model.dto.request.UserPreferenceUpdateRequest;
import com.api.documentmanagementservice.model.table.UserPreference;
import com.api.documentmanagementservice.repository.UserPreferenceRepository;
import com.api.documentmanagementservice.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_DATA_RETRIEVAL_ERROR;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserPreferenceImpl implements UserPreferenceService {
    private final UserPreferenceRepository userPreferenceRepository;
    private final HeaderContext headerContext;

    /**
     * Creates a new user preference based on the provided UserPreferenceRequest.
     *
     * @param userPreferenceRequest The request containing the user preference details to be created.
     * @return A ResponseEntity indicating the successful creation of the user preference.
     * @throws ResponseStatusException If a user preference of the same type already exists.
     */
    @Override
    public ResponseEntity<CommonResponse> createUserPreference(UserPreferenceRequest userPreferenceRequest) {
        if (isUserPreferenceExist(userPreferenceRequest.preferenceType()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User preference already exist");

        UserPreference userPreference = UserPreference.builder()
                .user(headerContext.getUser())
                .preference(userPreferenceRequest.preference())
                .preferenceType(userPreferenceRequest.preferenceType())
                .hospitalId(headerContext.getHospitalId())
                .tenantId(headerContext.getTenantId())
                .createdDateTime(LocalDateTime.now(ZoneId.of("UTC")))
                .updatedDateTime(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        UserPreference savedUserPreference = userPreferenceRepository.save(userPreference);
        return generateResponse(HttpStatus.CREATED, CREATED.getMessage(), DM_DMS_001.name(), savedUserPreference);
    }

    /**
     * Updates a user preference based on the provided UserPreferenceUpdateRequest.
     *
     * @param userPreferenceUpdateRequest The request containing the updated user preference details.
     * @return A ResponseEntity indicating the success of the update.
     */
    @Override
    public ResponseEntity<CommonResponse> updateUserPreference(UserPreferenceUpdateRequest userPreferenceUpdateRequest)
            throws DmsException {
        Optional<UserPreference> userPreference = userPreferenceRepository.findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(
                userPreferenceUpdateRequest.id(),
                headerContext.getUser(),
                userPreferenceUpdateRequest.preferenceType(), headerContext.getHospitalId(), headerContext.getTenantId());

        UserPreference updatedUserPreference = userPreference.orElseThrow(() ->
                new DmsException(DB_DATA_RETRIEVAL_ERROR.getCode(), "Error occurred while retrieving folder hierarchy details"));

        updatedUserPreference = updatedUserPreference.toBuilder()
                .preference(userPreferenceUpdateRequest.preference())
                .user(headerContext.getUser())
                .updatedDateTime(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        UserPreference updatedUserPreferenceRow = userPreferenceRepository.save(updatedUserPreference);
        return generateResponse(HttpStatus.OK, UPDATED.getMessage(), DM_DMS_002.name(), "Id : " + updatedUserPreferenceRow.getId() + " updated");
    }

    /**
     * Retrieves the user preference of the specified type for the current user, hospital, and tenant.
     *
     * @param userPreferenceType The type of user preference to retrieve.
     * @return A ResponseEntity containing the user preference if it exists, or a NO_CONTENT response if not found.
     */
    @Override
    public ResponseEntity<CommonResponse> getUserPreferenceType(String userPreferenceType) {

        Optional<UserPreference> userPreference = isUserPreferenceExist(userPreferenceType);
        if (userPreference.isPresent())
            return generateResponse(HttpStatus.OK, SUCCESS.getMessage(), DM_DMS_009.name(), userPreference);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No user preference found for given type");
    }

    /**
     * Checks if a user preference with the specified type exists for the current user, hospital, and tenant.
     *
     * @param userPreferenceType The type of user preference to check for.
     * @return An Optional containing the UserPreference if it exists, or an empty Optional otherwise.
     * @throws RuntimeException If one or more header values are empty or if an unexpected error occurs.
     */
    public Optional<UserPreference> isUserPreferenceExist(String userPreferenceType) {

        return userPreferenceRepository.findByUserAndPreferenceTypeAndHospitalIdAndTenantId(
                headerContext.getUser(),
                userPreferenceType,
                headerContext.getHospitalId(),
                headerContext.getTenantId());
    }
}
