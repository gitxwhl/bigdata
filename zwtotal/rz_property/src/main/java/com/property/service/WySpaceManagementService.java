package com.property.service;

import com.property.utils.PageBean;
import java.util.Map;

public interface WySpaceManagementService {
    //空间查询
    PageBean selectSpace(String param);

    //空间信息修改
    Map updateSpaceById(String param);
    void updateSpace(String param);

    //空间信息删除
    void deleteSpace(String param);

    //新增空间信息
    void insertSpace(String param);

    //查看附件
    Map getSpaceFile(String param);
}
