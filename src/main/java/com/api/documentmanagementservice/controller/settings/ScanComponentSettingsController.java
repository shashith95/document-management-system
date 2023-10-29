package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.ScanComponentSettingRequest;
import com.api.documentmanagementservice.service.ScanComponentSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/settings")
@RequiredArgsConstructor
@Slf4j
public class ScanComponentSettingsController {
    private final ScanComponentSettingsService scanComponentSettingsService;

    @RequestMapping(path = "scan-component-settings", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<CommonResponse> createOrUpdateScanComponentSettings(
            @Valid @RequestBody ScanComponentSettingRequest scanComponentSettingRequest) throws DmsException {
        log.info("Create or Update scan component setting API {}", scanComponentSettingRequest);
        return scanComponentSettingsService.createOrUpdateScanComponentSettings(scanComponentSettingRequest);
    }

    @GetMapping("scan-component-settings")
    public ResponseEntity<CommonResponse> getScanComponentSettings(
            @Valid @RequestParam(name = "component") Long componentId) throws DmsException {
        log.info("Get scan component setting by componentId: {} API", componentId);
        return scanComponentSettingsService.getScanComponentSettings(componentId);
    }

    @DeleteMapping("scan-component-settings")
    public ResponseEntity<CommonResponse> deleteScanComponentSettings(
            @Valid @RequestParam(name = "component") Long componentId) throws DmsException {
        log.info("Get scan component setting by componentId: {} API", componentId);
        return scanComponentSettingsService.deleteScanComponentSettings(componentId);
    }
}
