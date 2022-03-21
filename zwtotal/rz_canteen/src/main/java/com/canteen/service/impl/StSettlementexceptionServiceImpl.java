package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.mapper.StSettlementexceptionMapper;
import com.canteen.service.StSettlementexceptionService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StSettlementexceptionServiceImpl implements StSettlementexceptionService {

    @Autowired
    private StSettlementexceptionMapper stSettlementexceptionMapper;

    @Autowired
    private StOperationrestaurantServiceImpl stOperationrestaurantService;


    /*
    *查询异常列表
    *{"pageNum":"","pageSize":"","param":{"restaurantName":"","startTime":"","endTime":"","mealType":""}}
    * */
    @Override
    public PageBean getExceptionList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        String string = param.getString("restaurantName");
        String restaurantName = stOperationrestaurantService.getIds(string);
        String startTime = param.getString("startTime");
        String endTime = param.getString("endTime");
        String mealType = param.getString("mealType");
        int totalSize = stSettlementexceptionMapper.getExceptionCnt(restaurantName,startTime,endTime,mealType);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List exceptionList = stSettlementexceptionMapper.getExceptionList(startIndex, pageSize, restaurantName, startTime, endTime, mealType);
            pageBean.setList(exceptionList);
        }
        return pageBean;
    }


    /*
     * 获取字典表定义
     * */
    @Override
    public Map dictionaries() {
        Map<String,List<JSONObject>> resultMap = new LinkedHashMap<>();

        //餐别
        List<JSONObject> mealType = new ArrayList<>();
        mealType.add(JSONObject.parseObject("{'id':'','text':'全部'}"));

        List<Map> dictionaries = stSettlementexceptionMapper.dictionaries();

        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if("mealtype".equals(map.get("paraname"))){
                mealType.add(jsonObject);
            }
        });

        resultMap.put("mealType",mealType);

        return resultMap;
    }
}
