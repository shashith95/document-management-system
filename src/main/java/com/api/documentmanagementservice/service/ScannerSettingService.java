package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.request.ScannerSettingRequest;
import org.springframework.http.ResponseEntity;

public interface ScannerSettingService {
    ResponseEntity<CommonResponse> createOrUpdateScannerSetting(ScannerSettingRequest scannerSettingRequest) throws DmsException, BadRequestException;

    ResponseEntity<CommonResponse> getScannerSetting();

    ResponseEntity<CommonResponse> deleteScannerProfileById(Long profileId);
}
