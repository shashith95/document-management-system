package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UploadService {
    ResponseEntity<CommonResponse> uploadFile(HttpServletRequest request, String documentData, String fileData, String base64File) throws BadRequestException, DmsException, JsonProcessingException;

    ResponseEntity<Map<String, Object>> getMongoDoc(String documentId) throws DmsException;

    ResponseEntity<Map<String, Object>> findAndReplaceDoc(String documentId, String newMongoData) throws DmsException, JsonProcessingException;

}
