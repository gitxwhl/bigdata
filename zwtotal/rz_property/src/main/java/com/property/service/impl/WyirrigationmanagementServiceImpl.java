package com.property.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.property.entity.SysParameter;
import com.property.entity.Wyirrigationmanagement;
import com.property.mapper.WyirrigationmanagementMapper;
import com.property.service.WyirrigationmanagementService;
import com.property.utils.MySFTP;
import com.property.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WyirrigationmanagementServiceImpl implements WyirrigationmanagementService {


    @Autowired
    private WyirrigationmanagementMapper wyirrigationmanagementMapper;

    /**
     * 新增计划
     *
     * @param paramJson
     * @return
     */
    @Override
    public Object getNewlyIncreasedPlan(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String programname = param.getString("programname");
        String spacename = param.getString("spacename");
        String vegetationname = param.getString("vegetationname");
        String irrigationtime = param.getString("irrigationtime");
        String irrigationmethod = param.getString("irrigationmethod");
        int i = wyirrigationmanagementMapper.getNewlyIncreasedPlan(programname, spacename, vegetationname, irrigationtime, irrigationmethod);
        if (i > 0) {
            return "新增成功";
        }
        return "新增失败";
    }

    /**
     * 修改计划
     *
     * @param paramJson
     * @return
     */
    @Override
    public Object getamendPlan(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        int id = Integer.valueOf(param.getString("id"));
        String programname = param.getString("programname");
        String spacename = param.getString("spacename");
        String vegetationname = param.getString("vegetationname");
        String irrigationtime = param.getString("irrigationtime");
        String irrigationmethod = param.getString("irrigationmethod");
        int i = wyirrigationmanagementMapper.getamendPlan(id, programname, spacename, vegetationname, irrigationtime, irrigationmethod);
        if (i > 0) {
            return "修改成功";
        }
        return "修改失败";
    }


    /**
     * 返回灌溉方式
     *
     * @return
     */
    @Override
    public List<SysParameter> getIrrigationMethods() {
        return wyirrigationmanagementMapper.getIrrigationMethods();
    }

    /**
     * 计划列表
     *
     * @param paramJson
     * @return
     */
    @Override
    public PageBean WyirrigationmanagementList(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int pageNum = Integer.parseInt(rowData.getString("pageNum"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        JSONObject param = rowData.getJSONObject("param");
        System.out.println("param:" + param);

        String programname = param.getString("programname");

        int totalSize = wyirrigationmanagementMapper.getWyirrigationmanagementCnt(programname);
        PageBean pageBean = new PageBean();
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalRecord(totalSize);
        if (totalSize == 0) {
            pageBean.setList(new ArrayList());
        } else {
            int startIndex = pageBean.getStartIndex();
            List list = wyirrigationmanagementMapper.getWyirrigationmanagementList(startIndex, pageSize, programname);
            pageBean.setList(list);
        }
        return pageBean;
    }

    /**
     * 计划详情接口
     *
     * @return paramJson
     */
    @Override
    public Wyirrigationmanagement getPlanDetails(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.valueOf(rowData.getString("id"));
        return wyirrigationmanagementMapper.getPlanDetails(id);
    }

    /**
     * //计划评价 评估打分接口
     *
     * @param paramJson
     * @return
     */
    @Override
    public Object getAssessRedact(String paramJson) {
        JSONObject rowData = JSONObject.parseObject(paramJson);
        int id = Integer.valueOf(rowData.getString("id"));
        String isplan = rowData.getString("isplan");//是否按计划执行(1:按时执行2:未按时执行)
        String rategradestar = rowData.getString("rategradestar");//评级星数个数(12345)
        String details = rowData.getString("details");
        String dataFile = rowData.getString("enclosure");//附件名称
        String enclosure = "";
        if(!"".equals(dataFile)){
            enclosure = MySFTP.uploadFile(dataFile);
        }
        System.out.println("enclosure:    "+ enclosure);
        Wyirrigationmanagement wyirrigationmanagement = new Wyirrigationmanagement();
        wyirrigationmanagement.setId(id);
        wyirrigationmanagement.setIsplan(isplan);
        wyirrigationmanagement.setRategradestar(rategradestar);
        wyirrigationmanagement.setDetails(details);
        wyirrigationmanagement.setEnclosure(enclosure);

        if ("1".equals(isplan)) {
            if ("1".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("20");
            }
            if ("2".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("40");
            }
            if ("3".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("60");
            }
            if ("4".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("80");
            }
            if ("5".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("100");
            }
        }
        if ("2".equals(isplan)) {
            if ("1".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("10");
            }
            if ("2".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("30");
            }
            if ("3".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("50");
            }
            if ("4".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("70");
            }
            if ("5".equals(rategradestar)) {
                wyirrigationmanagement.setEvaluation("90");
            }
        }
        System.out.println("wyirrigationmanagement:" + wyirrigationmanagement);
        int i = wyirrigationmanagementMapper.getAssessRedact(wyirrigationmanagement);
        if (i > 0) {
            return "评估打分成功";
        }
        return "评估打分失败";
    }


    /**
     * 计划及检查情况图
     *
     * @param paramJson
     * @return
     */

    @Override
    public Map<String, Object> getIrrigateExamine(String paramJson) {
        Map map = new HashMap<>();
        JSONObject rowData = JSONObject.parseObject(paramJson);
        JSONObject param = rowData.getJSONObject("param");
        String startTime = param.getString("startTime");//开始时间
        String finishTime = param.getString("finishTime");//结束时间

        int getexecute = wyirrigationmanagementMapper.getexecute(startTime, finishTime);
        int getNotexecute = wyirrigationmanagementMapper.getNotexecute(startTime, finishTime);
        int OneStar = wyirrigationmanagementMapper.getOneStar(startTime, finishTime);
        int TwoStar = wyirrigationmanagementMapper.getTwoStar(startTime, finishTime);
        int ThreeStar = wyirrigationmanagementMapper.getThreeStar(startTime, finishTime);
        int FourStar = wyirrigationmanagementMapper.getFourStar(startTime, finishTime);
        int FiveStar = wyirrigationmanagementMapper.getFiveStar(startTime, finishTime);

        map.put("getexecute", getexecute);
        map.put("getNotexecute", getNotexecute);
        map.put("OneStar", OneStar);
        map.put("TwoStar", TwoStar);
        map.put("ThreeStar", ThreeStar);
        map.put("FourStar", FourStar);
        map.put("FiveStar", FiveStar);
        return map;
    }

    /**
     * 上传附件
     *
     * @param param
     * @return
     */
    @Override
    public Object getuploading(String param) {
        JSONObject paramDate = JSONObject.parseObject(param);
        String dataFile =  paramDate.getString("enclosure");
        String enclosure = "";
        if(!"".equals(dataFile)){
            enclosure = MySFTP.uploadFile(dataFile);
        }
        System.out.println("enclosure:    "+enclosure);
        return enclosure;
    }


}
