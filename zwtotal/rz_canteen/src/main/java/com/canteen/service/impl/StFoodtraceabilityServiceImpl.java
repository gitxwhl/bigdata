package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.mapper.StFoodtraceabilityMapper;
import com.canteen.service.StFoodtraceabilityService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StFoodtraceabilityServiceImpl implements StFoodtraceabilityService {

    @Autowired
    private StFoodtraceabilityMapper stFoodtraceabilityMapper;

    /*
    * 查询菜品溯源列表
    * {"pageNum":"","pageSize":"","dishName":"","personName":""}
    * */
    @Override
    public Object getList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String dishName = rowData.getString("dishName");
        String personName = rowData.getString("personName");
        Integer totalSize = stFoodtraceabilityMapper.getListCnt(dishName, personName);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if ( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List list = stFoodtraceabilityMapper.getList(startIndex, pageSize, dishName, personName);
            pageBean.setList(list);
        }
        return pageBean;
    }
}
