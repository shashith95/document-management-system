package com.api.documentmanagementservice.service.impl;

import com.api.documentmanagementservice.model.table.DocumentDetails;
import com.api.documentmanagementservice.repository.DocumentDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {DocumentDetailsImpl.class})
@ExtendWith(SpringExtension.class)
class DocumentDetailsImplTest {
    @Autowired
    private DocumentDetailsImpl documentDetailsImpl;

    @MockBean
    private DocumentDetailsRepository documentDetailsRepository;

    /**
     * Method under test: {@link DocumentDetailsImpl#createDocumentDetails(Long, String)}
     */
    @Test
    void testCreateDocumentDetails() {
        DocumentDetails documentDetails = new DocumentDetails();
        when(documentDetailsRepository.save(Mockito.<DocumentDetails>any())).thenReturn(documentDetails);
        assertSame(documentDetails, documentDetailsImpl.createDocumentDetails(1L, "42"));
        verify(documentDetailsRepository).save(Mockito.<DocumentDetails>any());
    }
}

