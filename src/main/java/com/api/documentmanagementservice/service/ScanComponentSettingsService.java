package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.ScanComponentSettingRequest;
import org.springframework.http.ResponseEntity;

public interface ScanComponentSettingsService {
    ResponseEntity<CommonResponse> createOrUpdateScanComponentSettings(ScanComponentSettingRequest scanComponentSettingRequest) throws DmsException;

    ResponseEntity<CommonResponse> getScanComponentSettings(Long componentId) throws DmsException;

    ResponseEntity<CommonResponse> deleteScanComponentSettings(Long componentId) throws DmsException;
}
