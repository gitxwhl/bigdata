package com.canteen.service;

import com.canteen.utils.PageBean;

public interface StquantitypriceService {
    /**
     * 获取商品管理列表
     * @param param
     * @return
     */
    PageBean getCommodityList(String param);

    /**
     * 改下列表价格数据
     * @param param
     * @return
     */
    String amendPrice(String param);
}
