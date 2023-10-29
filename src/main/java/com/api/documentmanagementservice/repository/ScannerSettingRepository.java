package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.ScannerSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScannerSettingRepository extends JpaRepository<ScannerSetting, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(ss) > 0 THEN TRUE ELSE FALSE END FROM ScannerSetting ss
            WHERE TRIM(LOWER(ss.name)) = TRIM(LOWER(:name))
            AND ss.hospitalId = :hospitalId
            AND ss.tenantId = :tenantId
            """)
    boolean existsByNameAndHospitalIdAndTenantId(String name, String hospitalId, String tenantId);

    @Query("""
            SELECT CASE WHEN COUNT(ss) > 0 THEN TRUE ELSE FALSE END FROM ScannerSetting ss
            WHERE TRIM(LOWER(ss.name)) = TRIM(LOWER(:name))
            AND ss.hospitalId = :hospitalId
            AND ss.tenantId = :tenantId
            AND ss.id != :id
            """)
    boolean existsByNameAndHospitalIdAndTenantIdAndIdNot(String name, String hospitalId, String tenantId, Long id);

    Optional<List<ScannerSetting>> findAllByHospitalIdAndTenantId(String hospitalId, String tenantId);

    Optional<List<ScannerSetting>> findAllByHospitalIdAndTenantIdAndIsDefaultProfile(String hospitalId,
                                                                                     String tenantId,
                                                                                     Boolean isDefault);
}
