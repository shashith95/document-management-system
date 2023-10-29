package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.DirectoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DirectoryDetailsRepository extends JpaRepository<DirectoryDetails, Long> {

    @Query("SELECT d.id FROM DirectoryDetails d WHERE d.path = :path AND d.tenant = :tenant AND d.bucket = :bucket")
    Optional<Long> findDirectoryIdByPathAndTenantAndBucket(
            @Param("path") String path,
            @Param("tenant") String tenant,
            @Param("bucket") String bucket
    );

}
