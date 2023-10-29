package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.model.table.DocumentDetails;

public interface DocumentDetailsService {
    DocumentDetails createDocumentDetails(Long directoryId, String documentId);

}
