package com.raysdata.scheduleserver.service.impl;

import com.raysdata.scheduleserver.service.HomeService;
import com.raysdata.scheduleserver.service.WPWorkPlanBizc;
import org.springframework.stereotype.Service;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import com.nariit.pi6000.framework.util.StringUtil;
import com.nariit.pi6000.ua.po.AppOrgUnit;
import com.nariit.rmcp.base.vo.PeccancyManage;
import com.nariit.rmcp.base.vo.WorkPlanMapVO;
import com.nariit.rmcp.base.vo.WpWorkPlanDay;
import com.nariit.rmcp.common.constant.ZyglbmTypeEnum;
import com.nariit.rmcp.common.constant.ZylblxTypeEnum;
import com.nariit.rmcp.common.util.DateUtil;
import com.nariit.rmcp.common.util.DictUtil;
import com.nariit.rmcp.common.util.OrgUtil;
import com.nariit.rmcp.common.util.ResultConvertUtil;
import com.nariit.rmcp.common.vo.DicItems;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private OrgUtil orgUtil;
    @Autowired
    private JdbcDataAccess jDataAccess;
    @Autowired
    private DictUtil dictUtil;
    @Autowired
    private WPWorkPlanBizc wPWorkPlanBizc;

    public HomeServiceImpl() {
    }

    //查询统计县用户作业计划一览
    public List<Map> getWorkPlanList(String userId) {
        AppOrgUnit superUnit = this.orgUtil.getSuperiorUnit(userId);
        int level = superUnit.getLevel();
        String sqlId = "ProvinceList";
        if (level == 2) {
            sqlId = "CityList";
        } else if (level == 3) {
            sqlId = level == 2 ? "CityList" : "CountyList";
        }

        List<Map> countresultList = this.queryCountByUserId(sqlId, userId);
        Map<String, Object> resultMap = new HashMap();
        countresultList.forEach((mapx) -> {
            resultMap.put((String) mapx.get("unit_id"), mapx);
        });
        List<Map> resultList = new ArrayList();
        List<AppOrgUnit> lowerFirstLevelUnits = this.orgUtil.getLowerFirstLevelUnits(userId);
        if (level > 1) {
            lowerFirstLevelUnits.add(0, superUnit);
        }

        Map viewMap = null;

        for (Iterator var9 = lowerFirstLevelUnits.iterator(); var9.hasNext(); resultList.add(viewMap)) {
            AppOrgUnit unit = (AppOrgUnit) var9.next();
            viewMap = new HashMap();
            Map map = (Map) resultMap.get(unit.getId());
            if (map == null) {
                viewMap.put("unitId", unit.getId());
                viewMap.put("unitName", unit.getShortName());
                viewMap.put("totalNum", 0);
                viewMap.put("undoNum", 0);
                viewMap.put("doingNum", 0);
                viewMap.put("doneNum", 0);
            } else {
                viewMap.put("unitId", unit.getId());
                viewMap.put("unitName", unit.getShortName());
                viewMap.put("totalNum", map.get("total_num"));
                viewMap.put("undoNum", map.get("undo_num"));
                viewMap.put("doingNum", map.get("doing_num"));
                viewMap.put("doneNum", map.get("done_num"));
            }
        }

        return resultList;
    }

    //查询统计作业风险等级
    public Map<String, Object> getZyaqRisk(String userId) {
        List<Map> resultList = this.queryCountByUserId("ZyaqRisk", userId);
        return this.getRiskLevelResult(resultList);
    }

    //查询统计今日施工情况
    public List<Map> getExeState(String userId) {
        List<Map> resultList = this.queryCountByUserId("ExeState", userId);
        Iterator var3 = resultList.iterator();

        while (var3.hasNext()) {
            Map map = (Map) var3.next();
            map.put("exeState", map.get("exe_state"));
            map.put("countNum", map.get("count_num"));
            map.remove("exe_state");
            map.remove("count_num");
        }

        return resultList;
    }

    protected List<Map> queryCountByUserId(String sqlId, String userId) {
        Map<String, Object> paramMap = new HashMap();
        String filter = this.createCommonFilter(userId).toString();
        paramMap.put("filter", filter);
        List<Map> resultList = this.jDataAccess.queryWithParamById(sqlId, paramMap);
        return resultList;
    }

    //查询统计电网风险等级
    public Map<String, Object> getDwfxRisk(String userId) {
        List<Map> resultList = this.queryCountByUserId("DwyxRisk", userId);
        return this.getRiskLevelResult(resultList);
    }

    //查询统计施工风险等级
    public Map<String, Object> getSgRisk(String userId) {
        List<Map> resultList = this.queryCountByUserId("SgRisk", userId);
        return this.getRiskLevelResult(resultList);
    }

    private Map<String, Object> getRiskLevelResult(List<Map> resultList) {
        Map<String, Object> resultMap = new HashMap();
        long totalNum = 0L;
        Iterator var5 = resultList.iterator();

        while (var5.hasNext()) {
            Map map = (Map) var5.next();
            long countNum = (Long) map.get("count_num");
            totalNum += countNum;
            map.put("riskLevel", map.get("risk_level"));
            map.put("countNum", map.get("count_num"));
            map.remove("risk_level");
            map.remove("count_num");
        }

        resultMap.put("totalNum", totalNum);
        resultMap.put("list", resultList);
        return resultMap;
    }

    //查询统计管理专业
    public Object getZyglbmType(String userId) {
        List<Map> countResultList = this.queryCountByUserId("ZyglbmType", userId);
        Map<String, Map> resultMap = new HashMap();
        countResultList.forEach((mapx) -> {
            resultMap.put((String) mapx.get("zyglbm_type"), mapx);
        });
        List<Map> resultList = new ArrayList();
        ZyglbmTypeEnum zyglbmType = this.wPWorkPlanBizc.getUserGlzy(userId);
        DicItems dicItems = this.dictUtil.getDicByDicName("GLZY", (String) null);
        Iterator var6 = dicItems.getValues().iterator();

        while (true) {
            String value;
            Map map;
            do {
                if (!var6.hasNext()) {
                    return resultList;
                }

                Map<String, String> valMap = (Map) var6.next();
                value = (String) valMap.get("value");
                map = (Map) resultMap.get(value);
                if (map == null) {
                    map = new HashMap();
                    ((Map) map).put("zyglbmType", value);
                    ((Map) map).put("zyglbmName", valMap.get("text"));
                    ((Map) map).put("countNum", 0);
                } else {
                    ((Map) map).put("zyglbmType", ((Map) map).get("zyglbm_type"));
                    ((Map) map).put("zyglbmName", valMap.get("text"));
                    ((Map) map).put("countNum", ((Map) map).get("count_num"));
                    ((Map) map).remove("zyglbm_type");
                    ((Map) map).remove("count_num");
                }
            } while (zyglbmType != null && !value.equals(zyglbmType.getValue()));

            resultList.add(map);
        }
    }

    //查询作业计划地图分布
    public List<WorkPlanMapVO> getWorkplanMap(String userId, String riskLevel) {
        StringBuilder filter = this.createCommonFilter(userId);
        if (StringUtil.isNotEmpty(riskLevel)) {
            filter.append(" AND RISK_LEVEL = '").append(riskLevel).append("'");
        }

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", filter.toString());
        List<Map> resultList = this.jDataAccess.queryWithParamById("WorkplanMap", paramMap);
        Map<String, String> glzyMap = this.getDictMap("GLZY");
        Map<String, String> riskLevelMap = this.getDictMap("FXDJ");
        Map<String, String> exeStateMap = this.getDictMap("JHZXZT");
        Iterator var9 = resultList.iterator();

        while (var9.hasNext()) {
            Map map = (Map) var9.next();
            String zyglName = (String) glzyMap.get(ObjectUtils.toString(map.get("zyglbm_type")));
            String zyName = this.getNameByCode(ZylblxTypeEnum.getZylblxName(zyglName), ObjectUtils.toString(map.get("zylb_type")));
            String zylxType = ObjectUtils.toString(map.get("zylx_type"));
            String workManage;
            if (StringUtil.isNotEmpty(zylxType)) {
                workManage = this.getNameByCode(ZylblxTypeEnum.getZylblxName(zyName), zylxType);
                zyName = zyName + "（" + workManage + "）";
            }

            map.put("zy_name", zyName);
            workManage = ObjectUtils.toString(map.get("work_manage"));
            String[] nameAndPhone = workManage.split("/");
            map.put("work_manage_name", nameAndPhone.length > 0 ? nameAndPhone[0] : "");
            map.put("work_manage_phone", nameAndPhone.length > 1 ? nameAndPhone[1] : "");
            map.put("risk_level_name", riskLevelMap.get(ObjectUtils.toString(map.get("risk_level"))));
            map.put("exe_state_name", exeStateMap.get(ObjectUtils.toString(map.get("exe_state"))));
        }

        return ResultConvertUtil.convert(resultList, WorkPlanMapVO.class);
    }

    private StringBuilder createCommonFilter(String userId) {
        StringBuilder commonFilter = this.createCommonFilter();
        AppOrgUnit superUnit = this.orgUtil.getSuperiorUnit(userId);
        int level = superUnit.getLevel();
        if (level == 2) {
            commonFilter.append(" AND DS_UNIT_ID = '").append(superUnit.getId()).append("'");
        } else if (level == 3) {
            commonFilter.append(" AND INSERT_UNIT_ID = '").append(superUnit.getId()).append("'");
        }

        ZyglbmTypeEnum zyglbmType = this.wPWorkPlanBizc.getUserGlzy(userId);
        if (zyglbmType != null) {
            commonFilter.append(" AND ZYGLBM_TYPE = '").append(zyglbmType.getValue()).append("'");
        }

        return commonFilter;
    }

    protected StringBuilder createCommonFilter() {
        String nowdate = DateUtil.formatToString(new Date(), "yyyy-MM-dd");
        StringBuilder commonFilter = new StringBuilder("1 = 1");
        commonFilter.append(" AND DATE_FORMAT(BEGIN_TIME, '%Y-%m-%d') <= '").append(nowdate).append("'");
        commonFilter.append(" AND DATE_FORMAT(END_TIME, '%Y-%m-%d') >= '").append(nowdate).append("'");
        commonFilter.append(" AND STATE = '50' ");
        commonFilter.append(" AND EXE_STATE IN ('1', '2', '5') ");
        commonFilter.append(" AND IS_DELETE = '0' ");
        return commonFilter;
    }

    public WpWorkPlanDay getWorkplanMapById(String userId, String workPlanId) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("workPlanId", workPlanId);
        List<Map> resultList = this.jDataAccess.queryWithParamById("WorkplanMapById", paramMap);
        return (WpWorkPlanDay) ResultConvertUtil.convert((Map) resultList.get(0), WpWorkPlanDay.class);
    }

    //查询违章统计一览
    public List<Map> getPeccancyList(String userId) {
        AppOrgUnit superUnit = this.orgUtil.getSuperiorUnit(userId);
        int level = superUnit.getLevel();
        StringBuilder filter = new StringBuilder("");
        if (level == 2) {
            filter.append(" AND WP.DS_UNIT_ID = '").append(superUnit.getId()).append("'");
        } else if (level == 3) {
            filter.append(" AND WP.INSERT_UNIT_ID = '").append(superUnit.getId()).append("'");
        }

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", filter);
        paramMap.put("nowdate", DateUtil.formatToString(new Date(), "yyyy-MM-dd"));
        List<Map> resultList = this.jDataAccess.queryWithParamById("PeccancyList", paramMap);
        Iterator var7 = resultList.iterator();

        while (var7.hasNext()) {
            Map map = (Map) var7.next();
            map.put("violationLevel", map.get("violation_level"));
            map.put("violationOrg", map.get("violation_org"));
            map.put("peccancyDatetime", map.get("peccancy_datetime"));
            map.remove("violation_level");
            map.remove("violation_org");
            map.remove("peccancy_datetime");
        }

        return resultList;
    }

    public PeccancyManage getPeccancyById(String userId, String id) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", id);
        List<Map> resultList = this.jDataAccess.queryWithParamById("PeccancyById", paramMap);
        return (PeccancyManage) ResultConvertUtil.convert((Map) resultList.get(0), PeccancyManage.class);
    }

    //查询违章数量
    public Object getPeccancyAmount(String userId) {
        AppOrgUnit superUnit = this.orgUtil.getSuperiorUnit(userId);
        int level = superUnit.getLevel();
        StringBuilder filter = new StringBuilder("");
        if (level == 2) {
            filter.append(" AND WP.DS_UNIT_ID = '").append(superUnit.getId()).append("'");
        } else if (level == 3) {
            filter.append(" AND WP.INSERT_UNIT_ID = '").append(superUnit.getId()).append("'");
        }

        ZyglbmTypeEnum zyglbmType = this.wPWorkPlanBizc.getUserGlzy(userId);
        if (zyglbmType != null) {
            filter.append(" AND WP.ZYGLBM_TYPE = '").append(zyglbmType.getValue()).append("'");
        }

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", filter);
        String nowdate = DateUtil.formatToString(new Date(), "yyyy-MM-dd");
        paramMap.put("nowdate", nowdate);
        paramMap.put("nowweek", DateUtil.getMonday(new Date()));
        paramMap.put("nowyear", nowdate.substring(0, 4));
        List<Map> resultList = this.jDataAccess.queryWithParamById("PeccancyAmount", paramMap);
        Iterator var9 = resultList.iterator();

        while (var9.hasNext()) {
            Map map = (Map) var9.next();
            map.put("yearNum", map.get("year_num"));
            map.put("weekNum", map.get("week_num"));
            map.put("dayNum", map.get("day_num"));
            map.remove("year_num");
            map.remove("week_num");
            map.remove("day_num");
        }

        return resultList.get(0);
    }

    private String getNameByCode(String dictName, String code) {
        DicItems dicItems = this.dictUtil.getDicByDicName(dictName, (String) null);
        List<Map<String, String>> values = dicItems.getValues();
        if (CollectionUtils.isNotEmpty(values)) {
            Iterator var5 = values.iterator();

            while (var5.hasNext()) {
                Map<String, String> map = (Map) var5.next();
                if (((String) map.get("value")).equals(code)) {
                    return (String) map.get("text");
                }
            }
        }

        return "";
    }

    private Map<String, String> getDictMap(String dictname) {
        Map<String, String> dictMap = new HashMap();
        DicItems dicItems = this.dictUtil.getDicByDicName(dictname, (String) null);
        List<Map<String, String>> values = dicItems.getValues();
        Iterator var5 = values.iterator();

        while (var5.hasNext()) {
            Map<String, String> map = (Map) var5.next();
            dictMap.put(map.get("value"), map.get("text"));
        }

        return dictMap;
    }

}
