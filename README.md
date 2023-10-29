# Application Configuration Setup

## Local Environment

When running the application locally, you can create custom `application.yml` files with desired environment properties.
For example, create `application-dev.yml` with all properties related to the development environment. Then, you can add
that profile in `application.yml` or pass it as a VM option. For example, use `-Dspring.profiles.active=dev`.

## Test Containers for Local Testing

For local application testing, you can utilize TestContainers to configure database Docker containers. This approach
simplifies the process of setting up databases for developer testing. To run the application with TestContainers, use
the following class:
You can use TestCsiDocumentManagementService.class for this
