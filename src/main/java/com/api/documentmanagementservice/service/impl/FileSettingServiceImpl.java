package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.FolderHierarchyCreateResponse;
import com.api.documentmanagementservice.model.dto.request.FolderHierarchyRequest;
import com.api.documentmanagementservice.model.dto.request.SettingUploadRequest;
import com.api.documentmanagementservice.model.table.FolderHierarchy;
import com.api.documentmanagementservice.model.table.HierarchyResource;
import com.api.documentmanagementservice.model.table.UploadSetting;
import com.api.documentmanagementservice.repository.FilePropertyRepository;
import com.api.documentmanagementservice.repository.FolderHierarchyRepository;
import com.api.documentmanagementservice.repository.HierarchyResourceRepository;
import com.api.documentmanagementservice.repository.UploadSettingRepository;
import com.api.documentmanagementservice.service.FileSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.documentmanagementservice.commons.DateTimeUtils.getCurrentTime;
import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_NO_DATA;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.REQ_INVALID_INPUT_FORMAT;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.*;
import static com.api.documentmanagementservice.commons.mapper.CommonDataMapper.getUploadSettingResponse;
import static com.api.documentmanagementservice.model.table.UploadSetting.getUploadSettingSaveObject;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileSettingServiceImpl implements FileSettingService {
    private final FilePropertyRepository filePropertyRepository;
    private final FolderHierarchyRepository folderHierarchyRepository;
    private final HierarchyResourceRepository hierarchyResourceRepository;
    private final UploadSettingRepository uploadSettingRepository;
    private final HeaderContext headerContext;

    /**
     * Updates the folder hierarchy based on the provided parameters.
     *
     * @param folderHierarchyRequest The request object containing folder hierarchy data.
     * @return A ResponseEntity containing the updated CommonResponse.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createOrUpdateFolderHierarchy(FolderHierarchyRequest folderHierarchyRequest)
            throws DmsException, BadRequestException {

        // Validate hierarchy
        if (!validateHierarchy(headerContext, folderHierarchyRequest)) {
            throw new BadRequestException(REQ_INVALID_INPUT_FORMAT.getCode(), "Invalid property ids contain in folder hierarchy array");
        }

        // Update hierarchy table and retrieve updated hierarchy if available
        // Handle case where updated hierarchy is not found
        Optional<FolderHierarchy> savedFolderHierarchy = createOrUpdateHierarchyTable(headerContext, folderHierarchyRequest);
        if (savedFolderHierarchy.isEmpty()) {
            throw new DmsException(DB_NO_DATA.getCode(), "Error occurred while updating folder hierarchy table");
        }

        if (folderHierarchyRequest.id().isPresent()) {
            // Delete existing hierarchy resources by folderHierarchyId
            hierarchyResourceRepository.deleteAllByFolderHierarchyId(folderHierarchyRequest.id().get());
        }

        // Build list of HierarchyResource objects with new hierarchy
        List<HierarchyResource> hierarchyResourceList = folderHierarchyRequest.folderHierarchy()
                .stream()
                .map(propertyId -> HierarchyResource.builder()
                        .folderHierarchyId(savedFolderHierarchy.get().getId())
                        .propertyId(propertyId)
                        .index(folderHierarchyRequest.folderHierarchy().indexOf(propertyId))
                        .createdDateTime(getCurrentTime())
                        .build())
                .toList();

        // Save the new hierarchy resources
        List<HierarchyResource> savedHierarchyResourceList = hierarchyResourceRepository.saveAll(hierarchyResourceList);

        if (savedHierarchyResourceList.isEmpty()) {
            throw new DmsException(DB_NO_DATA.getCode(), "Error occurred while updating resources");
        }

        log.info("Folder hierarchy updated successfully. Hierarchy ID: {}", folderHierarchyRequest.id());
        if (folderHierarchyRequest.id().isPresent()) {
            return generateResponse(HttpStatus.CREATED, CREATED.getMessage(), DM_DMS_001.name(),
                    getFolderHierarchyResultData(savedFolderHierarchy.get(), savedHierarchyResourceList
                            .stream().map(HierarchyResource::getId).toList()));
        }
        return generateResponse(HttpStatus.CREATED, UPDATED.getMessage(), DM_DMS_002.name(),
                "Id : " + folderHierarchyRequest.id() + " updated");
    }

    /**
     * Retrieves the folder hierarchy based on the provided headers and customization flag.
     *
     * @param isCustom A flag indicating whether to retrieve custom hierarchy.
     * @return A ResponseEntity containing the retrieved CommonResponse.
     */
    @Override
    public ResponseEntity<CommonResponse> getFolderHierarchy(Boolean isCustom) throws DmsException {
        List<FolderHierarchy> folderHierarchyList = isCustom
                ? folderHierarchyRepository.findByUserAndHospitalIdAndTenantIdAndIsCustom(
                        headerContext.getUser(), headerContext.getHospitalId(), headerContext.getTenantId(), isCustom)
                .orElseThrow(() -> new DmsException(DB_NO_DATA.getCode(), "Folder Hierarchy not found for Custom"))
                : folderHierarchyRepository.findByHospitalIdAndTenantIdAndIsCustom(
                        headerContext.getHospitalId(), headerContext.getTenantId(), isCustom)
                .orElseThrow(() -> new DmsException(DB_NO_DATA.getCode(), "Folder Hierarchy not found for Default"));

        ResponseEntity<CommonResponse> commonResponse = folderHierarchyList.stream()
                .map(folderHierarchy -> {
                    List<Long> folderResourceIdList = hierarchyResourceRepository.findAllByFolderHierarchyIdOrderByIndex(folderHierarchy.getId())
                            .orElseThrow()
                            .stream()
                            .map(HierarchyResource::getPropertyId)
                            .toList();

                    return generateResponse(HttpStatus.OK, SUCCESS.getMessage(), DM_DMS_009.name(),
                            getFolderHierarchyResultData(folderHierarchy, folderResourceIdList));
                })
                .findFirst()
                .orElse(generateResponse(HttpStatus.NO_CONTENT, SUCCESS.getMessage(), DM_DMS_009.name(), new ArrayList<>()));

        log.info("Folder hierarchy retrieved successfully.");
        return commonResponse;
    }

    /**
     * Method to create upload settings.
     *
     * @param settingUploadRequest The request containing upload setting details.
     * @return A ResponseEntity containing the response.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createOrUpdateUploadSettings(SettingUploadRequest settingUploadRequest) {
        // Check if upload settings already exist for the hospital and tenant
        List<UploadSetting> uploadSettingList = uploadSettingRepository
                .findAllByHospitalIdAndTenantId(headerContext.getHospitalId(), headerContext.getTenantId());

        if (settingUploadRequest.id().isEmpty() && !uploadSettingList.isEmpty()) {
            return generateResponse(HttpStatus.CONFLICT, DM_DMS_028.getMessage(), DM_DMS_028.name());
        }

        if (settingUploadRequest.id().isPresent() && uploadSettingList.isEmpty()) {
            return generateResponse(HttpStatus.CONFLICT, DM_DMS_029.getMessage(), DM_DMS_029.name());
        }

        //Setup upload setting object to save
        UploadSetting uploadSetting = getUploadSettingSaveObject(
                settingUploadRequest, headerContext, uploadSettingList.get(0));

        // Save the new upload setting
        UploadSetting savedUploadSetting = uploadSettingRepository.save(uploadSetting);

        if (settingUploadRequest.id().isEmpty()) {
            log.info("Upload Setting created successfully. Upload Setting ID: {}", savedUploadSetting.getId());
            return generateResponse(HttpStatus.CREATED, CREATED.getMessage(), DM_DMS_001.name(),
                    getUploadSettingResponse(savedUploadSetting));
        }

        log.info("Upload Setting updated successfully. Upload Setting ID: {}", savedUploadSetting.getId());
        return generateResponse(HttpStatus.OK, UPDATED.getMessage(), DM_DMS_002.name(),
                "Id : " + savedUploadSetting.getId() + " updated");
    }

    /**
     * Retrieves upload settings based on the provided request.
     *
     * @return A ResponseEntity containing the response status and message.
     * @If one or more header values are empty.
     */
    @Override
    public ResponseEntity<CommonResponse> getUploadSettings() {

        // Retrieve upload settings for the given hospital; if not available, get default settings
        // There can be only one upload setting per hospital
        List<UploadSetting> uploadSettingList = uploadSettingRepository
                .findAllByHospitalIdAndTenantId(headerContext.getHospitalId(), headerContext.getTenantId());

        if (uploadSettingList.isEmpty()) {
            uploadSettingList = uploadSettingRepository.findAllByHospitalIdAndTenantId("0", "0");
        }

        if (uploadSettingList.isEmpty()) {
            log.warn("Upload settings not found for Hospital ID: {} and Tenant ID: {}",
                    headerContext.getHospitalId(), headerContext.getTenantId());
            return generateResponse(HttpStatus.NO_CONTENT, UPLOAD_SETTING_NOT_FOUND.getMessage(),
                    DM_DMS_029.name());
        } else {
            return generateResponse(HttpStatus.OK, SUCCESS.getMessage(), DM_DMS_009.name(),
                    getUploadSettingResponse(uploadSettingList.get(0)));
        }

    }

    /**
     * Validates the folder hierarchy properties based on provided header values and request data.
     *
     * @param headerContext          The header values from the HTTP request.
     * @param folderHierarchyRequest The folder hierarchy request containing property IDs.
     * @return True if all property IDs are valid, false otherwise.
     */
    public boolean validateHierarchy(HeaderContext headerContext,
                                     FolderHierarchyRequest folderHierarchyRequest) throws BadRequestException {
        log.info("Validating folder hierarchy properties");

        List<Long> propertyIdList = folderHierarchyRequest.folderHierarchy();

        List<String> existingPropertyIdList = filePropertyRepository.findByIdListHospitalTenant(propertyIdList,
                        headerContext.getHospitalId(),
                        headerContext.getTenantId())
                .orElseThrow(() ->
                        new BadRequestException(REQ_INVALID_INPUT_FORMAT.getCode(),
                                "Invalid property ids contain in folder hierarchy array"));

        log.info("Validation completed. Found {} valid properties out of {} requested.",
                existingPropertyIdList.size(), propertyIdList.size());

        return existingPropertyIdList.size() == propertyIdList.size();
    }

    /**
     * Updates the folder hierarchy table based on the provided data and returns the updated hierarchy if available.
     *
     * @param headerContext          The header values from the HTTP request.
     * @param folderHierarchyRequest The folder hierarchy request data.
     * @return An Optional containing the updated FolderHierarchy if successful, empty otherwise.
     */
    public Optional<FolderHierarchy> createOrUpdateHierarchyTable(HeaderContext headerContext,
                                                                  FolderHierarchyRequest folderHierarchyRequest) throws DmsException {
        log.info("Updating folder hierarchy table");

        Boolean isCustom = folderHierarchyRequest.isCustom();

        if (folderHierarchyRequest.id().isPresent()) {
            Optional<FolderHierarchy> folderHierarchyOptional = isCustom
                    ? folderHierarchyRepository.findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(
                    folderHierarchyRequest.id().get(),
                    headerContext.getUser(),
                    isCustom,
                    headerContext.getHospitalId(),
                    headerContext.getTenantId())
                    : folderHierarchyRepository.findByIdAndIsCustomAndHospitalIdAndTenantId(
                    folderHierarchyRequest.id().get(),
                    isCustom,
                    headerContext.getHospitalId(),
                    headerContext.getTenantId());

            FolderHierarchy folderHierarchy = folderHierarchyOptional.orElseThrow(() ->
                    new DmsException(DB_NO_DATA.getCode(), "Error occurred while retrieving folder hierarchy details"));

            FolderHierarchy updatedFolderHierarchy = folderHierarchy.toBuilder()
                    .user(headerContext.getUser())
                    .updatedDateTime(getCurrentTime())
                    .hospitalId(headerContext.getHospitalId())
                    .tenantId(headerContext.getTenantId())
                    .isCustom(isCustom)
                    .build();

            FolderHierarchy savedFolderHierarchy = folderHierarchyRepository.save(updatedFolderHierarchy);

            log.info("Folder hierarchy updated successfully. ID: {}", savedFolderHierarchy.getId());

            return Optional.of(savedFolderHierarchy);
        } else {

            FolderHierarchy savedFolderHierarchy = folderHierarchyRepository.save(FolderHierarchy.builder()
                    .isCustom(isCustom)
                    .user(headerContext.getUser())
                    .hospitalId(headerContext.getHospitalId())
                    .tenantId(headerContext.getTenantId())
                    .createdDateTime(getCurrentTime())
                    .updatedDateTime(getCurrentTime())
                    .build());

            log.info("Folder hierarchy updated successfully. ID: {}", savedFolderHierarchy.getId());

            return Optional.of(savedFolderHierarchy);
        }
    }

    /**
     * Constructs a FolderHierarchyCreateResponse object using the provided FolderHierarchy and hierarchy resource list.
     *
     * @param folderHierarchy       The FolderHierarchy object to include in the response.
     * @param hierarchyResourceList The list of hierarchy resource IDs to include in the response.
     * @return A FolderHierarchyCreateResponse containing the provided data.
     */
    private FolderHierarchyCreateResponse getFolderHierarchyResultData(FolderHierarchy folderHierarchy,
                                                                       List<Long> hierarchyResourceList) {
        return FolderHierarchyCreateResponse.builder()
                .folderHierarchy(hierarchyResourceList)
                .id(folderHierarchy.getId())
                .createdDateTime(folderHierarchy.getCreatedDateTime())
                .updatedDateTime(folderHierarchy.getUpdatedDateTime())
                .tenantId(folderHierarchy.getTenantId())
                .hospitalId(folderHierarchy.getHospitalId())
                .user(folderHierarchy.getUser())
                .isCustom(folderHierarchy.getIsCustom())
                .build();
    }
}
