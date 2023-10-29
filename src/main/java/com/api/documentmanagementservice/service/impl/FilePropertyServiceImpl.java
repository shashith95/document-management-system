package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.commons.mapper.FilePropertyMapper;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.AllFileDetailsDto;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.FilePropertyWithAttributesRecord;
import com.api.documentmanagementservice.model.dto.PropertyAttributeRecord;
import com.api.documentmanagementservice.model.dto.request.FilePropertyRequest;
import com.api.documentmanagementservice.model.dto.request.PropertyRenameRequest;
import com.api.documentmanagementservice.model.table.FileProperty;
import com.api.documentmanagementservice.model.table.PropertyAttribute;
import com.api.documentmanagementservice.repository.*;
import com.api.documentmanagementservice.service.CustomMongoService;
import com.api.documentmanagementservice.service.FilePropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.CommonContent.STATUS;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.*;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.*;
import static com.api.documentmanagementservice.commons.mapper.FilePropertyMapper.mapFilePropertyWithAttributesRecord;
import static com.api.documentmanagementservice.model.table.FileProperty.generateFileProperty;
import static com.api.documentmanagementservice.model.table.PropertyAttribute.getPropertyAttributeSaveObject;
import static com.api.documentmanagementservice.model.table.PropertyAttribute.getPropertyAttributeUpdateObject;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilePropertyServiceImpl implements FilePropertyService {
    private final FilePropertyRepository filePropertyRepository;
    private final PropertyAttributeRepository propertyAttributeRepository;
    private final CustomMongoService customMongoService;
    private final HierarchyResourceRepository hierarchyResourceRepository;
    private final ScanComponentResourceRepository scanComponentResourceRepository;
    private final ViewComponentResourceRepository viewComponentResourceRepository;
    private final LocalizationServiceImpl localizationServiceImpl;
    private final HeaderContext headerContext;

    /**
     * Retrieves all file properties with associated attributes for the given hospital and tenant.
     *
     * @return A ResponseEntity containing the list of file properties with attributes.
     */
    @Override
    public ResponseEntity<CommonResponse> getAllFileProperties(Optional<Boolean> hierarchyAvailable) {
        // Retrieve file properties with attributes
        List<FilePropertyWithAttributesRecord> filePropertyWithAttributesRecordList =
                getFilePropertiesWithAttributes(headerContext.getHospitalId(), headerContext.getTenantId(), hierarchyAvailable);

        // Return the empty response if no data available
        if (filePropertyWithAttributesRecordList.isEmpty()) {
            return generateResponse(HttpStatus.NOT_FOUND, FILE_PROPERTY_NOT_FOUND.getMessage(), DM_DMS_022.name());
        }

        // Return response with file details
        return generateResponse(HttpStatus.OK,
                localizationServiceImpl.getLocalizedMessage(DM_DMS_009.name()),
                DM_DMS_009.name(),
                filePropertyWithAttributesRecordList);
    }

    /**
     * Retrieves all document properties with associated file details for the given cluster MRN, tenant, and status.
     *
     * @return A ResponseEntity containing the list of document properties with associated file details.
     */
    @Override
    public ResponseEntity<CommonResponse> getAllProperties(Map<String, Object> requestMap) {
        // Retrieve document properties with associated file details
        List<Map<String, Object>> documentFileDetailsList = customMongoService.getDocumentFileDetailsDto(requestMap,
                headerContext.getHospitalId());

        if (documentFileDetailsList.isEmpty()) {
            return generateResponse(HttpStatus.OK, FILE_PROPERTY_NOT_FOUND.getMessage(), DM_DMS_022.name());
        }

        // Return the response with document properties and file details
        log.info("Returning final response...");
        return generateResponse(HttpStatus.OK, SUCCESS.getMessage(), DM_DMS_009.name(), documentFileDetailsList);
    }

    @Override
    public ResponseEntity<CommonResponse> renameProperty(PropertyRenameRequest propertyRenameRequest) {
        return null;
    }

    /**
     * Updates the properties of a document specified by its ID.
     *
     * @param updatePropertyRequestMap The map containing the updated properties.
     * @return A ResponseEntity indicating the status of the update operation.
     * @throws RuntimeException if the tenant ID in the update request doesn't match the header value or if the status is invalid.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updatePropertiesByDocumentId(Map<String, Object> updatePropertyRequestMap) throws DmsException {
        // Save the updated properties and get the updated document
        // Remove file details before saving since it's stored in another database
        updatePropertyRequestMap.remove("fileDetails");
        Document savedDocument = customMongoService.save(updatePropertyRequestMap);

        // Return a successful response
        return generateResponse(HttpStatus.CREATED, DM_DMS_002.getMessage(), DM_DMS_002.name(),
                "Id : " + savedDocument.get("_id") + " updated");
    }

    /**
     * Creates or updates a file property along with its attributes.
     *
     * @param filePropertyRequest The request containing file property information.
     * @return ResponseEntity containing the operation result.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> createOrUpdateFileProperty(FilePropertyRequest filePropertyRequest) throws DmsException, BadRequestException {

        // If the request is a creation one, check whether properties available with same name
        if (filePropertyRequest.id().isEmpty()) {
            Optional<List<FileProperty>> existingFilePropertiesByName = filePropertyRepository
                    .findExistingFilePropertiesByName(filePropertyRequest.name(),
                            headerContext.getHospitalId(), headerContext.getTenantId());
            if (existingFilePropertiesByName.isPresent() && !existingFilePropertiesByName.get().isEmpty()) {
                // Return your response and end execution
                return generateResponse(HttpStatus.CONFLICT, DM_DMS_023.getMessage(), DM_DMS_023.name());
            }
        }

        // Check for duplicate attribute names
        checkForDuplicateAttributeNames(filePropertyRequest);

        // Save/Update the file property
        FileProperty savedFileProperty = filePropertyRepository.save(getFilePropertySaveObject(headerContext, filePropertyRequest));
        log.info("File property saved successfully with ID: {}", savedFileProperty.getId());

        // Save the property attributes if available
        if (filePropertyRequest.attributes().isPresent()) {
            List<PropertyAttribute> savedPropertyAttributes = propertyAttributeRepository.saveAll(
                    getPropertyAttributeSaveList(savedFileProperty, headerContext, filePropertyRequest.attributes().get(),
                            filePropertyRequest.id()));
            log.info("File attributes saved successfully for property ID: {} with IDs: {}",
                    savedFileProperty.getId(),
                    savedPropertyAttributes.stream().map(PropertyAttribute::getId).toList());
        }

        if (filePropertyRequest.id().isPresent()) {
            return generateResponse(HttpStatus.CREATED, CREATED.getMessage(), DM_DMS_001.name(), CREATED.getMessage());
        }

        return generateResponse(HttpStatus.OK, UPDATED.getMessage(), DM_DMS_002.name(), UPDATED.getMessage());
    }

    /**
     * Deletes a property and its related attributes by its ID.
     *
     * @param propertyId The ID of the property to be deleted.
     * @return A ResponseEntity with a CommonResponse indicating the result of the deletion.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deletePropertyById(Long propertyId) throws DmsException, BadRequestException {

        FileProperty fileProperty = filePropertyRepository.findFilePropertiesByIdAndIsDeletable(propertyId, TRUE)
                .orElseThrow(() -> new BadRequestException(DM_DMS_079.name(), "This property is not deletable"));

        try {
            filePropertyRepository.deleteById(fileProperty.getId());
            propertyAttributeRepository.deleteAllByPropertyId(fileProperty.getId());
            hierarchyResourceRepository.deleteAllByPropertyId(fileProperty.getId());
            scanComponentResourceRepository.deleteAllByPropertyId(fileProperty.getId());
            viewComponentResourceRepository.deleteAllByPropertyId(fileProperty.getId());
        } catch (Exception e) {
            log.error("An error occurred while deleting property with ID {}: {}", propertyId, e.getMessage());
            throw new DmsException(DB_DATA_DELETE_ERROR.getCode(), "An error occurred while deleting property");
        }

        log.info("Property with ID {} and related resources deleted successfully", propertyId);
        return generateResponse(HttpStatus.OK, DM_DMS_010.getMessage(), DM_DMS_010.name());
    }


    /**
     * Generates a list of {@link PropertyAttribute} objects based on the provided list of {@link PropertyAttributeRecord}.
     *
     * @param headerContext               The HeaderValues containing header information.
     * @param propertyAttributeRecordList The list of PropertyAttributeRecord to be processed.
     * @param propertyRequestId           Property Id from the request
     * @return A list of PropertyAttribute objects.
     */
    private List<PropertyAttribute> getPropertyAttributeSaveList(FileProperty savedFileProperty,
                                                                 HeaderContext headerContext,
                                                                 List<PropertyAttributeRecord> propertyAttributeRecordList,
                                                                 Optional<Long> propertyRequestId) {

        return propertyAttributeRecordList
                .stream()
                .map(propertyAttributeRecord -> {
                    if (propertyRequestId.isEmpty()) {
                        return getPropertyAttributeSaveObject(propertyAttributeRecord, headerContext, savedFileProperty);
                    }

                    Optional<PropertyAttribute> existingPropertyAttribute = propertyAttributeRepository
                            .findById(propertyAttributeRecord.id());

                    if (existingPropertyAttribute.isEmpty()) {
                        return getPropertyAttributeSaveObject(propertyAttributeRecord, headerContext, savedFileProperty);
                    }

                    return getPropertyAttributeUpdateObject(propertyAttributeRecord, headerContext, existingPropertyAttribute.get());

                }).toList();
    }

    /**
     * Creates a FileProperty object from the given request.
     *
     * @param headerContext       The header values extracted from the request.
     * @param filePropertyRequest The request containing file property information.
     * @return FileProperty object.
     */
    private FileProperty getFilePropertySaveObject(HeaderContext headerContext,
                                                   FilePropertyRequest filePropertyRequest) throws DmsException {

        if (filePropertyRequest.id().isEmpty()) {
            return generateFileProperty(headerContext, filePropertyRequest, Optional.empty());
        }

        FileProperty filePropertiesById = filePropertyRepository
                .findFilePropertiesById(filePropertyRequest.id().get(), headerContext.getHospitalId(), headerContext.getTenantId())
                .orElseThrow(() -> new DmsException(DB_NO_DATA.getCode(), DB_NO_DATA.getDescription()));

        return generateFileProperty(headerContext, filePropertyRequest, Optional.of(filePropertiesById));

    }

    /**
     * Checks for duplicate attribute names in the request.
     *
     * @param filePropertyRequest The request containing file property information.
     * @throws BadRequestException If duplicate attribute names are found.
     */
    private void checkForDuplicateAttributeNames(FilePropertyRequest filePropertyRequest) throws BadRequestException {
        var attributeRecordList = filePropertyRequest.attributes().orElse(Collections.emptyList());

        if (attributeRecordList.isEmpty()) {
            log.warn("No property attributes found in request");
            return;
        }

        boolean hasDuplicates = attributeRecordList.stream()
                .map(propertyAttributeRecord -> propertyAttributeRecord.name().strip().toLowerCase())
                .distinct()
                .count() != attributeRecordList.size();

        if (hasDuplicates) {
            throw new BadRequestException(DB_DUPLICATE_ENTRY.getCode(),
                    "There are duplicate attribute names in the request");
        }

        log.info("No duplicates found in property attributes");
    }

    /**
     * Retrieves a list of FilePropertyWithAttributesRecord objects based on hospital ID and tenant ID.
     *
     * @param hospitalId The hospital ID to filter the records.
     * @param tenantId   The tenant ID to filter the records.
     * @return A list of FilePropertyWithAttributesRecord objects.
     */
    public List<FilePropertyWithAttributesRecord> getFilePropertiesWithAttributes(String hospitalId,
                                                                                  String tenantId,
                                                                                  Optional<Boolean> hierarchyAvailable) {

        Optional<List<AllFileDetailsDto>> allFileDetailsDtoList = hierarchyAvailable.isPresent()
                ? filePropertyRepository.findAllFileDetailsWithHierarchy(hospitalId, tenantId, hierarchyAvailable.get())
                : filePropertyRepository.findAllFileDetails(hospitalId, tenantId);

        return allFileDetailsDtoList
                //if allFileDetailsDtoList is empty, return empty list
                .orElseGet(ArrayList::new)
                .stream()
                //remove Status field
                .filter(fileProperty -> !Objects.equals(fileProperty.name(), STATUS.getContent()))
                //collect to a map like Map<Long, AllFileDetailsDto>
                .collect(Collectors.groupingBy(AllFileDetailsDto::id))
                .values()
                .stream()
                .map(allFileDetailsDtos -> {
                    List<PropertyAttributeRecord> attributes = allFileDetailsDtos
                            .stream()
                            .distinct() //remove duplicates
                            .filter(allFileDetailsDto -> allFileDetailsDto.attributeId() != null)
                            .map(FilePropertyMapper::mapPropertyAttributeToRecord)
                            .toList();

                    var fileProperty = allFileDetailsDtos.get(0);
                    return mapFilePropertyWithAttributesRecord(fileProperty, attributes);
                })
                .sorted(Comparator.comparingLong(FilePropertyWithAttributesRecord::id)) // sort final result by id
                .toList();
    }
}
