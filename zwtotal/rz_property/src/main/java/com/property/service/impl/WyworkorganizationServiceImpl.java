package com.property.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wyregister;
import com.property.entity.Wyworkorganization;
import com.property.mapper.WyworkorganizationMapper;
import com.property.service.WyworkorganizationService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WyworkorganizationServiceImpl implements WyworkorganizationService {
    private static final Integer thisQuarter = 1;   //本季度
    private static final Integer lastQuarter = 2;   //去年本季度

    @Autowired
    private WyworkorganizationMapper wyworkorganizationMapper;

    /*
     * 工作安排列表
     * {"pageNum":"","pageSize":"","name":""}
     * */
    @Override
    public Object getJobPlacement(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String name = rowData.getString("name");
        Integer totalSize = wyworkorganizationMapper.getJobPlacementCnt(name);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List jobPlacement = wyworkorganizationMapper.getJobPlacement(name, startIndex, pageSize);
            pageBean.setList(jobPlacement);
        }
        return pageBean;
    }

    /*
     * 新增工作安排
     * {"name":"","plantime":"","msg":[{"registrationtype":"","content":""}]}
     * */
    @Transactional
    @Override
    public Object addJobPlacement(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String name = rowData.getString("name");
        String plantime = rowData.getString("plantime");
        List msg = rowData.getJSONArray("msg");
        for (Object o : msg) {
            JSONObject jsonObject = (JSONObject) o;
            String registrationtype = jsonObject.getString("registrationtype");
            String content = jsonObject.getString("content");
            Wyworkorganization wyworkorganization = new Wyworkorganization(1, name, plantime, content, null, registrationtype, "0");
            wyworkorganizationMapper.addJobPlacement(wyworkorganization);
        }
        return "新增成功";
    }

    /*
     * 修改工作安排
     * {"id":"","name":"","plantime":"","registrationtype":"","content":""}
     * */
    @Override
    public Object updateJobPlacement(Wyworkorganization wyworkorganization) {
        wyworkorganizationMapper.updateJobPlacement(wyworkorganization);
        return "修改成功";
    }

    /*
     * 删除工作安排
     * {"id":""}
     * */
    @Override
    public Object deleteJobPlacement(Wyworkorganization wyworkorganization) {
        wyworkorganizationMapper.deleteJobPlacement(wyworkorganization);
        return "删除成功";
    }

    /*
     * 工作提醒
     * */
    @Override
    public Object reminderWork() {
        List list = wyworkorganizationMapper.reminderWork();
        return list;
    }

    /*
     * 登记植被种植情况
     * {"plantingTime":"","vegetationType":"","isReplanting":"","person":"",
     * "plantingArea":"","number":"","workingTime":"","msg":[{"vegetationName":"","plantingNum":""}]}
     * */
    @Transactional
    @Override
    public Object addRegisterZz(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String plantingTime = rowData.getString("plantingTime");
        String vegetationType = rowData.getString("vegetationType");
        String isReplanting = rowData.getString("isReplanting");
        String person = rowData.getString("person");
        String plantingArea = rowData.getString("plantingArea");
        String number = rowData.getString("number");
        String workingTime = rowData.getString("workingTime");
        List msg = rowData.getJSONArray("msg");
        for (Object o : msg) {
            JSONObject jsonObject = (JSONObject) o;
            String vegetationName = jsonObject.getString("vegetationName");
            String plantingNum = jsonObject.getString("plantingNum");
            Wyregister wyregister = new Wyregister(1, plantingTime, vegetationType, isReplanting, plantingArea, number,
                    workingTime, vegetationName, plantingNum, person,"2");
            wyworkorganizationMapper.addRegister(wyregister);
        }
        return "添加成功";
    }

    /*
     * 登记植被修剪情况
     * {"plantingTime":"","vegetationType":"","isReplanting":"","person":"",
     * "msg":[{"plantingArea":"","number":"","workingTime":""}]}
     * */
    @Transactional
    @Override
    public Object addRegisterXj(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String plantingTime = rowData.getString("plantingTime");
        String vegetationType = rowData.getString("vegetationType");
        String isReplanting = rowData.getString("isReplanting");
        String person = rowData.getString("person");
        List msg = rowData.getJSONArray("msg");
        for (Object o : msg) {
            JSONObject jsonObject = (JSONObject) o;
            String plantingArea = jsonObject.getString("plantingArea");
            String number = jsonObject.getString("number");
            String workingTime = jsonObject.getString("workingTime");
            Wyregister wyregister = new Wyregister(1, plantingTime, vegetationType, isReplanting, plantingArea, number,
                    workingTime, null, null, person,"1");
            wyworkorganizationMapper.addRegister(wyregister);
        }
        return "添加成功";
    }

    /*
     * 查询空间情况
     * {"id":""}
     * */
    @Override
    public Object getSpaceInfo(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        String id = rowData.getString("id");
        Map map = new LinkedHashMap();
        //本季度空间种植情况
        List spaceInfo = wyworkorganizationMapper.getSpaceInfo("2", thisQuarter, id);
        //去年同期空间种植情况
        List spaceInfo1 = wyworkorganizationMapper.getSpaceInfo("2", lastQuarter, id);
        //本季度空间修剪情况
        List spaceInfo2 = wyworkorganizationMapper.getSpaceInfo("1", thisQuarter, id);
        //去年同期空间修剪情况
        List spaceInfo3 = wyworkorganizationMapper.getSpaceInfo("1", lastQuarter, id);
        map.put("thisQuarterZz", dataProcessing(spaceInfo));
        map.put("lastQuarterZz", dataProcessing(spaceInfo1));
        map.put("thisQuarterXj", dataProcessing(spaceInfo2));
        map.put("lastQuarterXj", dataProcessing(spaceInfo3));
        return map;
    }

    /*
     * 获取下拉框数据
     * */
    @Override
    public Object dropDownBox() {
        Map resultMap = new LinkedHashMap();

        //植被种类
        List vegetationType = new ArrayList();

        //工作类型
        List jobType = new ArrayList();

        List<Map> dictionaries = wyworkorganizationMapper.getDictionaries();
        dictionaries.stream().forEach(map -> {
            JSONObject jsonObject = JSONObject.parseObject("{'id':'" + map.get("parakey") + "','text':'" + map.get("paravalue") + "'}");
            if ("vegetation_type".equals(map.get("paraname"))) {
                vegetationType.add(jsonObject);
            }
            if ("job_type".equals(map.get("paraname"))) {
                jobType.add(jsonObject);
            }
        });

        //可关联空间区域
        List space = wyworkorganizationMapper.getSpace();
        resultMap.put("jobType", jobType);
        resultMap.put("vegetationType", vegetationType);
        resultMap.put("space", space);
        return resultMap;
    }

    public List dataProcessing(List<Map> list) {
        for (Map map : list) {
            Set set = map.keySet();
            Object peopleDay = map.get("peopleDay");
            if (peopleDay != null && !"".equals(peopleDay)) {
                peopleDay = ((Double) peopleDay).intValue();
            } else {
                peopleDay = 0;
            }
            map.put("peopleDay", peopleDay);

            if (set.contains("plantingNum")) {
                Object plantingNum = map.get("plantingNum");
                if (plantingNum != null && !"".equals(plantingNum)) {
                    plantingNum = ((Double) plantingNum).intValue();
                } else {
                    plantingNum = 0;
                }
                map.put("plantingNum", plantingNum);
            }
        }
        return list;
    }

}
