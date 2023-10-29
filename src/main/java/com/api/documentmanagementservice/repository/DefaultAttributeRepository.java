package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.DefaultAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultAttributeRepository extends JpaRepository<DefaultAttribute, Long> {

    Optional<DefaultAttribute> findByComponentIdAndPropertyIdAndHospitalIdAndTenantId(Long componentId,
                                                                                      Long propertyId,
                                                                                      String hospitalId,
                                                                                      String tenantId);

    void deleteAllByComponentIdAndPropertyIdAndDefaultAttributeIdAndHospitalIdAndTenantId(Long componentId,
                                                                                          Long propertyId,
                                                                                          Long defaultAttributeId,
                                                                                          String hospitalId,
                                                                                          String tenantId);

    void deleteAllByComponentIdAndHospitalIdAndTenantId(Long componentId,
                                                        String hospitalId,
                                                        String tenantId);
}
