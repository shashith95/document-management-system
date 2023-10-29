package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.SettingUploadRequest;
import com.api.documentmanagementservice.service.FileSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/settings")
@RequiredArgsConstructor
@Slf4j
public class UploadSettingsController {
    private final FileSettingService fileSettingService;

    @RequestMapping(path = "upload-settings", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<CommonResponse> createOrUpdateUploadSettings(
            @Valid @RequestBody SettingUploadRequest settingUploadRequest) {
        log.info("Create or Update upload settings API {}", settingUploadRequest);
        return fileSettingService.createOrUpdateUploadSettings(settingUploadRequest);
    }

    @GetMapping("upload-settings")
    public ResponseEntity<CommonResponse> getUploadSettings() {
        log.info("Get upload settings API");
        return fileSettingService.getUploadSettings();
    }
}
