package com.api.documentmanagementservice.commons.mapper;

import com.api.documentmanagementservice.model.dto.AllFileDetailsDto;
import com.api.documentmanagementservice.model.dto.FilePropertyWithAttributesRecord;
import com.api.documentmanagementservice.model.dto.PropertyAttributeRecord;
import com.api.documentmanagementservice.model.table.PropertyAttribute;

import java.util.List;

public class FilePropertyMapper {

    public static PropertyAttributeRecord mapPropertyAttributeToRecord(AllFileDetailsDto allFileDetailsDto) {
        return PropertyAttributeRecord.builder()
                .id(allFileDetailsDto.attributeId())
                .propertyId(allFileDetailsDto.attributePropertyId())
                .name(allFileDetailsDto.attributeName())
                .isDeletable(allFileDetailsDto.isDeletable())
                .createdUser(allFileDetailsDto.createdUser())
                .createdDateTime(allFileDetailsDto.createdDateTime())
                .updatedDateTime(allFileDetailsDto.updatedDateTime())
                .build();
    }

    public static FilePropertyWithAttributesRecord mapFilePropertyWithAttributesRecord(AllFileDetailsDto fileProperty,
                                                                                       List<PropertyAttributeRecord> attributes) {
        return FilePropertyWithAttributesRecord.builder()
                .id(fileProperty.id())
                .name(fileProperty.name())
                .isDeletable(fileProperty.isDeletable())
                .isMandatory(fileProperty.isMandatory())
                .isFreeText(fileProperty.isFreeText())
                .isAddable(fileProperty.isAddable())
                .propertyType(fileProperty.propertyType())
                .defaultAttributeId(fileProperty.defaultAttributeId() == null ? 0 : fileProperty.defaultAttributeId())
                .tenantId(fileProperty.tenantId())
                .hospitalId(fileProperty.hospitalId())
                .hierarchyAvailable(fileProperty.hierarchyAvailable())
                .createdDateTime(fileProperty.createdDateTime())
                .updatedDateTime(fileProperty.updatedDateTime())
                .createdUser(fileProperty.createdUser())
                .attributes(attributes)
                .build();
    }

    public static PropertyAttributeRecord mapPropertyAttributeToRecord(PropertyAttribute propertyAttribute) {
        return PropertyAttributeRecord.builder()
                .id(propertyAttribute.getId())
                .propertyId(propertyAttribute.getPropertyId())
                .name(propertyAttribute.getName())
                .isDeletable(propertyAttribute.getIsDeletable())
                .createdUser(propertyAttribute.getCreatedUser())
                .createdDateTime(propertyAttribute.getCreatedDateTime())
                .updatedDateTime(propertyAttribute.getUpdatedDateTime())
                .build();
    }
}
