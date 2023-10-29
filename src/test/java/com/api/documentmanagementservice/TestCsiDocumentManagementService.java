package com.api.documentmanagementservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Configuration class for testing the CSI Document Management Service.
 * This class allows running the Spring Boot application with additional configurations
 * specifically designed for testing.
 *
 * <p>The {@link TestCsiDocumentManagementService#main(String[])} method starts the application
 * with the configurations defined in both {@link DocumentManagementServiceApplication} and this class.
 * It enables additional testing features such as the use of a PostgreSQL Docker container.
 *
 * <p>The {@link TestCsiDocumentManagementService#jdbcDatabaseContainer()} ()} method defines a Docker container
 * for PostgreSQL (version 15.4) and Oracle to be used during testing. This container is annotated with
 * {@link org.springframework.boot.devtools.restart.RestartScope},
 * indicating it's scoped for restarting with specific configurations.
 */
@Configuration
public class TestCsiDocumentManagementService {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    /**
     * Main method to run the Spring Boot application with test configurations.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication
                // Do everything defined in CsiDocumentManagementServiceApplication::main
                .from(DocumentManagementServiceApplication::main)
                // With additional configs in TestCsiDocumentManagementService.class
                .with(TestCsiDocumentManagementService.class)
                .run(args);
    }

    /**
     * Defines a PostgreSQL Docker container for testing.
     * This container is configured to use PostgreSQL version 15.4.
     *
     * @return A PostgreSQLContainer instance to be used during testing.
     */
    @Bean
    @RestartScope
    @ServiceConnection
    JdbcDatabaseContainer jdbcDatabaseContainer() {
        if (datasourceUrl.startsWith("jdbc:oracle:")) {
            return new OracleContainer("gvenzl/oracle-xe:21-slim-faststart");
        } else {
            return new PostgreSQLContainer("postgres:15.4");
        }
    }
}
