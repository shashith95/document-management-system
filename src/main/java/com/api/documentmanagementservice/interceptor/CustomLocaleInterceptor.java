package com.api.documentmanagementservice.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

@Slf4j
public class CustomLocaleInterceptor implements HandlerInterceptor {

    /**
     * Interceptor method for setting the desired locale based on the "lang" parameter in the request.
     * If the "lang" parameter is present in the request, this method sets the desired locale in the
     * LocaleContextHolder, allowing for localization of the application based on the specified language.
     *
     * @param request  The incoming HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param handler  The handler for the request.
     * @return true to continue processing the request; false to stop processing.
     * @throws Exception if an error occurs during the execution of the interceptor.
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws Exception {

        // Extract the "lang" parameter from the request
        String lang = request.getParameter("lang");

        // If the "lang" parameter is present, set the desired locale
        if (lang != null) {
            // Set the desired locale in the LocaleContextHolder
            Locale desiredLocale = new Locale(lang);
            LocaleContextHolder.setLocale(desiredLocale);
            log.info("Locale setup for Lang: {}", lang);
        }

        // Continue processing the request
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
