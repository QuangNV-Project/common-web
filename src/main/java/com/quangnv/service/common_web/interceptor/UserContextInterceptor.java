package com.quangnv.service.common_web.interceptor;

import com.quangnv.service.common_web.constant.HeaderConstants;
import com.quangnv.service.common_web.context.UserContext;
import com.quangnv.service.common_web.dto.UserContextDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(HeaderConstants.USER_ID);
        String userName = request.getHeader(HeaderConstants.USER_NAME);
        String userRole = request.getHeader(HeaderConstants.USER_ROLE);
        UserContext.set(new UserContextDto(userId, userName, userRole));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}