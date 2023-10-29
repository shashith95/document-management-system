package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.ViewComponentSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewComponentSettingRepository extends JpaRepository<ViewComponentSetting, Long> {

    Boolean existsByComponentAndHospitalIdAndTenantIdAndStatus(Long componentId,
                                                               String hospitalId,
                                                               String tenantId,
                                                               Boolean status);

    Optional<List<ViewComponentSetting>> findAllByComponentAndHospitalIdAndTenantIdAndStatus(Long componentId,
                                                                                             String hospitalId,
                                                                                             String tenantId,
                                                                                             Boolean status);
}
