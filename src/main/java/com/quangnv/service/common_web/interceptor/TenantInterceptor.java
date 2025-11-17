package com.quangnv.service.common_web.interceptor;

import com.quangnv.service.common_web.context.TenantContext;
import com.quangnv.service.common_web.dto.TenantContextDto;
import com.quangnv.service.utility_shared.constant.HeaderConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TenantInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getHeader(HeaderConstants.TENANT_ID);
        String domainName = request.getHeader(HeaderConstants.DOMAIN_NAME);
        String projectType = request.getHeader(HeaderConstants.PROJECT_TYPE);
        String projectId = request.getHeader(HeaderConstants.PROJECT_ID);

        if (tenantId != null) {
            TenantContextDto tenantInfo = new TenantContextDto(
                    tenantId,
                    domainName,
                    projectType,
                    projectId
            );
            log.info("Setting tenant ID to TenantContext: {}", tenantInfo);
            TenantContext.setCurrentTenant(tenantInfo);
        } else {
            log.error("Header X-Tenant-ID header not found");
            throw new ValidationException("X-Tenant-ID header is missing!");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            TenantContext.clear();
        } catch (Exception e) {
            log.error("TenantInterceptor{}", e.getMessage());
            throw e;
        }
    }
}
