package com.api.documentmanagementservice.commons.validator;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;

import static com.api.documentmanagementservice.commons.constant.HeaderContent.TENANT_ID;

@Component
public class TenantIdValidator implements Validator {
    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        Map<String, Object> requestMap = (Map<String, Object>) target;
        Object tenantValue = requestMap.get("Tenant");

        // Check if request headers are empty or null
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String headerTenantId = request.getHeader(TENANT_ID.getFieldName());

        if (!headerTenantId.equals(tenantValue)) {
            errors.rejectValue("Tenant", "Invalid", "Header tenant does not match with request body");
        }
    }
}
