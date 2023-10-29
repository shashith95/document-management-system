package com.api.documentmanagementservice.commons;

import com.api.documentmanagementservice.model.dto.CommonResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseHandlerTest {
    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.CONTINUE,
                "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(100, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse2() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.SWITCHING_PROTOCOLS, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(101, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse3() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.PROCESSING, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(102, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse5() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.CHECKPOINT, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(103, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse6() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.OK,
                "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(200, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse7() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.CREATED,
                "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(201, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse8() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.ACCEPTED, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(202, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse9() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(203, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse10() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.NO_CONTENT, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(204, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse11() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.RESET_CONTENT, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(205, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse12() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.PARTIAL_CONTENT, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(206, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse13() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.MULTI_STATUS, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(207, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse14() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.ALREADY_REPORTED, "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(208, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String)}
     */
    @Test
    void testGenerateResponse15() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.IM_USED,
                "Response Message", "Response Code");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(226, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse16() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.CONTINUE, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(100, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse17() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.SWITCHING_PROTOCOLS, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(101, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse18() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.PROCESSING, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(102, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }


    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse20() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.CHECKPOINT, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(103, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse21() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.OK,
                "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(200, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse22() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.CREATED,
                "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(201, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse23() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.ACCEPTED, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(202, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse24() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(
                HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(203, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse25() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.NO_CONTENT, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(204, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse26() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.RESET_CONTENT, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(205, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse27() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.PARTIAL_CONTENT, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(206, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse28() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.MULTI_STATUS, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(207, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse29() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.ALREADY_REPORTED, "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(208, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, String, Object)}
     */
    @Test
    void testGenerateResponse30() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.IM_USED,
                "Response Message", "Response Code", "Result Data");
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(226, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("Response Code", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals("Result Data", body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse31() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.CONTINUE, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(100, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse32() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.SWITCHING_PROTOCOLS, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(101, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse33() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.PROCESSING, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(102, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse35() {
        ArrayList<Object> errorList = new ArrayList<>();
        errorList.add("42");
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.CONTINUE, "Response Message", errorList);
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(100, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        assertEquals(1, body.errorList().size());
        assertFalse(body.validity());
        assertTrue(((Collection<Object>) body.resultData()).isEmpty());
        assertEquals("Response Message", body.message());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse36() {
        ArrayList<Object> errorList = new ArrayList<>();
        errorList.add("42");
        errorList.add("42");
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.CONTINUE, "Response Message", errorList);
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(100, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        assertEquals(2, body.errorList().size());
        assertFalse(body.validity());
        assertTrue(((Collection<Object>) body.resultData()).isEmpty());
        assertEquals("Response Message", body.message());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse37() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.CHECKPOINT, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(103, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse38() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.OK,
                "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(200, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse39() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.CREATED,
                "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(201, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse40() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.ACCEPTED, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(202, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse41() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(203, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse42() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.NO_CONTENT, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(204, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse43() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.RESET_CONTENT, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(205, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse44() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.PARTIAL_CONTENT, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(206, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse45() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.MULTI_STATUS, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(207, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse46() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler
                .generateResponse(HttpStatus.ALREADY_REPORTED, "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(208, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }

    /**
     * Method under test: {@link ResponseHandler#generateResponse(HttpStatus, String, List)}
     */
    @Test
    void testGenerateResponse47() {
        ResponseEntity<CommonResponse> actualGenerateResponseResult = ResponseHandler.generateResponse(HttpStatus.IM_USED,
                "Response Message", new ArrayList<>());
        assertTrue(actualGenerateResponseResult.hasBody());
        assertTrue(actualGenerateResponseResult.getHeaders().isEmpty());
        assertEquals(226, actualGenerateResponseResult.getStatusCodeValue());
        CommonResponse body = actualGenerateResponseResult.getBody();
        assertEquals("DM_DMS_011", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertFalse(body.validity());
        assertEquals("Response Message", body.message());
        assertEquals(errorListResult, body.resultData());
    }
}

