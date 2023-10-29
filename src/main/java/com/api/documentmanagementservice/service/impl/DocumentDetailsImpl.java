package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.model.table.DocumentDetails;
import com.api.documentmanagementservice.repository.DocumentDetailsRepository;
import com.api.documentmanagementservice.service.DocumentDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class DocumentDetailsImpl implements DocumentDetailsService {

    private final DocumentDetailsRepository documentDetailsRepository;

    @Override
    public DocumentDetails createDocumentDetails(Long directoryId, String documentId) {
        DocumentDetails documentDetails = DocumentDetails.builder()
                .id(documentId)
                .directoryDetailsId(directoryId)
                .documentName(documentId)
                .status(true)
                .createdDateTime(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        return documentDetailsRepository.save(documentDetails);
    }
}
