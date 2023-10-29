package com.api.documentmanagementservice.exception;

import lombok.Getter;

@Getter
public class CustomExceptionBase extends Exception {
    private final String errorCode;

    public CustomExceptionBase(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
