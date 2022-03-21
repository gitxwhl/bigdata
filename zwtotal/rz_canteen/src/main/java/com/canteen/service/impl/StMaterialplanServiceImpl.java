package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StMaterialplan;
import com.canteen.mapper.StMaterialplanMapper;
import com.canteen.service.StMaterialplanService;
import com.canteen.utils.ExportExcel;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StMaterialplanServiceImpl implements StMaterialplanService {

    @Autowired
    private StMaterialplanMapper stMaterialplanMapper;

    @Autowired
    private StOperationrestaurantServiceImpl operationrestaurantService;

    /*
    * 原料需求查询
    * {"pageNum":"","pageSize":"","beginTime":"","endTime":"","mealType":"","restaurant":""}
    * */
    @Override
    public Object getMaterialPlan(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String beginTime = rowData.getString("beginTime");
        String endTime = rowData.getString("endTime");
        String mealType = rowData.getString("mealType");
        String str = rowData.getString("restaurant");
        String restaurant = operationrestaurantService.getIds(str);
        Integer totalSize = stMaterialplanMapper.getMaterialPlanCnt(beginTime, endTime, mealType, restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List materialPlan = stMaterialplanMapper.getMaterialPlan(beginTime, endTime, mealType, restaurant, startIndex, pageSize);
            pageBean.setList(materialPlan);
        }
        return pageBean;
    }


    /*
     * 导出原料需求查询列表
     * {"beginTime":"","endTime":"","mealType":"","restaurant":""}
     * */
    @Override
    public Object exportMaterialPlan(HttpServletRequest request, HttpServletResponse response, String paramJson) throws UnsupportedEncodingException {//文件名称
        String fileName = "原料需求计划";
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String beginTime = rowData.getString("beginTime");
        String endTime = rowData.getString("endTime");
        String mealType = rowData.getString("mealType");
        String str = rowData.getString("restaurant");
        String restaurant = operationrestaurantService.getIds(str);
        List<StMaterialplan> materialplanList = stMaterialplanMapper.exportMaterialPlan(beginTime, endTime, mealType, restaurant);
        //第一行标题
        List<String> titles = new ArrayList<String>();
        titles.add("食材名称");
        titles.add("需求量");
        titles.add("需求单位");
        titles.add("库存量");
        titles.add("库存单位");
        titles.add("修正需求量");
        titles.add("修正单位");
        //表格数据数据
        List<Map> varList = new ArrayList<Map>();
        for (StMaterialplan st : materialplanList) {
            Map vpd = new LinkedHashMap();
            vpd.put("var1", st.getIngredientsName());//菜品名称
            vpd.put("var2", st.getRequirement());//菜品编码
            vpd.put("var3", st.getDemandUnit());//菜品类别
            vpd.put("var4", st.getInventory());//参考价格(元)
            vpd.put("var5", st.getInventoryUnit());//单位
            vpd.put("var6", st.getRevisedDemand());//状态
            vpd.put("var7", st.getRevisionUnit());//备注

            varList.add(vpd);
        }
        ExportExcel ex = new ExportExcel();
        ex.export(response, fileName, titles, varList);
        int j = 1;
        return j;
    }


}
