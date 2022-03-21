package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wyequipment;
import com.property.mapper.WyequipmentMapper;
import com.property.service.WyEquipmentService;
import com.property.utils.PageBean;
import com.property.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WyEquipmentServiceImpl implements WyEquipmentService {
    @Autowired
    WyequipmentMapper stEquipmentMapper;


    /**
     * 基本信息
     * @param paramJson
     * @return
     */
    @Override
    public Object wyEquipmentList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);

        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);
        String name = param.getString("name");
        String code = param.getString("code");
        String department = param.getString("department"); //部门关联字段

        List list = stEquipmentMapper.getWyEquipmentList( name, code, department);
        return list;
    }

    /**
     * 详情列表
     *
     * @param paramJson
     * @return
     */
    @Override
    public PageBean WySpacemanagementListDis(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);
        Integer id = Integer.valueOf(param.getString("id"));

        int totalSize = stEquipmentMapper.getWyEquipmentCnt1(id);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            int startIndex = pageBean.getStartIndex();
            List list1 = stEquipmentMapper.getWyEquipmentList1(startIndex, pageSize, id);
            pageBean.setList(list1);
        }
        return pageBean;
    }

    /*
    获取设备台账信息
     */
    @Override
    public PageBean selectEquipment(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String company = paramDate.getString("company");
        String name = paramDate.getString("name");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = stEquipmentMapper.getTotalSize(company, name);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            Integer startIndex = pageBean.getStartIndex();
            List equipmentList = stEquipmentMapper.selectEquipment(company, name, startIndex, pageSize);
            pageBean.setList(equipmentList);
        }
        return pageBean;
    }

    /*
       添加设备台账信息
        */
    @Override
    public void insertEquipment(Wyequipment stEquipment) {
        stEquipmentMapper.insertEquipment(stEquipment);
    }

   /*
    查询修改设备信息:存在安全风险方法隐藏
   */
//    @Override
//    public Map selectEquipmentById(String param) {
//        JSONObject paramDate = JSONObject.parseObject(param);
//        int id = paramDate.getIntValue("id");
//      return  stEquipmentMapper.selectEquipmentById(id);
//
//    }

    /*
    修改设备
     */
    @Override
    public void updateEquipment(Wyequipment stEquipment) {
        stEquipmentMapper.updateEquipment(stEquipment);
    }

    /*
    删除设备台账信息
     */
    @Override
    public void deleteEquipment(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getIntValue("id");
        stEquipmentMapper.deleteEquipment(id);
    }

    /*
    生成二维码：存在安全风险方法隐藏
     */
//    @Override
//    public String getCode(String param) {
//        JSONObject paramData = JSONObject.parseObject(param);
//        int id = paramData.getIntValue("id");
//        Map map = stEquipmentMapper.getCode(id);
//        String name = (String)map.get("name");
//        String code = (String)map.get("code");
//        String model = (String)map.get("model");
//        String parameter = (String)map.get("parameter");
//        String status = (String)map.get("status");
//        String location = (String)map.get("location");
//        String manufacturer = (String)map.get("manufacturer");
//        String production = (String)map.get("production");
//        String period = (String)map.get("period");
//        String servicelife = (String)map.get("servicelife");
//        String qr = QRCodeUtils.crateB64QRCode("设备名称："+name+"\n"+"设备编码："+code+"\n"+"规格型号："
//                +model+"\n"+"参数："+parameter+"\n"+"设备状态："+status+"\n"+"当前位置："+location+"\n"+"生产厂家："
//                +manufacturer+"\n"+"生产日期："+production+"\n"+"质保期："+period+"\n"+"使用年限："+servicelife,
//                400, 400);
//        return qr;
//
//    }


}
