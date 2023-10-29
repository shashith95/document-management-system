package com.api.documentmanagementservice.model.table;

import com.api.documentmanagementservice.commons.DateTimeUtils;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.request.SettingUploadRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "UPLOAD_SETTINGS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MAX_FILE_SIZE", nullable = false)
    private Integer maxFileSize;

    @Column(name = "FILE_SIZE_UNIT", nullable = false)
    private Integer fileSizeUnit;

    @Column(name = "IS_SIZE_LIMITED", nullable = false)
    private Boolean isSizeLimited;

    @Column(name = "FILE_TYPES", nullable = false)
    private String fileTypes;

    @Column(name = "FILE_NAMING_FORMAT", nullable = false)
    private String fileNamingFormat;

    @Column(name = "HOSPITAL_ID", nullable = false)
    private String hospitalId;

    @Column(name = "TENANT_ID", nullable = false)
    private String tenantId;

    @Column(name = "CREATED_USER", nullable = false)
    private String createdUser;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATE_TIME", nullable = false)
    private LocalDateTime updatedDateTime;

    public static UploadSetting getUploadSettingSaveObject(SettingUploadRequest settingUploadRequest,
                                                           HeaderContext headerContext,
                                                           UploadSetting exitingSetting) {
        if (settingUploadRequest.id().isEmpty()) {
            return UploadSetting.builder()
                    .maxFileSize(settingUploadRequest.maxFileSize().intValue())
                    .fileSizeUnit(settingUploadRequest.fileSizeUnit())
                    .isSizeLimited(settingUploadRequest.isSizeLimited())
                    .fileTypes(settingUploadRequest.fileTypes())
                    .fileNamingFormat(settingUploadRequest.fileNamingFormat())
                    .hospitalId(headerContext.getHospitalId())
                    .tenantId(headerContext.getTenantId())
                    .createdUser(headerContext.getUser())
                    .updatedDateTime(DateTimeUtils.getCurrentTime())
                    .createdDateTime(DateTimeUtils.getCurrentTime())
                    .build();
        } else {
            exitingSetting.setMaxFileSize(settingUploadRequest.maxFileSize().intValue());
            exitingSetting.setFileSizeUnit(settingUploadRequest.fileSizeUnit());
            exitingSetting.setIsSizeLimited(settingUploadRequest.isSizeLimited());
            exitingSetting.setFileTypes(settingUploadRequest.fileTypes());
            exitingSetting.setFileNamingFormat(settingUploadRequest.fileNamingFormat());
            exitingSetting.setCreatedUser(headerContext.getUser());
            exitingSetting.setUpdatedDateTime(DateTimeUtils.getCurrentTime());
            exitingSetting.setHospitalId(headerContext.getHospitalId());
            exitingSetting.setTenantId(headerContext.getTenantId());
            return exitingSetting;
        }
    }
}
