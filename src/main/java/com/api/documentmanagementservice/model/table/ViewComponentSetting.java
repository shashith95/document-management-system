package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "VIEW_COMPONENT_SETTINGS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ViewComponentSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COMPONENT", nullable = false)
    private Long component;

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

    @Column(name = "STATUS", nullable = false)
    private boolean status;
}
