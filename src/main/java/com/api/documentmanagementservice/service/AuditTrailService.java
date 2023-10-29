package com.api.documentmanagementservice.service;


import com.api.documentmanagementservice.model.context.HeaderContext;

public interface AuditTrailService {
    void saveAuditTrail(String documentId, String document, int value, HeaderContext headerContext, Object o);
}
