package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.DocumentDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DocumentDetailsRepository extends JpaRepository<DocumentDetails, String> {
    @Transactional
    @Modifying
    @Query("UPDATE DocumentDetails d SET d.directoryDetailsId = :directoryDetailsId, d.documentName = :documentName, d.status = true WHERE d.id = :id")
    int updateDocumentDetailsById(String id, Long directoryDetailsId, String documentName);
}
