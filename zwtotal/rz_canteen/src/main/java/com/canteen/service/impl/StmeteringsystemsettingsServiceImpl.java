package com.canteen.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.Stmeteringsystemsettings;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.mapper.StmeteringsystemsettingsMapper;
import com.canteen.service.StmeteringsystemsettingsService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StmeteringsystemsettingsServiceImpl implements StmeteringsystemsettingsService {


    @Autowired
    private StmeteringsystemsettingsMapper stmeteringsystemsettingsMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;

    @Autowired
    private StOperationrestaurantMapper stOperationrestaurantMapper;

    /**
     * 菜品类别分页查询分页
     * @return
     */
    @Override
    public PageBean<Stmeteringsystemsettings> getStmeteringsystemsettingsPage(PageBean<Stmeteringsystemsettings> pageBean, String Dishtime, String warningWeight, String platedetectionTime) {
        List<Stmeteringsystemsettings> list = stmeteringsystemsettingsMapper.getStmeteringsystemsettingsList((pageBean.getPageNum()-1)*pageBean.getPageSize(),pageBean.getPageSize(),Dishtime,warningWeight,platedetectionTime,null);
        pageBean.setList(list);
        int count= stmeteringsystemsettingsMapper.getStmeteringsystemsettingsCount(Dishtime,warningWeight,platedetectionTime,null);
        pageBean.setTotalPage(count);
        return pageBean;
    }
    /**
     * 停启用
     * @return
     */
    @Override
    public boolean updateEnable(Stmeteringsystemsettings stmeteringsystemsettings) {
        if(stmeteringsystemsettings.getState().equals("0")){
            stmeteringsystemsettings.setState("1");
            return stmeteringsystemsettingsMapper.updateEnable(stmeteringsystemsettings);
        }
            stmeteringsystemsettings.setState("0");
            return stmeteringsystemsettingsMapper.updateEnable(stmeteringsystemsettings);
    }

    /**
     *输入条件可查询为空时，设置添加
     */
    @Override
    public boolean saveStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings) {
      List<Stmeteringsystemsettings> stmeteringsystemsettingslist= stmeteringsystemsettingsMapper.getStmeteringsystemsettings(stmeteringsystemsettings.getPlatedetectionTime(),stmeteringsystemsettings.getDishTime(),stmeteringsystemsettings.getWarningWeight());
      if(!stmeteringsystemsettings.getPlatedetectionTime().isEmpty()&& !stmeteringsystemsettings.getDishTime().isEmpty()&& !stmeteringsystemsettings.getWarningWeight().isEmpty()){
           if(stmeteringsystemsettingslist.size()==0){
               stmeteringsystemsettingsMapper.saveStmeteringsystemsettings(stmeteringsystemsettings);
           }
      }
        return false;
    }

    @Override
    public List<Stmeteringsystemsettings> getStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings) {
        return null;
    }


    //---------------------------------------------------------------------------------------------------------------

    /*
    * 计量系统设置列表
    * {"pageNum":"","pageSize":"","platedetectionTime":"","dishTime":"","warningWeight":"","restaurant":""}
    * */
    @Override
    public Object getStmetering(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String platedetectionTime = rowData.getString("platedetectionTime");
        String dishTime = rowData.getString("dishTime");
        String warningWeight = rowData.getString("warningWeight");
        String str = rowData.getString("restaurant");
        String restaurant = stOperationrestaurantService.getIds(str);
        Integer totalSize = stmeteringsystemsettingsMapper.getStmeteringsystemsettingsCount(dishTime, warningWeight, platedetectionTime, restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Stmeteringsystemsettings> stmeteringsystemsettingsList = stmeteringsystemsettingsMapper.getStmeteringsystemsettingsList(startIndex, pageSize, dishTime, warningWeight, platedetectionTime, restaurant);
            pageBean.setList(stmeteringsystemsettingsList);
        }
        return pageBean;
    }

    /*
    * 变更状态
    * {"id":"","state":""}
    * */
    @Override
    public Object updateState(Stmeteringsystemsettings stmeteringsystemsettings) {
        if(stmeteringsystemsettings.getState().equals("0")){
            stmeteringsystemsettings.setState("1");
        }else {
            stmeteringsystemsettings.setState("0");
        }
        stmeteringsystemsettingsMapper.updateEnable(stmeteringsystemsettings);
        return "变更成功";
    }

    /*
    * 删除设置
    * {"id":""}
    * */
    @Override
    public Object deleteStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings) {
        stmeteringsystemsettingsMapper.deleteById(stmeteringsystemsettings);
        return "删除成功";
    }

    /*
    * 添加设置
    * {"dishTime":"","platedetectionTime":"","warningWeight":"","operationreStaurant":""}
    * */
    @Override
    public Object addStmeteringsystemsettings(Stmeteringsystemsettings stmeteringsystemsettings) {
        stmeteringsystemsettings.setDelFlag("0");
        stmeteringsystemsettings.setState("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(new Date());
        stmeteringsystemsettings.setSynchronizationTime(s);
        stmeteringsystemsettingsMapper.addStmetering(stmeteringsystemsettings);
        return "设置成功";
    }

    //获取下拉框数据
    @Override
    public Object dropDownBox() {
        Map map = new LinkedHashMap<>();
        List restaurant = stOperationrestaurantMapper.getRestaurant();
        List state = new ArrayList();
        state.add(JSONObject.parseObject("{'id':'0','text':'停用'}"));
        state.add(JSONObject.parseObject("{'id':'1','text':'启用'}"));
        map.put("restaurant",restaurant);
        map.put("state",state);
        return map;
    }
}
