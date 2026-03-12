package com.autocode.autocodeback.model.dto;

import com.autocode.autocodeback.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户分页查询请求 DTO
 * <p>
 * 继承 {@link PageRequest} 提供分页参数，额外包含用户名、账号、角色等筛选条件。
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryDTO extends PageRequest implements Serializable {
    
    /**
     * id
     */
    private Long id;
    
    /**
     * 用户昵称
     */
    private String userName;
    
    /**
     * 账号
     */
    private String userAccount;
    
    /**
     * 简介
     */
    private String userProfile;
    
    /**
     * 用户角色：user/admin/ban
     */
    private String userRole;
    
    @Serial
    private static final long serialVersionUID = 1L;
}
