package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.DmsException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface FileDownloadService {
    ResponseEntity<Resource> getFile(String fileHash) throws DmsException, IOException;
}
