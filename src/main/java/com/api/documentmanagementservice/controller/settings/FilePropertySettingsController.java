package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.FilePropertyRequest;
import com.api.documentmanagementservice.service.FilePropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/settings")
@RequiredArgsConstructor
@Slf4j
public class FilePropertySettingsController {
    private final FilePropertyService filePropertyService;

    @GetMapping("all-file-properties")
    public ResponseEntity<CommonResponse> getAllFileProperties(
            @Valid @RequestParam(name = "hierarchyAvailable", required = false) Optional<Boolean> hierarchyAvailable) {
        log.info("Get all file properties API");
        return filePropertyService.getAllFileProperties(hierarchyAvailable);
    }

    @RequestMapping(path = "file-property", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<CommonResponse> createOrUpdateFileProperty(
            @Valid @RequestBody FilePropertyRequest filePropertyRequest) throws DmsException, BadRequestException {
        log.info("Create or Update file property API {}", filePropertyRequest);
        return filePropertyService.createOrUpdateFileProperty(filePropertyRequest);
    }

    @DeleteMapping("property-by-id")
    public ResponseEntity<CommonResponse> deletePropertyById(
            @Valid @RequestParam(name = "propertyId") Long propertyId) throws DmsException, BadRequestException {
        log.info("Delete file by property API {}", propertyId);
        return filePropertyService.deletePropertyById(propertyId);
    }
}
