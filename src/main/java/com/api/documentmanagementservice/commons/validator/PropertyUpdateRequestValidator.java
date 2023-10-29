package com.api.documentmanagementservice.commons.validator;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;

import static com.api.documentmanagementservice.commons.constant.CommonContent.TENANT;
import static com.api.documentmanagementservice.commons.constant.HeaderContent.TENANT_ID;

@Slf4j
@Component
public class PropertyUpdateRequestValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        Map<String, Object> requestMap = (Map<String, Object>) target;

        Object tenantValue = requestMap.get("Tenant");
        Object statusValue = requestMap.get("Status");
        Object idValue = requestMap.get("_id");

        // Check if request headers are empty or null
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String headerTenantId = request.getHeader(TENANT_ID.getFieldName());

        if (tenantValue == null || tenantValue.toString().isEmpty()) {
            errors.rejectValue(TENANT.getContent(), "NotEmpty", "Tenant cannot be null or empty");
        }

        if (!(statusValue instanceof Boolean)) {
            errors.rejectValue("Status", "Invalid", "Status cannot be null and should be a boolean");
        }

        if (idValue == null || idValue.toString().isEmpty()) {
            errors.rejectValue("_id", "NotEmpty", "_id cannot be null or empty");
        }

        if (!headerTenantId.equals(tenantValue)) {
            errors.rejectValue(TENANT.getContent(), "Invalid", "Header tenant does not match with request body");
        }

    }

}
