package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.service.ScannerSettingService;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.ScannerSettingRequest;
import com.api.documentmanagementservice.model.table.ScannerSetting;
import com.api.documentmanagementservice.repository.ScannerSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_DUPLICATE_ENTRY;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_NO_DATA;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.*;
import static com.api.documentmanagementservice.commons.mapper.CommonDataMapper.getScannerSettingResponse;
import static com.api.documentmanagementservice.model.table.ScannerSetting.generateScannerSettingObject;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScannerSettingImpl implements ScannerSettingService {
    private final ScannerSettingRepository scannerSettingRepository;
    private final HeaderContext headerContext;

    /**
     * Creates or updates a scanner setting based on the provided request.
     *
     * @param scannerSettingRequest The scanner setting details to be created or updated.
     * @return A ResponseEntity containing the response status and message.
     * @If one or more header values are empty.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createOrUpdateScannerSetting(ScannerSettingRequest scannerSettingRequest)
            throws DmsException, BadRequestException {
        log.info("Creating or updating scanner setting...");

        // Check if scanner settings already exist for the hospital and tenant
        if (scannerSettingRequest.id().isEmpty() && scannerSettingRepository.existsByNameAndHospitalIdAndTenantId(scannerSettingRequest.name(),
                headerContext.getHospitalId(), headerContext.getTenantId())) {
            log.warn("Scanner profile already exists for Hospital ID: {} and Tenant ID: {}",
                    headerContext.getHospitalId(), headerContext.getTenantId());
            throw new BadRequestException(DB_DUPLICATE_ENTRY.getCode(), SCANNER_PROFILE_EXISTS.getMessage());
        }

        ScannerSetting savedScannerSetting = scannerSettingRepository
                .save(getScannerSettingSaveObject(scannerSettingRequest, headerContext));

        if (scannerSettingRequest.id().isEmpty()) {
            log.info("Scanner profile created successfully. Scanner Setting ID: {}", savedScannerSetting.getId());
            return generateResponse(HttpStatus.CREATED, CREATED.getMessage(), DM_DMS_001.name(),
                    getScannerSettingResponse(savedScannerSetting));
        }
        log.info("Scanner profile updated successfully. Scanner Setting ID: {}", savedScannerSetting.getId());
        return generateResponse(HttpStatus.OK, UPDATED.getMessage(), DM_DMS_002.name(),
                "Id : " + savedScannerSetting.getId() + " updated");
    }

    /**
     * Generates a ScannerSetting object for saving or updating based on the provided parameters.
     *
     * @param request The ScannerSettingRequest containing the data for the ScannerSetting object.
     * @param header  The HeaderValues containing contextual information.
     * @return The generated ScannerSetting object for saving or updating.
     */
    private ScannerSetting getScannerSettingSaveObject(ScannerSettingRequest request,
                                                       HeaderContext header) throws DmsException {
        if (request.id().isEmpty()) {
            return generateScannerSettingObject(request, header, null, TRUE);
        }

        log.info("Updating scanner setting for ID: {}", request.id());
        ScannerSetting existingScannerSetting = scannerSettingRepository.findById(request.id().get())
                .orElseThrow(() -> new DmsException(DB_NO_DATA.getCode(), "Didn't find any Scanner profiles to update"));

        boolean updateNameExists = scannerSettingRepository.existsByNameAndHospitalIdAndTenantIdAndIdNot(
                request.name(), header.getHospitalId(), header.getTenantId(), request.id().get());

        if (updateNameExists) {
            throw new DmsException(DB_DUPLICATE_ENTRY.getCode(),
                    "Scanner profile name for update already exists in a different profile");
        }

        if (request.isDefaultProfile()) {
            unsetScannerDefault(header);
        }

        return generateScannerSettingObject(request, header, existingScannerSetting, FALSE);
    }

    /**
     * Unsets the default profile status for all ScannerSetting objects associated with a specific hospital and tenant,
     * as indicated by the provided HeaderValues.
     *
     * @param headerContext The HeaderValues containing contextual information, including hospitalId and tenantId.
     */
    private void unsetScannerDefault(HeaderContext headerContext) {
        List<ScannerSetting> scannerSettings = scannerSettingRepository
                .findAllByHospitalIdAndTenantIdAndIsDefaultProfile(headerContext.getHospitalId(), headerContext.getTenantId(), TRUE)
                .orElse(new ArrayList<>());

        scannerSettings.forEach(scannerSetting -> scannerSetting.setIsDefaultProfile(FALSE));

        scannerSettingRepository.saveAll(scannerSettings);
    }

    /**
     * Retrieves scanner settings based on the provided headers.
     *
     * @return ResponseEntity containing the scanner settings response.
     */
    @Override
    public ResponseEntity<CommonResponse> getScannerSetting() {
        Optional<List<ScannerSetting>> scannerSettingList = scannerSettingRepository
                .findAllByHospitalIdAndTenantId(headerContext.getHospitalId(), headerContext.getTenantId());

        return scannerSettingList
                .map(scannerSettings ->
                        generateResponse(HttpStatus.OK, SUCCESS.getMessage(), DM_DMS_009.name(), getScannerSettingResponse(scannerSettings)))
                .orElseGet(() -> {
                    log.warn("Scanner profile not found for Hospital ID: {} and Tenant ID: {}",
                            headerContext.getHospitalId(), headerContext.getTenantId());
                    return generateResponse(HttpStatus.NOT_FOUND, SCANNER_PROFILE_NOT_FOUND.getMessage(), DM_DMS_032.name());
                });
    }

    /**
     * Deletes a scanner profile by its ID.
     *
     * @param profileId The ID of the scanner profile to be deleted.
     * @return A ResponseEntity containing the response status and message.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteScannerProfileById(Long profileId) {
        log.info("Deleting scanner profile with ID: {}", profileId);

        try {
            scannerSettingRepository.deleteById(profileId);
            log.info("Scanner profile with ID {} deleted successfully.", profileId);
        } catch (Exception e) {
            log.error("An error occurred while deleting scanner profile with ID {}: {}", profileId, e.getMessage());
            return generateResponse(HttpStatus.CONFLICT, SCANNER_PROFILE_NOT_FOUND.getMessage(), DM_DMS_032.name());
        }

        return generateResponse(HttpStatus.OK, DELETED.getMessage(), DM_DMS_032.name());
    }
}
