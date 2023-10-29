package com.api.documentmanagementservice.controller;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.service.DirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/directory")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;

    /**
     * Handles an HTTP POST request to retrieve directory information based on the provided request body.
     *
     * @param requestBody A Map containing the request body data, typically containing key-value pairs.
     * @return A ResponseEntity containing a CommonResponse object, which holds the directory information.
     * @throws BadRequestException    If the request body is malformed or contains invalid data.
     */

    @PostMapping("/directory")
    public ResponseEntity<CommonResponse> getDirectory(@RequestBody Map<String, Object> requestBody) throws BadRequestException {
        return directoryService.getDirectory(requestBody);

    }


}
