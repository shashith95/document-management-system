package com.api.documentmanagementservice.exception;


public class BadRequestException extends CustomExceptionBase {

    public BadRequestException(String errorCode, String message) {
        super(errorCode, message);
    }
}
