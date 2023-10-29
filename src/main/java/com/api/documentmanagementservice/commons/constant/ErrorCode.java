package com.api.documentmanagementservice.commons.constant;

public enum ErrorCode {
    // Header Validation Failures
    HDR_MISSING_API_KEY("HDR-1001", "Header Validation Error: Missing API Key"),
    HDR_INVALID_CONTENT_TYPE("HDR-1002", "Header Validation Error: Invalid Content-Type"),
    HDR_INVALID_DATA("HDR-1003", "Header Validation Error: One or more headers are empty"),

    // Request Validation Failures
    REQ_MISSING_REQUIRED_FIELD("REQ-2001", "Request Validation Error: Missing Required Field"),
    REQ_INVALID_INPUT_FORMAT("REQ-2002", "Request Validation Error: Invalid Input Format"),
    REQ_REQUEST_TOO_LARGE("REQ-2003", "Request Validation Error: Request Too Large"),
    REQ_REQUEST_INPUT_FILE_MISSING("REQ-2004", "Request Validation Error: Input File Missing"),

    // Database Conflicts
    DB_DUPLICATE_ENTRY("DB-3001", "Database Conflict: Duplicate Entry"),
    DB_UNIQUE_CONSTRAINT_VIOLATION("DB-3002", "Database Conflict: Unique Constraint Violation"),

    // File Operation Failures
    FILE_NOT_FOUND("FILE-4001", "File Operation Error: File Not Found"),
    FILE_PERMISSION_DENIED("FILE-4002", "File Operation Error: Permission Denied"),
    FILE_UPLOAD_ERROR("FILE-4003", "File Operation Error: File Upload Failure"),
    FILE_UPLOAD_DMS_ERROR("FILE-4004", "File Operation Error: File Upload Failure to DMS"),
    FILE_RENAME_ERROR("FILE-4005", "File Operation Error: Error encountered in file renaming"),
    FILE_UNZIP_ERROR("FILE-4006", "File Operation Error: Error occurred while unzipping the file"),
    FILE_NAME_ENCODE_ERROR("FILE-4007", "File Operation Error: Error occurred while encoding file name"),
    FILE_ZIP_ERROR("FILE-4008", "File Operation Error: Error occurred while zipping file"),
    FILE_WRITE_ERROR("FILE-4009", "File Operation Error: Error occurred while writing file to local storage"),

    // Database Operation Failures
    DB_CONNECTION_TIMEOUT("DB-5001", "Database Operation Error: Connection Timeout"),
    DB_SQL_SYNTAX_ERROR("DB-5002", "Database Operation Error: SQL Syntax Error"),
    DB_DATA_RETRIEVAL_ERROR("DB-5003", "Database Operation Error: Data Retrieval Error"),
    DB_DATA_UPDATE_ERROR("DB-5004", "Database Operation Error: Failed to save data to the database"),
    DB_DATA_DELETE_ERROR("DB-5005", "Database Operation Error: Failed to delete data in database"),
    DB_NO_DATA("DB-5006", "No Data Found:: The requested data was not found in the database");


    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
