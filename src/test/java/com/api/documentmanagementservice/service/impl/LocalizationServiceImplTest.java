package com.api.documentmanagementservice.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {LocalizationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class LocalizationServiceImplTest {
    @Autowired
    private LocalizationServiceImpl localizationServiceImpl;

    @MockBean
    private MessageSource messageSource;

    /**
     * Method under test: {@link LocalizationServiceImpl#getLocalizedMessage(String)}
     */
    @Test
    void testGetLocalizedMessage() {
        assertEquals("Server Side Error Occurred", localizationServiceImpl.getLocalizedMessage("Key"));
    }
}

