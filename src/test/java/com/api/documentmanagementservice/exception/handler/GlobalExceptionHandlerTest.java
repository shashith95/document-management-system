package com.api.documentmanagementservice.exception.handler;

import com.api.documentmanagementservice.model.dto.CommonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerTest {
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * Method under test: {@link GlobalExceptionHandler#handleException(Exception)}
     */
    @Test
    void testHandleException() {
        ResponseEntity<CommonResponse> actualHandleExceptionResult = globalExceptionHandler
                .handleException(new Exception("foo"));
        assertTrue(actualHandleExceptionResult.hasBody());
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
        assertEquals(500, actualHandleExceptionResult.getStatusCodeValue());
        CommonResponse body = actualHandleExceptionResult.getBody();
        assertEquals("REQ-2001", body.messageCode());
        assertEquals(1, body.errorList().size());
        assertFalse(body.validity());
        assertTrue(((Collection<Object>) body.resultData()).isEmpty());
        assertEquals("Internal Server Error", body.message());
    }
}

