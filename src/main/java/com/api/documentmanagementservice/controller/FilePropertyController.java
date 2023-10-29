package com.api.documentmanagementservice.controller;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.commons.validator.PropertyUpdateRequestValidator;
import com.api.documentmanagementservice.commons.validator.TenantIdValidator;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.service.FilePropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/property")
@RequiredArgsConstructor
@Validated
@Slf4j
public class FilePropertyController {

    private final FilePropertyService filePropertyService;
    private final PropertyUpdateRequestValidator propertyUpdateRequestValidator;
    private final TenantIdValidator tenantIdValidator;

    @PostMapping("all-properties")
    public ResponseEntity<CommonResponse> getAllProperties(
            @Valid @RequestBody Map<String, Object> requestMap) {
        log.info("Get all properties API: {}", requestMap);

        // validate using custom spring validators
        BindingResult errors = new BeanPropertyBindingResult(requestMap, "requestMap");
        tenantIdValidator.validate(requestMap, errors);

        return filePropertyService.getAllProperties(requestMap);
    }

    @PutMapping("by-id")
    public ResponseEntity<CommonResponse> updateProperties(
            @RequestBody Map<String, Object> updatePropertyRequestMap) throws DmsException {
        log.info("Update properties API: {}", updatePropertyRequestMap);

        // validate request using custom spring validators
        BindingResult errors = new BeanPropertyBindingResult(updatePropertyRequestMap, "updatePropertyRequestMap");
        propertyUpdateRequestValidator.validate(updatePropertyRequestMap, errors);

        return filePropertyService.updatePropertiesByDocumentId(updatePropertyRequestMap);
    }
}
