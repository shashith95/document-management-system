package com.api.documentmanagementservice.commons.mapper;

import com.api.documentmanagementservice.model.dto.ScannerSettingDto;
import com.api.documentmanagementservice.model.dto.UploadSettingDto;
import com.api.documentmanagementservice.model.table.ScannerSetting;
import com.api.documentmanagementservice.model.table.UploadSetting;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommonDataMapperTest {
    /**
     * Method under test: {@link CommonDataMapper#getUploadSettingResponse(UploadSetting)}
     */
    @Test
    void testGetUploadSettingResponse() {
        UploadSetting uploadSetting = new UploadSetting();
        uploadSetting.setCreatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        uploadSetting.setCreatedUser("Jan 1, 2020 8:00am GMT+0100");
        uploadSetting.setFileNamingFormat("File Naming Format");
        uploadSetting.setFileSizeUnit(3);
        uploadSetting.setFileTypes("File Types");
        uploadSetting.setHospitalId("42");
        uploadSetting.setId(1L);
        uploadSetting.setIsSizeLimited(true);
        uploadSetting.setMaxFileSize(3);
        uploadSetting.setTenantId("42");
        uploadSetting.setUpdatedDateTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        UploadSettingDto actualUploadSettingResponse = CommonDataMapper.getUploadSettingResponse(uploadSetting);
        assertEquals("00:00", actualUploadSettingResponse.createdDateTime().toLocalTime().toString());
        assertEquals("00:00", actualUploadSettingResponse.updatedDateTime().toLocalTime().toString());
        assertEquals("42", actualUploadSettingResponse.tenantId());
        assertEquals(3.0f, actualUploadSettingResponse.maxFileSize().floatValue());
        assertTrue(actualUploadSettingResponse.isSizeLimited());
        assertEquals(1L, actualUploadSettingResponse.id().longValue());
        assertEquals("42", actualUploadSettingResponse.hospitalId());
        assertEquals("File Types", actualUploadSettingResponse.fileTypes());
        assertEquals(3, actualUploadSettingResponse.fileSizeUnit().intValue());
        assertEquals("File Naming Format", actualUploadSettingResponse.fileNamingFormat());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualUploadSettingResponse.createdUser());
    }

    /**
     * Method under test: {@link CommonDataMapper#getScannerSettingResponse(ScannerSetting)}
     */
    @Test
    void testGetScannerSettingResponse() {
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
        ScannerSettingDto actualScannerSettingResponse = CommonDataMapper.getScannerSettingResponse(scannerSetting);
        assertEquals("42", actualScannerSettingResponse.tenantId());
        assertEquals("Scanner Name", actualScannerSettingResponse.scannerName());
        assertEquals(1, actualScannerSettingResponse.rotate().intValue());
        assertEquals(1, actualScannerSettingResponse.resolution().intValue());
        assertEquals(1, actualScannerSettingResponse.qualityId().intValue());
        assertEquals("Quality", actualScannerSettingResponse.quality());
        assertEquals("Pixel Type Name", actualScannerSettingResponse.pixelTypeName());
        assertEquals("Pixel Type", actualScannerSettingResponse.pixelType());
        assertEquals("Page Size Name", actualScannerSettingResponse.pageSizeName());
        assertEquals("Page Size", actualScannerSettingResponse.pageSize());
        assertEquals("Name", actualScannerSettingResponse.name());
        assertTrue(actualScannerSettingResponse.isDefaultProfile());
        assertEquals(1L, actualScannerSettingResponse.id().longValue());
        assertEquals("42", actualScannerSettingResponse.hospitalId());
        assertEquals(1, actualScannerSettingResponse.dpi().intValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualScannerSettingResponse.createdUser());
    }

    /**
     * Method under test: {@link CommonDataMapper#getScannerSettingResponse(List)}
     */
    @Test
    void testGetScannerSettingResponse2() {
        List<ScannerSettingDto> actualScannerSettingResponse = CommonDataMapper
                .getScannerSettingResponse(new ArrayList<>());
        assertTrue(actualScannerSettingResponse.isEmpty());
    }

    /**
     * Method under test: {@link CommonDataMapper#getScannerSettingResponse(List)}
     */
    @Test
    void testGetScannerSettingResponse3() {
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
        List<ScannerSettingDto> actualScannerSettingResponse = CommonDataMapper
                .getScannerSettingResponse(scannerSettingList);
        assertEquals(1, actualScannerSettingResponse.size());
    }

    /**
     * Method under test: {@link CommonDataMapper#getScannerSettingResponse(List)}
     */
    @Test
    void testGetScannerSettingResponse4() {
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
        List<ScannerSettingDto> actualScannerSettingResponse = CommonDataMapper
                .getScannerSettingResponse(scannerSettingList);
        assertEquals(2, actualScannerSettingResponse.size());
    }
}

