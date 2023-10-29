package com.api.documentmanagementservice.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentStorageConfiguration {

    @Value(value = "${minio.endPoint}")
    private String minioEndPoint;

    @Value(value = "${minio.accessKey}")
    private String minioAccessKey;

    @Value(value = "${minio.secretKey}")
    private String minioSecretKey;

    @Bean
    public MinioClient getMinioClient() throws InvalidPortException, InvalidEndpointException {

        return new MinioClient(minioEndPoint, minioAccessKey, minioSecretKey);
    }
}
