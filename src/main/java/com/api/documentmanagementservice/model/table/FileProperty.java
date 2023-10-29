package com.api.documentmanagementservice.model.table;

import com.api.documentmanagementservice.commons.DateTimeUtils;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.request.FilePropertyRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.lang.Boolean.TRUE;

@Entity
@Table(name = "FILE_PROPERTY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IS_DELETABLE", nullable = false)
    private Boolean isDeletable;

    @Column(name = "IS_MANDATORY", nullable = false)
    private Boolean isMandatory;

    @Column(name = "IS_FREE_TEXT", nullable = false)
    private Boolean isFreeText;

    @Column(name = "IS_ADDABLE", nullable = false)
    private Boolean isAddable;

    @Column(name = "PROPERTY_TYPE")
    private Integer propertyType;

    @Column(name = "TENANT_ID", nullable = false)
    private String tenantId;

    @Column(name = "HOSPITAL_ID", nullable = false)
    private String hospitalId;

    @Column(name = "HIERARCHY_AVAILABLE", nullable = false)
    private Boolean hierarchyAvailable;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATE_TIME", nullable = false)
    private LocalDateTime updatedDateTime;

    @Column(name = "CREATED_USER", nullable = false)
    private String createdUser;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;

    public static FileProperty generateFileProperty(HeaderContext header,
                                                    FilePropertyRequest request,
                                                    Optional<FileProperty> existingFileProperty) {
        LocalDateTime currentTime = DateTimeUtils.getCurrentTime();

        FileProperty.FilePropertyBuilder builder = FileProperty.builder()
                .name(request.name())
                .isDeletable(request.isDeletable())
                .isMandatory(request.isMandatory())
                .isFreeText(request.isFreeText())
                .isAddable(request.isAddable())
                .propertyType(request.propertyType())
                .tenantId(header.getTenantId())
                .hospitalId(header.getHospitalId())
                .hierarchyAvailable(request.hierarchyAvailable())
                .createdDateTime(currentTime)
                .updatedDateTime(currentTime)
                .createdUser(header.getUser())
                .status(TRUE);

        existingFileProperty.ifPresent(fileProperty ->
                builder.id(fileProperty.getId()).createdDateTime(fileProperty.getCreatedDateTime()));

        return builder.build();
    }
}
