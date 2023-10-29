package com.api.documentmanagementservice.controller;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.service.UploadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/upload")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> upload(@RequestParam("document-data") String documentData,
                                                 @RequestParam("file-data") String fileData,
                                                 @NotNull HttpServletRequest request)
            throws BadRequestException, DmsException, JsonProcessingException {
        log.info("File upload API");
        return uploadService.uploadFile(request, documentData, fileData, null);
    }

    @PostMapping("/base64")
    public ResponseEntity<CommonResponse> uploadAsBase64(@RequestParam("document-data") String documentData,
                                                         @RequestParam("file-data") String fileData,
                                                         @RequestParam("base64File") String base64File)
            throws BadRequestException, DmsException, JsonProcessingException {
        log.info("Base64 file upload API");
        return uploadService.uploadFile(null, documentData, fileData, base64File);
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<Map<String, Object>> getMongoDoc(@RequestParam("documentId") String documentId,
                                                           @RequestParam("newMongoData") String newMongoData)
            throws IOException, DmsException {
        log.info("Get data by document ID: {} API", documentId);
        return uploadService.findAndReplaceDoc(documentId, newMongoData);
    }
}