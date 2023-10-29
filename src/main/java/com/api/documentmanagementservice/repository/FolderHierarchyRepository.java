package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.dto.PropertyListDto;
import com.api.documentmanagementservice.model.table.FolderHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FolderHierarchyRepository extends JpaRepository<FolderHierarchy, Long> {

    Optional<FolderHierarchy> findByIdAndUserAndIsCustomAndHospitalIdAndTenantId(Long id,
                                                                                 String user,
                                                                                 Boolean isCustom,
                                                                                 String hospitalId,
                                                                                 String tenantId);

    Optional<FolderHierarchy> findByIdAndIsCustomAndHospitalIdAndTenantId(Long id,
                                                                          Boolean isCustom,
                                                                          String hospitalId,
                                                                          String tenantId);

    Optional<List<FolderHierarchy>> findByUserAndHospitalIdAndTenantId(String user,
                                                                       String hospitalId,
                                                                       String tenantId);

    Optional<List<FolderHierarchy>> findByUserAndHospitalIdAndTenantIdAndIsCustom(String user,
                                                                                  String hospitalId,
                                                                                  String tenantId,
                                                                                  Boolean isCustom);

    Optional<List<FolderHierarchy>> findByHospitalIdAndTenantId(String hospitalId,
                                                                String tenantId);

    Optional<List<FolderHierarchy>> findByHospitalIdAndTenantIdAndIsCustom(String hospitalId,
                                                                           String tenantId,
                                                                           Boolean isCustom);

    @Query("""

                        SELECT NEW com.csi.csidocumentmanagementservice.model.dto.PropertyListDto( hr.index, fp.name)
             FROM FileProperty fp
                     INNER JOIN HierarchyResource hr ON fp.id = hr.propertyId
                     INNER JOIN FolderHierarchy fh ON hr.folderHierarchyId = fh.id
            WHERE fh.tenantId = :tenantId
              AND fh.hospitalId = :hospitalId
              AND fh.user = :user
              AND fh.isCustom = :isCustom 
            order by hr.index
            """)
    Optional<List<PropertyListDto>> findPropertiesByUserAndIsCustomAndHospitalIdAndTenantId(String user,
                                                                                            Boolean isCustom,
                                                                                            String hospitalId,
                                                                                            String tenantId);
}
