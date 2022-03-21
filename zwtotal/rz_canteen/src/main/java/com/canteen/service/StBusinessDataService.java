package com.canteen.service;

import com.canteen.utils.PageBean;

public interface StBusinessDataService {

    //查询营业数据
    PageBean selectStBusinessData(String param);
}
