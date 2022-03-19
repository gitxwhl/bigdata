package com.raysdata.riskmanagementserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskSysTabVo;
import com.raysdata.riskmanagementserver.dao.RiskEventWarnDao;
import com.raysdata.riskmanagementserver.service.RiskEventWarnServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Service
public class RiskEventWarnServerImpl implements RiskEventWarnServer {

    @Autowired
    private RiskEventWarnDao riskEventWarnDao;


    /**
     * 地图各单位网络风险数量统计
     *  {"warningLevel":"", "systremApplication":"", "netoprationStatus":"","startTime":"","endTime":""}
     * */
    @Override
    public List<Map<String, Object>> getSrpriskeventwarnareacnt(String paramJson) {
        String warningLevel = JSONObject.parseObject(paramJson).getString("warningLevel");
        String systremApplication = JSONObject.parseObject(paramJson).getString("systremApplication");
        String netoprationStatus = JSONObject.parseObject(paramJson).getString("netoprationStatus");
        String startTime = JSONObject.parseObject(paramJson).getString("startTime");
        String endTime = JSONObject.parseObject(paramJson).getString("endTime");
        SrpRiskSysTabVo SrpRiskSysTabVo =new SrpRiskSysTabVo();
        SrpRiskSysTabVo.setStartTime(startTime);
        SrpRiskSysTabVo.setEndTime(endTime);
        SrpRiskSysTabVo.setWarningLevel(warningLevel);
        SrpRiskSysTabVo.setSystremApplication(systremApplication);
        SrpRiskSysTabVo.setNetoprationStatus(netoprationStatus);
        return riskEventWarnDao.getSrpriskeventwarnareacnt(SrpRiskSysTabVo);
    }


    /*
     * 网络预警各维度数量统计
     * */

    @Override
    public Map<String, Object> getSrpriskeventtypecnt() {
        return riskEventWarnDao.getSrpriskeventtypecnt();
    }


    /*
     * 攻击威胁类型占比情况
     * */

    @Override
    public Map<String, Object> getSrpriskeventwarnattackthreatcnt() {
        return riskEventWarnDao.getSrpriskeventwarnattackthreatcnt();
    }


    /**
     * 违规行为类型占比情况
     * */
    @Override
    public Map<String, Object> getSrpriskeventwarnviolationcnt() {
        return riskEventWarnDao.getSrpriskeventwarnviolationcnt();
    }

    /**
     * 各单位网络风险数量统计
     * */
    @Override
    public List<Map<String, Object>> getSrpriskeventwarncnt() {
        return riskEventWarnDao.getSrpriskeventwarncnt();
    }


    @Override
    public List<Map<String, Object>> getProvinceList() {
        return riskEventWarnDao.getProvinceList();
    }
}
