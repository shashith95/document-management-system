package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.dto.BucketDataDto;
import com.api.documentmanagementservice.model.table.FileDetails;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FileDetailsRepository extends JpaRepository<FileDetails, String> {

    Optional<List<FileDetails>> findByDocumentIdAndStatus(String documentId, Boolean status);

    Optional<List<FileDetails>> findByDocumentIdIn(Set<String> documentIdList);

    @Query("""
            SELECT NEW com.csi.csidocumentmanagementservice.model.dto.BucketDataDto(dir.bucket, dir.path, doc.id, fd.fileName)
                       FROM FileDetails fd
                       JOIN DocumentDetails doc ON fd.documentId = doc.id
                       JOIN DirectoryDetails dir ON doc.directoryDetailsId = dir.id
                       WHERE fd.id = :fileHash
                       """)
    Optional<BucketDataDto> getBucketDataRecordById(String fileHash);

    @Transactional
    void deleteFileDetailsByDocumentId(String documentId);

    boolean existsByDocumentId(String documentId);

    @Modifying
    @Query("UPDATE FileDetails fd SET fd.fileName = :fileName WHERE fd.documentId = :documentId")
    int updateFileNameById(String documentId, String fileName);

}
