package com.api.documentmanagementservice.model.table;

import com.api.documentmanagementservice.commons.DateTimeUtils;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.request.ScannerSettingRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SCANNER_SETTINGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScannerSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SCANNER_NAME")
    private String scannerName;

    @Column(name = "QUALITY_ID")
    private Integer qualityId;

    @Column(name = "QUALITY")
    private String quality;

    @Column(name = "IS_DEFAULT_PROFILE")
    private Boolean isDefaultProfile;

    @Column(name = "RESOLUTION")
    private Integer resolution;

    @Column(name = "PAGE_SIZE")
    private String pageSize;

    @Column(name = "PAGE_SIZE_NAME")
    private String pageSizeName;

    @Column(name = "ROTATE")
    private Integer rotate;

    @Column(name = "DPI")
    private Integer dpi;

    @Column(name = "PIXEL_TYPE")
    private String pixelType;

    @Column(name = "PIXEL_TYPE_NAME")
    private String pixelTypeName;

    @Column(name = "HOSPITAL_ID")
    private String hospitalId;

    @Column(name = "TENANT_ID")
    private String tenantId;

    @Column(name = "CREATED_USER")
    private String createdUser;

    @Column(name = "CREATED_DATE_TIME")
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATE_TIME")
    private LocalDateTime updatedDateTime;

    public static ScannerSetting generateScannerSettingObject(ScannerSettingRequest scannerSettingRequest,
                                                              HeaderContext headerContext,
                                                              ScannerSetting existingSettings,
                                                              boolean isForSave) {
        ScannerSettingBuilder builder = ScannerSetting.builder()
                .name(scannerSettingRequest.name())
                .scannerName(scannerSettingRequest.scannerName())
                .qualityId(scannerSettingRequest.qualityId())
                .quality(scannerSettingRequest.quality())
                .isDefaultProfile(scannerSettingRequest.isDefaultProfile())
                .resolution(scannerSettingRequest.resolution())
                .pageSize(scannerSettingRequest.pageSize())
                .pageSizeName(scannerSettingRequest.pageSizeName())
                .rotate(scannerSettingRequest.rotate())
                .dpi(scannerSettingRequest.dpi())
                .pixelType(scannerSettingRequest.pixelType())
                .pixelTypeName(scannerSettingRequest.pixelTypeName())
                .hospitalId(headerContext.getHospitalId())
                .tenantId(headerContext.getTenantId())
                .createdUser(headerContext.getUser())
                .updatedDateTime(DateTimeUtils.getCurrentTime());

        if (isForSave) {
            builder.createdDateTime(DateTimeUtils.getCurrentTime());
        } else {
            builder.id(existingSettings.getId());
            builder.createdDateTime(existingSettings.getCreatedDateTime());
        }

        return builder.build();
    }
}
