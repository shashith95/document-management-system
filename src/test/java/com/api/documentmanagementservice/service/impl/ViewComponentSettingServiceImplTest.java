package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.*;
import com.api.documentmanagementservice.model.table.ViewComponentResource;
import com.api.documentmanagementservice.model.table.ViewComponentSetting;
import com.api.documentmanagementservice.repository.ViewComponentResourceRepository;
import com.api.documentmanagementservice.repository.ViewComponentSettingRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ViewComponentSettingServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ViewComponentSettingServiceImplTest {
    @MockBean
    private HeaderContext headerContext;

    @MockBean
    private ViewComponentResourceRepository viewComponentResourceRepository;

    @MockBean
    private ViewComponentSettingRepository viewComponentSettingRepository;

    @Autowired
    private ViewComponentSettingServiceImpl viewComponentSettingServiceImpl;

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings() throws DmsException {
        ViewComponentSetting viewComponentSetting = new ViewComponentSetting();
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ViewComponentSetting> ofResult = Optional.of(viewComponentSetting);

        ViewComponentSetting viewComponentSetting2 = new ViewComponentSetting();
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSettingRepository.save(Mockito.<ViewComponentSetting>any())).thenReturn(viewComponentSetting2);
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(selectedProperties, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).save(Mockito.<ViewComponentSetting>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentResourceRepository, atLeast(1)).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings2() throws DmsException {
        ViewComponentSetting viewComponentSetting = new ViewComponentSetting();
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ViewComponentSetting> ofResult = Optional.of(viewComponentSetting);
        ViewComponentSetting viewComponentSetting2 = mock(ViewComponentSetting.class);
        when(viewComponentSetting2.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting2).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSettingRepository.save(Mockito.<ViewComponentSetting>any())).thenReturn(viewComponentSetting2);
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(selectedProperties, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).save(Mockito.<ViewComponentSetting>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentSetting2, atLeast(1)).getId();
        verify(viewComponentSetting2).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting2).setId(Mockito.<Long>any());
        verify(viewComponentSetting2).setStatus(anyBoolean());
        verify(viewComponentSetting2).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository, atLeast(1)).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings3() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.toBuilder()).thenReturn(ViewComponentSetting.builder());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ViewComponentSetting> ofResult = Optional.of(viewComponentSetting);
        ViewComponentSetting viewComponentSetting2 = mock(ViewComponentSetting.class);
        when(viewComponentSetting2.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting2).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSettingRepository.save(Mockito.<ViewComponentSetting>any())).thenReturn(viewComponentSetting2);
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(selectedProperties, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).save(Mockito.<ViewComponentSetting>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentSetting2, atLeast(1)).getId();
        verify(viewComponentSetting2).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting2).setId(Mockito.<Long>any());
        verify(viewComponentSetting2).setStatus(anyBoolean());
        verify(viewComponentSetting2).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).toBuilder();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository, atLeast(1)).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings5() throws DmsException {
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        Optional<ViewComponentSetting> emptyResult = Optional.empty();
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(DmsException.class, () -> viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, new ArrayList<>())));
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings6() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.toBuilder()).thenReturn(ViewComponentSetting.builder());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ViewComponentSetting> ofResult = Optional.of(viewComponentSetting);
        ViewComponentSetting viewComponentSetting2 = mock(ViewComponentSetting.class);
        when(viewComponentSetting2.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting2).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSettingRepository.save(Mockito.<ViewComponentSetting>any())).thenReturn(viewComponentSetting2);
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ViewComponentResource viewComponentResource = new ViewComponentResource();
        viewComponentResource.setAttributeId(1L);
        viewComponentResource.setAttributeName("Creating or updating view component setting...");
        viewComponentResource.setComponentSettingId(1L);
        viewComponentResource.setId(1L);
        viewComponentResource.setPropertyId(1L);
        viewComponentResource.setPropertyName("Creating or updating view component setting...");
        viewComponentResource.setStatus(true);

        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        viewComponentResourceList.add(viewComponentResource);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(viewComponentResourceList);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(selectedProperties, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).save(Mockito.<ViewComponentSetting>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentSetting2, atLeast(1)).getId();
        verify(viewComponentSetting2).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting2).setId(Mockito.<Long>any());
        verify(viewComponentSetting2).setStatus(anyBoolean());
        verify(viewComponentSetting2).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).toBuilder();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository, atLeast(1)).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings7() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.toBuilder()).thenReturn(ViewComponentSetting.builder());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ViewComponentSetting> ofResult = Optional.of(viewComponentSetting);
        ViewComponentSetting viewComponentSetting2 = mock(ViewComponentSetting.class);
        when(viewComponentSetting2.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting2).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSettingRepository.save(Mockito.<ViewComponentSetting>any())).thenReturn(viewComponentSetting2);
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ViewComponentResource viewComponentResource = new ViewComponentResource();
        viewComponentResource.setAttributeId(1L);
        viewComponentResource.setAttributeName("Creating or updating view component setting...");
        viewComponentResource.setComponentSettingId(1L);
        viewComponentResource.setId(1L);
        viewComponentResource.setPropertyId(1L);
        viewComponentResource.setPropertyName("Creating or updating view component setting...");
        viewComponentResource.setStatus(true);

        ViewComponentResource viewComponentResource2 = new ViewComponentResource();
        viewComponentResource2.setAttributeId(2L);
        viewComponentResource2.setAttributeName("UTC");
        viewComponentResource2.setComponentSettingId(2L);
        viewComponentResource2.setId(2L);
        viewComponentResource2.setPropertyId(2L);
        viewComponentResource2.setPropertyName("UTC");
        viewComponentResource2.setStatus(false);

        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        viewComponentResourceList.add(viewComponentResource2);
        viewComponentResourceList.add(viewComponentResource);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(viewComponentResourceList);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");
        Optional<Long> id = Optional.<Long>of(1L);
        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(selectedProperties, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).save(Mockito.<ViewComponentSetting>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentSetting2, atLeast(1)).getId();
        verify(viewComponentSetting2).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting2).setId(Mockito.<Long>any());
        verify(viewComponentSetting2).setStatus(anyBoolean());
        verify(viewComponentSetting2).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).toBuilder();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository, atLeast(1)).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings8() throws DmsException {
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        ViewComponentSetting viewComponentSetting2 = mock(ViewComponentSetting.class);
        doNothing().when(viewComponentSetting2).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional.of(viewComponentSetting2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.empty();
        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(409, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_051", body.messageCode());
        assertEquals(selectedProperties, body.errorList());
        assertTrue(body.validity());
        assertEquals("Default property attribute not found for this property", body.message());
        assertEquals(selectedProperties, body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting2).setId(Mockito.<Long>any());
        verify(viewComponentSetting2).setStatus(anyBoolean());
        verify(viewComponentSetting2).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings9() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.toBuilder()).thenReturn(ViewComponentSetting.builder());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ViewComponentSetting> ofResult = Optional.of(viewComponentSetting);
        ViewComponentSetting viewComponentSetting2 = mock(ViewComponentSetting.class);
        when(viewComponentSetting2.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting2).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSettingRepository.save(Mockito.<ViewComponentSetting>any())).thenReturn(viewComponentSetting2);
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(viewComponentResourceList);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");

        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        selectedProperties.add(new ViewComponentSelectedPropertyDto(1L, "Creating or updating view component setting...",
                new ArrayList<>()));
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(viewComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).save(Mockito.<ViewComponentSetting>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentSetting2, atLeast(1)).getId();
        verify(viewComponentSetting2).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting2).setId(Mockito.<Long>any());
        verify(viewComponentSetting2).setStatus(anyBoolean());
        verify(viewComponentSetting2).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).toBuilder();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository, atLeast(1)).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#createOrUpdateViewComponentSettings(ViewComponentSettingDto)}
     */
    @Test
    void testCreateOrUpdateViewComponentSettings10() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.toBuilder()).thenReturn(ViewComponentSetting.builder());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ViewComponentSetting> ofResult = Optional.of(viewComponentSetting);
        ViewComponentSetting viewComponentSetting2 = mock(ViewComponentSetting.class);
        when(viewComponentSetting2.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting2).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting2).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting2.setComponent(1L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting2.setHospitalId("42");
        viewComponentSetting2.setId(1L);
        viewComponentSetting2.setStatus(true);
        viewComponentSetting2.setTenantId("42");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSettingRepository.save(Mockito.<ViewComponentSetting>any())).thenReturn(viewComponentSetting2);
        when(viewComponentSettingRepository.existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(true);
        when(viewComponentSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(viewComponentResourceList);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        when(headerContext.getUser()).thenReturn("User");

        ArrayList<ViewComponentSelectedPropertyDto> selectedProperties = new ArrayList<>();
        selectedProperties.add(new ViewComponentSelectedPropertyDto(1L, "Creating or updating view component setting...",
                new ArrayList<>()));
        selectedProperties.add(new ViewComponentSelectedPropertyDto(1L, "Creating or updating view component setting...",
                new ArrayList<>()));
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateViewComponentSettingsResult = viewComponentSettingServiceImpl
                .createOrUpdateViewComponentSettings(new ViewComponentSettingDto(id, 1L, selectedProperties));
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.hasBody());
        assertTrue(actualCreateOrUpdateViewComponentSettingsResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateViewComponentSettingsResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateViewComponentSettingsResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(viewComponentResourceList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(viewComponentSettingRepository).existsByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSettingRepository).save(Mockito.<ViewComponentSetting>any());
        verify(viewComponentSettingRepository).findById(Mockito.<Long>any());
        verify(viewComponentSetting2, atLeast(1)).getId();
        verify(viewComponentSetting2).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting2).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting2).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting2).setId(Mockito.<Long>any());
        verify(viewComponentSetting2).setStatus(anyBoolean());
        verify(viewComponentSetting2).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).toBuilder();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository, atLeast(1)).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#getViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testGetViewComponentSettingsByComponentId() throws DmsException {
        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualViewComponentSettingsByComponentId = viewComponentSettingServiceImpl
                .getViewComponentSettingsByComponentId(1L);
        assertTrue(actualViewComponentSettingsByComponentId.hasBody());
        assertTrue(actualViewComponentSettingsByComponentId.getHeaders().isEmpty());
        assertEquals(204, actualViewComponentSettingsByComponentId.getStatusCodeValue());
        CommonResponse body = actualViewComponentSettingsByComponentId.getBody();
        assertEquals("DM_DMS_055", body.messageCode());
        assertEquals(viewComponentSettingList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Data for this component is not available", body.message());
        assertEquals(viewComponentSettingList, body.resultData());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#getViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testGetViewComponentSettingsByComponentId2() throws DmsException {
        ViewComponentSetting viewComponentSetting = new ViewComponentSetting();
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<ViewComponentSetting> expectedErrorListResult = new ArrayList<>();
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualViewComponentSettingsByComponentId = viewComponentSettingServiceImpl
                .getViewComponentSettingsByComponentId(1L);
        assertTrue(actualViewComponentSettingsByComponentId.hasBody());
        assertTrue(actualViewComponentSettingsByComponentId.getHeaders().isEmpty());
        assertEquals(200, actualViewComponentSettingsByComponentId.getStatusCodeValue());
        CommonResponse body = actualViewComponentSettingsByComponentId.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(expectedErrorListResult, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ViewComponentSettingsResponse);
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ViewComponentSettingsResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertTrue(((ViewComponentSettingsResponse) resultDataResult).selectedProperties().isEmpty());
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).hospitalId());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ViewComponentSettingsResponse) resultDataResult).createdUser());
        assertEquals("1970-01-01",
                ((ViewComponentSettingsResponse) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#getViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testGetViewComponentSettingsByComponentId3() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getComponent()).thenReturn(1L);
        when(viewComponentSetting.getId()).thenReturn(1L);
        when(viewComponentSetting.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(viewComponentSetting.getHospitalId()).thenReturn("42");
        when(viewComponentSetting.getTenantId()).thenReturn("42");
        when(viewComponentSetting.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSetting.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<ViewComponentSetting> expectedErrorListResult = new ArrayList<>();
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualViewComponentSettingsByComponentId = viewComponentSettingServiceImpl
                .getViewComponentSettingsByComponentId(1L);
        assertTrue(actualViewComponentSettingsByComponentId.hasBody());
        assertTrue(actualViewComponentSettingsByComponentId.getHeaders().isEmpty());
        assertEquals(200, actualViewComponentSettingsByComponentId.getStatusCodeValue());
        CommonResponse body = actualViewComponentSettingsByComponentId.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(expectedErrorListResult, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ViewComponentSettingsResponse);
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ViewComponentSettingsResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        assertTrue(((ViewComponentSettingsResponse) resultDataResult).selectedProperties().isEmpty());
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).hospitalId());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ViewComponentSettingsResponse) resultDataResult).createdUser());
        assertEquals("1970-01-01",
                ((ViewComponentSettingsResponse) resultDataResult).createdDateTime().toLocalDate().toString());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getComponent();
        verify(viewComponentSetting, atLeast(1)).getId();
        verify(viewComponentSetting).getCreatedUser();
        verify(viewComponentSetting).getHospitalId();
        verify(viewComponentSetting).getTenantId();
        verify(viewComponentSetting).getCreatedDateTime();
        verify(viewComponentSetting).getUpdatedDateTime();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#getViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testGetViewComponentSettingsByComponentId4() throws DmsException {
        Optional<List<ViewComponentSetting>> emptyResult = Optional.empty();
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        assertThrows(DmsException.class, () -> viewComponentSettingServiceImpl.getViewComponentSettingsByComponentId(1L));
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#getViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testGetViewComponentSettingsByComponentId5() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getComponent()).thenReturn(1L);
        when(viewComponentSetting.getId()).thenReturn(1L);
        when(viewComponentSetting.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(viewComponentSetting.getHospitalId()).thenReturn("42");
        when(viewComponentSetting.getTenantId()).thenReturn("42");
        when(viewComponentSetting.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSetting.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<ViewComponentSetting> expectedErrorListResult = new ArrayList<>();

        ViewComponentResource viewComponentResource = new ViewComponentResource();
        viewComponentResource.setAttributeId(1L);
        viewComponentResource.setAttributeName("Getting view component setting...");
        viewComponentResource.setComponentSettingId(1L);
        viewComponentResource.setId(1L);
        viewComponentResource.setPropertyId(1L);
        viewComponentResource.setPropertyName("Getting view component setting...");
        viewComponentResource.setStatus(true);

        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        viewComponentResourceList.add(viewComponentResource);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(viewComponentResourceList);
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualViewComponentSettingsByComponentId = viewComponentSettingServiceImpl
                .getViewComponentSettingsByComponentId(1L);
        assertTrue(actualViewComponentSettingsByComponentId.hasBody());
        assertTrue(actualViewComponentSettingsByComponentId.getHeaders().isEmpty());
        assertEquals(200, actualViewComponentSettingsByComponentId.getStatusCodeValue());
        CommonResponse body = actualViewComponentSettingsByComponentId.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(expectedErrorListResult, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ViewComponentSettingsResponse);
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ViewComponentSettingsResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        List<SelectedViewPropertyResponse> selectedPropertiesResult = ((ViewComponentSettingsResponse) resultDataResult)
                .selectedProperties();
        assertEquals(1, selectedPropertiesResult.size());
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).hospitalId());
        assertEquals("00:00",
                ((ViewComponentSettingsResponse) resultDataResult).createdDateTime().toLocalTime().toString());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ViewComponentSettingsResponse) resultDataResult).createdUser());
        SelectedViewPropertyResponse getResult = selectedPropertiesResult.get(0);
        List<SelectedAttributeResponse> attributesResult = getResult.attributes();
        assertEquals(1, attributesResult.size());
        assertEquals("Getting view component setting...", getResult.name());
        assertEquals(1L, getResult.id().longValue());
        SelectedAttributeResponse getResult2 = attributesResult.get(0);
        assertEquals(1L, getResult2.id().longValue());
        assertEquals("Getting view component setting...", getResult2.name());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getComponent();
        verify(viewComponentSetting, atLeast(1)).getId();
        verify(viewComponentSetting).getCreatedUser();
        verify(viewComponentSetting).getHospitalId();
        verify(viewComponentSetting).getTenantId();
        verify(viewComponentSetting).getCreatedDateTime();
        verify(viewComponentSetting).getUpdatedDateTime();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#getViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testGetViewComponentSettingsByComponentId6() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getComponent()).thenReturn(1L);
        when(viewComponentSetting.getId()).thenReturn(1L);
        when(viewComponentSetting.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(viewComponentSetting.getHospitalId()).thenReturn("42");
        when(viewComponentSetting.getTenantId()).thenReturn("42");
        when(viewComponentSetting.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(viewComponentSetting.getUpdatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ArrayList<ViewComponentSetting> expectedErrorListResult = new ArrayList<>();

        ViewComponentResource viewComponentResource = new ViewComponentResource();
        viewComponentResource.setAttributeId(1L);
        viewComponentResource.setAttributeName("Getting view component setting...");
        viewComponentResource.setComponentSettingId(1L);
        viewComponentResource.setId(1L);
        viewComponentResource.setPropertyId(1L);
        viewComponentResource.setPropertyName("Getting view component setting...");
        viewComponentResource.setStatus(true);

        ViewComponentResource viewComponentResource2 = new ViewComponentResource();
        viewComponentResource2.setAttributeId(2L);
        viewComponentResource2.setAttributeName("Attribute Name");
        viewComponentResource2.setComponentSettingId(2L);
        viewComponentResource2.setId(2L);
        viewComponentResource2.setPropertyId(2L);
        viewComponentResource2.setPropertyName("Property Name");
        viewComponentResource2.setStatus(false);

        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        viewComponentResourceList.add(viewComponentResource2);
        viewComponentResourceList.add(viewComponentResource);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(viewComponentResourceList);
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualViewComponentSettingsByComponentId = viewComponentSettingServiceImpl
                .getViewComponentSettingsByComponentId(1L);
        assertTrue(actualViewComponentSettingsByComponentId.hasBody());
        assertTrue(actualViewComponentSettingsByComponentId.getHeaders().isEmpty());
        assertEquals(200, actualViewComponentSettingsByComponentId.getStatusCodeValue());
        CommonResponse body = actualViewComponentSettingsByComponentId.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(expectedErrorListResult, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        Object resultDataResult = body.resultData();
        assertTrue(resultDataResult instanceof ViewComponentSettingsResponse);
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).component().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).tenantId());
        assertEquals("00:00",
                ((ViewComponentSettingsResponse) resultDataResult).updatedDateTime().toLocalTime().toString());
        List<SelectedViewPropertyResponse> selectedPropertiesResult = ((ViewComponentSettingsResponse) resultDataResult)
                .selectedProperties();
        assertEquals(2, selectedPropertiesResult.size());
        assertEquals(1L, ((ViewComponentSettingsResponse) resultDataResult).id().longValue());
        assertEquals("42", ((ViewComponentSettingsResponse) resultDataResult).hospitalId());
        assertEquals("00:00",
                ((ViewComponentSettingsResponse) resultDataResult).createdDateTime().toLocalTime().toString());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", ((ViewComponentSettingsResponse) resultDataResult).createdUser());
        SelectedViewPropertyResponse getResult = selectedPropertiesResult.get(1);
        assertEquals("Property Name", getResult.name());
        SelectedViewPropertyResponse getResult2 = selectedPropertiesResult.get(0);
        assertEquals("Getting view component setting...", getResult2.name());
        assertEquals(1L, getResult2.id().longValue());
        List<SelectedAttributeResponse> attributesResult = getResult2.attributes();
        assertEquals(1, attributesResult.size());
        assertEquals(2L, getResult.id().longValue());
        List<SelectedAttributeResponse> attributesResult2 = getResult.attributes();
        assertEquals(1, attributesResult2.size());
        SelectedAttributeResponse getResult3 = attributesResult.get(0);
        assertEquals("Getting view component setting...", getResult3.name());
        SelectedAttributeResponse getResult4 = attributesResult2.get(0);
        assertEquals("Attribute Name", getResult4.name());
        assertEquals(2L, getResult4.id().longValue());
        assertEquals(1L, getResult3.id().longValue());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getComponent();
        verify(viewComponentSetting, atLeast(1)).getId();
        verify(viewComponentSetting).getCreatedUser();
        verify(viewComponentSetting).getHospitalId();
        verify(viewComponentSetting).getTenantId();
        verify(viewComponentSetting).getCreatedDateTime();
        verify(viewComponentSetting).getUpdatedDateTime();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#getViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testGetViewComponentSettingsByComponentId7() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        Optional<List<ViewComponentResource>> emptyResult = Optional.empty();
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualViewComponentSettingsByComponentId = viewComponentSettingServiceImpl
                .getViewComponentSettingsByComponentId(1L);
        assertTrue(actualViewComponentSettingsByComponentId.hasBody());
        assertTrue(actualViewComponentSettingsByComponentId.getHeaders().isEmpty());
        assertEquals(404, actualViewComponentSettingsByComponentId.getStatusCodeValue());
        CommonResponse body = actualViewComponentSettingsByComponentId.getBody();
        assertEquals("DM_DMS_055", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Data for this component is not available", body.message());
        assertEquals(viewComponentSettingList2, body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getId();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting, atLeast(1)).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId2() throws DmsException {
        ViewComponentSetting viewComponentSetting = new ViewComponentSetting();
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteViewComponentSettingsByComponentIdResult = viewComponentSettingServiceImpl
                .deleteViewComponentSettingsByComponentId(1L);
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.hasBody());
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteViewComponentSettingsByComponentIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteViewComponentSettingsByComponentIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentResourceRepository).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId3() throws DmsException {
        ViewComponentSetting viewComponentSetting = new ViewComponentSetting();
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ViewComponentSetting viewComponentSetting2 = new ViewComponentSetting();
        viewComponentSetting2.setComponent(0L);
        viewComponentSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting2.setCreatedUser("Deleting view component setting...");
        viewComponentSetting2.setHospitalId("Deleting view component setting...");
        viewComponentSetting2.setId(2L);
        viewComponentSetting2.setStatus(false);
        viewComponentSetting2.setTenantId("Deleting view component setting...");
        viewComponentSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting2);
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteViewComponentSettingsByComponentIdResult = viewComponentSettingServiceImpl
                .deleteViewComponentSettingsByComponentId(1L);
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.hasBody());
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteViewComponentSettingsByComponentIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteViewComponentSettingsByComponentIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 0 deleted", body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentResourceRepository).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId4() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getComponent()).thenReturn(1L);
        when(viewComponentSetting.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteViewComponentSettingsByComponentIdResult = viewComponentSettingServiceImpl
                .deleteViewComponentSettingsByComponentId(1L);
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.hasBody());
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteViewComponentSettingsByComponentIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteViewComponentSettingsByComponentIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getComponent();
        verify(viewComponentSetting).getId();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting, atLeast(1)).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId5() throws DmsException {
        Optional<List<ViewComponentSetting>> emptyResult = Optional.empty();
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        assertThrows(DmsException.class,
                () -> viewComponentSettingServiceImpl.deleteViewComponentSettingsByComponentId(1L));
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId6() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getComponent()).thenReturn(1L);
        when(viewComponentSetting.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);

        ViewComponentResource viewComponentResource = new ViewComponentResource();
        viewComponentResource.setAttributeId(1L);
        viewComponentResource.setAttributeName("Deleting view component setting...");
        viewComponentResource.setComponentSettingId(1L);
        viewComponentResource.setId(1L);
        viewComponentResource.setPropertyId(1L);
        viewComponentResource.setPropertyName("Deleting view component setting...");
        viewComponentResource.setStatus(true);

        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        viewComponentResourceList.add(viewComponentResource);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(viewComponentResourceList);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteViewComponentSettingsByComponentIdResult = viewComponentSettingServiceImpl
                .deleteViewComponentSettingsByComponentId(1L);
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.hasBody());
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteViewComponentSettingsByComponentIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteViewComponentSettingsByComponentIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getComponent();
        verify(viewComponentSetting).getId();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting, atLeast(1)).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId7() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getComponent()).thenReturn(1L);
        when(viewComponentSetting.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);

        ViewComponentResource viewComponentResource = new ViewComponentResource();
        viewComponentResource.setAttributeId(1L);
        viewComponentResource.setAttributeName("Deleting view component setting...");
        viewComponentResource.setComponentSettingId(1L);
        viewComponentResource.setId(1L);
        viewComponentResource.setPropertyId(1L);
        viewComponentResource.setPropertyName("Deleting view component setting...");
        viewComponentResource.setStatus(true);

        ViewComponentResource viewComponentResource2 = new ViewComponentResource();
        viewComponentResource2.setAttributeId(2L);
        viewComponentResource2.setAttributeName("Attribute Name");
        viewComponentResource2.setComponentSettingId(2L);
        viewComponentResource2.setId(2L);
        viewComponentResource2.setPropertyId(2L);
        viewComponentResource2.setPropertyName("Property Name");
        viewComponentResource2.setStatus(false);

        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        viewComponentResourceList.add(viewComponentResource2);
        viewComponentResourceList.add(viewComponentResource);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(viewComponentResourceList);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteViewComponentSettingsByComponentIdResult = viewComponentSettingServiceImpl
                .deleteViewComponentSettingsByComponentId(1L);
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.hasBody());
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteViewComponentSettingsByComponentIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteViewComponentSettingsByComponentIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getComponent();
        verify(viewComponentSetting).getId();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting, atLeast(1)).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId8() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        Optional<List<ViewComponentResource>> emptyResult = Optional.empty();
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteViewComponentSettingsByComponentIdResult = viewComponentSettingServiceImpl
                .deleteViewComponentSettingsByComponentId(1L);
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.hasBody());
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.getHeaders().isEmpty());
        assertEquals(404, actualDeleteViewComponentSettingsByComponentIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteViewComponentSettingsByComponentIdResult.getBody();
        assertEquals("DM_DMS_055", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Data for this component is not available", body.message());
        assertEquals(viewComponentSettingList2, body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getId();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting, atLeast(1)).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ViewComponentSettingServiceImpl#deleteViewComponentSettingsByComponentId(Long)}
     */
    @Test
    void testDeleteViewComponentSettingsByComponentId9() throws DmsException {
        ViewComponentSetting viewComponentSetting = mock(ViewComponentSetting.class);
        when(viewComponentSetting.getComponent()).thenReturn(1L);
        when(viewComponentSetting.getId()).thenReturn(1L);
        doNothing().when(viewComponentSetting).setComponent(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setId(Mockito.<Long>any());
        doNothing().when(viewComponentSetting).setStatus(anyBoolean());
        doNothing().when(viewComponentSetting).setTenantId(Mockito.<String>any());
        doNothing().when(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        viewComponentSetting.setComponent(1L);
        viewComponentSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        viewComponentSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        viewComponentSetting.setHospitalId("42");
        viewComponentSetting.setId(1L);
        viewComponentSetting.setStatus(true);
        viewComponentSetting.setTenantId("42");
        viewComponentSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ViewComponentSetting> viewComponentSettingList = new ArrayList<>();
        viewComponentSettingList.add(viewComponentSetting);
        Optional<List<ViewComponentSetting>> ofResult = Optional.of(viewComponentSettingList);
        ArrayList<ViewComponentSetting> viewComponentSettingList2 = new ArrayList<>();
        when(viewComponentSettingRepository.saveAll(Mockito.<Iterable<ViewComponentSetting>>any()))
                .thenReturn(viewComponentSettingList2);
        when(viewComponentSettingRepository.findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult);
        ViewComponentResource viewComponentResource = mock(ViewComponentResource.class);
        doNothing().when(viewComponentResource).setAttributeId(Mockito.<Long>any());
        doNothing().when(viewComponentResource).setAttributeName(Mockito.<String>any());
        doNothing().when(viewComponentResource).setComponentSettingId(Mockito.<Long>any());
        doNothing().when(viewComponentResource).setId(Mockito.<Long>any());
        doNothing().when(viewComponentResource).setPropertyId(Mockito.<Long>any());
        doNothing().when(viewComponentResource).setPropertyName(Mockito.<String>any());
        doNothing().when(viewComponentResource).setStatus(Mockito.<Boolean>any());
        viewComponentResource.setAttributeId(1L);
        viewComponentResource.setAttributeName("Deleting view component setting...");
        viewComponentResource.setComponentSettingId(1L);
        viewComponentResource.setId(1L);
        viewComponentResource.setPropertyId(1L);
        viewComponentResource.setPropertyName("Deleting view component setting...");
        viewComponentResource.setStatus(true);

        ArrayList<ViewComponentResource> viewComponentResourceList = new ArrayList<>();
        viewComponentResourceList.add(viewComponentResource);
        Optional<List<ViewComponentResource>> ofResult2 = Optional.of(viewComponentResourceList);
        when(viewComponentResourceRepository.saveAll(Mockito.<Iterable<ViewComponentResource>>any()))
                .thenReturn(new ArrayList<>());
        when(viewComponentResourceRepository.findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteViewComponentSettingsByComponentIdResult = viewComponentSettingServiceImpl
                .deleteViewComponentSettingsByComponentId(1L);
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.hasBody());
        assertTrue(actualDeleteViewComponentSettingsByComponentIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteViewComponentSettingsByComponentIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteViewComponentSettingsByComponentIdResult.getBody();
        assertEquals("DM_DMS_010", body.messageCode());
        assertEquals(viewComponentSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Id : 1 deleted", body.resultData());
        verify(viewComponentSettingRepository).saveAll(Mockito.<Iterable<ViewComponentSetting>>any());
        verify(viewComponentSettingRepository).findAllByComponentAndHospitalIdAndTenantIdAndStatus(Mockito.<Long>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Boolean>any());
        verify(viewComponentSetting).getComponent();
        verify(viewComponentSetting).getId();
        verify(viewComponentSetting).setComponent(Mockito.<Long>any());
        verify(viewComponentSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentSetting).setCreatedUser(Mockito.<String>any());
        verify(viewComponentSetting).setHospitalId(Mockito.<String>any());
        verify(viewComponentSetting).setId(Mockito.<Long>any());
        verify(viewComponentSetting, atLeast(1)).setStatus(anyBoolean());
        verify(viewComponentSetting).setTenantId(Mockito.<String>any());
        verify(viewComponentSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(viewComponentResourceRepository).saveAll(Mockito.<Iterable<ViewComponentResource>>any());
        verify(viewComponentResourceRepository).findAllByComponentSettingIdAndStatus(Mockito.<Long>any(),
                Mockito.<Boolean>any());
        verify(viewComponentResource).setAttributeId(Mockito.<Long>any());
        verify(viewComponentResource).setAttributeName(Mockito.<String>any());
        verify(viewComponentResource).setComponentSettingId(Mockito.<Long>any());
        verify(viewComponentResource).setId(Mockito.<Long>any());
        verify(viewComponentResource).setPropertyId(Mockito.<Long>any());
        verify(viewComponentResource).setPropertyName(Mockito.<String>any());
        verify(viewComponentResource, atLeast(1)).setStatus(Mockito.<Boolean>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }
}

