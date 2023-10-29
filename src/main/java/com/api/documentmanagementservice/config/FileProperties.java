package com.api.documentmanagementservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FileProperties {

    @Value("${file.unzip_file_path}")
    private String unzipFilePath;

    @Value("${minio.developmentEnvironment}")
    private String environment;

    @Value("${file.ws_downLoad_Path}")
    private String wsDownloadFilePath;

    @Value("${file.temp_file_upload_path}")
    private String tempUploadPath;

}
