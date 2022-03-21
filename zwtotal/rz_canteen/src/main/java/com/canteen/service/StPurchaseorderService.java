package com.canteen.service;

import com.canteen.utils.PageBean;

public interface StPurchaseorderService {

    /**
     * 采购订单列表
     *
     * @param param
     * @return
     */
    PageBean getPurchaseIndentList(String param);
}
