package com.raysdata.riskdataanalyzeserver.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface RiskInfosService {

    Map<String, Object> gridRiskInfosCnt(int timeType);

    List<Map> areaRiskCountInfo(int timeType);

    Map planPublishCount(int timeType);

    Map stateGridRiskLevelCount(int timeType);

    Map workRiskLevelCount(int timeType);

    Map warnRiskCount(int timeType);

    List<Map> gridRiskInfosCommonCnt(int infoType, int timeType, String beginTime, String endTime);

    Map<String, Object> allRiskWarnInfo(String jsonStr);

    List<Map> warnCount(int timeType, String beginTime, String endTime);

    List<Map> voltageClassCount(int timeType, String beginTime, String endTime);

    List<Map> failureReasonCount(int timeType, String beginTime, String endTime);

    List<Map> riskDevCount(int timeType, String beginTime, String endTime);

    List<Map> riskKeepDayCount(int timeType, String beginTime, String endTime);

    List<Map> warnDevTypeCount(int timeType, String beginTime, String endTime);

    Map<String, Object> allRiskWarnInfosList(String jsonStr);

    //MapPageResult allRiskWarnInfosList1(String jsonStr);

    Map<String, Object> gridWarnContent(String gridwarnnoticeId);

    Map<String, List<JSONObject>> getSrpRiskSysTab();

    String deleteInfo(String jsonStr);
}
