package com.api.documentmanagementservice.service;

import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.table.DirectoryDetails;

import java.util.Optional;

public interface DirectoryDetailsService {
    DirectoryDetails createDirectory(String path, String bucket, HeaderContext headerContext);

    Optional<Long> checkDirectoryExist(String path, String bucket, HeaderContext headerContext);

    Optional<DirectoryDetails> getDirectoryById(Long id);
}
