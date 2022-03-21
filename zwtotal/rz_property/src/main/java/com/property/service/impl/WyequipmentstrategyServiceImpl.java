package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.entity.Wyequipmentstrategy;
import com.property.mapper.WyequipmentstrategyMapper;
import com.property.service.WyequipmentstrategyService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WyequipmentstrategyServiceImpl implements WyequipmentstrategyService {

    @Autowired
    WyequipmentstrategyMapper wyequipmentstrategyMapper;

    /**
     * 查询维保策略
     * @return
     */
    @Override
    public PageBean getEquipmentStrategy(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String policyname = paramDate.getString("policyname");
        String policy = paramDate.getString("policy");
        String planningtime = paramDate.getString("planningtime");
        String company = paramDate.getString("company");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = wyequipmentstrategyMapper.getEquipmentStrategyCnt(policyname,policy,planningtime,company);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List equipmentStrategyList = wyequipmentstrategyMapper.getEquipmentStrategy(policyname,policy,planningtime,company,startIndex,pageSize);
            pageBean.setList(equipmentStrategyList);
        }
        return pageBean;
    }

    /**
     * 添加维保策略
     * policyname,policy,planningtime,content,resources,company,delflag,policyid
     * @return
     */
    @Override
    public void insertEquipmentStrategy(String param){
        JSONObject paramDate = JSONObject.parseObject(param);
        String policyname = paramDate.getString("policyname");
        String policy = paramDate.getString("policy");
        String planningtime = paramDate.getString("planningtime");
        String planendtime = paramDate.getString("planendtime");
        String content = paramDate.getString("content");
        String resources = paramDate.getString("resources");
        String company = paramDate.getString("company");
        String delflag = "0";
        //随机生成编码
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String seconds = new SimpleDateFormat("HHmmss").format(new Date());
        String policyid = date + seconds;
        //添加维保策略
        wyequipmentstrategyMapper.insertEquipmentStrategy(policyname,policy,planningtime,planendtime,content,resources,company,delflag,policyid);
        //生成维保工单
        String workno = policyid;
        wyequipmentstrategyMapper.insertMainOrder(workno);
    }
    /**
     * 查询修改维保策略
     */
    @Override
    public Map getEquipmentStrategyById(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getInteger("id");
        return wyequipmentstrategyMapper.getEquipmentStrategyById(id);
    }

    /**
     * 修改维保策略
     */
    @Override
    public void updateEquipmentStrategy(Wyequipmentstrategy wyequipmentstrategy) {
       wyequipmentstrategyMapper.updateEquipmentStrategy(wyequipmentstrategy);
    }

    /**
     * 删除维保策略
     * @param param
     * @return
     */
    @Override
    public void deleteEquipmentStrategy(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int id = paramDate.getInteger("id");
        wyequipmentstrategyMapper.deleteEquipmentStrategy(id);
    }

    /**
     * 获取策略分类
     * @return
     */
    @Override
    public Map getPolicy() {
        Map map = new HashMap();
        map.put("policy",wyequipmentstrategyMapper.getPolicy());
        return map;
    }
}
