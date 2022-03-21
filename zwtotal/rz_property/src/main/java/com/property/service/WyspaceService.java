package com.property.service;

import com.property.entity.Wyspace;

public interface WyspaceService {

    //保洁区域列表
    Object getCleanSpace(String paramJson);

    //查看附件
    Object getFiles(String paramJson);

    //新增保洁区域
    Object addCleanSpace(String paramJson);

    //修改保洁区域
    Object updateCleanSpace(String paramJson);

    //删除保洁区域
    Object deleteCleanSpace(Wyspace wyspace);

    //获取下拉框数据
    Object dropDownBox();
}
