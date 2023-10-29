package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.ScannerSettingDto;
import com.api.documentmanagementservice.model.dto.request.ScannerSettingRequest;
import com.api.documentmanagementservice.model.table.ScannerSetting;
import com.api.documentmanagementservice.repository.ScannerSettingRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ScannerSettingImpl.class})
@ExtendWith(SpringExtension.class)
class ScannerSettingImplTest {
    @MockBean
    private HeaderContext headerContext;

    @Autowired
    private ScannerSettingImpl scannerSettingImpl;

    @MockBean
    private ScannerSettingRepository scannerSettingRepository;

    /**
     * Method under test: {@link ScannerSettingImpl#createOrUpdateScannerSetting(ScannerSettingRequest)}
     */
    @Test
    void testCreateOrUpdateScannerSetting() throws BadRequestException, DmsException {
        ScannerSetting scannerSetting = new ScannerSetting();
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScannerSetting> ofResult = Optional.of(scannerSetting);
        when(scannerSettingRepository.existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(true);
        when(scannerSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        assertThrows(DmsException.class,
                () -> scannerSettingImpl.createOrUpdateScannerSetting(new ScannerSettingRequest(id, "Name", "Scanner Name", 1,
                        "Quality", true, 1, "Page Size", "Page Size Name", 1, 1, "Pixel Type", "Pixel Type Name")));
        verify(scannerSettingRepository).existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any());
        verify(scannerSettingRepository).findById(Mockito.<Long>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#createOrUpdateScannerSetting(ScannerSettingRequest)}
     */
    @Test
    void testCreateOrUpdateScannerSetting2() throws BadRequestException, DmsException {
        ScannerSetting scannerSetting = new ScannerSetting();
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScannerSetting> ofResult = Optional.of(scannerSetting);

        ScannerSetting scannerSetting2 = new ScannerSetting();
        scannerSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting2.setDpi(1);
        scannerSetting2.setHospitalId("42");
        scannerSetting2.setId(1L);
        scannerSetting2.setIsDefaultProfile(true);
        scannerSetting2.setName("Name");
        scannerSetting2.setPageSize("Page Size");
        scannerSetting2.setPageSizeName("Page Size Name");
        scannerSetting2.setPixelType("Pixel Type");
        scannerSetting2.setPixelTypeName("Pixel Type Name");
        scannerSetting2.setQuality("Quality");
        scannerSetting2.setQualityId(1);
        scannerSetting2.setResolution(1);
        scannerSetting2.setRotate(1);
        scannerSetting2.setScannerName("Scanner Name");
        scannerSetting2.setTenantId("42");
        scannerSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scannerSettingRepository.existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(false);
        when(scannerSettingRepository.save(Mockito.<ScannerSetting>any())).thenReturn(scannerSetting2);
        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        when(scannerSettingRepository.saveAll(Mockito.<Iterable<ScannerSetting>>any())).thenReturn(scannerSettingList);
        Optional<List<ScannerSetting>> ofResult2 = Optional.of(new ArrayList<>());
        when(scannerSettingRepository.findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(scannerSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateScannerSettingResult = scannerSettingImpl
                .createOrUpdateScannerSetting(new ScannerSettingRequest(id, "Name", "Scanner Name", 1, "Quality", true, 1,
                        "Page Size", "Page Size Name", 1, 1, "Pixel Type", "Pixel Type Name"));
        assertTrue(actualCreateOrUpdateScannerSettingResult.hasBody());
        assertTrue(actualCreateOrUpdateScannerSettingResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateScannerSettingResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateScannerSettingResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(scannerSettingList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(scannerSettingRepository).existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any());
        verify(scannerSettingRepository).save(Mockito.<ScannerSetting>any());
        verify(scannerSettingRepository).saveAll(Mockito.<Iterable<ScannerSetting>>any());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scannerSettingRepository).findById(Mockito.<Long>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#createOrUpdateScannerSetting(ScannerSettingRequest)}
     */
    @Test
    void testCreateOrUpdateScannerSetting3() throws BadRequestException, DmsException {
        ScannerSetting scannerSetting = new ScannerSetting();
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScannerSetting> ofResult = Optional.of(scannerSetting);
        ScannerSetting scannerSetting2 = mock(ScannerSetting.class);
        when(scannerSetting2.getId()).thenReturn(1L);
        doNothing().when(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scannerSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(scannerSetting2).setDpi(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setId(Mockito.<Long>any());
        doNothing().when(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        doNothing().when(scannerSetting2).setName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSize(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSizeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelType(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQuality(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQualityId(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setResolution(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setRotate(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setScannerName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scannerSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting2.setDpi(1);
        scannerSetting2.setHospitalId("42");
        scannerSetting2.setId(1L);
        scannerSetting2.setIsDefaultProfile(true);
        scannerSetting2.setName("Name");
        scannerSetting2.setPageSize("Page Size");
        scannerSetting2.setPageSizeName("Page Size Name");
        scannerSetting2.setPixelType("Pixel Type");
        scannerSetting2.setPixelTypeName("Pixel Type Name");
        scannerSetting2.setQuality("Quality");
        scannerSetting2.setQualityId(1);
        scannerSetting2.setResolution(1);
        scannerSetting2.setRotate(1);
        scannerSetting2.setScannerName("Scanner Name");
        scannerSetting2.setTenantId("42");
        scannerSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scannerSettingRepository.existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(false);
        when(scannerSettingRepository.save(Mockito.<ScannerSetting>any())).thenReturn(scannerSetting2);
        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        when(scannerSettingRepository.saveAll(Mockito.<Iterable<ScannerSetting>>any())).thenReturn(scannerSettingList);
        Optional<List<ScannerSetting>> ofResult2 = Optional.of(new ArrayList<>());
        when(scannerSettingRepository.findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(scannerSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateScannerSettingResult = scannerSettingImpl
                .createOrUpdateScannerSetting(new ScannerSettingRequest(id, "Name", "Scanner Name", 1, "Quality", true, 1,
                        "Page Size", "Page Size Name", 1, 1, "Pixel Type", "Pixel Type Name"));
        assertTrue(actualCreateOrUpdateScannerSettingResult.hasBody());
        assertTrue(actualCreateOrUpdateScannerSettingResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateScannerSettingResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateScannerSettingResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(scannerSettingList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(scannerSettingRepository).existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any());
        verify(scannerSettingRepository).save(Mockito.<ScannerSetting>any());
        verify(scannerSettingRepository).saveAll(Mockito.<Iterable<ScannerSetting>>any());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scannerSettingRepository).findById(Mockito.<Long>any());
        verify(scannerSetting2, atLeast(1)).getId();
        verify(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scannerSetting2).setCreatedUser(Mockito.<String>any());
        verify(scannerSetting2).setDpi(Mockito.<Integer>any());
        verify(scannerSetting2).setHospitalId(Mockito.<String>any());
        verify(scannerSetting2).setId(Mockito.<Long>any());
        verify(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        verify(scannerSetting2).setName(Mockito.<String>any());
        verify(scannerSetting2).setPageSize(Mockito.<String>any());
        verify(scannerSetting2).setPageSizeName(Mockito.<String>any());
        verify(scannerSetting2).setPixelType(Mockito.<String>any());
        verify(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        verify(scannerSetting2).setQuality(Mockito.<String>any());
        verify(scannerSetting2).setQualityId(Mockito.<Integer>any());
        verify(scannerSetting2).setResolution(Mockito.<Integer>any());
        verify(scannerSetting2).setRotate(Mockito.<Integer>any());
        verify(scannerSetting2).setScannerName(Mockito.<String>any());
        verify(scannerSetting2).setTenantId(Mockito.<String>any());
        verify(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#createOrUpdateScannerSetting(ScannerSettingRequest)}
     */
    @Test
    void testCreateOrUpdateScannerSetting4() throws BadRequestException, DmsException {
        ScannerSetting scannerSetting = new ScannerSetting();
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScannerSetting> ofResult = Optional.of(scannerSetting);
        ScannerSetting scannerSetting2 = mock(ScannerSetting.class);
        when(scannerSetting2.getId()).thenReturn(1L);
        doNothing().when(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scannerSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(scannerSetting2).setDpi(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setId(Mockito.<Long>any());
        doNothing().when(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        doNothing().when(scannerSetting2).setName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSize(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSizeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelType(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQuality(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQualityId(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setResolution(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setRotate(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setScannerName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scannerSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting2.setDpi(1);
        scannerSetting2.setHospitalId("42");
        scannerSetting2.setId(1L);
        scannerSetting2.setIsDefaultProfile(true);
        scannerSetting2.setName("Name");
        scannerSetting2.setPageSize("Page Size");
        scannerSetting2.setPageSizeName("Page Size Name");
        scannerSetting2.setPixelType("Pixel Type");
        scannerSetting2.setPixelTypeName("Pixel Type Name");
        scannerSetting2.setQuality("Quality");
        scannerSetting2.setQualityId(1);
        scannerSetting2.setResolution(1);
        scannerSetting2.setRotate(1);
        scannerSetting2.setScannerName("Scanner Name");
        scannerSetting2.setTenantId("42");
        scannerSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ScannerSetting scannerSetting3 = new ScannerSetting();
        scannerSetting3.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting3.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting3.setDpi(1);
        scannerSetting3.setHospitalId("42");
        scannerSetting3.setId(1L);
        scannerSetting3.setIsDefaultProfile(true);
        scannerSetting3.setName("Creating or updating scanner setting...");
        scannerSetting3.setPageSize("Creating or updating scanner setting...");
        scannerSetting3.setPageSizeName("Creating or updating scanner setting...");
        scannerSetting3.setPixelType("Creating or updating scanner setting...");
        scannerSetting3.setPixelTypeName("Creating or updating scanner setting...");
        scannerSetting3.setQuality("Creating or updating scanner setting...");
        scannerSetting3.setQualityId(1);
        scannerSetting3.setResolution(1);
        scannerSetting3.setRotate(1);
        scannerSetting3.setScannerName("Creating or updating scanner setting...");
        scannerSetting3.setTenantId("42");
        scannerSetting3.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        scannerSettingList.add(scannerSetting3);
        Optional<List<ScannerSetting>> ofResult2 = Optional.of(scannerSettingList);
        when(scannerSettingRepository.existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(false);
        when(scannerSettingRepository.save(Mockito.<ScannerSetting>any())).thenReturn(scannerSetting2);
        ArrayList<ScannerSetting> scannerSettingList2 = new ArrayList<>();
        when(scannerSettingRepository.saveAll(Mockito.<Iterable<ScannerSetting>>any())).thenReturn(scannerSettingList2);
        when(scannerSettingRepository.findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(scannerSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateScannerSettingResult = scannerSettingImpl
                .createOrUpdateScannerSetting(new ScannerSettingRequest(id, "Name", "Scanner Name", 1, "Quality", true, 1,
                        "Page Size", "Page Size Name", 1, 1, "Pixel Type", "Pixel Type Name"));
        assertTrue(actualCreateOrUpdateScannerSettingResult.hasBody());
        assertTrue(actualCreateOrUpdateScannerSettingResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateScannerSettingResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateScannerSettingResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(scannerSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(scannerSettingRepository).existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any());
        verify(scannerSettingRepository).save(Mockito.<ScannerSetting>any());
        verify(scannerSettingRepository).saveAll(Mockito.<Iterable<ScannerSetting>>any());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scannerSettingRepository).findById(Mockito.<Long>any());
        verify(scannerSetting2, atLeast(1)).getId();
        verify(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scannerSetting2).setCreatedUser(Mockito.<String>any());
        verify(scannerSetting2).setDpi(Mockito.<Integer>any());
        verify(scannerSetting2).setHospitalId(Mockito.<String>any());
        verify(scannerSetting2).setId(Mockito.<Long>any());
        verify(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        verify(scannerSetting2).setName(Mockito.<String>any());
        verify(scannerSetting2).setPageSize(Mockito.<String>any());
        verify(scannerSetting2).setPageSizeName(Mockito.<String>any());
        verify(scannerSetting2).setPixelType(Mockito.<String>any());
        verify(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        verify(scannerSetting2).setQuality(Mockito.<String>any());
        verify(scannerSetting2).setQualityId(Mockito.<Integer>any());
        verify(scannerSetting2).setResolution(Mockito.<Integer>any());
        verify(scannerSetting2).setRotate(Mockito.<Integer>any());
        verify(scannerSetting2).setScannerName(Mockito.<String>any());
        verify(scannerSetting2).setTenantId(Mockito.<String>any());
        verify(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#createOrUpdateScannerSetting(ScannerSettingRequest)}
     */
    @Test
    void testCreateOrUpdateScannerSetting5() throws BadRequestException, DmsException {
        ScannerSetting scannerSetting = new ScannerSetting();
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScannerSetting> ofResult = Optional.of(scannerSetting);
        ScannerSetting scannerSetting2 = mock(ScannerSetting.class);
        when(scannerSetting2.getId()).thenReturn(1L);
        doNothing().when(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scannerSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(scannerSetting2).setDpi(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setId(Mockito.<Long>any());
        doNothing().when(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        doNothing().when(scannerSetting2).setName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSize(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSizeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelType(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQuality(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQualityId(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setResolution(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setRotate(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setScannerName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scannerSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting2.setDpi(1);
        scannerSetting2.setHospitalId("42");
        scannerSetting2.setId(1L);
        scannerSetting2.setIsDefaultProfile(true);
        scannerSetting2.setName("Name");
        scannerSetting2.setPageSize("Page Size");
        scannerSetting2.setPageSizeName("Page Size Name");
        scannerSetting2.setPixelType("Pixel Type");
        scannerSetting2.setPixelTypeName("Pixel Type Name");
        scannerSetting2.setQuality("Quality");
        scannerSetting2.setQualityId(1);
        scannerSetting2.setResolution(1);
        scannerSetting2.setRotate(1);
        scannerSetting2.setScannerName("Scanner Name");
        scannerSetting2.setTenantId("42");
        scannerSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ScannerSetting scannerSetting3 = new ScannerSetting();
        scannerSetting3.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting3.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting3.setDpi(1);
        scannerSetting3.setHospitalId("42");
        scannerSetting3.setId(1L);
        scannerSetting3.setIsDefaultProfile(true);
        scannerSetting3.setName("Creating or updating scanner setting...");
        scannerSetting3.setPageSize("Creating or updating scanner setting...");
        scannerSetting3.setPageSizeName("Creating or updating scanner setting...");
        scannerSetting3.setPixelType("Creating or updating scanner setting...");
        scannerSetting3.setPixelTypeName("Creating or updating scanner setting...");
        scannerSetting3.setQuality("Creating or updating scanner setting...");
        scannerSetting3.setQualityId(1);
        scannerSetting3.setResolution(1);
        scannerSetting3.setRotate(1);
        scannerSetting3.setScannerName("Creating or updating scanner setting...");
        scannerSetting3.setTenantId("42");
        scannerSetting3.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ScannerSetting scannerSetting4 = new ScannerSetting();
        scannerSetting4.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting4.setCreatedUser("Creating or updating scanner setting...");
        scannerSetting4.setDpi(0);
        scannerSetting4.setHospitalId("Creating or updating scanner setting...");
        scannerSetting4.setId(2L);
        scannerSetting4.setIsDefaultProfile(false);
        scannerSetting4.setName("Updating scanner setting for ID: {}");
        scannerSetting4.setPageSize("Updating scanner setting for ID: {}");
        scannerSetting4.setPageSizeName("Updating scanner setting for ID: {}");
        scannerSetting4.setPixelType("Updating scanner setting for ID: {}");
        scannerSetting4.setPixelTypeName("Updating scanner setting for ID: {}");
        scannerSetting4.setQuality("Updating scanner setting for ID: {}");
        scannerSetting4.setQualityId(2);
        scannerSetting4.setResolution(0);
        scannerSetting4.setRotate(0);
        scannerSetting4.setScannerName("Updating scanner setting for ID: {}");
        scannerSetting4.setTenantId("Creating or updating scanner setting...");
        scannerSetting4.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        scannerSettingList.add(scannerSetting4);
        scannerSettingList.add(scannerSetting3);
        Optional<List<ScannerSetting>> ofResult2 = Optional.of(scannerSettingList);
        when(scannerSettingRepository.existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(false);
        when(scannerSettingRepository.save(Mockito.<ScannerSetting>any())).thenReturn(scannerSetting2);
        ArrayList<ScannerSetting> scannerSettingList2 = new ArrayList<>();
        when(scannerSettingRepository.saveAll(Mockito.<Iterable<ScannerSetting>>any())).thenReturn(scannerSettingList2);
        when(scannerSettingRepository.findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(scannerSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateScannerSettingResult = scannerSettingImpl
                .createOrUpdateScannerSetting(new ScannerSettingRequest(id, "Name", "Scanner Name", 1, "Quality", true, 1,
                        "Page Size", "Page Size Name", 1, 1, "Pixel Type", "Pixel Type Name"));
        assertTrue(actualCreateOrUpdateScannerSettingResult.hasBody());
        assertTrue(actualCreateOrUpdateScannerSettingResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateScannerSettingResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateScannerSettingResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(scannerSettingList2, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(scannerSettingRepository).existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any());
        verify(scannerSettingRepository).save(Mockito.<ScannerSetting>any());
        verify(scannerSettingRepository).saveAll(Mockito.<Iterable<ScannerSetting>>any());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scannerSettingRepository).findById(Mockito.<Long>any());
        verify(scannerSetting2, atLeast(1)).getId();
        verify(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scannerSetting2).setCreatedUser(Mockito.<String>any());
        verify(scannerSetting2).setDpi(Mockito.<Integer>any());
        verify(scannerSetting2).setHospitalId(Mockito.<String>any());
        verify(scannerSetting2).setId(Mockito.<Long>any());
        verify(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        verify(scannerSetting2).setName(Mockito.<String>any());
        verify(scannerSetting2).setPageSize(Mockito.<String>any());
        verify(scannerSetting2).setPageSizeName(Mockito.<String>any());
        verify(scannerSetting2).setPixelType(Mockito.<String>any());
        verify(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        verify(scannerSetting2).setQuality(Mockito.<String>any());
        verify(scannerSetting2).setQualityId(Mockito.<Integer>any());
        verify(scannerSetting2).setResolution(Mockito.<Integer>any());
        verify(scannerSetting2).setRotate(Mockito.<Integer>any());
        verify(scannerSetting2).setScannerName(Mockito.<String>any());
        verify(scannerSetting2).setTenantId(Mockito.<String>any());
        verify(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#createOrUpdateScannerSetting(ScannerSettingRequest)}
     */
    @Test
    void testCreateOrUpdateScannerSetting6() throws BadRequestException, DmsException {
        ScannerSetting scannerSetting = mock(ScannerSetting.class);
        when(scannerSetting.getId()).thenReturn(1L);
        when(scannerSetting.getCreatedDateTime()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        doNothing().when(scannerSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scannerSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(scannerSetting).setDpi(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(scannerSetting).setId(Mockito.<Long>any());
        doNothing().when(scannerSetting).setIsDefaultProfile(Mockito.<Boolean>any());
        doNothing().when(scannerSetting).setName(Mockito.<String>any());
        doNothing().when(scannerSetting).setPageSize(Mockito.<String>any());
        doNothing().when(scannerSetting).setPageSizeName(Mockito.<String>any());
        doNothing().when(scannerSetting).setPixelType(Mockito.<String>any());
        doNothing().when(scannerSetting).setPixelTypeName(Mockito.<String>any());
        doNothing().when(scannerSetting).setQuality(Mockito.<String>any());
        doNothing().when(scannerSetting).setQualityId(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setResolution(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setRotate(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setScannerName(Mockito.<String>any());
        doNothing().when(scannerSetting).setTenantId(Mockito.<String>any());
        doNothing().when(scannerSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        Optional<ScannerSetting> ofResult = Optional.of(scannerSetting);
        ScannerSetting scannerSetting2 = mock(ScannerSetting.class);
        when(scannerSetting2.getId()).thenReturn(1L);
        doNothing().when(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scannerSetting2).setCreatedUser(Mockito.<String>any());
        doNothing().when(scannerSetting2).setDpi(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setHospitalId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setId(Mockito.<Long>any());
        doNothing().when(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        doNothing().when(scannerSetting2).setName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSize(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPageSizeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelType(Mockito.<String>any());
        doNothing().when(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQuality(Mockito.<String>any());
        doNothing().when(scannerSetting2).setQualityId(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setResolution(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setRotate(Mockito.<Integer>any());
        doNothing().when(scannerSetting2).setScannerName(Mockito.<String>any());
        doNothing().when(scannerSetting2).setTenantId(Mockito.<String>any());
        doNothing().when(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scannerSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting2.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting2.setDpi(1);
        scannerSetting2.setHospitalId("42");
        scannerSetting2.setId(1L);
        scannerSetting2.setIsDefaultProfile(true);
        scannerSetting2.setName("Name");
        scannerSetting2.setPageSize("Page Size");
        scannerSetting2.setPageSizeName("Page Size Name");
        scannerSetting2.setPixelType("Pixel Type");
        scannerSetting2.setPixelTypeName("Pixel Type Name");
        scannerSetting2.setQuality("Quality");
        scannerSetting2.setQualityId(1);
        scannerSetting2.setResolution(1);
        scannerSetting2.setRotate(1);
        scannerSetting2.setScannerName("Scanner Name");
        scannerSetting2.setTenantId("42");
        scannerSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(scannerSettingRepository.existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any())).thenReturn(false);
        when(scannerSettingRepository.save(Mockito.<ScannerSetting>any())).thenReturn(scannerSetting2);
        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        when(scannerSettingRepository.saveAll(Mockito.<Iterable<ScannerSetting>>any())).thenReturn(scannerSettingList);
        Optional<List<ScannerSetting>> ofResult2 = Optional.of(new ArrayList<>());
        when(scannerSettingRepository.findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(ofResult2);
        when(scannerSettingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(headerContext.getUser()).thenReturn("User");
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        Optional<Long> id = Optional.<Long>of(1L);
        ResponseEntity<CommonResponse> actualCreateOrUpdateScannerSettingResult = scannerSettingImpl
                .createOrUpdateScannerSetting(new ScannerSettingRequest(id, "Name", "Scanner Name", 1, "Quality", true, 1,
                        "Page Size", "Page Size Name", 1, 1, "Pixel Type", "Pixel Type Name"));
        assertTrue(actualCreateOrUpdateScannerSettingResult.hasBody());
        assertTrue(actualCreateOrUpdateScannerSettingResult.getHeaders().isEmpty());
        assertEquals(200, actualCreateOrUpdateScannerSettingResult.getStatusCodeValue());
        CommonResponse body = actualCreateOrUpdateScannerSettingResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertEquals(scannerSettingList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 1 updated", body.resultData());
        verify(scannerSettingRepository).existsByNameAndHospitalIdAndTenantIdAndIdNot(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any());
        verify(scannerSettingRepository).save(Mockito.<ScannerSetting>any());
        verify(scannerSettingRepository).saveAll(Mockito.<Iterable<ScannerSetting>>any());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantIdAndIsDefaultProfile(Mockito.<String>any(),
                Mockito.<String>any(), Mockito.<Boolean>any());
        verify(scannerSettingRepository).findById(Mockito.<Long>any());
        verify(scannerSetting2, atLeast(1)).getId();
        verify(scannerSetting2).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scannerSetting2).setCreatedUser(Mockito.<String>any());
        verify(scannerSetting2).setDpi(Mockito.<Integer>any());
        verify(scannerSetting2).setHospitalId(Mockito.<String>any());
        verify(scannerSetting2).setId(Mockito.<Long>any());
        verify(scannerSetting2).setIsDefaultProfile(Mockito.<Boolean>any());
        verify(scannerSetting2).setName(Mockito.<String>any());
        verify(scannerSetting2).setPageSize(Mockito.<String>any());
        verify(scannerSetting2).setPageSizeName(Mockito.<String>any());
        verify(scannerSetting2).setPixelType(Mockito.<String>any());
        verify(scannerSetting2).setPixelTypeName(Mockito.<String>any());
        verify(scannerSetting2).setQuality(Mockito.<String>any());
        verify(scannerSetting2).setQualityId(Mockito.<Integer>any());
        verify(scannerSetting2).setResolution(Mockito.<Integer>any());
        verify(scannerSetting2).setRotate(Mockito.<Integer>any());
        verify(scannerSetting2).setScannerName(Mockito.<String>any());
        verify(scannerSetting2).setTenantId(Mockito.<String>any());
        verify(scannerSetting2).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(scannerSetting).getId();
        verify(scannerSetting).getCreatedDateTime();
        verify(scannerSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scannerSetting).setCreatedUser(Mockito.<String>any());
        verify(scannerSetting).setDpi(Mockito.<Integer>any());
        verify(scannerSetting).setHospitalId(Mockito.<String>any());
        verify(scannerSetting).setId(Mockito.<Long>any());
        verify(scannerSetting).setIsDefaultProfile(Mockito.<Boolean>any());
        verify(scannerSetting).setName(Mockito.<String>any());
        verify(scannerSetting).setPageSize(Mockito.<String>any());
        verify(scannerSetting).setPageSizeName(Mockito.<String>any());
        verify(scannerSetting).setPixelType(Mockito.<String>any());
        verify(scannerSetting).setPixelTypeName(Mockito.<String>any());
        verify(scannerSetting).setQuality(Mockito.<String>any());
        verify(scannerSetting).setQualityId(Mockito.<Integer>any());
        verify(scannerSetting).setResolution(Mockito.<Integer>any());
        verify(scannerSetting).setRotate(Mockito.<Integer>any());
        verify(scannerSetting).setScannerName(Mockito.<String>any());
        verify(scannerSetting).setTenantId(Mockito.<String>any());
        verify(scannerSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
        verify(headerContext).getUser();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#getScannerSetting()}
     */
    @Test
    void testGetScannerSetting() {
        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        Optional<List<ScannerSetting>> ofResult = Optional.of(scannerSettingList);
        when(scannerSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScannerSetting = scannerSettingImpl.getScannerSetting();
        assertTrue(actualScannerSetting.hasBody());
        assertTrue(actualScannerSetting.getHeaders().isEmpty());
        assertEquals(200, actualScannerSetting.getStatusCodeValue());
        CommonResponse body = actualScannerSetting.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertEquals(scannerSettingList, body.errorList());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        assertTrue(((Collection<Object>) body.resultData()).isEmpty());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#getScannerSetting()}
     */
    @Test
    void testGetScannerSetting2() {
        ScannerSetting scannerSetting = new ScannerSetting();
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        scannerSettingList.add(scannerSetting);
        Optional<List<ScannerSetting>> ofResult = Optional.of(scannerSettingList);
        when(scannerSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScannerSetting = scannerSettingImpl.getScannerSetting();
        assertTrue(actualScannerSetting.hasBody());
        assertTrue(actualScannerSetting.getHeaders().isEmpty());
        assertEquals(200, actualScannerSetting.getStatusCodeValue());
        CommonResponse body = actualScannerSetting.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        assertEquals(1, ((Collection<ScannerSettingDto>) body.resultData()).size());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#getScannerSetting()}
     */
    @Test
    void testGetScannerSetting3() {
        ScannerSetting scannerSetting = new ScannerSetting();
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ScannerSetting scannerSetting2 = new ScannerSetting();
        scannerSetting2.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting2.setCreatedUser("UTC");
        scannerSetting2.setDpi(0);
        scannerSetting2.setHospitalId("UTC");
        scannerSetting2.setId(2L);
        scannerSetting2.setIsDefaultProfile(false);
        scannerSetting2.setName("Name");
        scannerSetting2.setPageSize("Page Size");
        scannerSetting2.setPageSizeName("Page Size Name");
        scannerSetting2.setPixelType("Pixel Type");
        scannerSetting2.setPixelTypeName("Pixel Type Name");
        scannerSetting2.setQuality("Quality");
        scannerSetting2.setQualityId(2);
        scannerSetting2.setResolution(0);
        scannerSetting2.setRotate(0);
        scannerSetting2.setScannerName("Scanner Name");
        scannerSetting2.setTenantId("UTC");
        scannerSetting2.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        scannerSettingList.add(scannerSetting2);
        scannerSettingList.add(scannerSetting);
        Optional<List<ScannerSetting>> ofResult = Optional.of(scannerSettingList);
        when(scannerSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScannerSetting = scannerSettingImpl.getScannerSetting();
        assertTrue(actualScannerSetting.hasBody());
        assertTrue(actualScannerSetting.getHeaders().isEmpty());
        assertEquals(200, actualScannerSetting.getStatusCodeValue());
        CommonResponse body = actualScannerSetting.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        assertEquals(2, ((Collection<ScannerSettingDto>) body.resultData()).size());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#getScannerSetting()}
     */
    @Test
    void testGetScannerSetting4() {
        Optional<List<ScannerSetting>> emptyResult = Optional.empty();
        when(scannerSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(emptyResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScannerSetting = scannerSettingImpl.getScannerSetting();
        assertTrue(actualScannerSetting.hasBody());
        assertTrue(actualScannerSetting.getHeaders().isEmpty());
        assertEquals(404, actualScannerSetting.getStatusCodeValue());
        CommonResponse body = actualScannerSetting.getBody();
        assertEquals("DM_DMS_032", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Scanner profile is not available", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(headerContext, atLeast(1)).getHospitalId();
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#getScannerSetting()}
     */
    @Test
    void testGetScannerSetting5() {
        ScannerSetting scannerSetting = mock(ScannerSetting.class);
        when(scannerSetting.getIsDefaultProfile()).thenReturn(true);
        when(scannerSetting.getDpi()).thenReturn(1);
        when(scannerSetting.getQualityId()).thenReturn(1);
        when(scannerSetting.getResolution()).thenReturn(1);
        when(scannerSetting.getRotate()).thenReturn(1);
        when(scannerSetting.getId()).thenReturn(1L);
        when(scannerSetting.getCreatedUser()).thenReturn("Jan 1, 2020 8:00am GMT+0100");
        when(scannerSetting.getHospitalId()).thenReturn("42");
        when(scannerSetting.getName()).thenReturn("Name");
        when(scannerSetting.getPageSize()).thenReturn("Page Size");
        when(scannerSetting.getPageSizeName()).thenReturn("Page Size Name");
        when(scannerSetting.getPixelType()).thenReturn("Pixel Type");
        when(scannerSetting.getPixelTypeName()).thenReturn("Pixel Type Name");
        when(scannerSetting.getQuality()).thenReturn("Quality");
        when(scannerSetting.getScannerName()).thenReturn("Scanner Name");
        when(scannerSetting.getTenantId()).thenReturn("42");
        doNothing().when(scannerSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        doNothing().when(scannerSetting).setCreatedUser(Mockito.<String>any());
        doNothing().when(scannerSetting).setDpi(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setHospitalId(Mockito.<String>any());
        doNothing().when(scannerSetting).setId(Mockito.<Long>any());
        doNothing().when(scannerSetting).setIsDefaultProfile(Mockito.<Boolean>any());
        doNothing().when(scannerSetting).setName(Mockito.<String>any());
        doNothing().when(scannerSetting).setPageSize(Mockito.<String>any());
        doNothing().when(scannerSetting).setPageSizeName(Mockito.<String>any());
        doNothing().when(scannerSetting).setPixelType(Mockito.<String>any());
        doNothing().when(scannerSetting).setPixelTypeName(Mockito.<String>any());
        doNothing().when(scannerSetting).setQuality(Mockito.<String>any());
        doNothing().when(scannerSetting).setQualityId(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setResolution(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setRotate(Mockito.<Integer>any());
        doNothing().when(scannerSetting).setScannerName(Mockito.<String>any());
        doNothing().when(scannerSetting).setTenantId(Mockito.<String>any());
        doNothing().when(scannerSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        scannerSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        scannerSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        scannerSetting.setDpi(1);
        scannerSetting.setHospitalId("42");
        scannerSetting.setId(1L);
        scannerSetting.setIsDefaultProfile(true);
        scannerSetting.setName("Name");
        scannerSetting.setPageSize("Page Size");
        scannerSetting.setPageSizeName("Page Size Name");
        scannerSetting.setPixelType("Pixel Type");
        scannerSetting.setPixelTypeName("Pixel Type Name");
        scannerSetting.setQuality("Quality");
        scannerSetting.setQualityId(1);
        scannerSetting.setResolution(1);
        scannerSetting.setRotate(1);
        scannerSetting.setScannerName("Scanner Name");
        scannerSetting.setTenantId("42");
        scannerSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<ScannerSetting> scannerSettingList = new ArrayList<>();
        scannerSettingList.add(scannerSetting);
        Optional<List<ScannerSetting>> ofResult = Optional.of(scannerSettingList);
        when(scannerSettingRepository.findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        when(headerContext.getHospitalId()).thenReturn("42");
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualScannerSetting = scannerSettingImpl.getScannerSetting();
        assertTrue(actualScannerSetting.hasBody());
        assertTrue(actualScannerSetting.getHeaders().isEmpty());
        assertEquals(200, actualScannerSetting.getStatusCodeValue());
        CommonResponse body = actualScannerSetting.getBody();
        assertEquals("DM_DMS_009", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Returned data successfully", body.message());
        assertEquals(1, ((Collection<ScannerSettingDto>) body.resultData()).size());
        verify(scannerSettingRepository).findAllByHospitalIdAndTenantId(Mockito.<String>any(), Mockito.<String>any());
        verify(scannerSetting).getIsDefaultProfile();
        verify(scannerSetting).getDpi();
        verify(scannerSetting).getQualityId();
        verify(scannerSetting).getResolution();
        verify(scannerSetting).getRotate();
        verify(scannerSetting).getId();
        verify(scannerSetting).getCreatedUser();
        verify(scannerSetting).getHospitalId();
        verify(scannerSetting).getName();
        verify(scannerSetting).getPageSize();
        verify(scannerSetting).getPageSizeName();
        verify(scannerSetting).getPixelType();
        verify(scannerSetting).getPixelTypeName();
        verify(scannerSetting).getQuality();
        verify(scannerSetting).getScannerName();
        verify(scannerSetting).getTenantId();
        verify(scannerSetting).setCreatedDateTime(Mockito.<LocalDateTime>any());
        verify(scannerSetting).setCreatedUser(Mockito.<String>any());
        verify(scannerSetting).setDpi(Mockito.<Integer>any());
        verify(scannerSetting).setHospitalId(Mockito.<String>any());
        verify(scannerSetting).setId(Mockito.<Long>any());
        verify(scannerSetting).setIsDefaultProfile(Mockito.<Boolean>any());
        verify(scannerSetting).setName(Mockito.<String>any());
        verify(scannerSetting).setPageSize(Mockito.<String>any());
        verify(scannerSetting).setPageSizeName(Mockito.<String>any());
        verify(scannerSetting).setPixelType(Mockito.<String>any());
        verify(scannerSetting).setPixelTypeName(Mockito.<String>any());
        verify(scannerSetting).setQuality(Mockito.<String>any());
        verify(scannerSetting).setQualityId(Mockito.<Integer>any());
        verify(scannerSetting).setResolution(Mockito.<Integer>any());
        verify(scannerSetting).setRotate(Mockito.<Integer>any());
        verify(scannerSetting).setScannerName(Mockito.<String>any());
        verify(scannerSetting).setTenantId(Mockito.<String>any());
        verify(scannerSetting).setUpdatedDateTime(Mockito.<LocalDateTime>any());
        verify(headerContext).getHospitalId();
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link ScannerSettingImpl#deleteScannerProfileById(Long)}
     */
    @Test
    void testDeleteScannerProfileById() {
        doNothing().when(scannerSettingRepository).deleteById(Mockito.<Long>any());
        ResponseEntity<CommonResponse> actualDeleteScannerProfileByIdResult = scannerSettingImpl
                .deleteScannerProfileById(1L);
        assertTrue(actualDeleteScannerProfileByIdResult.hasBody());
        assertTrue(actualDeleteScannerProfileByIdResult.getHeaders().isEmpty());
        assertEquals(200, actualDeleteScannerProfileByIdResult.getStatusCodeValue());
        CommonResponse body = actualDeleteScannerProfileByIdResult.getBody();
        assertEquals("DM_DMS_032", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(scannerSettingRepository).deleteById(Mockito.<Long>any());
    }
}

