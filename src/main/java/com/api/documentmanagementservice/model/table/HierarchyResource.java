package com.api.documentmanagementservice.model.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "HIERARCHY_RESOURCES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HierarchyResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROPERTY_ID", nullable = false)
    private Long propertyId;

    @Column(name = "FOLDER_HIERARCHY_ID", nullable = false)
    private Long folderHierarchyId;

    @Column(name = "INDEX", nullable = false)
    private Integer index;

    @CreationTimestamp
    @Column(name = "CREATED_DATE_TIME", nullable = false)
    private LocalDateTime createdDateTime;
}
