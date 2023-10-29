package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.service.ScanComponentSettingsService;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.ScanComponentSettingResponse;
import com.api.documentmanagementservice.model.dto.SelectedAttributeResponse;
import com.api.documentmanagementservice.model.dto.SelectedScanPropertyResponse;
import com.api.documentmanagementservice.model.dto.request.ScanComponentSettingRequest;
import com.api.documentmanagementservice.model.table.DefaultAttribute;
import com.api.documentmanagementservice.model.table.ScanComponentResource;
import com.api.documentmanagementservice.model.table.ScanComponentSettings;
import com.api.documentmanagementservice.repository.DefaultAttributeRepository;
import com.api.documentmanagementservice.repository.ScanComponentResourceRepository;
import com.api.documentmanagementservice.repository.ScanComponentSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.api.documentmanagementservice.commons.DateTimeUtils.getCurrentTime;
import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_DATA_UPDATE_ERROR;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_NO_DATA;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanComponentSettingsServiceImpl implements ScanComponentSettingsService {
    private final ScanComponentSettingsRepository scanComponentSettingsRepository;
    private final ScanComponentResourceRepository scanComponentResourceRepository;
    private final DefaultAttributeRepository defaultAttributeRepository;
    private final HeaderContext headerContext;

    /**
     * Creates or updates a scan component setting based on the provided request data.
     *
     * @param scanComponentSettingRequest The request data for creating or updating a scan component setting.
     * @return A ResponseEntity containing the result of the create or update operation.
     */
    @Override
    public ResponseEntity<CommonResponse> createOrUpdateScanComponentSettings(ScanComponentSettingRequest scanComponentSettingRequest)
            throws DmsException {
        log.info("Creating or updating view component setting...");

        ScanComponentSettings scanSettingToSave = generateScanComponentSaveObject(
                headerContext, scanComponentSettingRequest, TRUE);

        ScanComponentSettings savedSetting = scanComponentSettingsRepository.save(scanSettingToSave);

        inactivateComponentResources(savedSetting.getId());

        List<ScanComponentResource> scanComponentResourcesListToSave = getScanComponentResourceListToSave(
                savedSetting.getId(),
                scanComponentSettingRequest);
        List<ScanComponentResource> scanComponentResources = scanComponentResourceRepository.saveAll(scanComponentResourcesListToSave);

        if (scanComponentSettingRequest.id().isEmpty()) {
            return generateResponse(HttpStatus.CREATED,
                    DM_DMS_001.getMessage(),
                    DM_DMS_001.name(),
                    generateScanComponentSettingsResponse(savedSetting, scanComponentResources));
        }
        return generateResponse(HttpStatus.OK, DM_DMS_002.getMessage(),
                DM_DMS_002.name(), "Id : " + savedSetting.getId() + " updated");
    }

    private void inactivateComponentResources(Long componentSettingId) throws DmsException {
        try {
            List<ScanComponentResource> scanComponentResources = scanComponentResourceRepository
                    .findAllByComponentSettingIdAndStatus(componentSettingId, TRUE)
                    .orElse(new ArrayList<>());
            scanComponentResources.forEach(scanComponentResource -> scanComponentResource.setStatus(FALSE));
            scanComponentResourceRepository.saveAll(scanComponentResources);
        } catch (Exception e) {
            log.error("Issue occurred while updating data", e);
            throw new DmsException(DB_DATA_UPDATE_ERROR.getCode(), "Issue occurred while updating data");
        }
    }

    /**
     * Retrieves a scan component setting along with its associated resources.
     *
     * @param componentId The ID of the scan component setting to be retrieved.
     * @return A ResponseEntity containing the retrieved scan component setting and its associated resources.
     */
    @Override
    public ResponseEntity<CommonResponse> getScanComponentSettings(Long componentId) throws DmsException {
        log.info("Getting scan component setting...");

        // Get all active components
        ScanComponentSettings scanComponentSetting = scanComponentSettingsRepository
                .findByComponentAndHospitalIdAndTenantIdAndStatus(componentId, headerContext.getHospitalId(),
                        headerContext.getTenantId(), TRUE)
                .orElseGet(() -> {
                    log.warn("Scan component settings are not available for provided component ID: {}", componentId);
                    return null;
                });

        if (scanComponentSetting == null) {
            return generateResponse(HttpStatus.NO_CONTENT, DM_DMS_055.getMessage(), DM_DMS_055.name());
        }

        // Get all active component resources for given component setting ID
        Optional<List<ScanComponentResource>> scanComponentResourceList = scanComponentResourceRepository
                .findAllByComponentSettingIdAndStatus(scanComponentSetting.getId(), TRUE);

        // If no active resources are available return response to client with empty resource
        if (scanComponentResourceList.isEmpty()) {
            log.warn("Scan component resources are not available for provided component setting ID: {}", componentId);
            // return response without scanComponentResourceList
            return generateResponse(HttpStatus.OK, DM_DMS_009.getMessage(), DM_DMS_009.name(),
                    generateScanComponentSettingsResponse(scanComponentSetting, new ArrayList<>()));
        }

        return generateResponse(HttpStatus.OK, DM_DMS_009.getMessage(), DM_DMS_009.name(),
                generateScanComponentSettingsResponse(scanComponentSetting, scanComponentResourceList.get()));
    }

    /**
     * Deletes a scan component setting and associated resources.
     *
     * @param componentId The ID of the scan component setting to be deleted.
     * @return A ResponseEntity containing the result of the delete operation.
     */
    @Override
    public ResponseEntity<CommonResponse> deleteScanComponentSettings(Long componentId) throws DmsException {
        log.info("Deleting scan component setting...");

        // Get all active components
        Optional<ScanComponentSettings> scanComponentSettings = scanComponentSettingsRepository
                .findByComponentAndHospitalIdAndTenantIdAndStatus(componentId, headerContext.getHospitalId(),
                        headerContext.getTenantId(), TRUE);

        // If no active setting is available return response to client and end method execution
        if (scanComponentSettings.isEmpty()) {
            log.warn("Scan component settings are not available for provided component setting ID: {}", componentId);
            return generateResponse(HttpStatus.NOT_FOUND, DM_DMS_055.getMessage(), DM_DMS_055.name());
        }

        inactivateComponentSettings(scanComponentSettings.get());

        // Get all active component resources for given component setting ID
        Optional<List<ScanComponentResource>> scanComponentResourceList = scanComponentResourceRepository
                .findAllByComponentSettingIdAndStatus(componentId, TRUE);

        if (scanComponentResourceList.isEmpty()) {
            log.warn("Scan component resources are not available for provided component setting ID: {}", componentId);
            return generateResponse(HttpStatus.NOT_FOUND, DM_DMS_055.getMessage(), DM_DMS_055.name());
        }

        try {
            defaultAttributeRepository.deleteAllByComponentIdAndHospitalIdAndTenantId(componentId,
                    headerContext.getHospitalId(),
                    headerContext.getTenantId());

            scanComponentResourceList.get().forEach(scanComponentResource -> scanComponentResource.setStatus(FALSE));
            scanComponentResourceRepository.saveAll(scanComponentResourceList.get());

            log.info("Deleting scan component setting completed successfully for ID {}", componentId);
            return generateResponse(HttpStatus.NO_CONTENT, DM_DMS_010.getMessage(), DM_DMS_010.name(),
                    "Id : " + componentId + " deleted");
        } catch (Exception e) {
            log.error("Issue occurred while deleting scanComponentResource", e);
            throw new DmsException(DB_NO_DATA.getCode(), "Issue occurred while deleting scanComponentResource");
        }
    }

    /**
     * Inactivates a ScanComponentSettings by updating its status to false and saving the changes to the repository.
     *
     * @param scanComponentSettings The ScanComponentSettings object to be inactivated.
     */
    private void inactivateComponentSettings(ScanComponentSettings scanComponentSettings) {
        // updating component setting status to false
        scanComponentSettings.setStatus(FALSE);
        scanComponentSettingsRepository.save(scanComponentSettings);
    }

    /**
     * Generates a ScanComponentSettingResponse based on the provided ScanComponentSettings and list of
     * ScanComponentResource objects.
     *
     * @param savedSetting           The ScanComponentSettings object containing saved settings data.
     * @param scanComponentResources The list of ScanComponentResource objects containing property information.
     * @return A ScanComponentSettingResponse containing response data based on the saved settings and associated resources.
     */
    private ScanComponentSettingResponse generateScanComponentSettingsResponse(ScanComponentSettings savedSetting,
                                                                               List<ScanComponentResource> scanComponentResources) {

        List<SelectedScanPropertyResponse> propertyListList = getSelectedScanPropertyResponseList(savedSetting, scanComponentResources);

        return ScanComponentSettingResponse.builder()
                .id(savedSetting.getId())
                .component(savedSetting.getComponent())
                .defaultScannerProfileId(savedSetting.getDefaultScannerProfileId())
                .hospitalId(savedSetting.getHospitalId())
                .tenantId(savedSetting.getTenantId())
                .createdUser(savedSetting.getCreatedUser())
                .createdDateTime(savedSetting.getCreatedDateTime())
                .updatedDateTime(savedSetting.getUpdatedDateTime())
                .selectedProperties(propertyListList)
                .build();
    }

    /**
     * Generates a list of SelectedScanPropertyResponse objects based on the given ScanComponentSettings and
     * list of ScanComponentResource objects.
     *
     * @param savedSetting           The ScanComponentSettings object to use for querying.
     * @param scanComponentResources The list of ScanComponentResource objects containing property information.
     * @return A list of SelectedScanPropertyResponse objects containing selected property information and associated attributes.
     */
    private List<SelectedScanPropertyResponse> getSelectedScanPropertyResponseList(ScanComponentSettings savedSetting,
                                                                                   List<ScanComponentResource> scanComponentResources) {
        return scanComponentResources
                .stream()
                .collect(Collectors.groupingBy(ScanComponentResource::getPropertyId))
                .values()
                .stream()
                .map(resourceList -> {
                    List<SelectedAttributeResponse> selectedAttributeResponseList = resourceList
                            .stream()
                            .map(resource -> SelectedAttributeResponse.builder()
                                    .id(resource.getAttributeId())
                                    .name(resource.getAttributeName())
                                    .build())
                            .toList();

                    Long defaultAttributeId = getDefaultAttribute(savedSetting, resourceList.get(0))
                            .map(DefaultAttribute::getDefaultAttributeId)
                            .orElse(0L);

                    return SelectedScanPropertyResponse.builder()
                            .id(resourceList.get(0).getPropertyId())
                            .name(resourceList.get(0).getPropertyName())
                            .isMandatory(resourceList.get(0).getIsMandatory())
                            .isFreeText(resourceList.get(0).getIsFreeText())
                            .isAddable(resourceList.get(0).getIsAddable())
                            .freeTextType(resourceList.get(0).getFreeTextType())
                            .defaultAttributeId(defaultAttributeId)
                            .attributes(selectedAttributeResponseList)
                            .build();
                })
                .toList();
    }

    /**
     * Retrieves a DefaultAttribute based on the given ScanComponentSettings and ScanComponentResource.
     *
     * @param savedSetting          The ScanComponentSettings object to use for querying.
     * @param scanComponentResource The ScanComponentResource object containing property information.
     * @return An Optional containing a DefaultAttribute if found, otherwise an empty Optional.
     */
    private Optional<DefaultAttribute> getDefaultAttribute(ScanComponentSettings savedSetting,
                                                           ScanComponentResource scanComponentResource) {
        return defaultAttributeRepository
                .findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(
                        savedSetting.getComponent(),
                        scanComponentResource.getPropertyId(),
                        savedSetting.getHospitalId(),
                        savedSetting.getTenantId()
                )
                .or(() -> defaultAttributeRepository
                        .findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(
                                0L,
                                scanComponentResource.getPropertyId(),
                                savedSetting.getHospitalId(),
                                savedSetting.getTenantId()
                        )
                );
    }

    /**
     * Generates a list of ScanComponentResource objects based on the provided ScanComponentSettingRequest.
     *
     * @param componentSettingId          The ID of the component setting.
     * @param scanComponentSettingRequest The request object containing selected properties and attributes.
     * @return A list of ScanComponentResource objects prepared for saving.
     */
    private List<ScanComponentResource> getScanComponentResourceListToSave(Long componentSettingId,
                                                                           ScanComponentSettingRequest scanComponentSettingRequest) {

        return scanComponentSettingRequest
                .selectedProperties()
                .stream()
                .flatMap(selectedProperty -> selectedProperty
                        .selectedAttributes().stream()
                        .map(selectedAttributeDto ->
                                ScanComponentResource.builder()
                                        .componentSettingId(componentSettingId)
                                        .propertyId(selectedProperty.id())
                                        .propertyName(selectedProperty.name())
                                        .isMandatory(selectedProperty.isMandatory())
                                        .isFreeText(selectedProperty.isFreeText())
                                        .isAddable(selectedProperty.isAddable())
                                        .freeTextType(selectedProperty.freeTextType())
                                        .status(true)
                                        .attributeId(selectedAttributeDto.id())
                                        .attributeName(selectedAttributeDto.name())
                                        .build()))
                .toList();
    }

    /**
     * Generates a ScanComponentSettings object for saving or updating based on the provided inputs.
     *
     * @param headerContext               The header values containing user, hospitalId, and tenantId.
     * @param scanComponentSettingRequest The request object containing component settings data.
     * @param status                      The status of the ScanComponentSettings object.
     * @return A ScanComponentSettings object prepared for saving or updating.
     * @throws RuntimeException If attempting to update but the settings for the given ID are not found.
     */
    private ScanComponentSettings generateScanComponentSaveObject(HeaderContext headerContext,
                                                                  ScanComponentSettingRequest scanComponentSettingRequest,
                                                                  Boolean status) throws DmsException {
        // save new setting
        if (scanComponentSettingRequest.id().isEmpty()) {
            return ScanComponentSettings.builder()
                    .component(scanComponentSettingRequest.component())
                    .defaultScannerProfileId(scanComponentSettingRequest.defaultScannerProfileId())
                    .updatedDateTime(getCurrentTime())
                    .status(status)
                    .createdDateTime(getCurrentTime())
                    .createdUser(headerContext.getUser())
                    .hospitalId(headerContext.getHospitalId())
                    .tenantId(headerContext.getTenantId())
                    .build();
        } else {

            return scanComponentSettingsRepository
                    .findByIdAndComponentAndHospitalIdAndTenantIdAndStatus(
                            scanComponentSettingRequest.id().get(),
                            scanComponentSettingRequest.component(),
                            headerContext.getHospitalId(),
                            headerContext.getTenantId(),
                            TRUE)
                    .orElseThrow(() -> {
                        log.error("Scan component settings are not available for give ID: {} to Update",
                                scanComponentSettingRequest.id());
                        return new DmsException(DB_NO_DATA.getCode(), "Scan component settings are not available for component setting");
                    })
                    .toBuilder()
                    .createdUser(headerContext.getUser())
                    .defaultScannerProfileId(scanComponentSettingRequest.defaultScannerProfileId())
                    .updatedDateTime(getCurrentTime())
                    .hospitalId(headerContext.getHospitalId())
                    .tenantId(headerContext.getTenantId())
                    .build();
        }
    }
}
