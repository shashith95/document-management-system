package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.DefaultAttributeDto;
import com.api.documentmanagementservice.model.dto.PropertyAttributeRecord;
import com.api.documentmanagementservice.model.dto.request.DefaultAttributeRequest;
import com.api.documentmanagementservice.model.dto.request.PropertyAttributeRequest;
import com.api.documentmanagementservice.model.table.DefaultAttribute;
import com.api.documentmanagementservice.model.table.PropertyAttribute;
import com.api.documentmanagementservice.repository.DefaultAttributeRepository;
import com.api.documentmanagementservice.repository.PropertyAttributeRepository;
import com.api.documentmanagementservice.repository.ScanComponentResourceRepository;
import com.api.documentmanagementservice.repository.ViewComponentResourceRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PropertyAttributeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PropertyAttributeServiceImplTest {
    @MockBean
    private DefaultAttributeRepository defaultAttributeRepository;

    @MockBean
    private HeaderContext headerContext;

    @MockBean
    private LocalizationServiceImpl localizationServiceImpl;

    @MockBean
    private PropertyAttributeRepository propertyAttributeRepository;

    @Autowired
    private PropertyAttributeServiceImpl propertyAttributeServiceImpl;

    @MockBean
    private ScanComponentResourceRepository scanComponentResourceRepository;

    @MockBean
    private ViewComponentResourceRepository viewComponentResourceRepository;

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#deleteAttributeById(Long)}
     */
    @Test
    void testDeleteAttributeById() throws DmsException {
        doNothing().when(propertyAttributeRepository).deleteById(Mockito.<Long>any());
        Optional<PropertyAttribute> ofResult = Optional.of(new PropertyAttribute());
        when(propertyAttributeRepository.findAllByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn(ofResult);
        doNothing().when(scanComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        doNothing().when(viewComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        ResponseEntity<CommonResponse> actualDeleteAttributeByIdResult = propertyAttributeServiceImpl
                .deleteAttributeById(1L);
        assertTrue(actualDeleteAttributeByIdResult.hasBody());
        assertTrue(actualDeleteAttributeByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteAttributeByIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteAttributeByIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals("Localized Message", body.resultData());
        verify(propertyAttributeRepository).findAllByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any());
        verify(propertyAttributeRepository).deleteById(Mockito.<Long>any());
        verify(scanComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        verify(viewComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        verify(localizationServiceImpl, atLeast(1)).getLocalizedMessage(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#deleteAttributeById(Long)}
     */
    @Test
    void testDeleteAttributeById2() throws DmsException {
        PropertyAttribute propertyAttribute = mock(PropertyAttribute.class);
        when(propertyAttribute.getId()).thenReturn(1L);
        Optional<PropertyAttribute> ofResult = Optional.of(propertyAttribute);
        doNothing().when(propertyAttributeRepository).deleteById(Mockito.<Long>any());
        when(propertyAttributeRepository.findAllByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn(ofResult);
        doNothing().when(scanComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        doNothing().when(viewComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        ResponseEntity<CommonResponse> actualDeleteAttributeByIdResult = propertyAttributeServiceImpl
                .deleteAttributeById(1L);
        assertTrue(actualDeleteAttributeByIdResult.hasBody());
        assertTrue(actualDeleteAttributeByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteAttributeByIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteAttributeByIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals("Localized Message", body.resultData());
        verify(propertyAttributeRepository).findAllByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any());
        verify(propertyAttributeRepository).deleteById(Mockito.<Long>any());
        verify(propertyAttribute, atLeast(1)).getId();
        verify(scanComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        verify(viewComponentResourceRepository).deleteAllByAttributeId(Mockito.<Long>any());
        verify(localizationServiceImpl, atLeast(1)).getLocalizedMessage(Mockito.<String>any());
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#deleteAttributeById(Long)}
     */
    @Test
    void testDeleteAttributeById3() throws DmsException {
        Optional<PropertyAttribute> emptyResult = Optional.empty();
        when(propertyAttributeRepository.findAllByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any()))
                .thenReturn(emptyResult);
        assertThrows(DmsException.class, () -> propertyAttributeServiceImpl.deleteAttributeById(1L));
        verify(propertyAttributeRepository).findAllByIdAndIsDeletable(Mockito.<Long>any(), Mockito.<Boolean>any());
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#setDefaultAttribute(DefaultAttributeRequest)}
     */
    @Test
    void testSetDefaultAttribute() throws BadRequestException {
        Optional<PropertyAttribute> ofResult = Optional.of(new PropertyAttribute());
        when(propertyAttributeRepository.findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(ofResult);

        DefaultAttribute defaultAttribute = new DefaultAttribute();
        defaultAttribute.setComponentId(1L);
        defaultAttribute.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute.setDefaultAttributeId(1L);
        defaultAttribute.setHospitalId("42");
        defaultAttribute.setId(1L);
        defaultAttribute.setPropertyId(1L);
        defaultAttribute.setTenantId("42");
        defaultAttribute.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<DefaultAttribute> ofResult2 = Optional.of(defaultAttribute);
        doNothing().when(defaultAttributeRepository).deleteById(Mockito.<Long>any());
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualSetDefaultAttributeResult = propertyAttributeServiceImpl
                .setDefaultAttribute(new DefaultAttributeRequest(1L, 1L, 1L));
        assertTrue(actualSetDefaultAttributeResult.hasBody());
        assertTrue(actualSetDefaultAttributeResult.getHeaders().isEmpty());
        assertEquals(200, actualSetDefaultAttributeResult.getStatusCodeValue());
        CommonResponse body = actualSetDefaultAttributeResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals("Id : 1 Updated", body.resultData());
        verify(propertyAttributeRepository).findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(defaultAttributeRepository).deleteById(Mockito.<Long>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#setDefaultAttribute(DefaultAttributeRequest)}
     */
    @Test
    void testSetDefaultAttribute3() throws BadRequestException {
        Optional<PropertyAttribute> emptyResult = Optional.empty();
        when(propertyAttributeRepository.findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(emptyResult);
        assertThrows(BadRequestException.class,
                () -> propertyAttributeServiceImpl.setDefaultAttribute(new DefaultAttributeRequest(1L, 1L, 1L)));
        verify(propertyAttributeRepository).findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any());
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#setDefaultAttribute(DefaultAttributeRequest)}
     */
    @Test
    void testSetDefaultAttribute4() throws BadRequestException {
        Optional<PropertyAttribute> ofResult = Optional.of(new PropertyAttribute());
        when(propertyAttributeRepository.findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(ofResult);
        DefaultAttribute defaultAttribute = mock(DefaultAttribute.class);
        when(defaultAttribute.getId()).thenReturn(1L);
        when(defaultAttribute.getDefaultAttributeId()).thenReturn(1L);
        doNothing().when(defaultAttribute).setComponentId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(defaultAttribute).setCreatedUser(Mockito.<String>any());
        doNothing().when(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setHospitalId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setPropertyId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setTenantId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        defaultAttribute.setComponentId(1L);
        defaultAttribute.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute.setDefaultAttributeId(1L);
        defaultAttribute.setHospitalId("42");
        defaultAttribute.setId(1L);
        defaultAttribute.setPropertyId(1L);
        defaultAttribute.setTenantId("42");
        defaultAttribute.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<DefaultAttribute> ofResult2 = Optional.of(defaultAttribute);
        doNothing().when(defaultAttributeRepository).deleteById(Mockito.<Long>any());
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualSetDefaultAttributeResult = propertyAttributeServiceImpl
                .setDefaultAttribute(new DefaultAttributeRequest(1L, 1L, 1L));
        assertTrue(actualSetDefaultAttributeResult.hasBody());
        assertTrue(actualSetDefaultAttributeResult.getHeaders().isEmpty());
        assertEquals(200, actualSetDefaultAttributeResult.getStatusCodeValue());
        CommonResponse body = actualSetDefaultAttributeResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals("Id : 1 Updated", body.resultData());
        verify(propertyAttributeRepository).findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(defaultAttributeRepository).deleteById(Mockito.<Long>any());
        verify(defaultAttribute).getDefaultAttributeId();
        verify(defaultAttribute, atLeast(1)).getId();
        verify(defaultAttribute).setComponentId(Mockito.<Long>any());
        verify(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(defaultAttribute).setCreatedUser(Mockito.<String>any());
        verify(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        verify(defaultAttribute).setHospitalId(Mockito.<String>any());
        verify(defaultAttribute).setId(Mockito.<Long>any());
        verify(defaultAttribute).setPropertyId(Mockito.<Long>any());
        verify(defaultAttribute).setTenantId(Mockito.<String>any());
        verify(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#setDefaultAttribute(DefaultAttributeRequest)}
     */
    @Test
    void testSetDefaultAttribute5() throws BadRequestException {
        Optional<PropertyAttribute> ofResult = Optional.of(new PropertyAttribute());
        when(propertyAttributeRepository.findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(ofResult);

        DefaultAttribute defaultAttribute = new DefaultAttribute();
        defaultAttribute.setComponentId(1L);
        defaultAttribute.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute.setDefaultAttributeId(1L);
        defaultAttribute.setHospitalId("42");
        defaultAttribute.setId(1L);
        defaultAttribute.setPropertyId(1L);
        defaultAttribute.setTenantId("42");
        defaultAttribute.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        DefaultAttribute defaultAttribute2 = mock(DefaultAttribute.class);
        when(defaultAttribute2.getDefaultAttributeId()).thenReturn(0L);
        doNothing().when(defaultAttribute2).setComponentId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(defaultAttribute2).setCreatedUser(Mockito.<String>any());
        doNothing().when(defaultAttribute2).setDefaultAttributeId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setHospitalId(Mockito.<String>any());
        doNothing().when(defaultAttribute2).setId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setPropertyId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setTenantId(Mockito.<String>any());
        doNothing().when(defaultAttribute2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        defaultAttribute2.setComponentId(1L);
        defaultAttribute2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute2.setDefaultAttributeId(1L);
        defaultAttribute2.setHospitalId("42");
        defaultAttribute2.setId(1L);
        defaultAttribute2.setPropertyId(1L);
        defaultAttribute2.setTenantId("42");
        defaultAttribute2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<DefaultAttribute> ofResult2 = Optional.of(defaultAttribute2);
        when(defaultAttributeRepository.save(Mockito.<DefaultAttribute>any())).thenReturn(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualSetDefaultAttributeResult = propertyAttributeServiceImpl
                .setDefaultAttribute(new DefaultAttributeRequest(1L, 1L, 1L));
        assertTrue(actualSetDefaultAttributeResult.hasBody());
        assertTrue(actualSetDefaultAttributeResult.getHeaders().isEmpty());
        assertEquals(200, actualSetDefaultAttributeResult.getStatusCodeValue());
        CommonResponse body = actualSetDefaultAttributeResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals("Id : 1 Updated", body.resultData());
        verify(propertyAttributeRepository).findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any());
        verify(defaultAttributeRepository).save(Mockito.<DefaultAttribute>any());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(defaultAttribute2).getDefaultAttributeId();
        verify(defaultAttribute2).setComponentId(Mockito.<Long>any());
        verify(defaultAttribute2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(defaultAttribute2, atLeast(1)).setCreatedUser(Mockito.<String>any());
        verify(defaultAttribute2, atLeast(1)).setDefaultAttributeId(Mockito.<Long>any());
        verify(defaultAttribute2).setHospitalId(Mockito.<String>any());
        verify(defaultAttribute2).setId(Mockito.<Long>any());
        verify(defaultAttribute2).setPropertyId(Mockito.<Long>any());
        verify(defaultAttribute2).setTenantId(Mockito.<String>any());
        verify(defaultAttribute2, atLeast(1)).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#setDefaultAttribute(DefaultAttributeRequest)}
     */
    @Test
    void testSetDefaultAttribute6() throws BadRequestException {
        Optional<PropertyAttribute> ofResult = Optional.of(new PropertyAttribute());
        when(propertyAttributeRepository.findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any()))
                .thenReturn(ofResult);
        DefaultAttribute defaultAttribute = mock(DefaultAttribute.class);
        when(defaultAttribute.getId()).thenReturn(1L);
        doNothing().when(defaultAttribute).setComponentId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(defaultAttribute).setCreatedUser(Mockito.<String>any());
        doNothing().when(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setHospitalId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setPropertyId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setTenantId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        defaultAttribute.setComponentId(1L);
        defaultAttribute.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute.setDefaultAttributeId(1L);
        defaultAttribute.setHospitalId("42");
        defaultAttribute.setId(1L);
        defaultAttribute.setPropertyId(1L);
        defaultAttribute.setTenantId("42");
        defaultAttribute.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        DefaultAttribute defaultAttribute2 = mock(DefaultAttribute.class);
        when(defaultAttribute2.getId()).thenReturn(1L);
        when(defaultAttribute2.getDefaultAttributeId()).thenReturn(0L);
        doNothing().when(defaultAttribute2).setComponentId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(defaultAttribute2).setCreatedUser(Mockito.<String>any());
        doNothing().when(defaultAttribute2).setDefaultAttributeId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setHospitalId(Mockito.<String>any());
        doNothing().when(defaultAttribute2).setId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setPropertyId(Mockito.<Long>any());
        doNothing().when(defaultAttribute2).setTenantId(Mockito.<String>any());
        doNothing().when(defaultAttribute2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        defaultAttribute2.setComponentId(1L);
        defaultAttribute2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute2.setDefaultAttributeId(1L);
        defaultAttribute2.setHospitalId("42");
        defaultAttribute2.setId(1L);
        defaultAttribute2.setPropertyId(1L);
        defaultAttribute2.setTenantId("42");
        defaultAttribute2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<DefaultAttribute> ofResult2 = Optional.of(defaultAttribute2);
        when(defaultAttributeRepository.save(Mockito.<DefaultAttribute>any())).thenReturn(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult2);
        when(localizationServiceImpl.getLocalizedMessage(Mockito.<String>any())).thenReturn("Localized Message");
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualSetDefaultAttributeResult = propertyAttributeServiceImpl
                .setDefaultAttribute(new DefaultAttributeRequest(1L, 1L, 1L));
        assertTrue(actualSetDefaultAttributeResult.hasBody());
        assertTrue(actualSetDefaultAttributeResult.getHeaders().isEmpty());
        assertEquals(200, actualSetDefaultAttributeResult.getStatusCodeValue());
        CommonResponse body = actualSetDefaultAttributeResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Localized Message", body.message());
        assertEquals("Id : 1 Updated", body.resultData());
        verify(propertyAttributeRepository).findByIdAndPropertyId(Mockito.<Long>any(), Mockito.<Long>any());
        verify(defaultAttributeRepository).save(Mockito.<DefaultAttribute>any());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(defaultAttribute, atLeast(1)).getId();
        verify(defaultAttribute).setComponentId(Mockito.<Long>any());
        verify(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(defaultAttribute).setCreatedUser(Mockito.<String>any());
        verify(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        verify(defaultAttribute).setHospitalId(Mockito.<String>any());
        verify(defaultAttribute).setId(Mockito.<Long>any());
        verify(defaultAttribute).setPropertyId(Mockito.<Long>any());
        verify(defaultAttribute).setTenantId(Mockito.<String>any());
        verify(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(defaultAttribute2).getDefaultAttributeId();
        verify(defaultAttribute2).setComponentId(Mockito.<Long>any());
        verify(defaultAttribute2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(defaultAttribute2, atLeast(1)).setCreatedUser(Mockito.<String>any());
        verify(defaultAttribute2, atLeast(1)).setDefaultAttributeId(Mockito.<Long>any());
        verify(defaultAttribute2).setHospitalId(Mockito.<String>any());
        verify(defaultAttribute2).setId(Mockito.<Long>any());
        verify(defaultAttribute2).setPropertyId(Mockito.<Long>any());
        verify(defaultAttribute2).setTenantId(Mockito.<String>any());
        verify(defaultAttribute2, atLeast(1)).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(localizationServiceImpl).getLocalizedMessage(Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext, atLeast(1)).getUser();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#getDefaultAttribute(Long, Long)}
     */
    @Test
    void testGetDefaultAttribute() {
        DefaultAttribute defaultAttribute = new DefaultAttribute();
        defaultAttribute.setComponentId(1L);
        defaultAttribute.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute.setDefaultAttributeId(1L);
        defaultAttribute.setHospitalId("42");
        defaultAttribute.setId(1L);
        defaultAttribute.setPropertyId(1L);
        defaultAttribute.setTenantId("42");
        defaultAttribute.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<DefaultAttribute> ofResult = Optional.of(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDefaultAttribute = propertyAttributeServiceImpl.getDefaultAttribute(1L, 1L);
        assertTrue(actualDefaultAttribute.hasBody());
        assertTrue(actualDefaultAttribute.getHeaders().isEmpty());
        assertEquals(200, actualDefaultAttribute.getStatusCodeValue());
        CommonResponse body = actualDefaultAttribute.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof DefaultAttributeDto);
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).componentId().longValue());
        assertEquals("42", ((DefaultAttributeDto) resultDataResult).tenantId());
        assertEquals("00:00", ((DefaultAttributeDto) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).propertyId().longValue());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).id().longValue());
        assertEquals("42", ((DefaultAttributeDto) resultDataResult).hospitalId());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).defaultAttributeId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((DefaultAttributeDto) resultDataResult).createdUser());
        assertEquals("1970-01-01", ((DefaultAttributeDto) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#getDefaultAttribute(Long, Long)}
     */
    @Test
    void testGetDefaultAttribute2() {
        DefaultAttribute defaultAttribute = mock(DefaultAttribute.class);
        when(defaultAttribute.getComponentId()).thenReturn(1L);
        when(defaultAttribute.getDefaultAttributeId()).thenReturn(1L);
        when(defaultAttribute.getId()).thenReturn(1L);
        when(defaultAttribute.getPropertyId()).thenReturn(1L);
        when(defaultAttribute.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(defaultAttribute.getHospitalId()).thenReturn("42");
        when(defaultAttribute.getTenantId()).thenReturn("42");
        when(defaultAttribute.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(defaultAttribute.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(defaultAttribute).setComponentId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(defaultAttribute).setCreatedUser(Mockito.<String>any());
        doNothing().when(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setHospitalId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setPropertyId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setTenantId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        defaultAttribute.setComponentId(1L);
        defaultAttribute.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute.setDefaultAttributeId(1L);
        defaultAttribute.setHospitalId("42");
        defaultAttribute.setId(1L);
        defaultAttribute.setPropertyId(1L);
        defaultAttribute.setTenantId("42");
        defaultAttribute.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<DefaultAttribute> ofResult = Optional.of(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDefaultAttribute = propertyAttributeServiceImpl.getDefaultAttribute(1L, 1L);
        assertTrue(actualDefaultAttribute.hasBody());
        assertTrue(actualDefaultAttribute.getHeaders().isEmpty());
        assertEquals(200, actualDefaultAttribute.getStatusCodeValue());
        CommonResponse body = actualDefaultAttribute.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof DefaultAttributeDto);
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).componentId().longValue());
        assertEquals("42", ((DefaultAttributeDto) resultDataResult).tenantId());
        assertEquals("00:00", ((DefaultAttributeDto) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).propertyId().longValue());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).id().longValue());
        assertEquals("42", ((DefaultAttributeDto) resultDataResult).hospitalId());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).defaultAttributeId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((DefaultAttributeDto) resultDataResult).createdUser());
        assertEquals("1970-01-01", ((DefaultAttributeDto) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(defaultAttribute).getComponentId();
        verify(defaultAttribute).getDefaultAttributeId();
        verify(defaultAttribute).getId();
        verify(defaultAttribute).getPropertyId();
        verify(defaultAttribute).getCreatedUser();
        verify(defaultAttribute).getHospitalId();
        verify(defaultAttribute).getTenantId();
        verify(defaultAttribute).getCreatedDateTime();
        verify(defaultAttribute).getUpdatedDateTime();
        verify(defaultAttribute).setComponentId(Mockito.<Long>any());
        verify(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(defaultAttribute).setCreatedUser(Mockito.<String>any());
        verify(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        verify(defaultAttribute).setHospitalId(Mockito.<String>any());
        verify(defaultAttribute).setId(Mockito.<Long>any());
        verify(defaultAttribute).setPropertyId(Mockito.<Long>any());
        verify(defaultAttribute).setTenantId(Mockito.<String>any());
        verify(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#getDefaultAttribute(Long, Long)}
     */
    @Test
    void testGetDefaultAttribute3() {
        Optional<DefaultAttribute> emptyResult = Optional.empty();
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDefaultAttribute = propertyAttributeServiceImpl.getDefaultAttribute(1L, 1L);
        assertTrue(actualDefaultAttribute.hasBody());
        assertTrue(actualDefaultAttribute.getHeaders().isEmpty());
        assertEquals(204, actualDefaultAttribute.getStatusCodeValue());
        CommonResponse body = actualDefaultAttribute.getBody();
        assertEquals("DM_DMS_051", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Default property attribute not found for this property", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(defaultAttributeRepository, atLeast(1)).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#getDefaultAttribute(Long, Long)}
     */
    @Test
    void testGetDefaultAttribute4() {
        DefaultAttribute defaultAttribute = mock(DefaultAttribute.class);
        when(defaultAttribute.getComponentId()).thenReturn(1L);
        when(defaultAttribute.getDefaultAttributeId()).thenReturn(1L);
        when(defaultAttribute.getId()).thenReturn(1L);
        when(defaultAttribute.getPropertyId()).thenReturn(1L);
        when(defaultAttribute.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(defaultAttribute.getHospitalId()).thenReturn("42");
        when(defaultAttribute.getTenantId()).thenReturn("42");
        when(defaultAttribute.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(defaultAttribute.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(defaultAttribute).setComponentId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(defaultAttribute).setCreatedUser(Mockito.<String>any());
        doNothing().when(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setHospitalId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setPropertyId(Mockito.<Long>any());
        doNothing().when(defaultAttribute).setTenantId(Mockito.<String>any());
        doNothing().when(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        defaultAttribute.setComponentId(1L);
        defaultAttribute.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        defaultAttribute.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        defaultAttribute.setDefaultAttributeId(1L);
        defaultAttribute.setHospitalId("42");
        defaultAttribute.setId(1L);
        defaultAttribute.setPropertyId(1L);
        defaultAttribute.setTenantId("42");
        defaultAttribute.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<DefaultAttribute> ofResult = Optional.of(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDefaultAttribute = propertyAttributeServiceImpl.getDefaultAttribute(1L, 0L);
        assertTrue(actualDefaultAttribute.hasBody());
        assertTrue(actualDefaultAttribute.getHeaders().isEmpty());
        assertEquals(200, actualDefaultAttribute.getStatusCodeValue());
        CommonResponse body = actualDefaultAttribute.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof DefaultAttributeDto);
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).componentId().longValue());
        assertEquals("42", ((DefaultAttributeDto) resultDataResult).tenantId());
        assertEquals("00:00", ((DefaultAttributeDto) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).propertyId().longValue());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).id().longValue());
        assertEquals("42", ((DefaultAttributeDto) resultDataResult).hospitalId());
        assertEquals(1L, ((DefaultAttributeDto) resultDataResult).defaultAttributeId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((DefaultAttributeDto) resultDataResult).createdUser());
        assertEquals("1970-01-01", ((DefaultAttributeDto) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(defaultAttribute).getComponentId();
        verify(defaultAttribute).getDefaultAttributeId();
        verify(defaultAttribute).getId();
        verify(defaultAttribute).getPropertyId();
        verify(defaultAttribute).getCreatedUser();
        verify(defaultAttribute).getHospitalId();
        verify(defaultAttribute).getTenantId();
        verify(defaultAttribute).getCreatedDateTime();
        verify(defaultAttribute).getUpdatedDateTime();
        verify(defaultAttribute).setComponentId(Mockito.<Long>any());
        verify(defaultAttribute).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(defaultAttribute).setCreatedUser(Mockito.<String>any());
        verify(defaultAttribute).setDefaultAttributeId(Mockito.<Long>any());
        verify(defaultAttribute).setHospitalId(Mockito.<String>any());
        verify(defaultAttribute).setId(Mockito.<Long>any());
        verify(defaultAttribute).setPropertyId(Mockito.<Long>any());
        verify(defaultAttribute).setTenantId(Mockito.<String>any());
        verify(defaultAttribute).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#createPropertyAttribute(PropertyAttributeRequest)}
     */
    @Test
    void testCreatePropertyAttribute() {
        Optional<PropertyAttribute> ofResult = Optional.of(new PropertyAttribute());
        when(propertyAttributeRepository.findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualCreatePropertyAttributeResult = propertyAttributeServiceImpl
                .createPropertyAttribute(new PropertyAttributeRequest("Name", 1L, true));
        assertTrue(actualCreatePropertyAttributeResult.hasBody());
        assertTrue(actualCreatePropertyAttributeResult.getHeaders().isEmpty());
        assertEquals(409, actualCreatePropertyAttributeResult.getStatusCodeValue());
        CommonResponse body = actualCreatePropertyAttributeResult.getBody();
        assertEquals("DM_DMS_024", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Attribute already exists", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(propertyAttributeRepository).findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#createPropertyAttribute(PropertyAttributeRequest)}
     */
    @Test
    void testCreatePropertyAttribute2() {
        when(propertyAttributeRepository.save(Mockito.<PropertyAttribute>any())).thenReturn(new PropertyAttribute());
        Optional<PropertyAttribute> emptyResult = Optional.empty();
        when(propertyAttributeRepository.findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualCreatePropertyAttributeResult = propertyAttributeServiceImpl
                .createPropertyAttribute(new PropertyAttributeRequest("Name", 1L, true));
        assertTrue(actualCreatePropertyAttributeResult.hasBody());
        assertTrue(actualCreatePropertyAttributeResult.getHeaders().isEmpty());
        assertEquals(201, actualCreatePropertyAttributeResult.getStatusCodeValue());
        CommonResponse body = actualCreatePropertyAttributeResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof PropertyAttributeRecord);
        assertNull(((PropertyAttributeRecord) resultDataResult).createdDateTime());
        assertNull(((PropertyAttributeRecord) resultDataResult).updatedDateTime());
        assertNull(((PropertyAttributeRecord) resultDataResult).propertyId());
        assertNull(((PropertyAttributeRecord) resultDataResult).name());
        assertNull(((PropertyAttributeRecord) resultDataResult).isDeletable());
        assertNull(((PropertyAttributeRecord) resultDataResult).id());
        assertNull(((PropertyAttributeRecord) resultDataResult).createdUser());
        verify(propertyAttributeRepository).save(Mockito.<PropertyAttribute>any());
        verify(propertyAttributeRepository).findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link PropertyAttributeServiceImpl#createPropertyAttribute(PropertyAttributeRequest)}
     */
    @Test
    void testCreatePropertyAttribute4() {
        PropertyAttribute propertyAttribute = mock(PropertyAttribute.class);
        when(propertyAttribute.getIsDeletable()).thenReturn(true);
        when(propertyAttribute.getId()).thenReturn(1L);
        when(propertyAttribute.getPropertyId()).thenReturn(1L);
        when(propertyAttribute.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(propertyAttribute.getName()).thenReturn("Name");
        when(propertyAttribute.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(propertyAttribute.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(propertyAttributeRepository.save(Mockito.<PropertyAttribute>any())).thenReturn(propertyAttribute);
        Optional<PropertyAttribute> emptyResult = Optional.empty();
        when(propertyAttributeRepository.findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(emptyResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualCreatePropertyAttributeResult = propertyAttributeServiceImpl
                .createPropertyAttribute(new PropertyAttributeRequest("Name", 1L, true));
        assertTrue(actualCreatePropertyAttributeResult.hasBody());
        assertTrue(actualCreatePropertyAttributeResult.getHeaders().isEmpty());
        assertEquals(201, actualCreatePropertyAttributeResult.getStatusCodeValue());
        CommonResponse body = actualCreatePropertyAttributeResult.getBody();
        assertEquals("DM_DMS_001", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Created successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof PropertyAttributeRecord);
        assertEquals("00:00", ((PropertyAttributeRecord) resultDataResult).createdDateTime().toLocalTime().toString());
        assertEquals("00:00", ((PropertyAttributeRecord) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertEquals(1L, ((PropertyAttributeRecord) resultDataResult).propertyId().longValue());
        assertEquals("Name", ((PropertyAttributeRecord) resultDataResult).name());
        assertTrue(((PropertyAttributeRecord) resultDataResult).isDeletable());
        assertEquals(1L, ((PropertyAttributeRecord) resultDataResult).id().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((PropertyAttributeRecord) resultDataResult).createdUser());
        verify(propertyAttributeRepository).save(Mockito.<PropertyAttribute>any());
        verify(propertyAttributeRepository).findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(propertyAttribute).getIsDeletable();
        verify(propertyAttribute).getId();
        verify(propertyAttribute, atLeast(1)).getPropertyId();
        verify(propertyAttribute).getCreatedUser();
        verify(propertyAttribute).getName();
        verify(propertyAttribute).getCreatedDateTime();
        verify(propertyAttribute).getUpdatedDateTime();
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }
}

