package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.table.FileProperty;
import com.api.documentmanagementservice.repository.*;
import com.api.documentmanagementservice.service.CustomMongoService;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.AllFileDetailsDto;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.FilePropertyWithAttributesRecord;
import com.api.documentmanagementservice.model.dto.request.PropertyRenameRequest;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {FilePropertyServiceImpl.class})
@ExtendWith(SpringExtension.class)
class FilePropertyServiceImplTest {
    @MockBean
    private CustomMongoService customMongoService;

    @MockBean
    private FilePropertyRepository filePropertyRepository;

    @Autowired
    private FilePropertyServiceImpl filePropertyServiceImpl;

    @MockBean
    private HeaderContext headerContext;

    @MockBean
    private HierarchyResourceRepository hierarchyResourceRepository;

    @MockBean
    private LocalizationServiceImpl localizationServiceImpl;

    @MockBean
    private PropertyAttributeRepository propertyAttributeRepository;

    @MockBean
    private ScanComponentResourceRepository scanComponentResourceRepository;

    @MockBean
    private ViewComponentResourceRepository viewComponentResourceRepository;

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllFileProperties(Optional)}
     */
    @Test
    void testGetAllFileProperties() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        ResponseEntity<CommonResponse> actualAllFileProperties = filePropertyServiceImpl
                .getAllFileProperties(hierarchyAvailable);
        assertTrue(actualAllFileProperties.hasBody());
        assertTrue(actualAllFileProperties.getHeaders().isEmpty());
        assertEquals(404, actualAllFileProperties.getStatusCodeValue());
        CommonResponse body = actualAllFileProperties.getBody();
        assertEquals("DM_DMS_022", body.messageCode());
        assertEquals(allFileDetailsDtoList, body.errorList());
        assertTrue(body.validity());
        assertEquals("File properties not found", body.message());
        assertEquals(allFileDetailsDtoList, body.resultData());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllFileProperties(Optional)}
     */
    @Test
    void testGetAllFileProperties2() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime,
                        updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        ResponseEntity<CommonResponse> actualAllFileProperties = filePropertyServiceImpl
                .getAllFileProperties(hierarchyAvailable);
        assertTrue(actualAllFileProperties.hasBody());
        assertTrue(actualAllFileProperties.getHeaders().isEmpty());
        assertEquals(200, actualAllFileProperties.getStatusCodeValue());
        CommonResponse body = actualAllFileProperties.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals(1, ((Collection<FilePropertyWithAttributesRecord>) body.resultData()).size());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllFileProperties(Optional)}
     */
    @Test
    void testGetAllFileProperties3() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime,
                        updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
        LocalDateTime createdDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime2,
                        updatedDateTime2, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime2, LocalDate.of(1970, 1, 1).atStartOfDay()));
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        ResponseEntity<CommonResponse> actualAllFileProperties = filePropertyServiceImpl
                .getAllFileProperties(hierarchyAvailable);
        assertTrue(actualAllFileProperties.hasBody());
        assertTrue(actualAllFileProperties.getHeaders().isEmpty());
        assertEquals(200, actualAllFileProperties.getStatusCodeValue());
        CommonResponse body = actualAllFileProperties.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals(1, ((Collection<FilePropertyWithAttributesRecord>) body.resultData()).size());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllFileProperties(Optional)}
     */
    @Test
    void testGetAllFileProperties5() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime,
                        updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, null, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        ResponseEntity<CommonResponse> actualAllFileProperties = filePropertyServiceImpl
                .getAllFileProperties(hierarchyAvailable);
        assertTrue(actualAllFileProperties.hasBody());
        assertTrue(actualAllFileProperties.getHeaders().isEmpty());
        assertEquals(200, actualAllFileProperties.getStatusCodeValue());
        CommonResponse body = actualAllFileProperties.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals(1, ((Collection<FilePropertyWithAttributesRecord>) body.resultData()).size());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllFileProperties(Optional)}
     */
    @Test
    void testGetAllFileProperties6() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime,
                        updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, null, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        ResponseEntity<CommonResponse> actualAllFileProperties = filePropertyServiceImpl
                .getAllFileProperties(hierarchyAvailable);
        assertTrue(actualAllFileProperties.hasBody());
        assertTrue(actualAllFileProperties.getHeaders().isEmpty());
        assertEquals(200, actualAllFileProperties.getStatusCodeValue());
        CommonResponse body = actualAllFileProperties.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals(1, ((Collection<FilePropertyWithAttributesRecord>) body.resultData()).size());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllFileProperties(Optional)}
     */
    @Test
    void testGetAllFileProperties9() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetails(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Boolean> hierarchyAvailable = Optional.empty();
        ResponseEntity<CommonResponse> actualAllFileProperties = filePropertyServiceImpl
                .getAllFileProperties(hierarchyAvailable);
        assertTrue(actualAllFileProperties.hasBody());
        assertTrue(actualAllFileProperties.getHeaders().isEmpty());
        assertEquals(404, actualAllFileProperties.getStatusCodeValue());
        CommonResponse body = actualAllFileProperties.getBody();
        assertEquals("DM_DMS_022", body.messageCode());
        assertEquals(allFileDetailsDtoList, body.errorList());
        assertTrue(body.validity());
        assertEquals("File properties not found", body.message());
        assertEquals(allFileDetailsDtoList, body.resultData());
        verify(filePropertyRepository).findAllFileDetails(Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllProperties(Map)}
     */
    @Test
    void testGetAllProperties() {
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        when(customMongoService.getDocumentFileDetailsDto(Mockito.<Map<String, Object>>any(), Mockito.<String>any()))
                .thenReturn(mapList);
        when(headerContext.getHospitalId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualAllProperties = filePropertyServiceImpl.getAllProperties(new HashMap<>());
        assertTrue(actualAllProperties.hasBody());
        assertTrue(actualAllProperties.getHeaders().isEmpty());
        assertEquals(200, actualAllProperties.getStatusCodeValue());
        CommonResponse body = actualAllProperties.getBody();
        assertEquals("DM_DMS_022", body.messageCode());
        assertEquals(mapList, body.errorList());
        assertTrue(body.validity());
        assertEquals("File properties not found", body.message());
        assertEquals(mapList, body.resultData());
        verify(customMongoService).getDocumentFileDetailsDto(Mockito.<Map<String, Object>>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getAllProperties(Map)}
     */
    @Test
    void testGetAllProperties2() {
        ArrayList<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(new HashMap<>());
        when(customMongoService.getDocumentFileDetailsDto(Mockito.<Map<String, Object>>any(), Mockito.<String>any()))
                .thenReturn(mapList);
        when(headerContext.getHospitalId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualAllProperties = filePropertyServiceImpl.getAllProperties(new HashMap<>());
        assertTrue(actualAllProperties.hasBody());
        assertTrue(actualAllProperties.getHeaders().isEmpty());
        assertEquals(200, actualAllProperties.getStatusCodeValue());
        CommonResponse body = actualAllProperties.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        assertEquals(1, ((Collection<HashMap>) body.resultData()).size());
        verify(customMongoService).getDocumentFileDetailsDto(Mockito.<Map<String, Object>>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#renameProperty(PropertyRenameRequest)}
     */
    @Test
    void testRenameProperty() {
        assertNull(filePropertyServiceImpl.renameProperty(new PropertyRenameRequest("Conditions", "2020-03-01")));
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#updatePropertiesByDocumentId(Map)}
     */
    @Test
    void testUpdatePropertiesByDocumentId() throws DmsException {
        when(customMongoService.save(Mockito.<Map<String, Object>>any())).thenReturn(new Document());
        HashMap<String, Object> updatePropertyRequestMap = new HashMap<>();
        ResponseEntity<CommonResponse> actualUpdatePropertiesByDocumentIdResult = filePropertyServiceImpl
                .updatePropertiesByDocumentId(updatePropertyRequestMap);
        assertTrue(actualUpdatePropertiesByDocumentIdResult.hasBody());
        assertTrue(actualUpdatePropertiesByDocumentIdResult.getHeaders().isEmpty());
        assertEquals(201, actualUpdatePropertiesByDocumentIdResult.getStatusCodeValue());
        CommonResponse body = actualUpdatePropertiesByDocumentIdResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : null updated", body.resultData());
        verify(customMongoService).save(Mockito.<Map<String, Object>>any());
        assertTrue(updatePropertyRequestMap.isEmpty());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#updatePropertiesByDocumentId(Map)}
     */
    @Test
    void testUpdatePropertiesByDocumentId2() throws DmsException {
        when(customMongoService.save(Mockito.<Map<String, Object>>any()))
                .thenThrow(new DmsException("An error occurred", "An error occurred"));
        assertThrows(DmsException.class,
                () -> filePropertyServiceImpl.updatePropertiesByDocumentId(new HashMap<>()));
        verify(customMongoService).save(Mockito.<Map<String, Object>>any());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#deletePropertyById(Long)}
     */
    @Test
    void testDeletePropertyById() throws BadRequestException, DmsException {
        FileProperty fileProperty = new FileProperty();
        fileProperty.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        fileProperty.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        fileProperty.setHierarchyAvailable(true);
        fileProperty.setHospitalId("42");
        fileProperty.setId(1L);
        fileProperty.setIsAddable(true);
        fileProperty.setIsDeletable(true);
        fileProperty.setIsFreeText(true);
        fileProperty.setIsMandatory(true);
        fileProperty.setName("Name");
        fileProperty.setPropertyType(1);
        fileProperty.setStatus(true);
        fileProperty.setTenantId("42");
        fileProperty.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<FileProperty> ofResult = Optional.of(fileProperty);
        doNothing().when(filePropertyRepository).deleteById(Mockito.<Long>any());
        when(filePropertyRepository.findFilePropertiesByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn(ofResult);
        doNothing().when(propertyAttributeRepository).deleteAllByPropertyId(Mockito.<Long>any());
        doNothing().when(hierarchyResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        doNothing().when(scanComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        doNothing().when(viewComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        ResponseEntity<CommonResponse> actualDeletePropertyByIdResult = filePropertyServiceImpl.deletePropertyById(1L);
        assertTrue(actualDeletePropertyByIdResult.hasBody());
        assertTrue(actualDeletePropertyByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeletePropertyByIdResult.getStatusCodeValue());
        CommonResponse body = actualDeletePropertyByIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(filePropertyRepository).findFilePropertiesByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any());
        verify(filePropertyRepository).deleteById(Mockito.<Long>any());
        verify(propertyAttributeRepository).deleteAllByPropertyId(Mockito.<Long>any());
        verify(hierarchyResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        verify(scanComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        verify(viewComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#deletePropertyById(Long)}
     */
    @Test
    void testDeletePropertyById2() throws BadRequestException, DmsException {
        FileProperty fileProperty = mock(FileProperty.class);
        when(fileProperty.getId()).thenReturn(1L);
        doNothing().when(fileProperty).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(fileProperty).setCreatedUser(Mockito.<String>any());
        doNothing().when(fileProperty).setHierarchyAvailable(Mockito.<Boolean>any());
        doNothing().when(fileProperty).setHospitalId(Mockito.<String>any());
        doNothing().when(fileProperty).setId(Mockito.<Long>any());
        doNothing().when(fileProperty).setIsAddable(Mockito.<Boolean>any());
        doNothing().when(fileProperty).setIsDeletable(Mockito.<Boolean>any());
        doNothing().when(fileProperty).setIsFreeText(Mockito.<Boolean>any());
        doNothing().when(fileProperty).setIsMandatory(Mockito.<Boolean>any());
        doNothing().when(fileProperty).setName(Mockito.<String>any());
        doNothing().when(fileProperty).setPropertyType(Mockito.<Integer>any());
        doNothing().when(fileProperty).setStatus(Mockito.<Boolean>any());
        doNothing().when(fileProperty).setTenantId(Mockito.<String>any());
        doNothing().when(fileProperty).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        fileProperty.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        fileProperty.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        fileProperty.setHierarchyAvailable(true);
        fileProperty.setHospitalId("42");
        fileProperty.setId(1L);
        fileProperty.setIsAddable(true);
        fileProperty.setIsDeletable(true);
        fileProperty.setIsFreeText(true);
        fileProperty.setIsMandatory(true);
        fileProperty.setName("Name");
        fileProperty.setPropertyType(1);
        fileProperty.setStatus(true);
        fileProperty.setTenantId("42");
        fileProperty.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<FileProperty> ofResult = Optional.of(fileProperty);
        doNothing().when(filePropertyRepository).deleteById(Mockito.<Long>any());
        when(filePropertyRepository.findFilePropertiesByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn(ofResult);
        doNothing().when(propertyAttributeRepository).deleteAllByPropertyId(Mockito.<Long>any());
        doNothing().when(hierarchyResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        doNothing().when(scanComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        doNothing().when(viewComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        ResponseEntity<CommonResponse> actualDeletePropertyByIdResult = filePropertyServiceImpl.deletePropertyById(1L);
        assertTrue(actualDeletePropertyByIdResult.hasBody());
        assertTrue(actualDeletePropertyByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeletePropertyByIdResult.getStatusCodeValue());
        CommonResponse body = actualDeletePropertyByIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(filePropertyRepository).findFilePropertiesByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any());
        verify(filePropertyRepository).deleteById(Mockito.<Long>any());
        verify(fileProperty, atLeast(1)).getId();
        verify(fileProperty).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(fileProperty).setCreatedUser(Mockito.<String>any());
        verify(fileProperty).setHierarchyAvailable(Mockito.<Boolean>any());
        verify(fileProperty).setHospitalId(Mockito.<String>any());
        verify(fileProperty).setId(Mockito.<Long>any());
        verify(fileProperty).setIsAddable(Mockito.<Boolean>any());
        verify(fileProperty).setIsDeletable(Mockito.<Boolean>any());
        verify(fileProperty).setIsFreeText(Mockito.<Boolean>any());
        verify(fileProperty).setIsMandatory(Mockito.<Boolean>any());
        verify(fileProperty).setName(Mockito.<String>any());
        verify(fileProperty).setPropertyType(Mockito.<Integer>any());
        verify(fileProperty).setStatus(Mockito.<Boolean>any());
        verify(fileProperty).setTenantId(Mockito.<String>any());
        verify(fileProperty).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(propertyAttributeRepository).deleteAllByPropertyId(Mockito.<Long>any());
        verify(hierarchyResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        verify(scanComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
        verify(viewComponentResourceRepository).deleteAllByPropertyId(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#deletePropertyById(Long)}
     */
    @Test
    void testDeletePropertyById3() throws BadRequestException, DmsException {
        Optional<FileProperty> emptyResult = Optional.empty();
        when(filePropertyRepository.findFilePropertiesByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn(emptyResult);
        assertThrows(BadRequestException.class, () -> filePropertyServiceImpl.deletePropertyById(1L));
        verify(filePropertyRepository).findFilePropertiesByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getFilePropertiesWithAttributes(String, String, Optional)}
     */
    @Test
    void testGetFilePropertiesWithAttributes() {
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        assertTrue(filePropertyServiceImpl.getFilePropertiesWithAttributes("42", "42", hierarchyAvailable).isEmpty());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getFilePropertiesWithAttributes(String, String, Optional)}
     */
    @Test
    void testGetFilePropertiesWithAttributes2() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime,
                        updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        assertEquals(1, filePropertyServiceImpl.getFilePropertiesWithAttributes("42", "42", hierarchyAvailable).size());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getFilePropertiesWithAttributes(String, String, Optional)}
     */
    @Test
    void testGetFilePropertiesWithAttributes3() {
        ArrayList<AllFileDetailsDto> allFileDetailsDtoList = new ArrayList<>();
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime,
                        updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
        LocalDateTime createdDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        allFileDetailsDtoList
                .add(new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime2,
                        updatedDateTime2, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime2, LocalDate.of(1970, 1, 1).atStartOfDay()));
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(allFileDetailsDtoList);
        when(filePropertyRepository.findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult);
        Optional<Boolean> hierarchyAvailable = Optional.of(true);
        assertEquals(1, filePropertyServiceImpl.getFilePropertiesWithAttributes("42", "42", hierarchyAvailable).size());
        verify(filePropertyRepository).findAllFileDetailsWithHierarchy(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<Boolean>any());
    }

    /**
     * Method under test: {@link FilePropertyServiceImpl#getFilePropertiesWithAttributes(String, String, Optional)}
     */
    @Test
    void testGetFilePropertiesWithAttributes4() {
        Optional<List<AllFileDetailsDto>> ofResult = Optional.of(new ArrayList<>());
        when(filePropertyRepository.findAllFileDetails(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        Optional<Boolean> hierarchyAvailable = Optional.empty();
        assertTrue(filePropertyServiceImpl.getFilePropertiesWithAttributes("42", "42", hierarchyAvailable).isEmpty());
        verify(filePropertyRepository).findAllFileDetails(Mockito.<String>any(), Mockito.<String>any());
    }
}

