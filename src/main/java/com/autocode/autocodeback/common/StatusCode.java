package com.autocode.autocodeback.common;

import lombok.Getter;

/**
 * 自定义业务状态码枚举
 * <p>
 * 定义系统中所有标准化的响应状态码及对应提示信息，
 * 用于 {@link BaseResponse} 与
 * {@link com.autocode.autocodeback.exception.BusinessException} 统一状态标识。
 * </p>
 *
 * <h3>编码规则</h3>
 * <ul>
 * <li>{@code 2xxxx} — 成功</li>
 * <li>{@code 4xxxx} — 客户端错误（请求参数、权限等问题）</li>
 * <li>{@code 5xxxx} — 服务端错误（运行异常、系统故障）</li>
 * </ul>
 *
 * <h3>万位分组</h3>
 * <ul>
 * <li>{@code x0xxx} — 通用</li>
 * <li>{@code x1xxx} — 认证 / 授权</li>
 * <li>{@code x2xxx} — 参数校验</li>
 * <li>{@code x3xxx} — 数据 / 资源</li>
 * <li>{@code x4xxx} — 限流 / 频控</li>
 * </ul>
 */
@Getter
public enum StatusCode {

    // ======================== 2xxxx 成功 ========================

    /**
     * 操作成功
     */
    SUCCESS(20000, "操作成功"),

    // ======================== 4xxxx 客户端错误 ========================

    // ---------- 40xxx 通用客户端错误 ----------

    /**
     * 请求错误（通用）
     */
    BAD_REQUEST(40000, "请求错误"),

    // ---------- 41xxx 认证 / 授权 ----------

    /**
     * 未登录或登录已过期
     */
    NOT_LOGIN(41000, "未登录"),

    /**
     * 无权限访问
     */
    NO_AUTH(41001, "无权限"),

    /**
     * 账号已被封禁
     */
    ACCOUNT_BANNED(41002, "账号已封禁"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(41003, "密码错误"),

    // ---------- 42xxx 参数校验 ----------

    /**
     * 请求参数为空
     */
    PARAMS_NULL(42000, "参数为空"),

    /**
     * 请求参数格式或值不合法
     */
    PARAMS_ERROR(42001, "参数错误"),

    // ---------- 43xxx 数据 / 资源 ----------

    /**
     * 请求的数据不存在
     */
    NOT_FOUND(43000, "数据不存在"),

    /**
     * 数据已存在（重复提交 / 唯一约束冲突）
     */
    DATA_DUPLICATE(43001, "数据已存在"),

    // ---------- 44xxx 限流 / 频控 ----------

    /**
     * 请求过于频繁，触发限流
     */
    TOO_MANY_REQUESTS(44000, "请求过于频繁"),

    // ======================== 5xxxx 服务端错误 ========================

    // ---------- 50xxx 通用服务端错误 ----------

    /**
     * 操作失败（通用）
     */
    FAILED(50000, "操作失败"),

    /**
     * 运行时错误
     */
    FAILED_RUN(50001, "运行错误"),

    /**
     * 系统内部错误
     */
    FAILED_SYS(50002, "系统错误"),

    /**
     * 第三方服务调用失败
     */
    THIRD_PARTY_ERROR(50003, "第三方服务异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码对应的提示信息
     */
    private final String message;

    /**
     * 构造状态码枚举实例
     *
     * @param code    状态码值
     * @param message 提示信息
     */
    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
