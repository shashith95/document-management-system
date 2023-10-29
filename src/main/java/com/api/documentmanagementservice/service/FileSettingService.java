package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.FolderHierarchyRequest;
import com.api.documentmanagementservice.model.dto.request.SettingUploadRequest;
import org.springframework.http.ResponseEntity;

public interface FileSettingService {

    ResponseEntity<CommonResponse> createOrUpdateFolderHierarchy(FolderHierarchyRequest folderHierarchyRequest) throws DmsException, BadRequestException;

    ResponseEntity<CommonResponse> getFolderHierarchy(Boolean isCustom) throws DmsException;

    ResponseEntity<CommonResponse> createOrUpdateUploadSettings(SettingUploadRequest settingUploadRequest);

    ResponseEntity<CommonResponse> getUploadSettings();
}
