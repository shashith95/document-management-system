package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DirectoryService {
    ResponseEntity<CommonResponse> getDirectory(Map<String, Object> requestBody) throws BadRequestException;
}
