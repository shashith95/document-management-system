package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {


    Optional<UserPreference> findByUserAndPreferenceTypeAndHospitalIdAndTenantId(String user,
                                                                                 String preferenceType,
                                                                                 String hospitalId,
                                                                                 String tenantId);

    Optional<UserPreference> findByIdAndUserAndPreferenceTypeAndHospitalIdAndTenantId(Long id,
                                                                                      String user,
                                                                                      String preferenceType,
                                                                                      String hospitalId,
                                                                                      String tenantId);


}
