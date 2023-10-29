package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.ScanComponentSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScanComponentSettingsRepository extends JpaRepository<ScanComponentSettings, Long> {

    Optional<ScanComponentSettings> findByComponentAndHospitalIdAndTenantIdAndStatus(Long componentId,
                                                                                     String hospitalId,
                                                                                     String tenantId,
                                                                                     Boolean status);

    Optional<ScanComponentSettings> findByIdAndComponentAndHospitalIdAndTenantIdAndStatus(Long id,
                                                                                          Long componentId,
                                                                                          String hospitalId,
                                                                                          String tenantId,
                                                                                          Boolean status);
}
