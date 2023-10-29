package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.RenameDocumentDto;
import com.api.documentmanagementservice.model.dto.UpdateDocumentBody;
import com.api.documentmanagementservice.model.dto.UpdateMultipleDocumentBody;
import org.springframework.http.ResponseEntity;

public interface DeleteRestoreService {
    ResponseEntity<CommonResponse> delete(UpdateDocumentBody updateDocumentBody);

    ResponseEntity<CommonResponse> deleteMultiple(UpdateMultipleDocumentBody updateMultipleDocumentBody);

    ResponseEntity<CommonResponse> restore(UpdateDocumentBody updateDocumentBody);

    ResponseEntity<CommonResponse> rename(RenameDocumentDto renameDocumentDto);
}
