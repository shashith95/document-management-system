package com.api.documentmanagementservice.repository;

import com.api.documentmanagementservice.model.table.BucketDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketDetailsRepository extends JpaRepository<BucketDetails, Integer> {

    Optional<BucketDetails> findByName(String name);


}
