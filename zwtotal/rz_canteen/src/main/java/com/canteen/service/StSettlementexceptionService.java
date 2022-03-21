package com.canteen.service;

import com.canteen.utils.PageBean;

import java.util.Map;


public interface StSettlementexceptionService {
    PageBean getExceptionList(String paramJson);

    //获取字典表定义
    Map dictionaries();
}
