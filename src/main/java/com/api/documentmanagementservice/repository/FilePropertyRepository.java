package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.dto.AllFileDetailsDto;
import com.api.documentmanagementservice.model.table.FileProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FilePropertyRepository extends JpaRepository<FileProperty, Long> {

    @Query("""
            SELECT fp
            FROM FileProperty fp
            WHERE (fp.hospitalId = :hospitalId AND fp.tenantId = :tenantId) OR (fp.hospitalId = '0' AND fp.tenantId = '0')
            """)
    Optional<List<FileProperty>> findFilePropertiesByHospitalIdAndTenantId(String hospitalId, String tenantId);

    @Query("""
              SELECT fp
              FROM FileProperty fp
              WHERE fp.hierarchyAvailable = :hierarchyAvailable
                AND ((fp.hospitalId = :hospitalId AND fp.tenantId = :tenantId) OR (fp.hospitalId = '0' AND fp.tenantId = '0'))
            """)
    Optional<List<FileProperty>> findFilePropertiesByHospitalIdAndTenantIdAAndHierarchy(String hospitalId,
                                                                                        String tenantId,
                                                                                        Boolean hierarchyAvailable);

    @Query("""
            SELECT DISTINCT fp.id
            FROM FileProperty fp
            WHERE fp.id IN (:idList)
                AND ((fp.hospitalId = :hospitalId AND fp.tenantId = :tenantId)
               OR (fp.hospitalId = '0' AND fp.tenantId = '0'))
             """)
    Optional<List<String>> findByIdListHospitalTenant(List<Long> idList, String hospitalId, String tenantId);

    Optional<FileProperty> findFilePropertiesByIdAndIsDeletable(Long propertyId, Boolean isDeletable);

    @Query("""
            SELECT NEW com.csi.csidocumentmanagementservice.model.dto.AllFileDetailsDto(
                   fp.id,
                   fp.name,
                   fp.isDeletable,
                   fp.isMandatory,
                   fp.isFreeText,
                   fp.isAddable,
                   fp.propertyType,
                   fp.tenantId,
                   fp.hospitalId,
                   fp.hierarchyAvailable,
                   fp.createdDateTime,
                   fp.updatedDateTime,
                   fp.createdUser,
                   fp.status,
                   da.defaultAttributeId,
                   pa.id,
                   pa.propertyId,
                   pa.name,
                   pa.isDeletable,
                   pa.hospitalId,
                   pa.tenantId,
                   pa.createdUser,
                   pa.createdDateTime,
                   pa.updatedDateTime)
            FROM FileProperty fp
                LEFT JOIN PropertyAttribute pa
            ON fp.id = pa.propertyId
                LEFT JOIN DefaultAttribute da ON fp.id = da.propertyId
            WHERE ((fp.hospitalId = :hospitalId AND fp.tenantId = :tenantId) OR (fp.hospitalId = '0' AND fp.tenantId = '0'))
            """)
    Optional<List<AllFileDetailsDto>> findAllFileDetails(String hospitalId,
                                                         String tenantId);

    @Query("""
            SELECT NEW com.csi.csidocumentmanagementservice.model.dto.AllFileDetailsDto(
                   fp.id,
                   fp.name,
                   fp.isDeletable,
                   fp.isMandatory,
                   fp.isFreeText,
                   fp.isAddable,
                   fp.propertyType,
                   fp.tenantId,
                   fp.hospitalId,
                   fp.hierarchyAvailable,
                   fp.createdDateTime,
                   fp.updatedDateTime,
                   fp.createdUser,
                   fp.status,
                   da.defaultAttributeId,
                   pa.id,
                   pa.propertyId,
                   pa.name,
                   pa.isDeletable,
                   pa.hospitalId,
                   pa.tenantId,
                   pa.createdUser,
                   pa.createdDateTime,
                   pa.updatedDateTime)
            FROM FileProperty fp
                LEFT JOIN PropertyAttribute pa
            ON fp.id = pa.propertyId
                LEFT JOIN DefaultAttribute da ON fp.id = da.propertyId
            WHERE fp.hierarchyAvailable = :hierarchyAvailable
            AND ((fp.hospitalId = :hospitalId AND fp.tenantId = :tenantId) OR (fp.hospitalId = '0' AND fp.tenantId = '0'))
            """)
    Optional<List<AllFileDetailsDto>> findAllFileDetailsWithHierarchy(String hospitalId,
                                                                      String tenantId,
                                                                      Boolean hierarchyAvailable);

    @Query("""
            SELECT fp
            FROM FileProperty fp
            WHERE LOWER(TRIM(fp.name)) = LOWER(TRIM(:name))
              AND ((fp.hospitalId = :hospitalId AND fp.tenantId = :tenantId)
                OR (fp.tenantId = '0' AND fp.hospitalId = '0'))
            """)
    Optional<List<FileProperty>> findExistingFilePropertiesByName(String name,
                                                                  String hospitalId,
                                                                  String tenantId);

    @Query("""
              SELECT fp
              FROM FileProperty fp
              WHERE fp.id = :id
                AND ((fp.hospitalId = :hospitalId AND fp.tenantId = :tenantId) OR (fp.hospitalId = '0' AND fp.tenantId = '0'))
            """)
    Optional<FileProperty> findFilePropertiesById(Long id,
                                                  String hospitalId,
                                                  String tenantId);


}
