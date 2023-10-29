package com.api.documentmanagementservice.interceptor;

import com.api.documentmanagementservice.commons.constant.ErrorCode;
import com.api.documentmanagementservice.commons.constant.HeaderContent;
import com.api.documentmanagementservice.exception.BadRequestException;
import com.api.documentmanagementservice.model.context.HeaderContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderValidationInterceptor implements HandlerInterceptor {
    private final HeaderContext headerContext;

    /**
     * Interceptor for validating request headers before processing HTTP requests.
     * This interceptor checks if the required headers (tenantId, hospitalId, and user) are present and not empty
     * in incoming HTTP requests. If any header is missing or empty, it throws a BadRequestException.
     * Valid headers are stored in the HeaderContext for future use.
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws BadRequestException {
        log.debug("Validating request headers...");

        // Extract headers from the request
        String tenantId = request.getHeader(HeaderContent.TENANT_ID.getFieldName());
        String hospitalId = request.getHeader(HeaderContent.HOSPITAL_ID.getFieldName());
        String user = request.getHeader(HeaderContent.USER.getFieldName());

        // Check if any of the headers are empty
        boolean isHeaderEmpty = Stream.of(tenantId, hospitalId, user)
                .anyMatch(String::isEmpty);

        if (isHeaderEmpty) {
            log.error(ErrorCode.HDR_INVALID_DATA.getDescription());
            throw new BadRequestException(ErrorCode.HDR_INVALID_DATA.getCode(), ErrorCode.HDR_INVALID_DATA.getDescription());
        }
        log.info("Request headers validated successfully with tenantId: {}, hospitalId: {} and user: {}", tenantId, hospitalId, user);

        // Headers are valid, set them in the HeaderContext for future use
        headerContext.setTenantId(tenantId);
        headerContext.setHospitalId(hospitalId);
        headerContext.setUser(user);

        return true;
    }
}
