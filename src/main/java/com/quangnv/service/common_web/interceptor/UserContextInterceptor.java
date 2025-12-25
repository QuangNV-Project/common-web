package com.quangnv.service.common_web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.quangnv.service.common_web.dto.UserContextDto;
import com.quangnv.service.common_web.context.UserContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.quangnv.service.utility_shared.constant.HeaderConstants;

import java.util.List;

@Component
public class UserContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(HeaderConstants.USER_ID);
        String userName = request.getHeader(HeaderConstants.USER_NAME);
        String userRole = request.getHeader(HeaderConstants.USER_ROLE);
        if (userId == null && userName == null && userRole == null) {
            return true;
        }
        List<String> roles = List.of(userRole.split(","));
        UserContext.set(
                UserContextDto.fromHeaders(userId, userName, roles)
        );
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}