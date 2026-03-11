package com.autocode.autocodeback.exception;

import com.autocode.autocodeback.common.StatusCode;
import lombok.Getter;

/**
 * 自定义业务异常
 * <p>
 * 用于在业务逻辑中抛出可预期的异常，携带自定义错误码。
 * 由 {@link GlobalExceptionHandler} 统一捕获并返回标准化响应。
 * </p>
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final int code;

    /**
     * 通过自定义错误码和信息构造业务异常
     *
     * @param code    错误码
     * @param message 错误描述信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 通过标准状态码和自定义信息构造业务异常
     *
     * @param errorCode 标准状态码枚举
     * @param message   自定义错误描述信息
     */
    public BusinessException(StatusCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    /**
     * 通过标准状态码构造业务异常，信息取自状态码默认值
     *
     * @param errorCode 标准状态码枚举
     */
    public BusinessException(StatusCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
