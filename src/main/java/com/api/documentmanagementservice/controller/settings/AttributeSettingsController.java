package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.DefaultAttributeRequest;
import com.api.documentmanagementservice.model.dto.request.PropertyAttributeRequest;
import com.api.documentmanagementservice.service.PropertyAttributeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/settings")
@RequiredArgsConstructor
@Slf4j
public class AttributeSettingsController {
    private final PropertyAttributeService propertyAttributeService;

    @DeleteMapping("attribute-by-id")
    public ResponseEntity<CommonResponse> deleteAttributeById(
            @Valid @RequestParam(name = "attributeId") Long attributeId) throws DmsException {
        log.info("Delete file by attribute API {}", attributeId);
        return propertyAttributeService.deleteAttributeById(attributeId);
    }

    @PostMapping("property-attribute")
    public ResponseEntity<CommonResponse> createPropertyAttribute(
            @Valid @RequestBody PropertyAttributeRequest propertyAttributeRequest) {
        log.info("Create property attribute API {}", propertyAttributeRequest);
        return propertyAttributeService.createPropertyAttribute(propertyAttributeRequest);
    }

    @PostMapping("default-attribute")
    public ResponseEntity<CommonResponse> setDefaultAttribute(
            @Valid @RequestBody DefaultAttributeRequest defaultAttributeRequest) throws BadRequestException {
        log.info("Set default attribute API {}", defaultAttributeRequest);
        return propertyAttributeService.setDefaultAttribute(defaultAttributeRequest);
    }

    @GetMapping("default-attribute")
    public ResponseEntity<CommonResponse> getDefaultAttribute(
            @Valid @RequestParam(name = "propertyId") @NotNull Long propertyId,
            @RequestParam(name = "componentId", required = false, defaultValue = "0") @PositiveOrZero Long componentId) {
        log.info("Get default attribute by propertyId {} and componentId {} API", propertyId, componentId);
        return propertyAttributeService.getDefaultAttribute(propertyId, componentId);
    }
}
