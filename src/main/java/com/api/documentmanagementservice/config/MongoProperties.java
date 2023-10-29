package com.api.documentmanagementservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class MongoProperties {

    @Value("${mongo.documentDetailsCollectionName}")
    private String documentDetailsCollection;
}
