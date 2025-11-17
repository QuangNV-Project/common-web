package com.quangnv.service.common_web.config;

import com.quangnv.service.common_web.interceptor.TenantInterceptor;
import com.quangnv.service.common_web.interceptor.UserContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new UserContextInterceptor())
            .addPathPatterns("/**");

      registry.addInterceptor(new TenantInterceptor())
              .addPathPatterns("/**");
  }
}
