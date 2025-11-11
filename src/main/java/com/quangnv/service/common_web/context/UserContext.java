package com.quangnv.service.common_web.context;

import com.quangnv.service.common_web.dto.UserContextDto;

public class UserContext {
    private static final ThreadLocal<UserContextDto> CONTEXT = new ThreadLocal<>();

    public static void set(UserContextDto user) {
        CONTEXT.set(user);
    }

    public static UserContextDto get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
