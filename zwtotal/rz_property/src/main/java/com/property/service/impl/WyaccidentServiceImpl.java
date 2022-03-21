package com.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wyaccident;
import com.property.mapper.WyaccidentMapper;
import com.property.mapper.WydutyrecordMapper;
import com.property.service.WyaccidentService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WyaccidentServiceImpl implements WyaccidentService {

    @Autowired
    private WyaccidentMapper wyaccidentMapper;

    @Autowired
    private WydutyrecordMapper wydutyrecordMapper;

    /*
    * 添加事故记录
    * {"timestamp":"","place":"","involved":[],"loss":"","event":"","reporter":""}
    * */
    @Override
    public Object addAccident(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String timestamp = rowData.getString("timestamp");
        String place = rowData.getString("place");
        JSONArray ids = rowData.getJSONArray("involved");
        StringBuilder sb = new StringBuilder();
        String involved = null;
        if( ids != null && ids.size() != 0 ){
            for (Object o : ids) {
                sb.append(o.toString()).append(",");
            }
            involved = sb.substring(0,sb.length()-1);
        }
        String loss = rowData.getString("loss");
        String event = rowData.getString("event");
        String reporter = rowData.getString("reporter");
        Wyaccident wyaccident = new Wyaccident(0,event,loss,timestamp,place,involved,reporter,null);
        wyaccidentMapper.addAccident(wyaccident);
        return "新增成功";
    }

    /*
    * 查询事故记录
    * {"pageNum":"","pageSize":"","beginTime":"","endTime":""}
    * */
    @Override
    public Object getAccident(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String beginTime = rowData.getString("beginTime");
        String endTime = rowData.getString("endTime");
        Integer totalSize = wyaccidentMapper.getAccidentCnt(beginTime, endTime);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<Wyaccident> accident = wyaccidentMapper.getAccident(beginTime, endTime, startIndex, pageSize);
            for (Wyaccident wyaccident : accident) {
                String involveds = wyaccident.getInvolved();
                if( involveds != null && !involveds.equals("")){
                    List<Map> persons = wyaccidentMapper.getPersonById(involveds);
                    StringBuilder sb= new StringBuilder();
                    for (Map person : persons) {
                        sb.append(person.get("name")).append(",");
                    }
                    String s = sb.substring(0, sb.length() - 1);
                    wyaccident.setInvolved(s);
                }
            }
            pageBean.setList(accident);
        }
        return pageBean;
    }

    /*
    * 获取下拉框数据
    * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();
        //人员
        List person = wydutyrecordMapper.getPerson(null);
        resultMap.put("person",person);
        return resultMap;
    }
}
