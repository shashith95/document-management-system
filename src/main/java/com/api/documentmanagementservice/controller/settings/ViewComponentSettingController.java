package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.ViewComponentSettingDto;
import com.api.documentmanagementservice.service.ViewComponentSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/settings")
@RequiredArgsConstructor
@Slf4j
public class ViewComponentSettingController {
    private final ViewComponentSettingService viewComponentSettingService;

    @RequestMapping(path = "view-component-settings", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<CommonResponse> createOrUpdateViewComponentSettings(
            @Valid @RequestBody ViewComponentSettingDto viewComponentSettingDto) throws DmsException {
        log.info("Create or Update view component setting API {}", viewComponentSettingDto);
        return viewComponentSettingService.createOrUpdateViewComponentSettings(viewComponentSettingDto);
    }

    @GetMapping("view-component-settings")
    public ResponseEntity<CommonResponse> getViewComponentSettings(
            @Valid @RequestParam(name = "component") Long componentId) throws DmsException {
        log.info("Get view component setting by componentId: {} API", componentId);
        return viewComponentSettingService.getViewComponentSettingsByComponentId(componentId);
    }

    @DeleteMapping("view-component-settings")
    public ResponseEntity<CommonResponse> deleteViewComponentSettings(
            @Valid @RequestParam(name = "component") Long componentId) throws DmsException {
        log.info("Delete view component setting by componentId: {} API", componentId);
        return viewComponentSettingService.deleteViewComponentSettingsByComponentId(componentId);
    }
}
