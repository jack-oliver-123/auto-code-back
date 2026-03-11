package com.autocode.autocodeback.exception;

import com.autocode.autocodeback.common.StatusCode;

/**
 * 异常抛出工具类
 * <p>
 * 提供条件判断式的异常抛出快捷方法，简化参数校验和业务断言代码。
 * </p>
 *
 * <pre>
 * // 使用示例：
 * ThrowUtils.throwIf(user == null, StatusCode.FAILED, "用户不存在");
 * </pre>
 */
public class ThrowUtils {

    /**
     * 私有构造器，防止工具类被实例化
     */
    private ThrowUtils() {
    }

    /**
     * 条件成立则抛出指定的运行时异常
     *
     * @param condition        判断条件，为 {@code true} 时抛出异常
     * @param runtimeException 待抛出的运行时异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛出标准状态码对应的业务异常
     *
     * @param condition 判断条件，为 {@code true} 时抛出异常
     * @param errorCode 标准状态码枚举
     */
    public static void throwIf(boolean condition, StatusCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛出标准状态码配合自定义消息的业务异常
     *
     * @param condition 判断条件，为 {@code true} 时抛出异常
     * @param errorCode 标准状态码枚举
     * @param message   自定义错误描述信息
     */
    public static void throwIf(boolean condition, StatusCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
