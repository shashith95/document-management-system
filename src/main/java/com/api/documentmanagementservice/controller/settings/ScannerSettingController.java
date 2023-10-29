package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.ScannerSettingRequest;
import com.api.documentmanagementservice.service.ScannerSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/settings")
@RequiredArgsConstructor
@Slf4j
public class ScannerSettingController {
    private final ScannerSettingService scannerSettingService;

    @GetMapping("scanner-settings")
    public ResponseEntity<CommonResponse> getScannerSetting() {
        log.info("Get scanner setting API");
        return scannerSettingService.getScannerSetting();
    }

    @RequestMapping(path = "scanner-settings", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<CommonResponse> createOrUpdateScannerSetting(
            @Valid @RequestBody ScannerSettingRequest scannerSettingRequest) throws DmsException, BadRequestException {
        log.info("Create or Update scanner setting API {}", scannerSettingRequest);
        return scannerSettingService.createOrUpdateScannerSetting(scannerSettingRequest);
    }

    @DeleteMapping("scanner-profile-by-id")
    public ResponseEntity<CommonResponse> deleteScannerProfileById(@Valid @RequestParam Long profileId) {
        log.info("Delete scanner profile by id {} API", profileId);
        return scannerSettingService.deleteScannerProfileById(profileId);
    }
}
