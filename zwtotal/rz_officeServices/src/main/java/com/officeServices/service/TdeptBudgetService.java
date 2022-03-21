package com.officeServices.service;

import com.officeServices.entity.TdeptBudget;
import com.officeServices.entity.Tevaluate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TdeptBudgetService {

    //查询预算列表
    Object getBudget(Map<String,Object> map);

    //预算设置
    Object insertBudget(TdeptBudget tdeptBudget);

    //导入年度预算
    Object importBudget(HttpServletRequest request) throws Exception;

    //查询申请信息
    Object getBudgetInfo(Map<String,Object> map);

    //下拉框
    Object dropDownBox();

    //用户查询申请情况
    Object getSituation(Map<String,Object> map);

    //用户查询评价列表
    Object getPjList(Map<String,Object> map);

    //用户评价
    Object evaluate(List<Tevaluate> list);
}
