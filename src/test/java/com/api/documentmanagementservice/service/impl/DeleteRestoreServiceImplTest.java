package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.commons.constant.EventTypes;
import com.api.documentmanagementservice.service.CustomMongoService;
import com.api.documentmanagementservice.model.context.HeaderContext;
import com.api.documentmanagementservice.model.dto.CommonResponse;
import com.api.documentmanagementservice.model.dto.RenameDocumentDto;
import com.api.documentmanagementservice.model.dto.UpdateDocumentBody;
import com.api.documentmanagementservice.model.dto.UpdateMultipleDocumentBody;
import com.api.documentmanagementservice.repository.FileDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {DeleteRestoreServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DeleteRestoreServiceImplTest {
    @MockBean
    private CustomMongoService customMongoService;

    @Autowired
    private DeleteRestoreServiceImpl deleteRestoreServiceImpl;

    @MockBean
    private FileDetailsRepository fileDetailsRepository;

    @MockBean
    private HeaderContext headerContext;

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#delete(UpdateDocumentBody)}
     */
    @Test
    void testDelete() {
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteResult = deleteRestoreServiceImpl
                .delete(new UpdateDocumentBody("42", "Tenant", "Just cause"));
        assertTrue(actualDeleteResult.hasBody());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
        assertEquals(409, actualDeleteResult.getStatusCodeValue());
        CommonResponse body = actualDeleteResult.getBody();
        assertEquals("DM_DMS_052", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Conflict", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#delete(UpdateDocumentBody)}
     */
    @Test
    void testDelete2() {
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(false);
        ResponseEntity<CommonResponse> actualDeleteResult = deleteRestoreServiceImpl
                .delete(new UpdateDocumentBody("42", "Tenant", "Just cause"));
        assertTrue(actualDeleteResult.hasBody());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
        assertEquals(409, actualDeleteResult.getStatusCodeValue());
        CommonResponse body = actualDeleteResult.getBody();
        assertEquals("DM_DMS_052", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Conflict", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#delete(UpdateDocumentBody)}
     */
    @Test
    void testDelete3() {
        doNothing().when(customMongoService)
                .saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(), Mockito.<EventTypes>any());
        doNothing().when(customMongoService)
                .updateDocument(Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any());
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getTenantId()).thenReturn("Tenant");
        ResponseEntity<CommonResponse> actualDeleteResult = deleteRestoreServiceImpl
                .delete(new UpdateDocumentBody("42", "Tenant", "Just cause"));
        assertTrue(actualDeleteResult.hasBody());
        assertTrue(actualDeleteResult.getHeaders().isEmpty());
        assertEquals(201, actualDeleteResult.getStatusCodeValue());
        CommonResponse body = actualDeleteResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Deleted the document : 42", body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
        verify(customMongoService).saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(),
                Mockito.<EventTypes>any());
        verify(customMongoService).updateDocument(Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any());
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#deleteMultiple(UpdateMultipleDocumentBody)}
     */
    @Test
    void testDeleteMultiple() {
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualDeleteMultipleResult = deleteRestoreServiceImpl
                .deleteMultiple(new UpdateMultipleDocumentBody(new String[]{"42"}, "Tenant", "Just cause"));
        assertTrue(actualDeleteMultipleResult.hasBody());
        assertTrue(actualDeleteMultipleResult.getHeaders().isEmpty());
        assertEquals(201, actualDeleteMultipleResult.getStatusCodeValue());
        CommonResponse body = actualDeleteMultipleResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Deleted the documents: [42]", body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#deleteMultiple(UpdateMultipleDocumentBody)}
     */
    @Test
    void testDeleteMultiple2() {
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(false);
        ResponseEntity<CommonResponse> actualDeleteMultipleResult = deleteRestoreServiceImpl
                .deleteMultiple(new UpdateMultipleDocumentBody(new String[]{"42"}, "Tenant", "Just cause"));
        assertTrue(actualDeleteMultipleResult.hasBody());
        assertTrue(actualDeleteMultipleResult.getHeaders().isEmpty());
        assertEquals(201, actualDeleteMultipleResult.getStatusCodeValue());
        CommonResponse body = actualDeleteMultipleResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Deleted the documents: [42]", body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#deleteMultiple(UpdateMultipleDocumentBody)}
     */
    @Test
    void testDeleteMultiple3() {
        doNothing().when(customMongoService)
                .saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(), Mockito.<EventTypes>any());
        doNothing().when(customMongoService)
                .updateDocument(Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any());
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getTenantId()).thenReturn("Tenant");
        ResponseEntity<CommonResponse> actualDeleteMultipleResult = deleteRestoreServiceImpl
                .deleteMultiple(new UpdateMultipleDocumentBody(new String[]{"42"}, "Tenant", "Just cause"));
        assertTrue(actualDeleteMultipleResult.hasBody());
        assertTrue(actualDeleteMultipleResult.getHeaders().isEmpty());
        assertEquals(201, actualDeleteMultipleResult.getStatusCodeValue());
        CommonResponse body = actualDeleteMultipleResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Deleted the documents: [42]", body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
        verify(customMongoService).saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(),
                Mockito.<EventTypes>any());
        verify(customMongoService).updateDocument(Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any());
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#deleteMultiple(UpdateMultipleDocumentBody)}
     */
    @Test
    void testDeleteMultiple4() {
        doNothing().when(customMongoService)
                .saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(), Mockito.<EventTypes>any());
        doNothing().when(customMongoService)
                .updateDocument(Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any());
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getTenantId()).thenReturn("Tenant");
        ResponseEntity<CommonResponse> actualDeleteMultipleResult = deleteRestoreServiceImpl
                .deleteMultiple(new UpdateMultipleDocumentBody(new String[]{"42", "Tenant"}, "Tenant", "Just cause"));
        assertTrue(actualDeleteMultipleResult.hasBody());
        assertTrue(actualDeleteMultipleResult.getHeaders().isEmpty());
        assertEquals(201, actualDeleteMultipleResult.getStatusCodeValue());
        CommonResponse body = actualDeleteMultipleResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Deleted successfully", body.message());
        assertEquals("Deleted the documents: [42, Tenant]", body.resultData());
        verify(customMongoService, atLeast(1)).doesDocumentExist(Mockito.<String>any());
        verify(customMongoService, atLeast(1)).saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(),
                Mockito.<EventTypes>any());
        verify(customMongoService, atLeast(1)).updateDocument(Mockito.<Map<String, Object>>any(),
                Mockito.<Map<String, Object>>any());
        verify(headerContext, atLeast(1)).getTenantId();
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#restore(UpdateDocumentBody)}
     */
    @Test
    void testRestore() {
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getTenantId()).thenReturn("42");
        ResponseEntity<CommonResponse> actualRestoreResult = deleteRestoreServiceImpl
                .restore(new UpdateDocumentBody("42", "Tenant", "Just cause"));
        assertTrue(actualRestoreResult.hasBody());
        assertTrue(actualRestoreResult.getHeaders().isEmpty());
        assertEquals(409, actualRestoreResult.getStatusCodeValue());
        CommonResponse body = actualRestoreResult.getBody();
        assertEquals("DM_DMS_052", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Conflict", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#restore(UpdateDocumentBody)}
     */
    @Test
    void testRestore2() {
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(false);
        ResponseEntity<CommonResponse> actualRestoreResult = deleteRestoreServiceImpl
                .restore(new UpdateDocumentBody("42", "Tenant", "Just cause"));
        assertTrue(actualRestoreResult.hasBody());
        assertTrue(actualRestoreResult.getHeaders().isEmpty());
        assertEquals(409, actualRestoreResult.getStatusCodeValue());
        CommonResponse body = actualRestoreResult.getBody();
        assertEquals("DM_DMS_052", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Conflict", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#restore(UpdateDocumentBody)}
     */
    @Test
    void testRestore3() {
        doNothing().when(customMongoService)
                .saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(), Mockito.<EventTypes>any());
        doNothing().when(customMongoService)
                .updateDocument(Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any());
        when(customMongoService.doesDocumentExist(Mockito.<String>any())).thenReturn(true);
        when(headerContext.getTenantId()).thenReturn("Tenant");
        ResponseEntity<CommonResponse> actualRestoreResult = deleteRestoreServiceImpl
                .restore(new UpdateDocumentBody("42", "Tenant", "Just cause"));
        assertTrue(actualRestoreResult.hasBody());
        assertTrue(actualRestoreResult.getHeaders().isEmpty());
        assertEquals(201, actualRestoreResult.getStatusCodeValue());
        CommonResponse body = actualRestoreResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Restored the document : 42", body.resultData());
        verify(customMongoService).doesDocumentExist(Mockito.<String>any());
        verify(customMongoService).saveAuditTrail(Mockito.<String>any(), Mockito.<HeaderContext>any(),
                Mockito.<EventTypes>any());
        verify(customMongoService).updateDocument(Mockito.<Map<String, Object>>any(), Mockito.<Map<String, Object>>any());
        verify(headerContext).getTenantId();
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#rename(RenameDocumentDto)}
     */
    @Test
    void testRename() {
        when(fileDetailsRepository.updateFileNameById(Mockito.<String>any(), Mockito.<String>any())).thenReturn(1);
        when(fileDetailsRepository.existsByDocumentId(Mockito.<String>any())).thenReturn(true);
        ResponseEntity<CommonResponse> actualRenameResult = deleteRestoreServiceImpl
                .rename(new RenameDocumentDto("42", "Document Name"));
        assertTrue(actualRenameResult.hasBody());
        assertTrue(actualRenameResult.getHeaders().isEmpty());
        assertEquals(201, actualRenameResult.getStatusCodeValue());
        CommonResponse body = actualRenameResult.getBody();
        assertEquals("DM_DMS_002", body.messageCode());
        assertTrue(body.errorList().isEmpty());
        assertTrue(body.validity());
        assertEquals("Updated successfully", body.message());
        assertEquals("Id : 42 renamed", body.resultData());
        verify(fileDetailsRepository).existsByDocumentId(Mockito.<String>any());
        verify(fileDetailsRepository).updateFileNameById(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test: {@link DeleteRestoreServiceImpl#rename(RenameDocumentDto)}
     */
    @Test
    void testRename2() {
        when(fileDetailsRepository.existsByDocumentId(Mockito.<String>any())).thenReturn(false);
        ResponseEntity<CommonResponse> actualRenameResult = deleteRestoreServiceImpl
                .rename(new RenameDocumentDto("42", "Document Name"));
        assertTrue(actualRenameResult.hasBody());
        assertTrue(actualRenameResult.getHeaders().isEmpty());
        assertEquals(409, actualRenameResult.getStatusCodeValue());
        CommonResponse body = actualRenameResult.getBody();
        assertEquals("DB_NO_DATA", body.messageCode());
        List<?> errorListResult = body.errorList();
        assertTrue(errorListResult.isEmpty());
        assertTrue(body.validity());
        assertEquals("Document doesn't exist, please check the id", body.message());
        assertEquals(errorListResult, body.resultData());
        verify(fileDetailsRepository).existsByDocumentId(Mockito.<String>any());
    }
}

