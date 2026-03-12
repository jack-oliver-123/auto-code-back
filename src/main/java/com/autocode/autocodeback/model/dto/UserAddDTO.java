package com.autocode.autocodeback.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 管理员添加用户请求 DTO
 * <p>
 * 封装管理员手动创建用户时的参数：昵称、账号、头像、简介、角色。
 * </p>
 */
@Data
public class UserAddDTO implements Serializable {
    
    /**
     * 用户昵称
     */
    private String userName;
    
    /**
     * 账号
     */
    private String userAccount;
    
    /**
     * 用户头像
     */
    private String userAvatar;
    
    /**
     * 用户简介
     */
    private String userProfile;
    
    /**
     * 用户角色: user, admin
     */
    private String userRole;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
