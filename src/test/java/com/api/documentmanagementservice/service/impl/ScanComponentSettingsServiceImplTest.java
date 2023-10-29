package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.ScanComponentSettingResponse;
import com.api.documentmanagementservice.model.table.DefaultAttribute;
import com.api.documentmanagementservice.model.table.ScanComponentResource;
import com.api.documentmanagementservice.model.table.ScanComponentSettings;
import com.api.documentmanagementservice.repository.DefaultAttributeRepository;
import com.api.documentmanagementservice.repository.ScanComponentResourceRepository;
import com.api.documentmanagementservice.repository.ScanComponentSettingsRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ScanComponentSettingsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ScanComponentSettingsServiceImplTest {
    @MockBean
    private DefaultAttributeRepository defaultAttributeRepository;

    @MockBean
    private HeaderContext headerContext;

    @MockBean
    private ScanComponentResourceRepository scanComponentResourceRepository;

    @MockBean
    private ScanComponentSettingsRepository scanComponentSettingsRepository;

    @Autowired
    private ScanComponentSettingsServiceImpl scanComponentSettingsServiceImpl;

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#getScanComponentSettings(Long)}
     */
    @Test
    void testGetScanComponentSettings() throws DmsException {
        ScanComponentSettings scanComponentSettings = new ScanComponentSettings();
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScanComponentSettings = scanComponentSettingsServiceImpl
                .getScanComponentSettings(1L);
        assertTrue(actualScanComponentSettings.hasBody());
        assertTrue(actualScanComponentSettings.getHeaders().isEmpty());
        assertEquals(200, actualScanComponentSettings.getStatusCodeValue());
        CommonResponse body = actualScanComponentSettings.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(scanComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ScanComponentSettingResponse);
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ScanComponentSettingResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertTrue(((ScanComponentSettingResponse) resultDataResult).selectedProperties().isEmpty());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).hospitalId());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).defaultScannerProfileId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ScanComponentSettingResponse) resultDataResult).createdUser());
        assertEquals("1970-01-01",
                ((ScanComponentSettingResponse) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#getScanComponentSettings(Long)}
     */
    @Test
    void testGetScanComponentSettings2() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        when(scanComponentSettings.getComponent()).thenReturn(1L);
        when(scanComponentSettings.getDefaultScannerProfileId()).thenReturn(1L);
        when(scanComponentSettings.getId()).thenReturn(1L);
        when(scanComponentSettings.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(scanComponentSettings.getHospitalId()).thenReturn("42");
        when(scanComponentSettings.getTenantId()).thenReturn("42");
        when(scanComponentSettings.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettings.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScanComponentSettings = scanComponentSettingsServiceImpl
                .getScanComponentSettings(1L);
        assertTrue(actualScanComponentSettings.hasBody());
        assertTrue(actualScanComponentSettings.getHeaders().isEmpty());
        assertEquals(200, actualScanComponentSettings.getStatusCodeValue());
        CommonResponse body = actualScanComponentSettings.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(scanComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ScanComponentSettingResponse);
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ScanComponentSettingResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertTrue(((ScanComponentSettingResponse) resultDataResult).selectedProperties().isEmpty());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).hospitalId());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).defaultScannerProfileId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ScanComponentSettingResponse) resultDataResult).createdUser());
        assertEquals("1970-01-01",
                ((ScanComponentSettingResponse) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings).getComponent();
        verify(scanComponentSettings).getDefaultScannerProfileId();
        verify(scanComponentSettings, atLeast(1)).getId();
        verify(scanComponentSettings).getCreatedUser();
        verify(scanComponentSettings).getHospitalId();
        verify(scanComponentSettings).getTenantId();
        verify(scanComponentSettings).getCreatedDateTime();
        verify(scanComponentSettings).getUpdatedDateTime();
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#getScanComponentSettings(Long)}
     */
    @Test
    void testGetScanComponentSettings4() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        when(scanComponentSettings.getComponent()).thenReturn(1L);
        when(scanComponentSettings.getDefaultScannerProfileId()).thenReturn(1L);
        when(scanComponentSettings.getId()).thenReturn(1L);
        when(scanComponentSettings.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(scanComponentSettings.getHospitalId()).thenReturn("42");
        when(scanComponentSettings.getTenantId()).thenReturn("42");
        when(scanComponentSettings.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettings.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);

        ScanComponentResource scanComponentResource = new ScanComponentResource();
        scanComponentResource.setAttributeId(1L);
        scanComponentResource.setAttributeName("Getting scan component setting...");
        scanComponentResource.setComponentSettingId(1L);
        scanComponentResource.setFreeTextType(1L);
        scanComponentResource.setId(1L);
        scanComponentResource.setIsAddable(true);
        scanComponentResource.setIsFreeText(true);
        scanComponentResource.setIsMandatory(true);
        scanComponentResource.setPropertyId(1L);
        scanComponentResource.setPropertyName("Getting scan component setting...");
        scanComponentResource.setStatus(true);

        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        scanComponentResourceList.add(scanComponentResource);
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);

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
        Optional<DefaultAttribute> ofResult3 = Optional.of(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult3);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScanComponentSettings = scanComponentSettingsServiceImpl
                .getScanComponentSettings(1L);
        assertTrue(actualScanComponentSettings.hasBody());
        assertTrue(actualScanComponentSettings.getHeaders().isEmpty());
        assertEquals(200, actualScanComponentSettings.getStatusCodeValue());
        CommonResponse body = actualScanComponentSettings.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ScanComponentSettingResponse);
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ScanComponentSettingResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertEquals(1, ((ScanComponentSettingResponse) resultDataResult).selectedProperties().size());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).hospitalId());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).defaultScannerProfileId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ScanComponentSettingResponse) resultDataResult).createdUser());
        assertEquals("1970-01-01",
                ((ScanComponentSettingResponse) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings, atLeast(1)).getComponent();
        verify(scanComponentSettings).getDefaultScannerProfileId();
        verify(scanComponentSettings, atLeast(1)).getId();
        verify(scanComponentSettings).getCreatedUser();
        verify(scanComponentSettings, atLeast(1)).getHospitalId();
        verify(scanComponentSettings, atLeast(1)).getTenantId();
        verify(scanComponentSettings).getCreatedDateTime();
        verify(scanComponentSettings).getUpdatedDateTime();
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#getScanComponentSettings(Long)}
     */
    @Test
    void testGetScanComponentSettings5() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        when(scanComponentSettings.getComponent()).thenReturn(1L);
        when(scanComponentSettings.getDefaultScannerProfileId()).thenReturn(1L);
        when(scanComponentSettings.getId()).thenReturn(1L);
        when(scanComponentSettings.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(scanComponentSettings.getHospitalId()).thenReturn("42");
        when(scanComponentSettings.getTenantId()).thenReturn("42");
        when(scanComponentSettings.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettings.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);

        ScanComponentResource scanComponentResource = new ScanComponentResource();
        scanComponentResource.setAttributeId(1L);
        scanComponentResource.setAttributeName("Getting scan component setting...");
        scanComponentResource.setComponentSettingId(1L);
        scanComponentResource.setFreeTextType(1L);
        scanComponentResource.setId(1L);
        scanComponentResource.setIsAddable(true);
        scanComponentResource.setIsFreeText(true);
        scanComponentResource.setIsMandatory(true);
        scanComponentResource.setPropertyId(1L);
        scanComponentResource.setPropertyName("Getting scan component setting...");
        scanComponentResource.setStatus(true);

        ScanComponentResource scanComponentResource2 = new ScanComponentResource();
        scanComponentResource2.setAttributeId(2L);
        scanComponentResource2.setAttributeName("Attribute Name");
        scanComponentResource2.setComponentSettingId(2L);
        scanComponentResource2.setFreeTextType(0L);
        scanComponentResource2.setId(2L);
        scanComponentResource2.setIsAddable(false);
        scanComponentResource2.setIsFreeText(false);
        scanComponentResource2.setIsMandatory(false);
        scanComponentResource2.setPropertyId(2L);
        scanComponentResource2.setPropertyName("Property Name");
        scanComponentResource2.setStatus(false);

        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        scanComponentResourceList.add(scanComponentResource2);
        scanComponentResourceList.add(scanComponentResource);
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);

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
        Optional<DefaultAttribute> ofResult3 = Optional.of(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult3);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScanComponentSettings = scanComponentSettingsServiceImpl
                .getScanComponentSettings(1L);
        assertTrue(actualScanComponentSettings.hasBody());
        assertTrue(actualScanComponentSettings.getHeaders().isEmpty());
        assertEquals(200, actualScanComponentSettings.getStatusCodeValue());
        CommonResponse body = actualScanComponentSettings.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ScanComponentSettingResponse);
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ScanComponentSettingResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertEquals(2, ((ScanComponentSettingResponse) resultDataResult).selectedProperties().size());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).hospitalId());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).defaultScannerProfileId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ScanComponentSettingResponse) resultDataResult).createdUser());
        assertEquals("1970-01-01",
                ((ScanComponentSettingResponse) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings, atLeast(1)).getComponent();
        verify(scanComponentSettings).getDefaultScannerProfileId();
        verify(scanComponentSettings, atLeast(1)).getId();
        verify(scanComponentSettings).getCreatedUser();
        verify(scanComponentSettings, atLeast(1)).getHospitalId();
        verify(scanComponentSettings, atLeast(1)).getTenantId();
        verify(scanComponentSettings).getCreatedDateTime();
        verify(scanComponentSettings).getUpdatedDateTime();
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(defaultAttributeRepository, atLeast(1)).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(
                Mockito.<Long>any(), Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#getScanComponentSettings(Long)}
     */
    @Test
    void testGetScanComponentSettings6() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        when(scanComponentSettings.getComponent()).thenReturn(1L);
        when(scanComponentSettings.getDefaultScannerProfileId()).thenReturn(1L);
        when(scanComponentSettings.getId()).thenReturn(1L);
        when(scanComponentSettings.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(scanComponentSettings.getHospitalId()).thenReturn("42");
        when(scanComponentSettings.getTenantId()).thenReturn("42");
        when(scanComponentSettings.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettings.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ScanComponentResource scanComponentResource = mock(ScanComponentResource.class);
        when(scanComponentResource.getIsAddable()).thenReturn(true);
        when(scanComponentResource.getIsFreeText()).thenReturn(true);
        when(scanComponentResource.getIsMandatory()).thenReturn(true);
        when(scanComponentResource.getAttributeId()).thenReturn(1L);
        when(scanComponentResource.getFreeTextType()).thenReturn(1L);
        when(scanComponentResource.getPropertyId()).thenReturn(1L);
        when(scanComponentResource.getAttributeName()).thenReturn("Attribute Name");
        when(scanComponentResource.getPropertyName()).thenReturn("Property Name");
        doNothing().when(scanComponentResource).setAttributeId(Mockito.<Long>any());
        doNothing().when(scanComponentResource).setAttributeName(Mockito.<String>any());
        doNothing().when(scanComponentResource).setComponentSettingId(Mockito.<Long>any());
        doNothing().when(scanComponentResource).setFreeTextType(Mockito.<Long>any());
        doNothing().when(scanComponentResource).setId(Mockito.<Long>any());
        doNothing().when(scanComponentResource).setIsAddable(Mockito.<Boolean>any());
        doNothing().when(scanComponentResource).setIsFreeText(Mockito.<Boolean>any());
        doNothing().when(scanComponentResource).setIsMandatory(Mockito.<Boolean>any());
        doNothing().when(scanComponentResource).setPropertyId(Mockito.<Long>any());
        doNothing().when(scanComponentResource).setPropertyName(Mockito.<String>any());
        doNothing().when(scanComponentResource).setStatus(Mockito.<Boolean>any());
        scanComponentResource.setAttributeId(1L);
        scanComponentResource.setAttributeName("Getting scan component setting...");
        scanComponentResource.setComponentSettingId(1L);
        scanComponentResource.setFreeTextType(1L);
        scanComponentResource.setId(1L);
        scanComponentResource.setIsAddable(true);
        scanComponentResource.setIsFreeText(true);
        scanComponentResource.setIsMandatory(true);
        scanComponentResource.setPropertyId(1L);
        scanComponentResource.setPropertyName("Getting scan component setting...");
        scanComponentResource.setStatus(true);

        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        scanComponentResourceList.add(scanComponentResource);
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);

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
        Optional<DefaultAttribute> ofResult3 = Optional.of(defaultAttribute);
        when(defaultAttributeRepository.findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any())).thenReturn(ofResult3);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScanComponentSettings = scanComponentSettingsServiceImpl
                .getScanComponentSettings(1L);
        assertTrue(actualScanComponentSettings.hasBody());
        assertTrue(actualScanComponentSettings.getHeaders().isEmpty());
        assertEquals(200, actualScanComponentSettings.getStatusCodeValue());
        CommonResponse body = actualScanComponentSettings.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ScanComponentSettingResponse);
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ScanComponentSettingResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertEquals(1, ((ScanComponentSettingResponse) resultDataResult).selectedProperties().size());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ScanComponentSettingResponse) resultDataResult).hospitalId());
        assertEquals(1L, ((ScanComponentSettingResponse) resultDataResult).defaultScannerProfileId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ScanComponentSettingResponse) resultDataResult).createdUser());
        assertEquals("1970-01-01",
                ((ScanComponentSettingResponse) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings, atLeast(1)).getComponent();
        verify(scanComponentSettings).getDefaultScannerProfileId();
        verify(scanComponentSettings, atLeast(1)).getId();
        verify(scanComponentSettings).getCreatedUser();
        verify(scanComponentSettings, atLeast(1)).getHospitalId();
        verify(scanComponentSettings, atLeast(1)).getTenantId();
        verify(scanComponentSettings).getCreatedDateTime();
        verify(scanComponentSettings).getUpdatedDateTime();
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(scanComponentResource).getIsAddable();
        verify(scanComponentResource).getIsFreeText();
        verify(scanComponentResource).getIsMandatory();
        verify(scanComponentResource).getAttributeId();
        verify(scanComponentResource).getFreeTextType();
        verify(scanComponentResource, atLeast(1)).getPropertyId();
        verify(scanComponentResource).getAttributeName();
        verify(scanComponentResource).getPropertyName();
        verify(scanComponentResource).setAttributeId(Mockito.<Long>any());
        verify(scanComponentResource).setAttributeName(Mockito.<String>any());
        verify(scanComponentResource).setComponentSettingId(Mockito.<Long>any());
        verify(scanComponentResource).setFreeTextType(Mockito.<Long>any());
        verify(scanComponentResource).setId(Mockito.<Long>any());
        verify(scanComponentResource).setIsAddable(Mockito.<Boolean>any());
        verify(scanComponentResource).setIsFreeText(Mockito.<Boolean>any());
        verify(scanComponentResource).setIsMandatory(Mockito.<Boolean>any());
        verify(scanComponentResource).setPropertyId(Mockito.<Long>any());
        verify(scanComponentResource).setPropertyName(Mockito.<String>any());
        verify(scanComponentResource).setStatus(Mockito.<Boolean>any());
        verify(defaultAttributeRepository).findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<Long>any(), Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#deleteScanComponentSettings(Long)}
     */
    @Test
    void testDeleteScanComponentSettings() throws DmsException {
        ScanComponentSettings scanComponentSettings = new ScanComponentSettings();
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);

        ScanComponentSettings scanComponentSettings2 = new ScanComponentSettings();
        scanComponentSettings2.setComponent(1L);
        scanComponentSettings2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings2.setDefaultScannerProfileId(1L);
        scanComponentSettings2.setHospitalId("42");
        scanComponentSettings2.setId(1L);
        scanComponentSettings2.setStatus(true);
        scanComponentSettings2.setTenantId("42");
        scanComponentSettings2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettingsRepository.save(Mockito.<ScanComponentSettings>any()))
                .thenReturn(scanComponentSettings2);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        when(scanComponentResourceRepository.saveAll(Mockito.<Iterable<ScanComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        doNothing().when(defaultAttributeRepository)
                .deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(), Mockito.<String>any(),
                        Mockito.<String>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteScanComponentSettingsResult = scanComponentSettingsServiceImpl
                .deleteScanComponentSettings(1L);
        assertTrue(actualDeleteScanComponentSettingsResult.hasBody());
        assertTrue(actualDeleteScanComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(204, actualDeleteScanComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualDeleteScanComponentSettingsResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(scanComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(scanComponentSettingsRepository).save(Mockito.<ScanComponentSettings>any());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentResourceRepository).saveAll(Mockito.<Iterable<ScanComponentResource>>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(defaultAttributeRepository).deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#deleteScanComponentSettings(Long)}
     */
    @Test
    void testDeleteScanComponentSettings2() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);

        ScanComponentSettings scanComponentSettings2 = new ScanComponentSettings();
        scanComponentSettings2.setComponent(1L);
        scanComponentSettings2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings2.setDefaultScannerProfileId(1L);
        scanComponentSettings2.setHospitalId("42");
        scanComponentSettings2.setId(1L);
        scanComponentSettings2.setStatus(true);
        scanComponentSettings2.setTenantId("42");
        scanComponentSettings2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettingsRepository.save(Mockito.<ScanComponentSettings>any()))
                .thenReturn(scanComponentSettings2);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        when(scanComponentResourceRepository.saveAll(Mockito.<Iterable<ScanComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        doNothing().when(defaultAttributeRepository)
                .deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(), Mockito.<String>any(),
                        Mockito.<String>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteScanComponentSettingsResult = scanComponentSettingsServiceImpl
                .deleteScanComponentSettings(1L);
        assertTrue(actualDeleteScanComponentSettingsResult.hasBody());
        assertTrue(actualDeleteScanComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(204, actualDeleteScanComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualDeleteScanComponentSettingsResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(scanComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(scanComponentSettingsRepository).save(Mockito.<ScanComponentSettings>any());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings, atLeast(1)).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).saveAll(Mockito.<Iterable<ScanComponentResource>>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(defaultAttributeRepository).deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#deleteScanComponentSettings(Long)}
     */
    @Test
    void testDeleteScanComponentSettings3() throws DmsException {
        Optional<ScanComponentSettings> emptyResult = Optional.empty();
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(emptyResult);
        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        Optional.of(scanComponentResourceList);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteScanComponentSettingsResult = scanComponentSettingsServiceImpl
                .deleteScanComponentSettings(1L);
        assertTrue(actualDeleteScanComponentSettingsResult.hasBody());
        assertTrue(actualDeleteScanComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(404, actualDeleteScanComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualDeleteScanComponentSettingsResult.getBody();
        assertEquals("DM_DMS_055", body.messageCode());
        assertEquals(scanComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Data for this component is not available", body.message());
        assertEquals(scanComponentResourceList, body.resultData());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#deleteScanComponentSettings(Long)}
     */
    @Test
    void testDeleteScanComponentSettings4() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);

        ScanComponentSettings scanComponentSettings2 = new ScanComponentSettings();
        scanComponentSettings2.setComponent(1L);
        scanComponentSettings2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings2.setDefaultScannerProfileId(1L);
        scanComponentSettings2.setHospitalId("42");
        scanComponentSettings2.setId(1L);
        scanComponentSettings2.setStatus(true);
        scanComponentSettings2.setTenantId("42");
        scanComponentSettings2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettingsRepository.save(Mockito.<ScanComponentSettings>any()))
                .thenReturn(scanComponentSettings2);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);

        ScanComponentResource scanComponentResource = new ScanComponentResource();
        scanComponentResource.setAttributeId(1L);
        scanComponentResource.setAttributeName("Deleting scan component setting...");
        scanComponentResource.setComponentSettingId(1L);
        scanComponentResource.setFreeTextType(1L);
        scanComponentResource.setId(1L);
        scanComponentResource.setIsAddable(true);
        scanComponentResource.setIsFreeText(true);
        scanComponentResource.setIsMandatory(true);
        scanComponentResource.setPropertyId(1L);
        scanComponentResource.setPropertyName("Deleting scan component setting...");
        scanComponentResource.setStatus(true);

        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        scanComponentResourceList.add(scanComponentResource);
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        ArrayList<ScanComponentResource> scanComponentResourceList2 = new ArrayList<>();
        when(scanComponentResourceRepository.saveAll(Mockito.<Iterable<ScanComponentResource>>any()))
                .thenReturn(scanComponentResourceList2);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        doNothing().when(defaultAttributeRepository)
                .deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(), Mockito.<String>any(),
                        Mockito.<String>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteScanComponentSettingsResult = scanComponentSettingsServiceImpl
                .deleteScanComponentSettings(1L);
        assertTrue(actualDeleteScanComponentSettingsResult.hasBody());
        assertTrue(actualDeleteScanComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(204, actualDeleteScanComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualDeleteScanComponentSettingsResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(scanComponentResourceList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(scanComponentSettingsRepository).save(Mockito.<ScanComponentSettings>any());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings, atLeast(1)).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).saveAll(Mockito.<Iterable<ScanComponentResource>>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(defaultAttributeRepository).deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#deleteScanComponentSettings(Long)}
     */
    @Test
    void testDeleteScanComponentSettings5() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);

        ScanComponentSettings scanComponentSettings2 = new ScanComponentSettings();
        scanComponentSettings2.setComponent(1L);
        scanComponentSettings2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings2.setDefaultScannerProfileId(1L);
        scanComponentSettings2.setHospitalId("42");
        scanComponentSettings2.setId(1L);
        scanComponentSettings2.setStatus(true);
        scanComponentSettings2.setTenantId("42");
        scanComponentSettings2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettingsRepository.save(Mockito.<ScanComponentSettings>any()))
                .thenReturn(scanComponentSettings2);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);

        ScanComponentResource scanComponentResource = new ScanComponentResource();
        scanComponentResource.setAttributeId(1L);
        scanComponentResource.setAttributeName("Deleting scan component setting...");
        scanComponentResource.setComponentSettingId(1L);
        scanComponentResource.setFreeTextType(1L);
        scanComponentResource.setId(1L);
        scanComponentResource.setIsAddable(true);
        scanComponentResource.setIsFreeText(true);
        scanComponentResource.setIsMandatory(true);
        scanComponentResource.setPropertyId(1L);
        scanComponentResource.setPropertyName("Deleting scan component setting...");
        scanComponentResource.setStatus(true);

        ScanComponentResource scanComponentResource2 = new ScanComponentResource();
        scanComponentResource2.setAttributeId(2L);
        scanComponentResource2.setAttributeName("Deleting scan component setting completed successfully for ID {}");
        scanComponentResource2.setComponentSettingId(2L);
        scanComponentResource2.setFreeTextType(0L);
        scanComponentResource2.setId(2L);
        scanComponentResource2.setIsAddable(false);
        scanComponentResource2.setIsFreeText(false);
        scanComponentResource2.setIsMandatory(false);
        scanComponentResource2.setPropertyId(2L);
        scanComponentResource2.setPropertyName("Deleting scan component setting completed successfully for ID {}");
        scanComponentResource2.setStatus(false);

        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        scanComponentResourceList.add(scanComponentResource2);
        scanComponentResourceList.add(scanComponentResource);
        Optional<List<ScanComponentResource>> ofResult2 = Optional.of(scanComponentResourceList);
        ArrayList<ScanComponentResource> scanComponentResourceList2 = new ArrayList<>();
        when(scanComponentResourceRepository.saveAll(Mockito.<Iterable<ScanComponentResource>>any()))
                .thenReturn(scanComponentResourceList2);
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        doNothing().when(defaultAttributeRepository)
                .deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(), Mockito.<String>any(),
                        Mockito.<String>any());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteScanComponentSettingsResult = scanComponentSettingsServiceImpl
                .deleteScanComponentSettings(1L);
        assertTrue(actualDeleteScanComponentSettingsResult.hasBody());
        assertTrue(actualDeleteScanComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(204, actualDeleteScanComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualDeleteScanComponentSettingsResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(scanComponentResourceList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(scanComponentSettingsRepository).save(Mockito.<ScanComponentSettings>any());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings, atLeast(1)).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).saveAll(Mockito.<Iterable<ScanComponentResource>>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(defaultAttributeRepository).deleteAllByComponentIdAndHospitalIdAndTenantId(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link ScanComponentSettingsServiceImpl#deleteScanComponentSettings(Long)}
     */
    @Test
    void testDeleteScanComponentSettings6() throws DmsException {
        ScanComponentSettings scanComponentSettings = mock(ScanComponentSettings.class);
        doNothing().when(scanComponentSettings).setComponent(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setHospitalId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setId(Mockito.<Long>any());
        doNothing().when(scanComponentSettings).setStatus(Mockito.<Boolean>any());
        doNothing().when(scanComponentSettings).setTenantId(Mockito.<String>any());
        doNothing().when(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scanComponentSettings.setComponent(1L);
        scanComponentSettings.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings.setDefaultScannerProfileId(1L);
        scanComponentSettings.setHospitalId("42");
        scanComponentSettings.setId(1L);
        scanComponentSettings.setStatus(true);
        scanComponentSettings.setTenantId("42");
        scanComponentSettings.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScanComponentSettings> ofResult = Optional.of(scanComponentSettings);

        ScanComponentSettings scanComponentSettings2 = new ScanComponentSettings();
        scanComponentSettings2.setComponent(1L);
        scanComponentSettings2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scanComponentSettings2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scanComponentSettings2.setDefaultScannerProfileId(1L);
        scanComponentSettings2.setHospitalId("42");
        scanComponentSettings2.setId(1L);
        scanComponentSettings2.setStatus(true);
        scanComponentSettings2.setTenantId("42");
        scanComponentSettings2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scanComponentSettingsRepository.save(Mockito.<ScanComponentSettings>any()))
                .thenReturn(scanComponentSettings2);
        when(scanComponentSettingsRepository.findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        Optional<List<ScanComponentResource>> emptyResult = Optional.empty();
        when(scanComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(emptyResult);
        ArrayList<ScanComponentResource> scanComponentResourceList = new ArrayList<>();
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteScanComponentSettingsResult = scanComponentSettingsServiceImpl
                .deleteScanComponentSettings(1L);
        assertTrue(actualDeleteScanComponentSettingsResult.hasBody());
        assertTrue(actualDeleteScanComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(404, actualDeleteScanComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualDeleteScanComponentSettingsResult.getBody();
        assertEquals("DM_DMS_055", body.messageCode());
        assertEquals(scanComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Data for this component is not available", body.message());
        assertEquals(scanComponentResourceList, body.resultData());
        verify(scanComponentSettingsRepository).save(Mockito.<ScanComponentSettings>any());
        verify(scanComponentSettingsRepository).findByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scanComponentSettings).setComponent(Mockito.<Long>any());
        verify(scanComponentSettings).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentSettings).setCreatedUser(Mockito.<String>any());
        verify(scanComponentSettings).setDefaultScannerProfileId(Mockito.<Long>any());
        verify(scanComponentSettings).setHospitalId(Mockito.<String>any());
        verify(scanComponentSettings).setId(Mockito.<Long>any());
        verify(scanComponentSettings, atLeast(1)).setStatus(Mockito.<Boolean>any());
        verify(scanComponentSettings).setTenantId(Mockito.<String>any());
        verify(scanComponentSettings).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scanComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }
}

