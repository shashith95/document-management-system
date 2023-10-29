package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.HierarchyResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HierarchyResourceRepository extends JpaRepository<HierarchyResource, Long> {

    void deleteAllByFolderHierarchyId(Long fileHierarchyId);

    void deleteAllByPropertyId(Long propertyId);

    Optional<List<HierarchyResource>> findAllByFolderHierarchyIdOrderByIndex(Long fileHierarchyId);
}
