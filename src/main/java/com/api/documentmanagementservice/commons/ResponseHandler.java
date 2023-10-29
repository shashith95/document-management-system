package com.api.documentmanagementservice.commons;

import com.api.documentmanagementservice.model.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.api.documentmanagementservice.commons.constant.ResponseMessage.DM_DMS_011;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ResponseHandler {

    public static ResponseEntity<CommonResponse> generateResponse(HttpStatus httpStatus,
                                                                  String responseMessage,
                                                                  String responseCode) {

        return ResponseEntity
                .status(httpStatus)
                .body(new CommonResponse(
                        responseMessage,
                        responseCode,
                        TRUE,
                        new ArrayList<>(),
                        new ArrayList<>()));
    }

    public static ResponseEntity<CommonResponse> generateResponse(HttpStatus httpStatus,
                                                                  String responseMessage,
                                                                  String responseCode,
                                                                  Object resultData) {

        return ResponseEntity
                .status(httpStatus)
                .body(new CommonResponse(
                        responseMessage,
                        responseCode,
                        TRUE,
                        resultData,
                        new ArrayList<>()));
    }

    public static ResponseEntity<CommonResponse> generateResponse(HttpStatus httpStatus,
                                                                  String responseMessage,
                                                                  List<?> errorList) {

        return ResponseEntity
                .status(httpStatus)
                .body(new CommonResponse(
                        responseMessage,
                        DM_DMS_011.name(),
                        FALSE,
                        new ArrayList<>(),
                        errorList));
    }

    public static ResponseEntity<CommonResponse> generateErrorResponse(HttpStatus httpStatus,
                                                                       String responseMessage,
                                                                       String errorCode,
                                                                       List<?> errorList) {

        return ResponseEntity
                .status(httpStatus)
                .body(new CommonResponse(
                        responseMessage,
                        errorCode,
                        FALSE,
                        new ArrayList<>(),
                        errorList));
    }


}
