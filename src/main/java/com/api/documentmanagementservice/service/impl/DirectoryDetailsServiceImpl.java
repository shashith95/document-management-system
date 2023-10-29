package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.table.DirectoryDetails;
import com.api.documentmanagementservice.repository.DirectoryDetailsRepository;
import com.api.documentmanagementservice.service.DirectoryDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DirectoryDetailsServiceImpl implements DirectoryDetailsService {
    private final DirectoryDetailsRepository directoryDetailsRepository;

    @Override
    public DirectoryDetails createDirectory(String path, String bucket, HeaderContext headerContext) {
        DirectoryDetails directoryDetails = DirectoryDetails.builder()
                .path(path)
                .tenant(headerContext.getTenantId())
                .bucket(bucket)
                .status(true)
                .createdDateTime(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        return directoryDetailsRepository.save(directoryDetails);
    }

    @Override
    public Optional<Long> checkDirectoryExist(String path, String bucket, HeaderContext headerContext) {
        return directoryDetailsRepository.findDirectoryIdByPathAndTenantAndBucket(path, headerContext.getTenantId(), bucket);
    }

    @Override
    public Optional<DirectoryDetails> getDirectoryById(Long id) {
        return directoryDetailsRepository.findById(id);
    }

}
