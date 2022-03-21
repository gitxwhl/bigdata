package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.mapper.WyequipmentinspectionMapper;
import com.property.service.WyequipmentinspectionService;
import com.property.utils.PageBean;
import org.apache.commons.lang.StringUtils;
//import com.sun.deploy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WyequipmentinspectionServiceImpl implements WyequipmentinspectionService {
    @Autowired
    WyequipmentinspectionMapper stEquipmentInspectionMapper;

    /**
     * 获取设备巡检信息
     * @return
     */
    @Override
    public PageBean selectEquipmentInspection(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String company = paramDate.getString("company");
        String inspection = paramDate.getString("inspection");
        String plantime = paramDate.getString("plantime");
        String begintime = paramDate.getString("begintime");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = stEquipmentInspectionMapper.getTotalSize(company,inspection,plantime,begintime);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List equipmentInspectionList = stEquipmentInspectionMapper.selectEquipmentInspection(company,inspection,plantime,begintime,startIndex,pageSize);
            pageBean.setList(equipmentInspectionList);
        }
        return pageBean;
    }

    /**
     * 添加巡检计划信息
     * @param paramJson
     * @return
     */
    @Override
    public Object insertEquipmentInspection(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String inspection = param.getString("inspection");
        String incequipment = param.getString("incequipment");
        String plantime = param.getString("plantime");
        String planendtime = param.getString("planendtime");
        String planner = param.getString("planner");
        String company = param.getString("company");
        int ins = stEquipmentInspectionMapper.insertEquipmentInspection(inspection,incequipment,plantime,planendtime,planner,company);
        if(ins > 0){
            return "添加巡检计划成功";
        }
        return "添加巡检计划失败";
    }

    /**
     * 添加巡检记录信息
     * @param paramJson
     * @return
     */
    @Override
    public Object updateEquipmentInspection(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String inspection = param.getString("inspection");
        String incequipment = param.getString("incequipment");
        String begintime = param.getString("begintime");
        String endtime = param.getString("endtime");
        String inspectionperson = param.getString("inspectionperson");
        String results = param.getString("results");
        String remarks = param.getString("remarks");
        //拿到巡检计划添加的设备
        String strEqu = stEquipmentInspectionMapper.getIncEqu(inspection);
        //将两次选择的设备转换成数组,通过逗号分开
        String regex = ",|，";
        String[] strings = strEqu.split(regex);
        String[] strings1 = incequipment.split(regex);
        //再转换为集合进行比较，取出不同的元素
        List<String> list = Arrays.asList(strings);
        List<String> list1 = Arrays.asList(strings1);
        List<String> diff = new ArrayList<String>();
        for (String str : list) {
            if(!list1.contains(str)){
                diff.add(str);
            }
        }
        String missing = StringUtils.join(diff,"，");
        int ins = stEquipmentInspectionMapper.updateEquipmentInspection(inspection,begintime,endtime,inspectionperson,results,remarks,missing);
        if (ins > 0) {
            return "添加巡检记录成功";
        }
        return "添加巡检记录失败";
    }

    /**
     * 获取线路名称，涉及设备，巡检结果
     * @return
     */
    @Override
    public Map getInspectionAndResults(){
        Map map = new HashMap();
        map.put("inspection",stEquipmentInspectionMapper.getInspection());
        map.put("incequipment",stEquipmentInspectionMapper.getIncequipment());
        map.put("results",stEquipmentInspectionMapper.getResults());
        return map;
    }

}
