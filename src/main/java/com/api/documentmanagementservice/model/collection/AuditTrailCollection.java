package com.api.documentmanagementservice.model.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "audit_trail")
@Builder
public class AuditTrailCollection {

    @Id
    private String id;
    private String source;
    private String sourceId;
    private String tenantId;
    private String user;
    private String eventDateTime;
    private Integer eventType;
    private String hospitalId;
}
