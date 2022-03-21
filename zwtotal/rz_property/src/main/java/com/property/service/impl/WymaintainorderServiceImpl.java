package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.mapper.WymaintainorderMapper;
import com.property.service.WymaintainorderService;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WymaintainorderServiceImpl implements WymaintainorderService {

    @Autowired
    WymaintainorderMapper wymaintainorderMapper;

    //获取工单信息
    @Override
    public PageBean getMaintainorder(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String company = paramDate.getString("company");
        String policyname = paramDate.getString("policyname");
        String policy = paramDate.getString("policy");
        String state = paramDate.getString("state");
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        //生成超期订单
        wymaintainorderMapper.updateBeyondOrder();
        //获取工单信息总数
        int totalSize = wymaintainorderMapper.getMaintainorderCnt(company,policyname,policy,state);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List maintainorderList = wymaintainorderMapper.getMaintainorder(company,policyname,policy,state,startIndex,pageSize);
            pageBean.setList(maintainorderList);
        }

        return pageBean;
    }

    //获取工单状态
    @Override
    public Map getWorkState() {
        Map map = new HashMap();
        map.put("policyAndWorkState",wymaintainorderMapper.getWorkState());
        return map;
    }

    //获取工单详情信息
    @Override
    public Object getMaintainorderDetails(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        Integer id = paramDate.getInteger("id");
        List list = wymaintainorderMapper.getMaintainorderDetails(id);
        return list;
    }

    //修改派单信息
    @Override
    public void updateSingle(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String planningtime = paramDate.getString("planningtime");
        String receiving = paramDate.getString("receiving");
        String personnel = paramDate.getString("personnel");
        String operationrecord = paramDate.getString("operationrecord");
        String defectidentification = paramDate.getString("defectidentification");
        String completiontime = paramDate.getString("completiontime");
        Integer id = paramDate.getInteger("id");
        String state = null;
        //获取工单状态
        String strState = wymaintainorderMapper.getSingleState(id);
        //判断未派单和已派单并进行相应的修改
        if(strState.equals("1")){
            state = "2";
            wymaintainorderMapper.updateSingleOne(planningtime,receiving,personnel,state,id);
        }else if(strState.equals("2")){
            state = "3";
            wymaintainorderMapper.updateSingleTwo(operationrecord,defectidentification,completiontime,state,id);
        }
    }

    //查询超期订单
    @Override
    public PageBean getBeyondOrder(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        int pageNum = Integer.parseInt(paramDate.getString("pageNum"));
        int pageSize = Integer.parseInt(paramDate.getString("pageSize"));
        int totalSize = wymaintainorderMapper.getBeyondOrderCnt();
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List beyondOrderList = wymaintainorderMapper.getBeyondOrder(startIndex,pageSize);
            pageBean.setList(beyondOrderList);
        }
        return pageBean;
    }

    //重新生成超期订单告警
    @Override
    public void reOrderAlarm(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String policyname = paramDate.getString("policyname");
        String policy = paramDate.getString("policy");
        String planningtime = paramDate.getString("planningtime");
        String planendtime = paramDate.getString("planendtime");
        String content = paramDate.getString("content");
        String resources = paramDate.getString("resources");
        String armstatus = "1";
        Integer id = paramDate.getInteger("id");
        wymaintainorderMapper.reOrderAlarm(policyname,policy,planningtime,planendtime,content,resources,armstatus,id);
    }

    //取消超期订单告警
    @Override
    public void updateOrderAlarm(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        Integer id = paramDate.getInteger("id");
        wymaintainorderMapper.updateOrderAlarm(id);
    }

}
