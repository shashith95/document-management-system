package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.RenameDocumentDto;
import com.api.documentmanagementservice.model.dto.UpdateDocumentBody;
import com.api.documentmanagementservice.model.dto.UpdateMultipleDocumentBody;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import com.api.documentmanagementservice.service.CustomMongoService;
import com.api.documentmanagementservice.service.DeleteRestoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

import static com.api.documentmanagementservice.commons.ResponseHandler.generateResponse;
import static com.api.documentmanagementservice.commons.constant.CommonContent.*;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.DB_NO_DATA;
import static com.api.documentmanagementservice.commons.constant.EventTypes.DELETE;
import static com.api.documentmanagementservice.commons.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteRestoreServiceImpl implements DeleteRestoreService {
    private final CustomMongoService customMongoService;
    private final FileDetailsRepository fileDetailsRepository;
    private final HeaderContext headerContext;

    /**
     * Delete a document and update the audit trail.
     *
     * @param updateDocumentBody The request body for document deletion.
     * @return A response entity indicating the result of the deletion.
     */
    @Override
    public ResponseEntity<CommonResponse> delete(UpdateDocumentBody updateDocumentBody) {
        if (customMongoService.doesDocumentExist(updateDocumentBody.id()) &&
                updateDocumentBody.tenant().equals(headerContext.getTenantId())) {
            // Request is from the same tenant, proceed with deletion
            return deleteDBRecords(updateDocumentBody, headerContext);
        } else {
            // Document doesn't exist, return a conflict response
            return generateResponse(CONFLICT, CONFLICT.getReasonPhrase(), DM_DMS_052.name());
        }
    }

    /**
     * Delete the document and update the audit trail.
     *
     * @param updateDocumentBody The request body for document deletion.
     * @param headerContext      The header values for the request.
     * @return A response entity indicating the result of the deletion.
     */
    private ResponseEntity<CommonResponse> deleteDBRecords(UpdateDocumentBody updateDocumentBody, HeaderContext headerContext) {
        // Create condition and dataToUpdate maps for updating the document
        Map<String, Object> condition = Map.of(
                ID.getContent(), updateDocumentBody.id(),
                STATUS.getContent(), true,
                TENANT.getContent(), updateDocumentBody.tenant());

        Map<String, Object> dataToUpdate = Map.of(
                STATUS.getContent(), false,
                DELETE_REASON.getContent(), updateDocumentBody.deleteReason());

        // Update the document in the MongoDB collection
        customMongoService.updateDocument(condition, dataToUpdate);
        log.info("File Deleted Successfully!");

        customMongoService.saveAuditTrail(updateDocumentBody.id(), headerContext, DELETE);

        return generateResponse(HttpStatus.CREATED, DELETED.getMessage(), DM_DMS_002.name(),
                "Deleted the document : " + updateDocumentBody.id());
    }

    /**
     * Delete multiple documents and update the audit trail for each.
     *
     * @param updateMultipleDocumentBody The request body for multiple document deletion.
     * @return A response entity indicating the result of the deletion.
     */
    @Override
    public ResponseEntity<CommonResponse> deleteMultiple(UpdateMultipleDocumentBody updateMultipleDocumentBody) {

        Arrays.stream(updateMultipleDocumentBody.id())
                .filter(customMongoService::doesDocumentExist)
                .forEach(id -> {
                    if (updateMultipleDocumentBody.tenant().equals(headerContext.getTenantId())) {
                        // Request is from the same tenant, proceed with deletion
                        UpdateDocumentBody updateDocumentBody = new UpdateDocumentBody(id, updateMultipleDocumentBody.tenant(), updateMultipleDocumentBody.deleteReason());
                        deleteDBRecords(updateDocumentBody, headerContext);
                    }
                });

        return generateResponse(HttpStatus.CREATED, DELETED.getMessage(), DM_DMS_002.name(),
                "Deleted the documents: " + Arrays.toString(updateMultipleDocumentBody.id()));
    }

    /**
     * Restore a document and update the audit trail.
     *
     * @param updateDocumentBody The request body for document restoration.
     * @return A response entity indicating the result of the restoration.
     */
    @Override
    public ResponseEntity<CommonResponse> restore(UpdateDocumentBody updateDocumentBody) {

        if (customMongoService.doesDocumentExist(updateDocumentBody.id()) &&
                updateDocumentBody.tenant().equals(headerContext.getTenantId())) {
            // Request is from the same tenant, proceed with deletion
            return restoreDBRecords(updateDocumentBody, headerContext);
        } else {
            // Document doesn't exist, return a conflict response
            return generateResponse(CONFLICT, CONFLICT.getReasonPhrase(), DM_DMS_052.name());
        }
    }

    /**
     * Restores a document in the MongoDB collection by updating its status to 'true'.
     *
     * @param restoreDocumentBody The request body containing the information needed for restoration.
     * @param headerContext       The header context containing information about the request.
     * @return A ResponseEntity containing a CommonResponse indicating the result of the restoration operation.
     */
    private ResponseEntity<CommonResponse> restoreDBRecords(UpdateDocumentBody restoreDocumentBody, HeaderContext headerContext) {
        // Create condition and dataToUpdate maps for updating the document
        Map<String, Object> condition = Map.of(
                ID.getContent(), restoreDocumentBody.id(),
                STATUS.getContent(), false,
                TENANT.getContent(), restoreDocumentBody.tenant());

        Map<String, Object> dataToUpdate = Map.of(STATUS.getContent(), true);

        // Update the document in the MongoDB collection
        customMongoService.updateDocument(condition, dataToUpdate);
        log.info("File restored Successfully!"); 

        // Logging and audit trail
        customMongoService.saveAuditTrail(restoreDocumentBody.id(), headerContext, DELETE);

        // Return a response indicating successful deletion
        return generateResponse(HttpStatus.CREATED, UPDATED.getMessage(), DM_DMS_002.name(),
                "Restored the document : " + restoreDocumentBody.id());
    }

    /**
     * Rename a document.
     *
     * @param renameDocumentDto The request DTO containing document renaming details.
     * @return A response entity indicating the result of the renaming.
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> rename(RenameDocumentDto renameDocumentDto) {
        if (fileDetailsRepository.existsByDocumentId(renameDocumentDto.id())) {
            fileDetailsRepository.updateFileNameById(renameDocumentDto.id(), renameDocumentDto.documentName());
            log.info("Document renamed Successfully!");
            return generateResponse(HttpStatus.CREATED, UPDATED.getMessage(), DM_DMS_002.name(),
                    "Id : " + renameDocumentDto.id() + " renamed");

        } else {
            log.warn("Document doesn't exist, please check the id: {}", renameDocumentDto.id());
            return generateResponse(CONFLICT,
                    "Document doesn't exist, please check the id",
                    DB_NO_DATA.name());
        }
    }

}
