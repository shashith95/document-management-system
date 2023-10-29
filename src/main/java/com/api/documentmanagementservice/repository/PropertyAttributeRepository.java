package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.PropertyAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PropertyAttributeRepository extends JpaRepository<PropertyAttribute, Long> {

    Optional<List<PropertyAttribute>> findByPropertyId(Long propertyId);

    void deleteAllByPropertyId(Long propertyId);

    Optional<PropertyAttribute> findAllByIdAndIsDeletable(Long attributeId, Boolean isDeletable);

    Optional<PropertyAttribute> findByIdAndPropertyId(Long attributeId, Long propertyId);

    @Query("""
            SELECT pa
            FROM PropertyAttribute pa
            WHERE pa.propertyId = :propertyId
              AND LOWER(TRIM(pa.name)) = LOWER(TRIM(:name))
              AND ((pa.hospitalId = :hospitalId AND pa.tenantId = :tenantId)
                OR (pa.tenantId = '0' AND pa.hospitalId = '0'))
            """)
    Optional<PropertyAttribute> findIdByPropertyIdAndTrimmedNameAndHospitalIdAndTenantId(Long propertyId,
                                                                                         String name,
                                                                                         String hospitalId,
                                                                                         String tenantId);
}
