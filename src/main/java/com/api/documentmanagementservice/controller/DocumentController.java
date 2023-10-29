package com.api.documentmanagementservice.controller;

import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.RenameDocumentDto;
import com.api.documentmanagementservice.model.dto.UpdateDocumentBody;
import com.api.documentmanagementservice.model.dto.UpdateMultipleDocumentBody;
import com.api.documentmanagementservice.service.DeleteRestoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {
    private final DeleteRestoreService deleteRestoreService;

    @PostMapping("/delete")
    public ResponseEntity<CommonResponse> deleteFile(@RequestBody @Valid UpdateDocumentBody updateDocumentBody) {
        return deleteRestoreService.delete(updateDocumentBody);
    }

    @PostMapping("/delete-multiple")
    public ResponseEntity<CommonResponse> deleteMultipleFile(@RequestBody UpdateMultipleDocumentBody
                                                                     updateMultipleDocumentBody) {
        return deleteRestoreService.deleteMultiple(updateMultipleDocumentBody);
    }

    @PostMapping("/restore")
    public ResponseEntity<CommonResponse> restoreFile(@RequestBody @Valid UpdateDocumentBody updateDocumentBody) {
        return deleteRestoreService.restore(updateDocumentBody);
    }

    @PostMapping("/rename")
    public ResponseEntity<CommonResponse> renameFile(@RequestBody @Valid RenameDocumentDto renameDocumentDto) {
        return deleteRestoreService.rename(renameDocumentDto);
    }

}
