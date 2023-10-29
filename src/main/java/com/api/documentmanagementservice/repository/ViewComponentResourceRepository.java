package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.ViewComponentResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ViewComponentResourceRepository extends JpaRepository<ViewComponentResource, Long> {

    void deleteAllByPropertyId(Long propertyId);

    void deleteAllByAttributeId(Long attributeId);

    Optional<List<ViewComponentResource>> findAllByComponentSettingIdAndStatus(Long componentSettingId,
                                                                               Boolean status);
}
