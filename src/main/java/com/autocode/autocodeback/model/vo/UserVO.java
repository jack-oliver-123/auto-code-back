package com.autocode.autocodeback.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户脱敏视图对象（VO）
 * <p>
 * 用于列表查询等场景，不包含密码等敏感字段。
 * </p>
 */
@Data
public class UserVO implements Serializable {
    
    /**
     * id
     */
    private Long id;
    
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
     * 用户角色：user/admin
     */
    private String userRole;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    private static final long serialVersionUID = 1L;
}
