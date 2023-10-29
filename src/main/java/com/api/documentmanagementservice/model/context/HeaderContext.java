package com.api.documentmanagementservice.model.context;

import org.springframework.stereotype.Component;

@Component
public class HeaderContext {
    private final ThreadLocal<String> tenantId = new ThreadLocal<>();
    private final ThreadLocal<String> hospitalId = new ThreadLocal<>();
    private final ThreadLocal<String> user = new ThreadLocal<>();

    public String getTenantId() {
        return tenantId.get();
    }

    public void setTenantId(String value) {
        tenantId.set(value);
    }

    public String getHospitalId() {
        return hospitalId.get();
    }

    public void setHospitalId(String value) {
        hospitalId.set(value);
    }

    public String getUser() {
        return user.get();
    }

    public void setUser(String value) {
        user.set(value);
    }

    public void clear() {
        tenantId.remove();
        hospitalId.remove();
        user.remove();
    }
}
