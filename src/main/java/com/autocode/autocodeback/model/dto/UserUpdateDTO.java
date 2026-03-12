package com.autocode.autocodeback.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息更新请求 DTO
 * <p>
 * 封装管理员修改用户信息时的参数：用户 ID、昵称、头像、简介、角色。
 * </p>
 */
@Data
public class UserUpdateDTO implements Serializable {
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 用户昵称
     */
    private String userName;
    
    /**
     * 用户头像
     */
    private String userAvatar;
    
    /**
     * 简介
     */
    private String userProfile;
    
    /**
     * 用户角色：user/admin
     */
    private String userRole;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
