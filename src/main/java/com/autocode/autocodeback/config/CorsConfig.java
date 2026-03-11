package com.autocode.autocodeback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局跨域资源共享（CORS）配置
 * <p>
 * 允许前端应用从不同域名访问后端 API，支持携带 Cookie 的跨域请求。
 * </p>
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许发送 Cookie
                .allowCredentials(true)
                // 放行所有域名（使用 patterns 避免与 allowCredentials 冲突）
                .allowedOriginPatterns("*")
                // 允许的 HTTP 方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许的请求头
                .allowedHeaders("*")
                // 允许前端获取的响应头
                .exposedHeaders("*");
    }
}
