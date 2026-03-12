package com.autocode.autocodeback.constant;

/**
 * 用户模块常量定义
 * <p>
 * 定义用户登录态键名和角色标识等常量，供 Controller、Service、AOP 等层统一引用。
 * </p>
 */
public interface UserConstant {
    
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "user_login";
    
    //  region 权限
    
    /**
     * 默认角色
     */
    String DEFAULT_ROLE = "user";
    
    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "admin";
    
    // endregion
}
