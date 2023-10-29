package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "SCAN_COMPONENT_SETTINGS")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScanComponentSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COMPONENT", nullable = false)
    private Long component;

    @Column(name = "DEFAULT_SCANNER_PROFILE_ID", nullable = false)
    private Long defaultScannerProfileId;

    @Column(name = "HOSPITAL_ID", nullable = false, length = 45)
    private String hospitalId;

    @Column(name = "TENANT_ID", nullable = false, length = 45)
    private String tenantId;

    @Column(name = "CREATED_USER", nullable = false, length = 45)
    private String createdUser;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATE_TIME", nullable = false)
    private LocalDateTime updatedDateTime;

    @Column(name = "STATUS", nullable = false)
    private Boolean status;
}
