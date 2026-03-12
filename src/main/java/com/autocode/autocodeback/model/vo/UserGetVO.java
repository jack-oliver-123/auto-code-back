package com.autocode.autocodeback.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息前端展示类
 */
@Setter
@Getter
public class UserGetVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    /**
     * 账号
     */
    private String username;
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 用户头像
     */
    private String userAvatar;
    
    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色
     */
    private String userRole;

}
