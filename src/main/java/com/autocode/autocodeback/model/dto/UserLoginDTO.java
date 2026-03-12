package com.autocode.autocodeback.model.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求 DTO
 * <p>
 * 封装前端登录请求的参数：账号和密码。
 * </p>
 */
@Data
public class UserLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    
    /**
     * 账号
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
}
