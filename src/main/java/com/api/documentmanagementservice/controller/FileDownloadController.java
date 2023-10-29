package com.api.documentmanagementservice.controller;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.service.FileDownloadService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("v1/download")
@RequiredArgsConstructor
@Slf4j
public class FileDownloadController {

    private final FileDownloadService fileDownloadService;

    /**
     * Endpoint for retrieving a file by its file hash.
     *
     * @param fileHash  The hash of the file to retrieve.
     * @param sessionId An optional session ID, this is not currently being used
     * @return A ResponseEntity containing the requested file as a Resource.
     * @throws DmsException If an error occurs on the server while retrieving the file.
     */
    @GetMapping("getFile")
    public ResponseEntity<Resource> getFile(@Valid @NotNull String fileHash,
                                            Optional<String> sessionId) throws DmsException, IOException {
        log.info("Get file API with fileHash: {}", fileHash);
        return fileDownloadService.getFile(fileHash);
    }
}
