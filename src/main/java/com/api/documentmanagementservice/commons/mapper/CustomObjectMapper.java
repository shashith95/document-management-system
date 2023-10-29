package com.api.documentmanagementservice.commons.mapper;

import com.api.documentmanagementservice.model.dto.FileDataDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomObjectMapper {
    private final ObjectMapper objectMapper;

    public Map getMapFromJsonString(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, Map.class);
    }

    public FileDataDto getFileDataDtoFromJsonStringArray(String jsonString) throws JsonProcessingException {
        List<FileDataDto> fileDataMapList = objectMapper.readValue(jsonString, new TypeReference<>() {
        });
        return fileDataMapList.get(0);
    }
}
