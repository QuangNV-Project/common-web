package com.quangnv.service.common_web.dto;

import com.quangnv.service.utility_shared.constant.RoleValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserContextDto {
    String userId;
    String userName;
    List<RoleValue> userRoles;

    public static UserContextDto fromHeaders(
            String userId,
            String userName,
            List<String> roleHeaders
    ) {
        return new UserContextDto(
                userId,
                userName,
                roleHeaders.stream().map(RoleValue::fromAuthority).toList()
        );
    }
}
