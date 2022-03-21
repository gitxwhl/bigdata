package com.canteen.service;

import com.canteen.entity.StIntelligenceMealsVo;

import java.util.List;

public interface StproposalService {

    /**
     * 根据体检指标给出健康建议
     */
    void getStproposal();

    /**
     * 当日菜品营养分析查询
     */
    List<StIntelligenceMealsVo> findStIntelligenceMealsVo(String restaurant);


}
