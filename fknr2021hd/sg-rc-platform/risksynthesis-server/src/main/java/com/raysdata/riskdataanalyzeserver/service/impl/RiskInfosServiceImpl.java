package com.raysdata.riskdataanalyzeserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import com.raysdata.riskdataanalyzeserver.service.RiskInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskInfosServiceImpl implements RiskInfosService {

    private static final String WARNBEGINTIME = "WARNBEGINTIME";
    private static final String WARNENDTIME = "WARNENDTIME";


    @Autowired
    private JdbcDataAccess jDataAccess;

    //全网电网风险情况数据汇总
    @Override
    public Map<String, Object> gridRiskInfosCnt(int timeType) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("planPublishCount", planPublishCount(timeType));
        resultMap.put("stateGridRiskLevelCount", stateGridRiskLevelCount(timeType));
        resultMap.put("workRiskLevelCount", workRiskLevelCount(timeType));
        resultMap.put("warnRiskCount", warnRiskCount(timeType));
        return resultMap;
    }

    //各单位风险信息统计情况
    @Override
    public List<Map> areaRiskCountInfo(int timeType) {
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, null, null);
        List<Map> resultList = this.jDataAccess.queryWithParamById("AreaRiskCountInfo", paramMap);
        return resultList;
    }

    //各单位电网风险分布情况
    @Override
    public List<Map> gridRiskInfosCommonCnt(int infoType, int timeType, String beginTime, String endTime) {
        List<Map> resultMap = null;
        switch (infoType) {
            case 0:
                resultMap = warnCount(timeType, beginTime, endTime);
                break;
            case 1:
                resultMap = voltageClassCount(timeType, beginTime, endTime);
                break;
            case 2:
                resultMap = failureReasonCount(timeType, beginTime, endTime);
                break;
            case 3:
                resultMap = riskDevCount(timeType, beginTime, endTime);
                break;
            case 4:
                resultMap = riskKeepDayCount(timeType, beginTime, endTime);
                break;
            case 5:
                resultMap = warnDevTypeCount(timeType, beginTime, endTime);
                break;
            default:
                resultMap = warnCount(timeType, beginTime, endTime);
                break;
        }
        return resultMap;
    }

    //全网今日电网风险信息列表,分页查询
    @Override
    public Map<String, Object> allRiskWarnInfo(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
        List<Map> resultList = this.jDataAccess.queryById("AllRiskWarnInfo", new Object[]{}, pageSize, pageNo);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        Map<String, Object> paramMap = createCommonFilter(0, WARNBEGINTIME, null, null);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("GridWarnNoticeCount", paramMap);
        resultMap.put("listSize", cntResult.get(0).get("gridwarnnotice_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("gridwarnnotice_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;
    }


    //电网风险列表信息
    @Override
    public Map<String, Object> allRiskWarnInfosList(String jsonStr) {

        StringBuilder sqlBuilder = new StringBuilder("");
        sqlBuilder.append("SELECT\n" +
                " a.GRIDWARNNOTICE_ID,\n" +
                " a.TITLE,\n" +
                " a.WARNNUM,\n" +
                " a.WARNBEGINTIME,\n" +
                " a.WARNENDTIME,\n" +
                " a.PUBLISH_ORG,\n" +
                " a.PUBLISH_STAFF,\n" +
                " c.column_type_name WARNINGLEVEL,\n" +
                " d.column_type_name WARNSTATUS,\n" +
                " COUNT( CASE WHEN b.WORK_PLAN_CODE_DAY is NOT NULL THEN 1 ELSE NULL END ) AS WORK_PLAN_CODE_DAY_CNT,\n" +
                " MAX(b.WORK_PLAN_ID) WORK_PLAN_ID,\n" +
                " a.datareport_org,\n" +
                " a.datareport_org_id\n" +
                " FROM\n" +
                " SRP_RISK_GRIDWARNNOTICE a\n" +
                " LEFT JOIN SRP_RISK_GRIDWARNTOWORKPLAN e ON a.GRIDWARNNOTICE_ID = e.GRIDWARNNOTICE_ID\n" +
                " LEFT JOIN srp_work_workplanday b ON e.WORK_PLAN_ID = b.WORK_PLAN_ID\n" +
                " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'WARNINGLEVEL') c ON a.WARNINGLEVEL = c.column_type_code\n" +
                " LEFT JOIN (select * from srp_risk_sys_tab where column_code = 'WARNSTATUS') d ON a.WARNSTATUS = d.column_type_code\t\n" +
                " WHERE ");
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
        jsonStr = rowData.getString("dataStr");
        String paramStr = createWarnInfoFilter(jsonStr);
        sqlBuilder.append(paramStr);
        sqlBuilder.append(" and ( a.DELETELOGO is null or a.DELETELOGO != '1' ) ");
        sqlBuilder.append("GROUP BY\n" +
                " a.GRIDWARNNOTICE_ID,\n" +
                " a.TITLE,\n" +
                " a.WARNNUM,          \n" +
                " a.WARNBEGINTIME,\n" +
                " a.WARNENDTIME,\n" +
                " a.PUBLISH_ORG,\n" +
                " a.PUBLISH_STAFF,\n" +
                " c.column_type_name,\n" +
                " d.column_type_name\n" +
                " ORDER BY\n" +
                " a.WARNINGLEVEL ASC");
        String sql = sqlBuilder.toString();
        List<Map> resultList = this.jDataAccess.query(sql, new Object[]{}, pageSize, pageNo);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        String filter = createWarnInfoFilter(jsonStr);
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", filter);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("GridWarnNoticeCount", paramMap);
        resultMap.put("listSize", cntResult.get(0).get("gridwarnnotice_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("gridwarnnotice_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;
    }


    //电网风险详情
    @Override
    public Map<String, Object> gridWarnContent(String gridwarnnoticeId) {
        Map<String, Object> resultMap = new HashMap<>();

        String filter = " GRIDWARNNOTICE_ID = '" + gridwarnnoticeId + "' ";
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", filter);
        List<Map> srpRiskGridwarnnotice = this.jDataAccess.queryWithParamById("SrpRiskGridwarnnotice", paramMap);
        List<Map> srpRiskGridwarnfeedback = this.jDataAccess.queryWithParamById("SrpRiskGridwarnfeedback", paramMap);
        List<Map> srpRiskGridwarnreport = this.jDataAccess.queryWithParamById("SrpRiskGridwarnreport", paramMap);
        List<Map> srpRiskGridwarninform = this.jDataAccess.queryWithParamById("SrpRiskGridwarninform", paramMap);
        List<Map> srpRiskGridwarnrelinfo = this.jDataAccess.queryWithParamById("SrpRiskGridwarnrelinfo", paramMap);

        resultMap.put("srpRiskGridwarnnotice", srpRiskGridwarnnotice);
        resultMap.put("srpRiskGridwarnfeedback", srpRiskGridwarnfeedback);
        resultMap.put("srpRiskGridwarnreport", srpRiskGridwarnreport);
        resultMap.put("srpRiskGridwarninform", srpRiskGridwarninform);
        resultMap.put("srpRiskGridwarnrelinfo", srpRiskGridwarnrelinfo);
        return resultMap;
    }


//**************************************************************************************************************************************************************


    //作业计划发布数统计
    @Override
    public Map planPublishCount(int timeType) {//plan_cnt:2

        //数据查询
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, null, null);
        List<Map> resultList = this.jDataAccess.queryWithParamById("PlanPublishCount", paramMap);
        return resultList.get(0);
    }

    //电网风险数统计
    @Override
    public Map stateGridRiskLevelCount(int timeType) {
        //数据查询
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, null, null);
        List<Map> resultList = this.jDataAccess.queryWithParamById("StateGridRiskLevelCount", paramMap);
        Map resultMap = new HashMap();
        int level = 0, level6 = 0;
        for (int i = 0; i < resultList.size(); i++) {
            if ("06".equals(resultList.get(i).get("gridrisk_level")) || "07".equals(resultList.get(i).get("gridrisk_level")) || "08".equals(resultList.get(i).get("gridrisk_level")) || "09".equals(resultList.get(i).get("gridrisk_level"))) {
                if ("06".equals(resultList.get(i).get("gridrisk_level"))) {
                    level6 = Integer.parseInt(resultList.get(i).get("plan_cnt").toString());
                } else {
                    level = level + Integer.parseInt(resultList.get(i).get("plan_cnt").toString());
                }
            } else {
                resultMap.put("level" + resultList.get(i).get("gridrisk_level"), resultList.get(i).get("plan_cnt"));
            }
        }
        resultMap.put("level06", level + level6);
        return resultMap;
    }

    //作业风险数
    @Override
    public Map workRiskLevelCount(int timeType) {
        //数据查询
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, null, null);
        List<Map> resultList = this.jDataAccess.queryWithParamById("WorkRiskLevelCount", paramMap);
        return resultList.get(0);
    }

    //风险预警解除数
    @Override
    public Map warnRiskCount(int timeType) {
        //数据查询
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNENDTIME, null, null);
        List<Map> resultList = this.jDataAccess.queryWithParamById("WarnRiskCount", paramMap);
        return resultList.get(0);
    }


    //预警数量
    @Override
    public List<Map> warnCount(int timeType, String beginTime, String endTime) {
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, beginTime, endTime);
        List<Map> resultList = this.jDataAccess.queryWithParamById("WarnCount", paramMap);
        return resultList;
    }

    //电压等级
    @Override
    public List<Map> voltageClassCount(int timeType, String beginTime, String endTime) {
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, beginTime, endTime);
        List<Map> resultList = this.jDataAccess.queryWithParamById("VoltageClassCount", paramMap);
        return resultList;
    }

    //停电事由
    @Override
    public List<Map> failureReasonCount(int timeType, String beginTime, String endTime) {
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, beginTime, endTime);
        List<Map> resultList = this.jDataAccess.queryWithParamById("FailureReasonCount", paramMap);
        return resultList;
    }

    //停电设备
    @Override
    public List<Map> riskDevCount(int timeType, String beginTime, String endTime) {
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, beginTime, endTime);
        List<Map> resultList = this.jDataAccess.queryWithParamById("RiskDevCount", paramMap);
        return resultList;
    }

    //预警事件持续时间
    @Override
    public List<Map> riskKeepDayCount(int timeType, String beginTime, String endTime) {
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, beginTime, endTime);
        List<Map> resultList = this.jDataAccess.queryWithParamById("RiskKeepDayCount", paramMap);
        return resultList;
    }

    //预警影响类型
    @Override
    public List<Map> warnDevTypeCount(int timeType, String beginTime, String endTime) {
        Map<String, Object> paramMap = createCommonFilter(timeType, WARNBEGINTIME, beginTime, endTime);
        List<Map> resultList = this.jDataAccess.queryWithParamById("WarnDevTypeCount", paramMap);
        return resultList;
    }


    //获取字典定义
    @Override
    public Map<String, List<JSONObject>> getSrpRiskSysTab() {
        Map<String, List<JSONObject>> resultMap = new HashMap<>();

        List<JSONObject> warningLevel = new ArrayList<>();
        List<JSONObject> voltageLevel = new ArrayList<>();
        List<JSONObject> failureReason = new ArrayList<>();
        List<JSONObject> warndevType = new ArrayList<>();
        List<JSONObject> riskDev = new ArrayList<>();
        List<JSONObject> warnStatus = new ArrayList<>();


        List<Map> resultList = this.jDataAccess.queryWithParamById("GetSrpRiskSysTab", null);
        resultList.stream().forEach(map -> {
            JSONObject jsonObject = JSON.parseObject("{'id':'" + map.get("column_type_code") + "','text':'" + map.get("column_type_name") + "'}");
            if ("VOLTAGE_LEVEL".equals(map.get("column_code"))) {

                voltageLevel.add(jsonObject);
            }
            if ("FAILURE_REASON".equals(map.get("column_code"))) {
                failureReason.add(jsonObject);
            }
            if ("WARNDEVTYPE".equals(map.get("column_code"))) {
                warndevType.add(jsonObject);
            }
            if ("RISKDEV".equals(map.get("column_code"))) {
                riskDev.add(jsonObject);
            }
            if ("WARNSTATUS".equals(map.get("column_code"))) {
                warnStatus.add(jsonObject);
            }
            if ("WARNINGLEVEL".equals(map.get("column_code"))) {
                warningLevel.add(jsonObject);
            }

        });
        resultMap.put("warningLevel", warningLevel);
        resultMap.put("voltageLevel", voltageLevel);
        resultMap.put("failureReason", failureReason);
        resultMap.put("warndevType", warndevType);
        resultMap.put("riskDev", riskDev);
        resultMap.put("warnStatus", warnStatus);
        return resultMap;
    }

    //删除列表数据(批量删除)
    //json: {"ids":["1","2",....]}
    @Override
    public String deleteInfo(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        JSONArray ids = rowData.getJSONArray("ids");
        String s = ids.toString();
        String substring = s.substring(1, s.length() - 1);
        Map map = new HashMap();
        map.put("filter",substring);
        int deleteInfo = jDataAccess.updateWithParamById("deleteInfo", map);
        return "删除了"+deleteInfo+"条数据";
    }

