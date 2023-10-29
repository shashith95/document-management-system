package com.api.documentmanagementservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl {
    private final MessageSource messageSource;

    public String getLocalizedMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        // Use localizedMessage as needed
        return messageSource.getMessage(key, null, "Server Side Error Occurred", locale);
    }
}
