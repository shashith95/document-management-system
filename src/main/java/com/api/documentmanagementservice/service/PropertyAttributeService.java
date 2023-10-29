package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.DefaultAttributeRequest;
import com.api.documentmanagementservice.model.dto.request.PropertyAttributeRequest;
import org.springframework.http.ResponseEntity;

public interface PropertyAttributeService {
    ResponseEntity<CommonResponse> deleteAttributeById(Long attributeId) throws DmsException;

    ResponseEntity<CommonResponse> setDefaultAttribute(DefaultAttributeRequest defaultAttributeRequest) throws BadRequestException;

    ResponseEntity<CommonResponse> getDefaultAttribute(Long propertyId,
                                                       Long componentId);

    ResponseEntity<CommonResponse> createPropertyAttribute(PropertyAttributeRequest propertyAttributeRequest);
}
