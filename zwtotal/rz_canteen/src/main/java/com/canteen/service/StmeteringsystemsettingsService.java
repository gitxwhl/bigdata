package com.canteen.service;
import com.canteen.entity.Stmeteringsystemsettings;
import com.canteen.utils.PageBean;

import java.util.List;

public interface StmeteringsystemsettingsService {
    /**
     * 菜品类别分页查询分页
     * @return
     */
    PageBean<Stmeteringsystemsettings> getStmeteringsystemsettingsPage(PageBean<Stmeteringsystemsettings> pageBean, String Dishtime , String warningWeight,String platedetectionTime);
    /**
     *停启用 1 : 启用  0:停用
     */
    public boolean updateEnable(Stmeteringsystemsettings stmeteringsystemsettings);
    /**
     *输入条件可查询为空时，设置添加
     */
    boolean saveStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings);
    /**
     *输入条件可查询为空时，设置添加
     */
    List<Stmeteringsystemsettings> getStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings);


    //--------------------------------------------------------------------------------------------------------------
    //计量系统设置列表
    Object getStmetering(String paramJson);

    //状态变更
    Object updateState(Stmeteringsystemsettings stmeteringsystemsettings);

    //删除设置
    Object deleteStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings);

    //新增设置
    Object addStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings);

    //获取下拉框数据
    Object dropDownBox();
}
