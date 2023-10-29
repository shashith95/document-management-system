package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.DefaultAttributeDto;
import com.api.documentmanagementservice.model.dto.request.DefaultAttributeRequest;
import com.api.documentmanagementservice.model.dto.request.PropertyAttributeRequest;
import com.api.documentmanagementservice.model.table.DefaultAttribute;
import com.api.documentmanagementservice.model.table.PropertyAttribute;
import com.api.documentmanagementservice.repository.DefaultAttributeRepository;
import com.api.documentmanagementservice.repository.PropertyAttributeRepository;
import com.api.documentmanagementservice.repository.ScanComponentResourceRepository;
import com.api.documentmanagementservice.repository.ViewComponentResourceRepository;
import com.api.documentmanagementservice.service.PropertyAttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.api.documentmanagementservice.commons.DateTimeUtils.getCurrentTime;
import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_DATA_DELETE_ERROR;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.REQ_INVALID_INPUT_FORMAT;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.*;
import static com.api.documentmanagementservice.commons.mapper.FilePropertyMapper.mapPropertyAttributeToRecord;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyAttributeServiceImpl implements PropertyAttributeService {
    private final PropertyAttributeRepository propertyAttributeRepository;
    private final ScanComponentResourceRepository scanComponentResourceRepository;
    private final ViewComponentResourceRepository viewComponentResourceRepository;
    private final DefaultAttributeRepository defaultAttributeRepository;
    private final LocalizationServiceImpl localizationServiceImpl;
    private final HeaderContext headerContext;

    /**
     * Deletes a property attribute and its related resources by attribute ID.
     *
     * @param attributeId The ID of the attribute to be deleted.
     * @return A ResponseEntity containing a CommonResponse indicating the success of the operation.
     */
    public ResponseEntity<CommonResponse> deleteAttributeById(Long attributeId) throws DmsException {

        PropertyAttribute propertyAttribute = propertyAttributeRepository.findAllByIdAndIsDeletable(attributeId, TRUE)
                .orElseThrow(() -> new DmsException(DB_DATA_DELETE_ERROR.getCode(), "Provided attribute is not deletable"));

        try {
            propertyAttributeRepository.deleteById(propertyAttribute.getId());
            scanComponentResourceRepository.deleteAllByAttributeId(propertyAttribute.getId());
            viewComponentResourceRepository.deleteAllByAttributeId(propertyAttribute.getId());
        } catch (Exception e) {
            log.error("An error occurred while deleting attributeId with ID {}: {}", attributeId, e.getMessage());
            throw new DmsException(DB_DATA_DELETE_ERROR.getCode(), "An error occurred while deleting attribute");
        }

        log.info("Attribute with ID {} and related resources deleted successfully", attributeId);
        return generateResponse(HttpStatus.OK,
                localizationServiceImpl.getLocalizedMessage(DM_DMS_010.name()), DM_DMS_010.name(),
                localizationServiceImpl.getLocalizedMessage(DM_DMS_010.name()));
    }

    /**
     * Sets the default attribute based on the provided request.
     *
     * @param defaultAttributeRequest The request containing the information for setting the default attribute.
     * @return The ResponseEntity with the result of the operation.
     */
    @Override
    public ResponseEntity<CommonResponse> setDefaultAttribute(DefaultAttributeRequest defaultAttributeRequest) throws BadRequestException {
        log.info("Setting default attribute...");

        defaultAttributeRequest = DefaultAttributeRequest.builder()
                .componentId(defaultAttributeRequest.componentId() == null ? 0 : defaultAttributeRequest.componentId())
                .defaultAttributeId(defaultAttributeRequest.defaultAttributeId())
                .propertyId(defaultAttributeRequest.propertyId())
                .build();

        propertyAttributeRepository
                .findByIdAndPropertyId(defaultAttributeRequest.defaultAttributeId(),
                        defaultAttributeRequest.propertyId())
                .orElseThrow(() -> new BadRequestException(REQ_INVALID_INPUT_FORMAT.getCode(),
                        "Property id and default attribute id not matching"));

        Optional<DefaultAttribute> existingDefaultAttribute = defaultAttributeRepository
                .findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(
                        defaultAttributeRequest.componentId(),
                        defaultAttributeRequest.propertyId(),
                        headerContext.getHospitalId(),
                        headerContext.getTenantId());

        DefaultAttribute defaultAttributeSavedObj;

        if (existingDefaultAttribute.isEmpty()) {
            //create default attribute if its not available
            defaultAttributeSavedObj = defaultAttributeRepository.save(
                    getDefaultAttributeSaveObject(headerContext, defaultAttributeRequest,
                            Optional.empty()));

            log.info("Created new default attribute with ID: {}", defaultAttributeSavedObj.getId());
            return generateResponse(HttpStatus.CREATED,
                    localizationServiceImpl.getLocalizedMessage(DM_DMS_001.name()),
                    DM_DMS_001.name(),
                    "Id : " + defaultAttributeSavedObj.getId() + " created");

        } else {
            //create default attribute is available and matches with requests's id update the existing one
            if (Objects.equals(existingDefaultAttribute.get().getDefaultAttributeId(),
                    defaultAttributeRequest.defaultAttributeId())) {

                // delete existing one
                defaultAttributeRepository.deleteById(existingDefaultAttribute.get().getId());

                log.info("Deleted existing default attribute with ID: {}", existingDefaultAttribute.get().getId());
                return generateResponse(HttpStatus.OK,
                        localizationServiceImpl.getLocalizedMessage(DM_DMS_002.name()),
                        DM_DMS_002.name(),
                        "Id : " + existingDefaultAttribute.get().getId() + " Updated");

            } else {
                //create default attribute is available and NOT matches with request's id,
                defaultAttributeSavedObj = defaultAttributeRepository.save(
                        getDefaultAttributeSaveObject(headerContext, defaultAttributeRequest,
                                existingDefaultAttribute));

                log.info("Updated default attribute with ID {}", defaultAttributeSavedObj.getId());
                return generateResponse(HttpStatus.OK,
                        localizationServiceImpl.getLocalizedMessage(DM_DMS_002.name()),
                        DM_DMS_002.name(),
                        "Id : " + defaultAttributeSavedObj.getId() + " Updated");
            }
        }
    }

    /**
     * Retrieves the default attribute for the given property and component.
     *
     * @param propertyId  The property ID.
     * @param componentId The component ID.
     * @return A ResponseEntity containing the default attribute information or a not found response.
     */
    @Override
    public ResponseEntity<CommonResponse> getDefaultAttribute(Long propertyId,
                                                              Long componentId) {
        log.info("Getting default attribute...");


        //if no values available for provided component id or component id is not provided
        // getting data for default component id 0
        Optional<DefaultAttribute> defaultAttribute = componentId > 0
                ? defaultAttributeRepository
                .findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(componentId, propertyId,
                        headerContext.getHospitalId(), headerContext.getTenantId())
                .or(() -> defaultAttributeRepository
                        .findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(0L, propertyId,
                                headerContext.getHospitalId(), headerContext.getTenantId()))
                : defaultAttributeRepository
                .findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(0L, propertyId,
                        headerContext.getHospitalId(), headerContext.getTenantId());

        return defaultAttribute.map(attribute ->
                        generateResponse(HttpStatus.OK, SUCCESS.getMessage(), DM_DMS_009.name(),
                                getDefaultAttributeResponse(attribute)))
                .orElseGet(() ->
                        generateResponse(HttpStatus.NO_CONTENT,
                                DEFAULT_PROPERTY_NOT_FOUND.getMessage(), DM_DMS_051.name()));
    }

    /**
     * Creates a new property attribute or updates an existing one.
     *
     * @param requestBody The DTO containing information for creating a property attribute.
     * @return A ResponseEntity with the appropriate HTTP status and response body.
     * @If one or more header values are empty.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createPropertyAttribute(PropertyAttributeRequest requestBody) {

        Optional<PropertyAttribute> existingPropertyAttribute = propertyAttributeRepository
                .findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(requestBody.propertyId(), requestBody.name(),
                        headerContext.getHospitalId(), headerContext.getTenantId());

        if (existingPropertyAttribute.isPresent()) {
            return generateResponse(HttpStatus.CONFLICT, DM_DMS_024.getMessage(), DM_DMS_024.name());
        }

        PropertyAttribute savedPropertyAttribute = propertyAttributeRepository.save(
                PropertyAttribute.builder()
                        .propertyId(requestBody.propertyId())
                        .name(requestBody.name())
                        .isDeletable(requestBody.isDeletable())
                        .hospitalId(headerContext.getHospitalId())
                        .tenantId(headerContext.getTenantId())
                        .createdUser(headerContext.getUser())
                        .createdDateTime(getCurrentTime())
                        .updatedDateTime(getCurrentTime())
                        .build());
        log.info("Property attribute saved successfully for property ID: {}", savedPropertyAttribute.getPropertyId());

        return generateResponse(HttpStatus.CREATED, DM_DMS_001.getMessage(), DM_DMS_001.name(),
                mapPropertyAttributeToRecord(savedPropertyAttribute));
    }

    /**
     * Converts a DefaultAttribute entity to a DefaultAttributeDto.
     *
     * @param defaultAttribute The DefaultAttribute entity.
     * @return The DefaultAttributeDto.
     */
    private DefaultAttributeDto getDefaultAttributeResponse(DefaultAttribute defaultAttribute) {
        return DefaultAttributeDto.builder()
                .id(defaultAttribute.getId())
                .componentId(defaultAttribute.getComponentId())
                .propertyId(defaultAttribute.getPropertyId())
                .defaultAttributeId(defaultAttribute.getDefaultAttributeId())
                .createdUser(defaultAttribute.getCreatedUser())
                .hospitalId(defaultAttribute.getHospitalId())
                .tenantId(defaultAttribute.getTenantId())
                .createdDateTime(defaultAttribute.getCreatedDateTime())
                .updatedDateTime(defaultAttribute.getUpdatedDateTime())
                .build();
    }

    /**
     * Creates a DefaultAttribute entity for saving.
     *
     * @param headerContext  The HeaderValues containing header information.
     * @param inputAttribute The DefaultAttributeRequest containing input attribute data.
     * @return The DefaultAttribute entity.
     */
    private DefaultAttribute getDefaultAttributeSaveObject(HeaderContext headerContext,
                                                           DefaultAttributeRequest inputAttribute,
                                                           Optional<DefaultAttribute> existingAttribute) {
        DefaultAttribute.DefaultAttributeBuilder builder = DefaultAttribute.builder()
                .propertyId(inputAttribute.propertyId())
                .defaultAttributeId(inputAttribute.defaultAttributeId())
                .hospitalId(headerContext.getHospitalId())
                .tenantId(headerContext.getTenantId())
                .createdUser(headerContext.getUser())
                .updatedDateTime(getCurrentTime())
                .componentId(inputAttribute.componentId());

        if (existingAttribute.isPresent()) {
            existingAttribute.get().setDefaultAttributeId(inputAttribute.defaultAttributeId());
            existingAttribute.get().setCreatedUser(headerContext.getUser());
            existingAttribute.get().setUpdatedDateTime(getCurrentTime());
            return existingAttribute.get();
        } else {
            builder.createdDateTime(getCurrentTime());
            return builder.build();
        }
    }
}
