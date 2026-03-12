package com.autocode.autocodeback.common;

/**
 * 统一响应结果工具类
 * <p>
 * 提供快捷方法生成成功或失败的 {@link BaseResponse} 对象，
 * 简化 Controller 层的返回值构建。
 * </p>
 */
public class ResultUtils {
    
    /**
     * 私有构造器，防止工具类被实例化
     */
    private ResultUtils() {
    }
    
    // ======================== 成功响应 ========================
    
    /**
     * 返回无数据的成功响应
     *
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(StatusCode.SUCCESS);
    }
    
    /**
     * 返回无数据的成功响应
     *
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> BaseResponse<T> success(StatusCode statusCode) {
        return new BaseResponse<>(statusCode);
    }
    
    /**
     * 返回无数据的成功响应
     *
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> BaseResponse<T> success(StatusCode statusCode, T data) {
        return new BaseResponse<>(statusCode, data);
    }
    
    /**
     * 返回自定义消息的成功响应（无数据）
     *
     * @param message 自定义成功消息
     * @param <T>     数据类型
     * @return 成功响应
     */
    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<>(StatusCode.SUCCESS, message);
    }
    
    /**
     * 返回带数据的成功响应
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(StatusCode.SUCCESS, data);
    }
    
    /**
     * 返回带数据和自定义消息的成功响应
     *
     * @param data    响应数据
     * @param message 自定义成功消息
     * @param <T>     数据类型
     * @return 成功响应
     */
    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(StatusCode.SUCCESS, data, message);
    }
    
    // ======================== 失败响应 ========================
    
    /**
     * 返回默认的失败响应
     *
     * @return 失败响应
     */
    public static <T> BaseResponse<T> error() {
        return new BaseResponse<>(StatusCode.FAILED);
    }
    
    /**
     * 返回自定义消息的失败响应
     *
     * @param message 错误信息
     * @return 失败响应
     */
    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(StatusCode.FAILED, message);
    }
    
    /**
     * 返回自定义错误码和消息的失败响应
     *
     * @param code    自定义错误码
     * @param message 错误信息
     * @return 失败响应
     */
    public static <T> BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }
    
    /**
     * 返回标准状态码配合自定义消息的失败响应
     *
     * @param statusCode 标准状态码枚举
     * @param message    自定义错误信息
     * @return 失败响应
     */
    public static <T> BaseResponse<T> error(StatusCode statusCode, String message) {
        return new BaseResponse<T>(statusCode.getCode(), null, message);
    }
    
    /**
     * 返回标准状态码消息的失败响应
     *
     * @param statusCode 标准状态码枚举
     * @return 失败响应
     */
    public static <T> BaseResponse<T> error(StatusCode statusCode) {
        return new BaseResponse<T>(statusCode);
    }
    
    
}
