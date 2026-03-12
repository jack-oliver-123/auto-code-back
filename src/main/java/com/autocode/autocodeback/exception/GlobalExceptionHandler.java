package com.autocode.autocodeback.exception;

import com.autocode.autocodeback.common.BaseResponse;
import com.autocode.autocodeback.common.ResultUtils;
import com.autocode.autocodeback.common.StatusCode;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理器
 * <p>
 * 统一拦截 Controller 层抛出的异常，将其转换为标准化的 {@link BaseResponse} 响应。
 * 按异常类型分层处理：业务异常 → 运行时异常 → 兜底异常。
 * </p>
 */
@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(NoResourceFoundException.class)
    public void handleNoResourceFoundException(NoResourceFoundException e) {
        // 啥都不干，或者只记录一行 trace 日志
        // 这样控制台就不会炸出一大堆 ERROR 堆栈了
    }
    
    /**
     * 处理自定义业务异常
     * <p>
     * 捕获 {@link BusinessException}，返回异常中携带的错误码和信息。
     * </p>
     *
     * @param e 业务异常
     * @return 包含错误码和错误信息的标准响应
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException: code={}, message={}", e.getCode(), e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }
    
    /**
     * 处理运行时异常
     * <p>
     * 捕获未被 {@link BusinessException} 覆盖的 {@link RuntimeException}，
     * 返回通用运行错误响应，避免将内部堆栈信息暴露给前端。
     * </p>
     *
     * @param e 运行时异常
     * @return 通用运行错误响应
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(StatusCode.FAILED_RUN, "运行错误");
    }
    
    /**
     * 兜底异常处理
     * <p>
     * 捕获所有未被上层处理器拦截的 {@link Exception}，
     * 返回系统错误响应，确保任何异常都不会以原始形式返回给客户端。
     * </p>
     *
     * @param e 异常
     * @return 系统错误响应
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> exceptionHandler(Exception e) {
        log.error("Exception", e);
        return ResultUtils.error(StatusCode.FAILED_SYS, "系统错误");
    }
}
