package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "DEFAULT_ATTRIBUTE")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class DefaultAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COMPONENT_ID", nullable = false)
    private Long componentId;

    @Column(name = "PROPERTY_ID", nullable = false)
    private Long propertyId;

    @Column(name = "DEFAULT_ATTRIBUTE_ID", nullable = false)
    private Long defaultAttributeId;

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
}
