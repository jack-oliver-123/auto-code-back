package com.autocode.autocodeback.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用响应封装类
 * <p>
 * 统一封装所有 API 接口的返回格式，包含状态码、响应数据和提示信息。
 * 使用泛型 {@code <T>} 支持任意类型的响应数据。
 * </p>
 *
 * @param <T> 响应数据的类型
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 全参构造器
     *
     * @param code    状态码
     * @param data    响应数据
     * @param message 响应信息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 带状态码和数据的构造器，信息默认为空字符串
     *
     * @param code 状态码
     * @param data 响应数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 通过标准状态码构造（无数据）
     *
     * @param status 标准状态码枚举
     */
    public BaseResponse(StatusCode status) {
        this(status.getCode(), null, status.getMessage());
    }

    /**
     * 通过标准状态码和自定义信息构造（无数据）
     *
     * @param status  标准状态码枚举
     * @param message 自定义响应信息
     */
    public BaseResponse(StatusCode status, String message) {
        this(status.getCode(), null, message);
    }

    /**
     * 通过标准状态码和数据构造，信息取自状态码默认值
     *
     * @param status 标准状态码枚举
     * @param data   响应数据
     */
    public BaseResponse(StatusCode status, T data) {
        this(status.getCode(), data, status.getMessage());
    }

    /**
     * 通过标准状态码、数据和自定义信息构造
     *
     * @param status  标准状态码枚举
     * @param data    响应数据
     * @param message 自定义响应信息
     */
    public BaseResponse(StatusCode status, T data, String message) {
        this(status.getCode(), data, message);
    }
}
