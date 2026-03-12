package com.autocode.autocodeback.model.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用于接收用户信息的类
 */
@Data
public class UserRegisterDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    
    /**
     * 账号
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 确认密码
     */
    private String confirmPassword;
}
