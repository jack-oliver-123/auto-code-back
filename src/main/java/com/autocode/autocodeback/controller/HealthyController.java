package com.autocode.autocodeback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检测控制器
 * <p>
 * 提供简单的健康检查端点，用于监控服务是否正常运行。
 * 可供负载均衡器、Kubernetes 探针或运维脚本调用。
 * </p>
 */
@Tag(name = "健康检测", description = "服务健康状态检查接口")
@RestController
@RequestMapping("/healthy")
public class HealthyController {

    /**
     * 健康检查接口
     * <p>
     * 返回 "ok" 表示服务正常运行。
     * </p>
     *
     * @return 固定字符串 "ok"
     */
    @Operation(summary = "健康检查", description = "返回 ok 表示服务运行正常")
    @GetMapping("/")
    public String healthy() {
        return "ok";
    }
}
