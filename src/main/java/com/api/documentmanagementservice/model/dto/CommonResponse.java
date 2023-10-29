package com.api.documentmanagementservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommonResponse(String message,
                             String messageCode,
                             Boolean validity,
                             Object resultData,
                             List<?> errorList) {
}
