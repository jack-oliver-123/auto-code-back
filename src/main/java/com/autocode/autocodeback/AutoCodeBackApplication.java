package com.autocode.autocodeback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Auto Code 后端应用启动类
 * <p>
 * 基于 Spring Boot 构建，启用 AOP 代理并暴露代理对象，
 * 支持在同一类内通过 {@code AopContext.currentProxy()} 调用增强方法。
 * </p>
 *
 * @author AutoCode
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class AutoCodeBackApplication {

    /**
     * 应用程序入口方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(AutoCodeBackApplication.class, args);
    }
}
