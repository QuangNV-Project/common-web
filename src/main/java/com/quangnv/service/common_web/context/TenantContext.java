package com.quangnv.service.common_web.context;

import com.quangnv.service.common_web.dto.TenantContextDto;

public class TenantContext {
    private static final ThreadLocal<TenantContextDto> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(TenantContextDto tenantContext) {
        currentTenant.set(tenantContext);
    }

    public static TenantContextDto getCurrentTenant() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
