package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.ScanComponentResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScanComponentResourceRepository extends JpaRepository<ScanComponentResource, Long> {

    void deleteAllByPropertyId(Long propertyId);

    void deleteAllByAttributeId(Long attributeId);

    Optional<List<ScanComponentResource>> findAllByComponentSettingIdAndStatus(Long componentSettingId,
                                                                               Boolean status);
}
