package com.autocode.autocodeback.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求封装类
 * <p>
 * 用于接收前端传入的分页查询参数，包含页码、页大小以及排序配置。
 * </p>
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排序方式 — 升序
     */
    public static final String SORT_ORDER_ASC = "ascend";

    /**
     * 排序方式 — 降序
     */
    public static final String SORT_ORDER_DESC = "descend";

    /**
     * 当前页号（从 1 开始）
     */
    private int pageNum = 1;

    /**
     * 每页显示条数
     */
    private int pageSize = 10;

    /**
     * 排序字段名称
     */
    private String sortField;

    /**
     * 排序顺序（默认降序），可选值：{@link #SORT_ORDER_ASC}、{@link #SORT_ORDER_DESC}
     */
    private String sortOrder = SORT_ORDER_DESC;
}
