package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.FilePropertyRequest;
import com.api.documentmanagementservice.model.dto.request.PropertyRenameRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface FilePropertyService {

    ResponseEntity<CommonResponse> getAllFileProperties(Optional<Boolean> hierarchyAvailable);

    ResponseEntity<CommonResponse> getAllProperties(Map<String, Object> requestMap);

    ResponseEntity<CommonResponse> renameProperty(PropertyRenameRequest propertyRenameRequest);

    ResponseEntity<CommonResponse> updatePropertiesByDocumentId(Map<String, Object> updatePropertyRequestMap) throws DmsException;

    ResponseEntity<CommonResponse> createOrUpdateFileProperty(FilePropertyRequest filePropertyRequest) throws BadRequestException, DmsException;

    ResponseEntity<CommonResponse> deletePropertyById(Long propertyId) throws BadRequestException, DmsException;

}
