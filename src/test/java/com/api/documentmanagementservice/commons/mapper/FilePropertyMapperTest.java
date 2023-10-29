package com.api.documentmanagementservice.commons.mapper;

import com.api.documentmanagementservice.model.dto.AllFileDetailsDto;
import com.api.documentmanagementservice.model.dto.FilePropertyWithAttributesRecord;
import com.api.documentmanagementservice.model.dto.PropertyAttributeRecord;
import com.api.documentmanagementservice.model.table.PropertyAttribute;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilePropertyMapperTest {
    /**
     * Method under test: {@link FilePropertyMapper#mapPropertyAttributeToRecord(AllFileDetailsDto)}
     */
    @Test
    void testMapPropertyAttributeToRecord() {
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        PropertyAttributeRecord actualMapPropertyAttributeToRecordResult = FilePropertyMapper.mapPropertyAttributeToRecord(
                new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true, createdDateTime, updatedDateTime,
                        "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42", "42",
                        "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay()));
        assertEquals("00:00", actualMapPropertyAttributeToRecordResult.createdDateTime().toLocalTime().toString());
        assertEquals("00:00", actualMapPropertyAttributeToRecordResult.updatedDateTime().toLocalTime().toString());
        assertEquals(1L, actualMapPropertyAttributeToRecordResult.propertyId().longValue());
        assertEquals("Attribute Name", actualMapPropertyAttributeToRecordResult.name());
        assertTrue(actualMapPropertyAttributeToRecordResult.isDeletable());
        assertEquals(1L, actualMapPropertyAttributeToRecordResult.id().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualMapPropertyAttributeToRecordResult.createdUser());
    }

    /**
     * Method under test: {@link FilePropertyMapper#mapPropertyAttributeToRecord(PropertyAttribute)}
     */
    @Test
    void testMapPropertyAttributeToRecord3() {
        PropertyAttributeRecord actualMapPropertyAttributeToRecordResult = FilePropertyMapper
                .mapPropertyAttributeToRecord(new PropertyAttribute());
        assertNull(actualMapPropertyAttributeToRecordResult.createdDateTime());
        assertNull(actualMapPropertyAttributeToRecordResult.updatedDateTime());
        assertNull(actualMapPropertyAttributeToRecordResult.propertyId());
        assertNull(actualMapPropertyAttributeToRecordResult.name());
        assertNull(actualMapPropertyAttributeToRecordResult.isDeletable());
        assertNull(actualMapPropertyAttributeToRecordResult.id());
        assertNull(actualMapPropertyAttributeToRecordResult.createdUser());
    }

    /**
     * Method under test: {@link FilePropertyMapper#mapFilePropertyWithAttributesRecord(AllFileDetailsDto, List)}
     */
    @Test
    void testMapFilePropertyWithAttributesRecord() {
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        AllFileDetailsDto fileProperty = new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true,
                createdDateTime, updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true, "42",
                "42", "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay());

        FilePropertyWithAttributesRecord actualMapFilePropertyWithAttributesRecordResult = FilePropertyMapper
                .mapFilePropertyWithAttributesRecord(fileProperty, new ArrayList<>());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.attributes().isEmpty());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.tenantId());
        assertEquals("00:00", actualMapFilePropertyWithAttributesRecordResult.updatedDateTime().toLocalTime().toString());
        assertEquals(1, actualMapFilePropertyWithAttributesRecordResult.propertyType().intValue());
        assertEquals("Name", actualMapFilePropertyWithAttributesRecordResult.name());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isMandatory());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isFreeText());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isDeletable());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isAddable());
        assertEquals(1L, actualMapFilePropertyWithAttributesRecordResult.id().longValue());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.hospitalId());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.hierarchyAvailable());
        assertEquals(1L, actualMapFilePropertyWithAttributesRecordResult.defaultAttributeId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualMapFilePropertyWithAttributesRecordResult.createdUser());
        assertEquals("1970-01-01",
                actualMapFilePropertyWithAttributesRecordResult.createdDateTime().toLocalDate().toString());
    }

    /**
     * Method under test: {@link FilePropertyMapper#mapFilePropertyWithAttributesRecord(AllFileDetailsDto, List)}
     */
    @Test
    void testMapFilePropertyWithAttributesRecord2() {
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        AllFileDetailsDto fileProperty = new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true,
                createdDateTime, updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, null, 1L, 1L, "Attribute Name", true,
                "42", "42", "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay());

        FilePropertyWithAttributesRecord actualMapFilePropertyWithAttributesRecordResult = FilePropertyMapper
                .mapFilePropertyWithAttributesRecord(fileProperty, new ArrayList<>());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.attributes().isEmpty());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.tenantId());
        assertEquals("00:00", actualMapFilePropertyWithAttributesRecordResult.updatedDateTime().toLocalTime().toString());
        assertEquals(1, actualMapFilePropertyWithAttributesRecordResult.propertyType().intValue());
        assertEquals("Name", actualMapFilePropertyWithAttributesRecordResult.name());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isMandatory());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isFreeText());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isDeletable());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isAddable());
        assertEquals(1L, actualMapFilePropertyWithAttributesRecordResult.id().longValue());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.hospitalId());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.hierarchyAvailable());
        assertEquals(0L, actualMapFilePropertyWithAttributesRecordResult.defaultAttributeId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualMapFilePropertyWithAttributesRecordResult.createdUser());
        assertEquals("1970-01-01",
                actualMapFilePropertyWithAttributesRecordResult.createdDateTime().toLocalDate().toString());
    }

    /**
     * Method under test: {@link FilePropertyMapper#mapFilePropertyWithAttributesRecord(AllFileDetailsDto, List)}
     */
    @Test
    void testMapFilePropertyWithAttributesRecord4() {
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        AllFileDetailsDto fileProperty = new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true,
                createdDateTime, updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true,
                "42", "42", "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<PropertyAttributeRecord> attributes = new ArrayList<>();
        LocalDateTime createdDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        attributes.add(new PropertyAttributeRecord(1L, "Name", 1L, true, "Jan 1, 2020 8:00am GMT+0100", createdDateTime2,
                LocalDate.of(1970, 1, 1).atStartOfDay()));
        FilePropertyWithAttributesRecord actualMapFilePropertyWithAttributesRecordResult = FilePropertyMapper
                .mapFilePropertyWithAttributesRecord(fileProperty, attributes);
        assertEquals(1, actualMapFilePropertyWithAttributesRecordResult.attributes().size());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.tenantId());
        assertEquals("00:00", actualMapFilePropertyWithAttributesRecordResult.updatedDateTime().toLocalTime().toString());
        assertEquals(1, actualMapFilePropertyWithAttributesRecordResult.propertyType().intValue());
        assertEquals("Name", actualMapFilePropertyWithAttributesRecordResult.name());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isMandatory());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isFreeText());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isDeletable());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isAddable());
        assertEquals(1L, actualMapFilePropertyWithAttributesRecordResult.id().longValue());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.hospitalId());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.hierarchyAvailable());
        assertEquals(1L, actualMapFilePropertyWithAttributesRecordResult.defaultAttributeId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualMapFilePropertyWithAttributesRecordResult.createdUser());
        assertEquals("1970-01-01",
                actualMapFilePropertyWithAttributesRecordResult.createdDateTime().toLocalDate().toString());
    }

    /**
     * Method under test: {@link FilePropertyMapper#mapFilePropertyWithAttributesRecord(AllFileDetailsDto, List)}
     */
    @Test
    void testMapFilePropertyWithAttributesRecord5() {
        LocalDateTime createdDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime updatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        LocalDateTime attributeCreatedDateTime = LocalDate.of(1970, 1, 1).atStartOfDay();
        AllFileDetailsDto fileProperty = new AllFileDetailsDto(1L, "Name", true, true, true, true, 1, "42", "42", true,
                createdDateTime, updatedDateTime, "Jan 1, 2020 8:00am GMT+0100", true, 1L, 1L, 1L, "Attribute Name", true,
                "42", "42", "Jan 1, 2020 8:00am GMT+0100", attributeCreatedDateTime, LocalDate.of(1970, 1, 1).atStartOfDay());

        ArrayList<PropertyAttributeRecord> attributes = new ArrayList<>();
        LocalDateTime createdDateTime2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        attributes.add(new PropertyAttributeRecord(1L, "Name", 1L, true, "Jan 1, 2020 8:00am GMT+0100", createdDateTime2,
                LocalDate.of(1970, 1, 1).atStartOfDay()));
        LocalDateTime createdDateTime3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        attributes.add(new PropertyAttributeRecord(1L, "Name", 1L, true, "Jan 1, 2020 8:00am GMT+0100", createdDateTime3,
                LocalDate.of(1970, 1, 1).atStartOfDay()));
        FilePropertyWithAttributesRecord actualMapFilePropertyWithAttributesRecordResult = FilePropertyMapper
                .mapFilePropertyWithAttributesRecord(fileProperty, attributes);
        assertEquals(2, actualMapFilePropertyWithAttributesRecordResult.attributes().size());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.tenantId());
        assertEquals("00:00", actualMapFilePropertyWithAttributesRecordResult.updatedDateTime().toLocalTime().toString());
        assertEquals(1, actualMapFilePropertyWithAttributesRecordResult.propertyType().intValue());
        assertEquals("Name", actualMapFilePropertyWithAttributesRecordResult.name());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isMandatory());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isFreeText());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isDeletable());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.isAddable());
        assertEquals(1L, actualMapFilePropertyWithAttributesRecordResult.id().longValue());
        assertEquals("42", actualMapFilePropertyWithAttributesRecordResult.hospitalId());
        assertTrue(actualMapFilePropertyWithAttributesRecordResult.hierarchyAvailable());
        assertEquals(1L, actualMapFilePropertyWithAttributesRecordResult.defaultAttributeId().longValue());
        assertEquals("Jan 1, 2020 8:00am GMT+0100", actualMapFilePropertyWithAttributesRecordResult.createdUser());
        assertEquals("1970-01-01",
                actualMapFilePropertyWithAttributesRecordResult.createdDateTime().toLocalDate().toString());
    }
}

