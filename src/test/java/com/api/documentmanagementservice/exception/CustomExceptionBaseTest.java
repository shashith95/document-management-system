package com.api.documentmanagementservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomExceptionBaseTest {
    /**
     * Method under test: {@link CustomExceptionBase#CustomExceptionBase(String, String)}
     */
    @Test
    void testConstructor() {
        CustomExceptionBase actualCustomExceptionBase = new CustomExceptionBase("An error occurred", "An error occurred");

        assertNull(actualCustomExceptionBase.getCause());
        assertEquals(0, actualCustomExceptionBase.getSuppressed().length);
        assertEquals("An error occurred", actualCustomExceptionBase.getMessage());
        assertEquals("An error occurred", actualCustomExceptionBase.getLocalizedMessage());
        assertEquals("An error occurred", actualCustomExceptionBase.getErrorCode());
    }
}

