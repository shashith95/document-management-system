package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.FileDataDto;
import com.api.documentmanagementservice.model.dto.ZippedFileDetailsDto;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileOperationsService {

    ResponseEntity<Resource> getDocumentFromDocumentStorage(String fileHash,
                                                            HeaderContext headerContext) throws DmsException, IOException;

    ZippedFileDetailsDto uploadAndZipBase64ToTemporaryPath(String base64File,
                                                           FileDataDto fileDataDto,
                                                           String bucketName,
                                                           String documentPath) throws DmsException;

    ZippedFileDetailsDto uploadAndZipToTemporaryPath(MultipartFile multipartFile,
                                                     FileDataDto fileDataDto,
                                                     String previousDocId,
                                                     String bucketName,
                                                     String documentPath) throws DmsException;
}
