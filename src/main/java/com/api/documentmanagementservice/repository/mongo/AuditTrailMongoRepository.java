package com.api.documentmanagementservice.repository.mongo;

import com.api.documentmanagementservice.model.collection.AuditTrailCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditTrailMongoRepository extends MongoRepository<AuditTrailCollection, String> {
}
