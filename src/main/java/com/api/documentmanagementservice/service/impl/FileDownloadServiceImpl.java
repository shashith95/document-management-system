package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.service.FileDownloadService;
import com.api.documentmanagementservice.service.FileOperationsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileDownloadServiceImpl implements FileDownloadService {
    private final FileOperationsService fileOperationsService;
    private final HeaderContext headerContext;

    /**
     * Retrieves a file from the document storage service using its unique file hash.
     * This method delegates the file retrieval to the underlying service and returns it as a Resource
     * within a ResponseEntity. The file content is streamed directly to the client.
     *
     * @param fileHash The unique file hash used to identify the desired file in the document storage service.
     * @return A ResponseEntity containing the requested file data as a Resource.
     * @throws DmsException If an error occurs during the retrieval process.
     * @throws IOException   If an I/O error occurs while streaming the file content.
     */
    @Override
    public ResponseEntity<Resource> getFile(String fileHash) throws DmsException, IOException {

        log.info("Retrieving from Minio for hash: {}", fileHash);
        return fileOperationsService.getDocumentFromDocumentStorage(fileHash, headerContext);
    }
}
