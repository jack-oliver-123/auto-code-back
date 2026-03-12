package com.autocode.autocodeback.model.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户 实体类。
 *
 * @author Jack
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tb_user")
public class User implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 2L;
    
    /**
     * id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;
    
    /**
     * 账号
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
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
    
    /**
     * 编辑时间
     */
    private LocalDateTime editTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 是否删除
     */
    @Column(isLogicDelete = true)
    private Integer isDelete;
    
}
