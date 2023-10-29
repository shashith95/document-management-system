package com.api.documentmanagementservice.controller;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.model.dto.BucketDto;
import com.api.documentmanagementservice.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    public String createBucket(@RequestBody BucketDto bucketDTO) throws BadRequestException {
        return bucketService.createBucket(bucketDTO);
    }

    public String createBucketS() {
        return "ds";
    }
}
