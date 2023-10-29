package com.api.documentmanagementservice.model.dto.request;

import com.api.documentmanagementservice.model.dto.ScanComponentSelectedPropertyDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Optional;

public record ScanComponentSettingRequest(
        Optional<Long> id,

        @Positive(message = "component must be a positive integer")
        Long component,

        @Positive(message = "defaultScannerProfileId must be a positive integer")
        Long defaultScannerProfileId,

        @Valid
        List<ScanComponentSelectedPropertyDto> selectedProperties
) {
}
