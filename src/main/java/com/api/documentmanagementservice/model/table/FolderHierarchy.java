package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "FOLDER_HIERARCHY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class FolderHierarchy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "IS_CUSTOM", nullable = false)
    private Boolean isCustom;

    @Column(name = "USER", nullable = false)
    private String user;

    @Column(name = "HOSPITAL_ID", nullable = false)
    private String hospitalId;

    @Column(name = "TENANT_ID", nullable = false)
    private String tenantId;

    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATE_TIME", nullable = false)
    private LocalDateTime updatedDateTime;
}
