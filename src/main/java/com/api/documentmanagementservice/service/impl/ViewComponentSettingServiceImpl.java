package com.api.documentmanagementservice.service.impl;


import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.*;
import com.api.documentmanagementservice.model.table.ViewComponentResource;
import com.api.documentmanagementservice.model.table.ViewComponentSetting;
import com.api.documentmanagementservice.repository.ViewComponentResourceRepository;
import com.api.documentmanagementservice.repository.ViewComponentSettingRepository;
import com.api.documentmanagementservice.service.ViewComponentSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ViewComponentSettingServiceImpl implements ViewComponentSettingService {
    private final ViewComponentSettingRepository viewComponentSettingRepository;
    private final ViewComponentResourceRepository viewComponentResourceRepository;
    private final HeaderContext headerContext;

    /**
     * Creates or updates a View Component Setting along with associated resources.
     *
     * @param viewComponentSettingDto The DTO containing the data for the View Component Setting.
     * @return ResponseEntity containing a CommonResponse based on the operation's outcome.
     * @throws RuntimeException If an issue occurs while saving data.
     * @If one or more header values are empty.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createOrUpdateViewComponentSettings(ViewComponentSettingDto viewComponentSettingDto) throws DmsException {
        log.info("Creating or updating view component setting...");

        // Check for existing view component settings
        Boolean isExists = viewComponentSettingRepository
                .existsByComponentAndHospitalIdAndTenantIdAndStatus(viewComponentSettingDto.component(),
                        headerContext.getHospitalId(), headerContext.getTenantId(), TRUE);

        if (viewComponentSettingDto.id().isEmpty() && isExists) {
            return generateResponse(HttpStatus.CONFLICT, DM_DMS_051.getMessage(), DM_DMS_051.name());
        }

        ViewComponentSetting savedSetting = viewComponentSettingRepository.save(getViewSettingSaveObject(viewComponentSettingDto,
                headerContext, TRUE));

        if (viewComponentSettingDto.id().isPresent()) {
            inactivateComponentResources(savedSetting.getId());
        }

        //setup view component resources to save
        List<ViewComponentResource> viewComponentResourceList = getComponentResourceListToSave(savedSetting.getId(),
                viewComponentSettingDto);

        List<ViewComponentResource> savedViewComponentResourceList;
        try {
            savedViewComponentResourceList = viewComponentResourceRepository.saveAll(viewComponentResourceList);
        } catch (Exception e) {
            log.error("Issue occurred while saving data", e);
            throw new DmsException(DB_DATA_UPDATE_ERROR.getCode(), "Issue occurred while saving data");
        }

        if (viewComponentSettingDto.id().isEmpty()) {
            return generateResponse(HttpStatus.CREATED,
                    DM_DMS_001.getMessage(),
                    DM_DMS_001.name(),
                    viewComponentSettingsResponse(savedSetting, savedViewComponentResourceList));
        }
        return generateResponse(HttpStatus.OK, DM_DMS_002.getMessage(),
                DM_DMS_002.name(), "Id : " + savedSetting.getId() + " updated");
    }

    /**
     * Inactivates component resources associated with a given component setting ID by setting their status to FALSE.
     *
     * @param componentSettingId The ID of the component setting for which resources should be inactivated.
     * @throws RuntimeException If an issue occurs while updating the data in the repository.
     */
    private void inactivateComponentResources(Long componentSettingId) throws DmsException {
        try {
            List<ViewComponentResource> viewComponentResources = viewComponentResourceRepository
                    .findAllByComponentSettingIdAndStatus(componentSettingId, TRUE)
                    .orElse(new ArrayList<>());
            viewComponentResources.forEach(viewComponentResource -> viewComponentResource.setStatus(FALSE));
            viewComponentResourceRepository.saveAll(viewComponentResources);
        } catch (Exception e) {
            log.error("Issue occurred while updating data", e);
            throw new DmsException(DB_DATA_UPDATE_ERROR.getCode(), "Issue occurred while updating data");
        }
    }

    /**
     * Retrieves view component settings and associated resources for a given component ID.
     *
     * @param componentId The ID of the component to retrieve settings and resources for.
     * @return ResponseEntity containing a CommonResponse with the retrieved data or an appropriate error message.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> getViewComponentSettingsByComponentId(Long componentId) throws DmsException {
        log.info("Getting view component setting...");

        // Get all active components
        List<ViewComponentSetting> viewComponentSettingList = getViewComponentSettingList(componentId, headerContext);

        if (viewComponentSettingList.isEmpty()) {
            return generateResponse(HttpStatus.NO_CONTENT, DM_DMS_055.getMessage(), DM_DMS_055.name());
        }

        // Get all active component resources for given component setting ID
        Optional<List<ViewComponentResource>> viewComponentResourcesList = viewComponentResourceRepository
                .findAllByComponentSettingIdAndStatus(viewComponentSettingList.get(0).getId(), TRUE);

        // If no active resources are available return response to client, make settings INACTIVE and end execution
        if (viewComponentResourcesList.isEmpty()) {
            log.warn("View component resources are not available for provided component setting ID: {}", componentId);
            // updating component setting status to false
            inactivateComponentSettings(viewComponentSettingList);
            return generateResponse(HttpStatus.NOT_FOUND, DM_DMS_055.getMessage(), DM_DMS_055.name());
        }

        return generateResponse(HttpStatus.OK, DM_DMS_009.getMessage(), DM_DMS_009.name(),
                viewComponentSettingsResponse(viewComponentSettingList.get(0),
                        viewComponentResourcesList.get()));
    }

    /**
     * Retrieves a list of active view component settings for a specific component, hospital, and tenant.
     *
     * @param componentId   The ID of the component for which view component settings are to be retrieved.
     * @param headerContext An object containing header values, including hospital and tenant IDs.
     * @return A list of active view component settings.
     */
    private List<ViewComponentSetting> getViewComponentSettingList(Long componentId, HeaderContext headerContext) throws DmsException {
        return viewComponentSettingRepository
                .findAllByComponentAndHospitalIdAndTenantIdAndStatus(componentId, headerContext.getHospitalId(),
                        headerContext.getTenantId(), TRUE)
                .orElseThrow(() -> {
                    log.warn("View component settings are not available for provided component setting ID: {}", componentId);
                    return new DmsException(DB_NO_DATA.getCode(), "View component settings are not available for provided component setting ID");
                });
    }

    /**
     * Deletes view component settings and associated resources for a given component ID.
     *
     * @param componentId The ID of the component to delete settings and resources for.
     * @return ResponseEntity containing a CommonResponse indicating the success of the deletion or an appropriate error message.
     * @if one or more header values are empty or invalid.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteViewComponentSettingsByComponentId(Long componentId) throws DmsException {
        log.info("Deleting view component setting...");
        // Validate headers


        // Get all active components
        List<ViewComponentSetting> viewComponentSettingList = getViewComponentSettingList(componentId, headerContext);

        inactivateComponentSettings(viewComponentSettingList);

        // Get all active component resources for given component setting ID
        Optional<List<ViewComponentResource>> viewComponentResourcesList = viewComponentResourceRepository
                .findAllByComponentSettingIdAndStatus(viewComponentSettingList.get(0).getId(), TRUE);

        if (viewComponentResourcesList.isEmpty()) {
            log.warn("View component resources are not available for provided component setting ID: {}", componentId);
            return generateResponse(HttpStatus.NOT_FOUND, DM_DMS_055.getMessage(), DM_DMS_055.name());
        }

        viewComponentResourcesList.get().forEach(viewComponentResource -> viewComponentResource.setStatus(FALSE));
        viewComponentResourceRepository.saveAll(viewComponentResourcesList.get());

        return generateResponse(HttpStatus.OK, DM_DMS_010.getMessage(), DM_DMS_010.name(),
                "Id : " + viewComponentSettingList.get(0).getComponent() + " deleted");
    }

    private void inactivateComponentSettings(List<ViewComponentSetting> viewComponentSettingList) {
        // updating component setting status to false
        viewComponentSettingList.forEach(setting -> setting.setStatus(FALSE));
        viewComponentSettingRepository.saveAll(viewComponentSettingList);
    }

    /**
     * Generates the ViewComponentSettingsResponse based on savedSetting and savedViewComponentResourceList.
     *
     * @param savedSetting                   The saved ViewComponentSetting.
     * @param savedViewComponentResourceList The saved ViewComponentResource list.
     * @return List of ViewComponentSettingsResponse.
     */
    private ViewComponentSettingsResponse viewComponentSettingsResponse(ViewComponentSetting savedSetting,
                                                                        List<ViewComponentResource> savedViewComponentResourceList) {
        List<SelectedViewPropertyResponse> selectedPropertyList = getSelectedViewComponentPropertyList(savedViewComponentResourceList);

        return ViewComponentSettingsResponse.builder()
                .id(savedSetting.getId())
                .component(savedSetting.getComponent())
                .hospitalId(savedSetting.getHospitalId())
                .tenantId(savedSetting.getTenantId())
                .createdUser(savedSetting.getCreatedUser())
                .createdDateTime(savedSetting.getCreatedDateTime())
                .updatedDateTime(savedSetting.getUpdatedDateTime())
                .selectedProperties(selectedPropertyList)
                .build();
    }

    /**
     * Generates a list of SelectedViewPropertyResponse based on the provided list of ViewComponentResource.
     *
     * @param savedViewComponentResourceList The list of ViewComponentResource objects to process.
     * @return A list of SelectedViewPropertyResponse containing grouped and selected attribute information.
     */
    private List<SelectedViewPropertyResponse> getSelectedViewComponentPropertyList(List<ViewComponentResource> savedViewComponentResourceList) {

        return savedViewComponentResourceList
                .stream()
                .collect(Collectors.groupingBy(ViewComponentResource::getPropertyId))
                .values()
                .stream()
                .map(propertyList -> {
                    List<SelectedAttributeResponse> selectedAttributeResponseList = propertyList.stream()
                            .map(property ->
                                    SelectedAttributeResponse.builder()
                                            .id(property.getAttributeId())
                                            .name(property.getAttributeName())
                                            .build())
                            .toList();

                    return SelectedViewPropertyResponse.builder()
                            .id(propertyList.get(0).getPropertyId())
                            .name(propertyList.get(0).getPropertyName())
                            .attributes(selectedAttributeResponseList)
                            .build();
                })
                .toList();
    }

    /**
     * Creates a list of ViewComponentResource to save based on the given componentSettingId and viewComponentSettingDto.
     *
     * @param componentSettingId      The component setting ID.
     * @param viewComponentSettingDto The ViewComponentSettingDto containing data.
     * @return List of ViewComponentResource.
     */
    private List<ViewComponentResource> getComponentResourceListToSave(Long componentSettingId,
                                                                       ViewComponentSettingDto viewComponentSettingDto) {

        return viewComponentSettingDto
                .selectedProperties()
                .stream()
                .flatMap(selectedProperty -> selectedProperty
                        .selectedAttributes().stream()
                        .map(selectedAttributeDto ->
                                ViewComponentResource.builder()
                                        .componentSettingId(componentSettingId)
                                        .propertyId(selectedProperty.id())
                                        .propertyName(selectedProperty.name())
                                        .attributeId(selectedAttributeDto.id())
                                        .attributeName(selectedAttributeDto.name())
                                        .status(TRUE)
                                        .build()))
                .toList();
    }

    /**
     * Constructs a ViewComponentSetting object for saving or updating based on the provided parameters.
     *
     * @param viewComponentSettingDto The DTO containing information for the view component setting.
     * @param headerContext           An object containing header values, including user, hospital, and tenant IDs.
     * @param status                  The status of the view component setting (active/inactive).
     * @return A ViewComponentSetting object ready for saving or updating.
     */
    private ViewComponentSetting getViewSettingSaveObject(ViewComponentSettingDto viewComponentSettingDto,
                                                          HeaderContext headerContext,
                                                          Boolean status) throws DmsException {
        if (viewComponentSettingDto.id().isEmpty()) {
            return ViewComponentSetting.builder()
                    .component(viewComponentSettingDto.component())
                    .status(status)
                    .updatedDateTime(getCurrentTime())
                    .createdUser(headerContext.getUser())
                    .hospitalId(headerContext.getHospitalId())
                    .tenantId(headerContext.getTenantId())
                    .createdDateTime(getCurrentTime())
                    .build();
        } else {

            return viewComponentSettingRepository
                    .findById(viewComponentSettingDto.id().get())
                    .orElseThrow(() -> {
                        log.error("View component settings are not available for give ID: {} to Update.",
                                viewComponentSettingDto.id());
                        return new DmsException(DB_NO_DATA.getCode(), "View component settings are not available for component setting.");
                    }).toBuilder()
                    .createdUser(headerContext.getUser())
                    .updatedDateTime(getCurrentTime())
                    .hospitalId(headerContext.getHospitalId())
                    .tenantId(headerContext.getTenantId())
                    .build();
        }
    }
}
