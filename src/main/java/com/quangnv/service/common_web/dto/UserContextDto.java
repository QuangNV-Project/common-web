package com.quangnv.service.common_web.dto;

import com.quangnv.service.utility_shared.constant.RoleValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserContextDto {
    String userId;
    String userName;
    RoleValue userRole;

    public static UserContextDto fromHeaders(
            String userId,
            String userName,
            String roleHeader
    ) {
        return new UserContextDto(
                userId,
                userName,
                RoleValue.fromAuthority(roleHeader)
        );
    }
}
