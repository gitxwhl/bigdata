package com.raysdata.riskmanagementserver.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * @author zyyy
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> {
    /**
     * 内容列表
     */
    private List<T> content;
    /**
     * 每页大小
     */
    private int size ;
    /**
     * list中元素有多少个
     */
    private int elementTotalSize;
    /**
     * 当前页数
     */
    private int page;
    /**
     * 总的页数
     */
    private int totalPage;
    /**
     * 总共的数量
     */
    private int totalSize;


}
