package com.autocode.autocodeback.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用删除请求封装类
 * <p>
 * 用于接收前端传入的删除操作请求，包含待删除资源的唯一标识 ID。
 * </p>
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * 待删除资源的唯一标识 ID
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