//**************************************************************************************************************************************************************


    //时间查询串拼接
    private Map<String, Object> createCommonFilter(int timeType, String timeStr, String beginTime, String endTime) {
        //拼装sql
        StringBuilder commonFilter = new StringBuilder("1 = 1");
        if (timeType == 0) {//今日
            commonFilter.append(" AND to_days(" + timeStr + ") = to_days(now()) ");
        } else if (timeType == 1) {//明日
            commonFilter.append(" AND TO_DAYS(" + timeStr + ") - TO_DAYS(NOW( )) = 1");
        } else if (timeType == 2) {//本周
            commonFilter.append(" AND YEARWEEK(date_format(" + timeStr + ",'%Y-%m-%d'),1) = YEARWEEK(now(),1)");
        } else if (timeType == 3) {//本月
            commonFilter.append(" AND DATE_FORMAT( " + timeStr + ", '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
        } else if (timeType == 4) {//本年
            commonFilter.append(" AND YEAR(" + timeStr + ")=YEAR(NOW())");
        } else if (timeType == 5) {//自定义时间段
            commonFilter.append(" AND " + timeStr + " BETWEEN STR_TO_DATE('" + beginTime + "', '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE('" + endTime + "', '%Y-%m-%d %H:%i:%s')");
        }

        Map<String, Object> paramMap = new HashMap();
        String filter = commonFilter.toString();
        paramMap.put("filter", filter);
        return paramMap;
    }


    private String createWarnInfoFilter(String jsonStr) {

        JSONObject rowData = JSONObject.parseObject(jsonStr);
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        if (rowData.getString("title") != null && !"".equals(rowData.getString("title"))) {//标题
            commonFilter.append(" AND title like '%" + rowData.getString("title") + "%'");
        }
        if (rowData.getString("warningLevel") != null && !"".equals(rowData.getString("warningLevel"))) {//预警等级：
            commonFilter.append(" AND warningLevel ='" + rowData.getString("warningLevel") + "'");
        }
        if (rowData.getString("voltageLevel") != null && !"".equals(rowData.getString("voltageLevel"))) {//电压等级（kV）：
            commonFilter.append(" AND VOLTAGE_LEVEL = '" + rowData.getString("voltageLevel") + "'");
        }
        if (rowData.getString("warnbegintime") != null && !"".equals(rowData.getString("warnbegintime")) && rowData.getString("warnendtime") != null && !"".equals(rowData.getString("warnendtime"))) {//预警计划时间：
            commonFilter.append(" AND warnbegintime BETWEEN STR_TO_DATE('" + rowData.getString("warnbegintime") + "', '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE('" + rowData.getString("warnendtime") + "', '%Y-%m-%d %H:%i:%s')");
        }
        if (rowData.getString("failureReason") != null && !"".equals(rowData.getString("failureReason"))) {//停电事由：
            commonFilter.append(" AND failure_reason ='" + rowData.getString("failureReason") + "'");
        }
        if (rowData.getString("warnnum") != null && !"".equals(rowData.getString("warnnum"))) {//预警编号：
            commonFilter.append(" AND warnnum like '%" + rowData.getString("warnnum") + "%'");
        }
        if (rowData.getString("riskdev") != null && !"".equals(rowData.getString("riskdev"))) {//停电设备类型：
            commonFilter.append(" AND riskdev = '" + rowData.getString("riskdev") + "'");
        }
        if (rowData.getString("warnstatus") != null && !"".equals(rowData.getString("warnstatus"))) {//预警状态：
            commonFilter.append(" AND warnstatus = '" + rowData.getString("warnstatus") + "'");
        }
        if (rowData.getString("warndevtype") != null && !"".equals(rowData.getString("warndevtype"))) {//预警设备类型：
            commonFilter.append(" AND warndevtype = '" + rowData.getString("warndevtype") + "'");
        }
        if (rowData.getString("datareport_org") != null && !"".equals(rowData.getString("datareport_org"))) {//发布单位：
            commonFilter.append(" AND a.datareport_org like '%" + rowData.getString("datareport_org") + "%'");
        }
//
//        if(rowData.getString("warnresultType") != null && !"".equals(rowData.getString("warnresultType"))) {//风险后果类型：
//            commonFilter.append(" AND warnresultType ='"+rowData.getString("warnresultType")+"'");
//        }
//
//        if(rowData.getString("workPlanCodeDayCnt") != null && !"".equals(rowData.getString("workPlanCodeDayCnt"))) {//关联作业数：
//            commonFilter.append(" AND WORK_PLAN_CODE_DAY_CNT = "+rowData.getString("workPlanCodeDayCnt")+"");
//        }

        String filter = commonFilter.toString();
        return filter;
    }





  /*  @Override
    public MapPageResult allRiskWarnInfosList1(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        jsonStr = rowData.getString("dataStr");
        String filter = createWarnInfoFilter(jsonStr);
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", filter);
        MapPageResult cntResult = this.jDataAccess.queryWithParamById("AllRiskWarnInfosList", paramMap,pageSize,pageNo);

        return cntResult;
    }*/
}
