package com.api.documentmanagementservice.commons.mapper;

import com.api.documentmanagementservice.commons.DateTimeUtils;
import com.api.documentmanagementservice.model.dto.ScannerSettingDto;
import com.api.documentmanagementservice.model.dto.UploadSettingDto;
import com.api.documentmanagementservice.model.table.ScannerSetting;
import com.api.documentmanagementservice.model.table.UploadSetting;

import java.util.List;

public class CommonDataMapper {

    public static UploadSettingDto getUploadSettingResponse(UploadSetting uploadSetting) {

        return UploadSettingDto.builder()
                .id(uploadSetting.getId())
                .maxFileSize(uploadSetting.getMaxFileSize().floatValue())
                .fileSizeUnit(uploadSetting.getFileSizeUnit())
                .isSizeLimited(uploadSetting.getIsSizeLimited())
                .fileTypes(uploadSetting.getFileTypes())
                .fileNamingFormat(uploadSetting.getFileNamingFormat())
                .hospitalId(uploadSetting.getHospitalId())
                .tenantId(uploadSetting.getTenantId())
                .createdUser(uploadSetting.getCreatedUser())
                .createdDateTime(uploadSetting.getCreatedDateTime())
                .updatedDateTime(uploadSetting.getUpdatedDateTime())
                .build();
    }

    public static ScannerSettingDto getScannerSettingResponse(ScannerSetting scannerSetting) {

        return ScannerSettingDto.builder()
                .id(scannerSetting.getId())
                .name(scannerSetting.getName())
                .scannerName(scannerSetting.getScannerName())
                .qualityId(scannerSetting.getQualityId())
                .quality(scannerSetting.getQuality())
                .isDefaultProfile(scannerSetting.getIsDefaultProfile())
                .resolution(scannerSetting.getResolution())
                .pageSize(scannerSetting.getPageSize())
                .pageSizeName(scannerSetting.getPageSizeName())
                .rotate(scannerSetting.getRotate())
                .dpi(scannerSetting.getDpi())
                .pixelType(scannerSetting.getPixelType())
                .pixelTypeName(scannerSetting.getPixelTypeName())
                .hospitalId(scannerSetting.getHospitalId())
                .tenantId(scannerSetting.getTenantId())
                .createdUser(scannerSetting.getCreatedUser())
                .updatedDateTime(DateTimeUtils.getCurrentTime())
                .createdDateTime(DateTimeUtils.getCurrentTime())
                .build();
    }

    public static List<ScannerSettingDto> getScannerSettingResponse(List<ScannerSetting> scannerSettingList) {
        return scannerSettingList.stream().map(CommonDataMapper::getScannerSettingResponse).toList();
    }
}
