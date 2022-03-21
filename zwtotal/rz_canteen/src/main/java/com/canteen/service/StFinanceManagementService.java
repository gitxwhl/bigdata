package com.canteen.service;

public interface StFinanceManagementService {

    //就餐结算明细列表
    Object detailList(String paramJson);

    //食堂经营成本统计
    Object costStatistics(String paramJson);
}
