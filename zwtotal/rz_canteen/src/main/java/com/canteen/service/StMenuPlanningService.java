package com.canteen.service;

import com.canteen.entity.StMenuPlanning;

public interface StMenuPlanningService {

    //菜单计划查询
    Object getMenus(String paramJson);

    //获取关联属性
    Object getRelevance();

    //编辑订餐及退餐时间
    Object updateMenuPlan(StMenuPlanning stMenuPlanning);
}
