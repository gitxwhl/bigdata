package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wydutyrecord;
import com.property.mapper.WydutyrecordMapper;
import com.property.service.WydutyrecordService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WydutyrecordServiceimpl implements WydutyrecordService {

    @Autowired
    private WydutyrecordMapper wydutyrecordMapper;

    /*
    * 添加值班记录
    * {"timestamp":"","frequency":"","events":"","gatekeeper":""}
    * */
    @Override
    public Object addDutyRecord(Wydutyrecord wydutyrecord) {
        wydutyrecordMapper.addDutyRecord(wydutyrecord);
        return "添加值班记录成功";
    }

    /*
    * 查询值班记录
    * {"pageNum":"","pageSize":"","beginTime":"","endTime":"","frequency":"","gatekeeper":""}
    * */
    @Override
    public Object getDutyRecord(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String beginTime = rowData.getString("beginTime");
        String endTime = rowData.getString("endTime");
        String frequency = rowData.getString("frequency");
        String gatekeeper = rowData.getString("gatekeeper");
        Integer totalSize = wydutyrecordMapper.getDutyRecordCnt(beginTime, endTime, frequency, gatekeeper);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List dutyRecord = wydutyrecordMapper.getDutyRecord(beginTime, endTime, frequency, gatekeeper, startIndex, pageSize);
            pageBean.setList(dutyRecord);
        }
        return pageBean;
    }

    /*
    * 获取下拉框数据
    * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();
        //查询门岗人员 0:门岗人员
        List person = wydutyrecordMapper.getPerson("0");
        //班次
        List frequency = new ArrayList();
        List<Map> dictionaries = wydutyrecordMapper.dictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'"+map.get("parakey")+"','text':'"+map.get("paravalue")+"'}");
            if(map.get("paraname").equals("frequency")){
                frequency.add(jsonObject);
            }
        });
        resultMap.put("person",person);
        resultMap.put("frequency",frequency);
        return resultMap;
    }
}
