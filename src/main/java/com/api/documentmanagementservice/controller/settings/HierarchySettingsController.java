package com.api.documentmanagementservice.controller.settings;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.FolderHierarchyRequest;
import com.api.documentmanagementservice.service.FileSettingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping("v1/settings")
@RequiredArgsConstructor
@Slf4j
public class HierarchySettingsController {
    private final FileSettingService fileSettingService;

    @RequestMapping(path = "folder-hierarchy", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<CommonResponse> createOrUpdateFolderHierarchy(
            @Valid @NotNull @RequestBody FolderHierarchyRequest folderHierarchyRequest) throws DmsException, BadRequestException {
        log.info("Create or Update folder hierarchy API: {}", folderHierarchyRequest);
        return fileSettingService.createOrUpdateFolderHierarchy(folderHierarchyRequest);
    }

    @GetMapping("custom-hierarchy")
    public ResponseEntity<CommonResponse> getCustomHierarchy() throws DmsException {
        log.info("Get custom hierarchy API");
        return fileSettingService.getFolderHierarchy(TRUE);
    }

    @GetMapping("default-hierarchy")
    public ResponseEntity<CommonResponse> getDefaultHierarchy() throws DmsException {
        log.info("Get default hierarchy API");
        return fileSettingService.getFolderHierarchy(FALSE);
    }
}
