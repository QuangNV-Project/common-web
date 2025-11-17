package com.quangnv.service.common_web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TenantContextDto {
    String tenantId;
    String domainName;
    String projectType;
    String projectId;

    @Override
    public String toString() {
        return String.format(
                "TenantContextDto[tenantId=%s, domainName=%s, projectType=%s, projectId=%s]",
                tenantId, domainName, projectType, projectId
        );
    }
}
