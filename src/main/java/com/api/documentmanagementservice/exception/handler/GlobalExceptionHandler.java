package com.api.documentmanagementservice.exception.handler;

import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.exception.CustomExceptionBase;
import com.api.documentmanagementservice.exception.DmsException;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.api.documentmanagementservice.commons.ResponseHandler.generateErrorResponse;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.REQ_INVALID_INPUT_FORMAT;
import static com.api.documentmanagementservice.commons.constant.ErrorCode.REQ_MISSING_REQUIRED_FIELD;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for various types of exceptions, including custom exceptions.
     *
     * @param ex The exception to handle.
     * @return ResponseEntity containing error details.
     */
    @ExceptionHandler({
            ConstraintViolationException.class,
            BadRequestException.class,
            DmsException.class,
            NotReadablePropertyException.class,
            Exception.class
    })
    public ResponseEntity<CommonResponse> handleException(Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorCode;
        List<String> errorMessages = new ArrayList<>();

        if (ex instanceof ConstraintViolationException cve) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = REQ_MISSING_REQUIRED_FIELD.name();
            cve.getConstraintViolations().forEach(
                    constraintViolation -> errorMessages.add(
                            constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage()
                    )
            );
        } else if (ex instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = ((CustomExceptionBase) ex).getErrorCode();
            errorMessages.add(ex.getMessage());
        } else if (ex instanceof DmsException) {
            errorCode = ((CustomExceptionBase) ex).getErrorCode();
            errorMessages.add(ex.getMessage());
        } else if (ex instanceof NotReadablePropertyException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = REQ_INVALID_INPUT_FORMAT.getCode();
            errorMessages.add(ex.getLocalizedMessage());
        } else {
            errorCode = REQ_MISSING_REQUIRED_FIELD.getCode();
            errorMessages.add(ex.getMessage());
        }

        return generateExceptionResponse(status, status.getReasonPhrase(), errorCode, errorMessages);
    }


    /**
     * Generates a ResponseEntity containing error details.
     *
     * @param status    The HTTP status code.
     * @param message   The reason phrase.
     * @param errorCode The error code.
     * @param errorList The list of error messages.
     * @return ResponseEntity containing error details.
     */
    private ResponseEntity<CommonResponse> generateExceptionResponse(HttpStatus status, String message, String errorCode, List<String> errorList) {
        log.error("{} - {}: {}", status, errorCode, errorList);
        return generateErrorResponse(status, message, errorCode, errorList);
    }
}
