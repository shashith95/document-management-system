package com.api.documentmanagementservice.model.table;

import com.api.documentmanagementservice.commons.DateTimeUtils;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.PropertyAttributeRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PROPERTY_ATTRIBUTES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PropertyAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROPERTY_ID")
    private Long propertyId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IS_DELETABLE", nullable = false)
    private Boolean isDeletable;

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

    public static PropertyAttribute getPropertyAttributeSaveObject(PropertyAttributeRecord propertyAttributeRecord,
                                                                   HeaderContext headerContext,
                                                                   FileProperty fileProperty) {
        return PropertyAttribute.builder()
                .propertyId(fileProperty.getId())
                .name(propertyAttributeRecord.name())
                .isDeletable(propertyAttributeRecord.isDeletable())
                .createdUser(headerContext.getUser())
                .hospitalId(headerContext.getHospitalId())
                .tenantId(headerContext.getTenantId())
                .updatedDateTime(DateTimeUtils.getCurrentTime())
                .createdDateTime(DateTimeUtils.getCurrentTime())
                .build();
    }

    public static PropertyAttribute getPropertyAttributeUpdateObject(PropertyAttributeRecord propertyAttributeRecord,
                                                                     HeaderContext headerContext,
                                                                     PropertyAttribute existingPropertyAttribute) {
        return PropertyAttribute.builder()
                .name(propertyAttributeRecord.name())
                .isDeletable(propertyAttributeRecord.isDeletable())
                .createdUser(headerContext.getUser())
                .updatedDateTime(DateTimeUtils.getCurrentTime())
                //keep existing values
                .id(existingPropertyAttribute.getId())
                .propertyId(existingPropertyAttribute.getPropertyId())
                .hospitalId(existingPropertyAttribute.getHospitalId())
                .tenantId(existingPropertyAttribute.getTenantId())
                .createdDateTime(existingPropertyAttribute.getCreatedDateTime())
                .build();
    }
}
