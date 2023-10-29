package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.ViewComponentSettingDto;
import org.springframework.http.ResponseEntity;

public interface ViewComponentSettingService {
    ResponseEntity<CommonResponse> createOrUpdateViewComponentSettings(ViewComponentSettingDto viewComponentSettingDto) throws DmsException;

    ResponseEntity<CommonResponse> getViewComponentSettingsByComponentId(Long componentId) throws DmsException;

    ResponseEntity<CommonResponse> deleteViewComponentSettingsByComponentId(Long componentId) throws DmsException;
}
