package com.canteen.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.canteen.entity.StLocaleat;
import com.canteen.entity.StOperationrestaurant;
import com.canteen.mapper.StLocaleatMapper;
import com.canteen.mapper.StOperationrestaurantMapper;
import com.canteen.service.StLocaleatService;
import com.canteen.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StLocaleatServiceImpl implements StLocaleatService {

    @Autowired
    StLocaleatMapper stLocaleatMapper;

    @Autowired
    StOperationrestaurantServiceImpl stOper;

    @Autowired
    StOperationrestaurantMapper operationrestaurantMapper;

    /*
    * 添加申请
    * {"applicant":"","beginTime":"","endTime":"","place":"","restaurant":"","remarks":"","approvedBy":"","state":""}
    * */
    @Override
    public String addApplication(StLocaleat stLocaleat) {
        /*JSONObject rowData = JSONObject.parseObject(paramJson);
        String applicant = rowData.getString("applicant");
        String beginTime = rowData.getString("beginTime");
        String endTime = rowData.getString("endTime");
        String place = rowData.getString("place");
        String restaurant = rowData.getString("restaurant");
        String remarks = rowData.getString("remarks");
        String approved = rowData.getString("approved");
        int index = Integer.parseInt(rowData.getString("index"));
        if( index == 0 ){   //保存
            StLocaleat stLocaleat = new StLocaleat(0,applicant,beginTime,endTime,place,restaurant,remarks,approved,"0");
            stLocaleatMapper.addApplication(stLocaleat);
            return "保存成功";
        }else { //申请
            StLocaleat stLocaleat = new StLocaleat(0,applicant,beginTime,endTime,place,restaurant,remarks,approved,"1");
            stLocaleatMapper.addApplication(stLocaleat);
            return "申请成功";
        }*/
        stLocaleat.setDelFlag("0");
        stLocaleatMapper.addApplication(stLocaleat);
        String state = stLocaleat.getState();
        if( state.equals("0")){ //保存
            return "保存成功";
        }else { //申请
            return "申请成功";
        }
    }

    /*
    * 申请人查询保存的申请
    * {"pageNum":"","pageSize":"","applicant":"","restaurant":"","msg":""}
    * */
    @Override
    public Object getSave(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String applicant = rowData.getString("applicant");
        String str = rowData.getString("restaurant");
        String msg = rowData.getString("msg");
        String restaurant = stOper.getIds(str);
        Integer totalSize = stLocaleatMapper.getSaveCnt(applicant, msg, restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List save = stLocaleatMapper.getSave(applicant, msg, restaurant, startIndex, pageSize);
            pageBean.setList(save);
        }
        return pageBean;
    }

    /*修改保存的申请
    *{"id":"","applicant":"","beginTime":"","endTime":"","place":"","restaurant":"","remarks":"","approvedBy":"","state":""}
    * */
    @Override
    public Object updateSave(StLocaleat stLocaleat) {
        stLocaleatMapper.updateSave(stLocaleat);
        String state = stLocaleat.getState();
        if( state.equals("0")){ //保存
            return "保存成功";
        }else { //申请
            return "申请成功";
        }
    }

    /*
    * 删除保存的申请
    * {"id":""}
    * */
    @Override
    public Object deleteSave(StLocaleat stLocaleat) {
        stLocaleat.setDelFlag("1");
        stLocaleatMapper.deleteSave(stLocaleat);
        return "删除成功";
    }


    /*
     * 审批人查询已提交的申请
     * {"pageNum":"","pageSize":"","approvedBy":"","restaurant":"","msg":""}
     * */
    @Override
    public Object getSubmit(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String approvedBy = rowData.getString("approvedBy");
        String str = rowData.getString("restaurant");
        String restaurant = stOper.getIds(str);
        String msg = rowData.getString("msg");
        Integer totalSize = stLocaleatMapper.getSubmitCnt(approvedBy, msg, restaurant);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if( totalSize == 0 ){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List submit = stLocaleatMapper.getSubmit(approvedBy, msg, restaurant, startIndex, pageSize);
            pageBean.setList(submit);
        }
        return pageBean;
    }

    /*
    * 审批人进行审批
    * {"id":"","explainInfo":"","state":""}
    * */
    @Override
    public Object approval(StLocaleat stLocaleat) {
        stLocaleatMapper.approval(stLocaleat);
        String state = stLocaleat.getState();
        if( state.equals("2")){ //退回
            return "已退回";
        }else { //同意
            return "已同意";
        }
    }

    //查询关联属性
    @Override
    public Object getRelevance() {
        Map map = new LinkedHashMap<>();
        List approvalBy = stLocaleatMapper.getApprovalBy();
        List<StOperationrestaurant> stOperationList = operationrestaurantMapper.getRestaurant();
        map.put("approvalBy",approvalBy);
        map.put("stOperationList",stOperationList);
        return map;
    }

    /*
    * 查询待办
    * {"pageNum":"","pageSize":"","applicant":"","restaurant":"","msg":"","state":""}
    * */
    @Override
    public Object getBacklog(String paramJson) {
        Map resultMap = new LinkedHashMap();
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        String applicant = rowData.getString("applicant");
        String str = rowData.getString("restaurant");
        String msg = rowData.getString("msg");
        String state = rowData.getString("state");
        String restaurant = stOper.getIds(str);
        Integer totalSize = stLocaleatMapper.getBacklogCnt(applicant, msg, restaurant,state);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if(totalSize == 0){
            pageBean.setList(new ArrayList());
        }else {
            Integer startIndex = pageBean.getStartIndex();
            List<StLocaleat> backlog = stLocaleatMapper.getBacklog(applicant, msg, restaurant, state,startIndex, pageSize);
            for (StLocaleat stLocaleat : backlog) {
                stLocaleat.setEndTime(stLocaleat.getBeginTime() + " - " + stLocaleat.getEndTime());
            }
            pageBean.setList(backlog);
        }
        //审批数量
        Integer sp = stLocaleatMapper.getBacklogCnt(applicant, msg, restaurant,"3");
        //驳回数量
        Integer bh = stLocaleatMapper.getBacklogCnt(applicant, msg, restaurant,"2");
        //待提交数量
        Integer dtj = stLocaleatMapper.getBacklogCnt(applicant, msg, restaurant,"0");
        //待审批数量
        Integer dsp = stLocaleatMapper.getBacklogCnt(applicant, msg, restaurant,"1");
        //待办数量
        Integer db = dsp + dtj;
        //全部数量
        Integer all = stLocaleatMapper.getBacklogCnt(applicant, msg, restaurant,null);
        resultMap.put("page",pageBean);
        resultMap.put("sp",sp);
        resultMap.put("bh",bh);
        resultMap.put("db",db);
        resultMap.put("dtj",dtj);
        resultMap.put("dsp",dsp);
        resultMap.put("all",all);
        return resultMap;
    }


}
