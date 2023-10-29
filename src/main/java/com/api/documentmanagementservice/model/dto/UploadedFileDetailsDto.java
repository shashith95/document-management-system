package com.api.documentmanagementservice.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadedFileDetailsDto {
    private String filepath;
    private String extension;
    private String contentType;
    private Long size;
    private byte[] fileByteArray;
}
