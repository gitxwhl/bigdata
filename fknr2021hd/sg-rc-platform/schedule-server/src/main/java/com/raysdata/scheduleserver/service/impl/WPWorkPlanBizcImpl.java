package com.raysdata.scheduleserver.service.impl;

import com.raysdata.scheduleserver.service.WPWorkPlanBizc;
import com.alibaba.fastjson.JSON;
import com.nariit.adf.increment.service.IIncrementDataRuntimeService;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import com.nariit.pi6000.framework.remoting.HessianClient;
import com.nariit.pi6000.ua.bizc.IAppOrgUnitBizc;
import com.nariit.pi6000.ua.bizc.IAppRoleBizc;
import com.nariit.pi6000.ua.bizc.IOrgTypeBizc;
import com.nariit.pi6000.ua.po.AppOrgUnit;
import com.nariit.pi6000.ua.po.AppRole;
import com.nariit.rmcp.common.constant.ZyglbmTypeEnum;
import com.nariit.rmcp.common.util.ClsIdConstants;
import com.nariit.rmcp.common.util.DateUtil;
import com.nariit.rmcp.common.util.DictUtil;
import com.nariit.rmcp.common.util.OrgUtil;
import com.nariit.rmcp.common.util.ResultConvertUtil;
import com.nariit.rmcp.common.vo.BuildUnit;
import com.nariit.rmcp.common.vo.DicItems;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WPWorkPlanBizcImpl implements WPWorkPlanBizc {

    //    @Value("${appId}")
//    private String appId;
//    @Value("${orgSystemId}")
//    private String orgSystemId;
//    @HessianClient("pi6000-ua-web")
//    private IAppRoleBizc iAppRoleBizc;
    @HessianClient("pi6000-ua-web")
    private IAppOrgUnitBizc iAppOrgUnitBizc;
    //    @HessianClient("pi6000-ua-web")
//    private IOrgTypeBizc iOrgTypeBizc;
    @Autowired
    private DictUtil dictUtil;
    @Autowired
    private JdbcDataAccess jDataAccess;
    @Autowired
    private IIncrementDataRuntimeService runService;
    @Autowired
    private OrgUtil orgUtil;

    public WPWorkPlanBizcImpl() {
    }

    public List<DicItems> getAllDicts(String userId, boolean flag) {
        List<DicItems> result = new ArrayList();
        BuildUnit buildUnitList = this.orgUtil.getBuildUnits();
        List<Map<String, String>> dict = new ArrayList();
        Map<String, String> map = null;
        DicItems buildUnitDic = new DicItems();
        AppOrgUnit unit = this.orgUtil.getSuperiorUnit(userId);
        if (unit.getLevel() == 1) {
            Iterator var9 = buildUnitList.getUnits().iterator();

            while (var9.hasNext()) {
                AppOrgUnit item = (AppOrgUnit) var9.next();
                map = new HashMap();
                map.put("text", item.getShortName());
                map.put("value", item.getId());
                dict.add(map);
            }
        } else if (unit.getLevel() == 2) {
            map = new HashMap();
            map.put("text", unit.getShortName());
            map.put("value", unit.getId());
            dict.add(map);
        } else if (unit.getLevel() >= 3) {
            map = new HashMap();

            AppOrgUnit county;
            for (county = this.iAppOrgUnitBizc.getAppOrgUnit(unit.getPid()); county.getLevel() != 2; county = this.iAppOrgUnitBizc.getAppOrgUnit(county.getPid())) {
            }

            map.put("text", county.getShortName());
            map.put("value", county.getId());
            dict.add(map);
        }

        buildUnitDic.setName("dsUnitId");
        buildUnitDic.setValues(dict);
        result.add(buildUnitDic);
        dict = new ArrayList();
        List<AppOrgUnit> units = this.orgUtil.getReportUnit();
        Iterator var14 = units.iterator();

        AppOrgUnit item;
        while (var14.hasNext()) {
            item = (AppOrgUnit) var14.next();
            map = new HashMap();
            map.put("text", item.getShortName());
            map.put("value", item.getId());
            dict.add(map);
        }

        if (unit.getLevel() == 1) {
            var14 = buildUnitList.getUnits().iterator();

            while (var14.hasNext()) {
                item = (AppOrgUnit) var14.next();
                map = new HashMap();
                map.put("text", item.getShortName());
                map.put("value", item.getId());
                dict.add(map);
            }
        }

        DicItems reportUnitDic = new DicItems();
        reportUnitDic.setName("xjUnitId");
        reportUnitDic.setValues(dict);
        result.add(reportUnitDic);
        result.add(this.dictUtil.getDicByDicName("SHZT", "state"));
        result.add(this.dictUtil.getDicByDicName("GLZY", "zyglbmType"));
        result.add(this.dictUtil.getDicByDicName("GKBM", "relevantDept"));
        result.add(this.dictUtil.getDicByDicName("DYDJ", "voltCode"));
        result.add(this.dictUtil.getDicByDicName("TDLX", "powerCut"));
        result.add(this.dictUtil.getDicByDicName("DWYXFX", "dwyxRisk"));
        result.add(this.dictUtil.getDicByDicName("XXTXFX", "xxtxRisk"));
        result.add(this.dictUtil.getDicByDicName("ZYAQFX", "zyaqRisk"));
        result.add(this.dictUtil.getDicByDicName("DDGS", "scheduAttr"));
        result.add(this.dictUtil.getDicByDicName("SSDWLX", "ssUnitType"));
        result.add(this.dictUtil.getDicByDicName("JHZXZT", "exeState"));
        result.add(this.dictUtil.getDicByDicName("SGFX", "sgRisk"));
        result.add(this.dictUtil.getDicByDicName("BGYY", "changeReason"));
        result.add(this.dictUtil.getDicByDicName("JHZXBGLX", "wpsChangeType"));
        result.add(this.dictUtil.getDicByDicName("JHYWYX", "isAffectTxywRun"));
        result.add(this.dictUtil.getDicByDicName("SFLSJH", "planType"));
        result.add(this.dictUtil.getDicByDicName("SFDDZY", "isElectrifyWork"));
        result.add(this.dictUtil.getDicByDicName("BGLX", "changeType"));
        if (flag) {
            result.addAll(this.getAllDicts(userId));
        }
        return result;
    }

    public List<DicItems> getAllDicts(String userId) {
        ZyglbmTypeEnum zylbmType = this.getUserGlzy(userId);
        String authCode = this.getAuthUserCodey(userId);
        List<DicItems> result = new ArrayList();
        DicItems dict = new DicItems();
        List<Map<String, String>> list = new ArrayList();
        if (!"1".equals(authCode) && !"4".equals(authCode) && !"7".equals(authCode)) {
            if (zylbmType != null) {
                list.addAll(this.dictUtil.getDicByDicName(zylbmType.getCode(), (String) null).getValues());
            }
        } else {
            list.addAll(this.dictUtil.getDicByDicName("YJ_ZYLB", (String) null).getValues());
            list.addAll(this.dictUtil.getDicByDicName("JJ_ZYLB", (String) null).getValues());
            list.addAll(this.dictUtil.getDicByDicName("YX_ZYLB", (String) null).getValues());
            list.addAll(this.dictUtil.getDicByDicName("XT_ZYLB", (String) null).getValues());
            list.addAll(this.dictUtil.getDicByDicName("ZDH_ZYLB", (String) null).getValues());
            list.addAll(this.dictUtil.getDicByDicName("HQ_ZYLB", (String) null).getValues());
            list.addAll(this.dictUtil.getDicByDicName("JTQY_ZYLB", (String) null).getValues());
        }

        dict.setName("zylbType");
        dict.setValues(list);
        result.add(dict);
        return result;
    }

    public String getDataLimitCondition(String userId) {
        AppOrgUnit superUnit = this.orgUtil.getSuperiorUnit(userId);
        if (superUnit.getLevel() == 1) {
            BuildUnit buildUnit = this.orgUtil.getBuildUnits();
            StringBuffer stb = new StringBuffer();
            stb.append("DS_UNIT_ID IN (");
            boolean first = true;

            for (Iterator var6 = buildUnit.getUnits().iterator(); var6.hasNext(); first = false) {
                AppOrgUnit item = (AppOrgUnit) var6.next();
                if (!first) {
                    stb.append(",");
                }

                stb.append("'");
                stb.append(item.getId());
                stb.append("'");
            }

            stb.append(")");
            return stb.toString();
        } else {
            return superUnit.getLevel() == 2 ? " DS_UNIT_ID = '" + superUnit.getId() + "'" : " XJ_UNIT_ID = '" + superUnit.getId() + "'";
        }
    }

    public String getWorkPlanCode(String code, String unitId) {
        String sqlCode = "select CODE from WP_WORK_PLAN_CODE_RULE where DS_UNIT_ID = '" + unitId + "'";
        List<Map> list = this.jDataAccess.query(sqlCode);
        String unitCode = (String) ((Map) list.get(0)).get("code");
        if (StringUtils.isEmpty(unitCode)) {
            return null;
        } else {
            String currentDay = DateUtil.formatToString(new Date(), "yyyyMMdd");
            String sqlNum = "select ID, PIPELINE_NUM, date_format(RECORD_DATE, '%Y%m%d') as RECORD_DATE from NUMBER_RECORD where COMPANY_ID = '" + unitId + "' AND PLAN_FLAG = '" + code + "'";
            List<Map> listNum = this.jDataAccess.query(sqlNum);
            if (listNum != null && !listNum.isEmpty()) {
                Map<String, Object> resultMap = ResultConvertUtil.parseToMap(JSON.toJSONString(listNum.get(0)), String.class, Object.class);
                int pipelineNum = 1;
                if (currentDay.equals(resultMap.get("record_date").toString())) {
                    pipelineNum = Integer.parseInt(resultMap.get("pipeline_num").toString()) + 1;
                }

                resultMap.put("pipeline_num", (new DecimalFormat("000")).format((long) pipelineNum));
                resultMap.put("company_id", unitId);
                resultMap.put("record_date", DateUtil.formatToString(new Date(), "yyyy-MM-dd"));
                resultMap.put("plan_flag", code);
                this.runService.createOrSaveObject(ClsIdConstants.number_record_clsId, resultMap);
                return code + currentDay + unitCode + (new DecimalFormat("000")).format((long) pipelineNum);
            } else {
                Map<String, Object> map = new HashMap();
                map.put("pipeline_num", "001");
                map.put("company_id", unitId);
                map.put("record_date", DateUtil.formatToString(new Date(), "yyyy-MM-dd"));
                map.put("plan_flag", code);
                this.runService.createOrSaveObject(ClsIdConstants.number_record_clsId, map);
                return code + currentDay + unitCode + "001";
            }
        }
    }

    public ZyglbmTypeEnum getUserGlzy(String userId) {
        List<AppRole> role = this.orgUtil.getAppRoleByUserId(userId);
        String userRole = "";
        int i = 0;

        while (i < role.size()) {
            userRole = ((AppRole) role.get(i)).getCode();
            if (!"yunjiansheng".equals(userRole) && !"yunjianshi".equals(userRole) && !"yunjianshigongqu".equals(userRole) && !"yunjianxian".equals(userRole)) {
                if (!"jijiansheng".equals(userRole) && !"jijianshi".equals(userRole) && !"jijianshigongqu".equals(userRole) && !"jijianxian".equals(userRole)) {
                    if (!"yingxiaosheng".equals(userRole) && !"yingxiaoshi".equals(userRole) && !"yingxiaoshigongqu".equals(userRole) && !"yingxiaoxian".equals(userRole)) {
                        if (!"xintongsheng".equals(userRole) && !"xintongshi".equals(userRole) && !"xintongshigongqu".equals(userRole) && !"xintongxian".equals(userRole)) {
                            if (!"tiaokongsheng".equals(userRole) && !"tiaokongshi".equals(userRole) && !"tiaokongshigongqu".equals(userRole) && !"tiaokongxian".equals(userRole)) {
                                if (!"houqinsheng".equals(userRole) && !"houqinshi".equals(userRole) && !"houqinshigongqu".equals(userRole) && !"houqinxian".equals(userRole)) {
                                    if (!"jitisheng".equals(userRole) && !"jitishi".equals(userRole)) {
                                        ++i;
                                        continue;
                                    }

                                    return ZyglbmTypeEnum.JTQY;
                                }

                                return ZyglbmTypeEnum.HQ;
                            }

                            return ZyglbmTypeEnum.ZDH;
                        }

                        return ZyglbmTypeEnum.XTLB;
                    }

                    return ZyglbmTypeEnum.YX;
                }

                return ZyglbmTypeEnum.JJ;
            }

            return ZyglbmTypeEnum.YJ;
        }

        return null;
    }

    public String getAuthUserCodey(String userId) {
        List<AppRole> role = this.orgUtil.getAppRoleByUserId(userId);
        String userRole = "";
        int i = 0;

        while (i < role.size()) {
            userRole = ((AppRole) role.get(i)).getCode();
            if ("anjiansheng".equals(userRole)) {
                return "1";
            }

            if (!"yunjiansheng".equals(userRole) && !"jijiansheng".equals(userRole) && !"yingxiaosheng".equals(userRole) && !"xintongsheng".equals(userRole) && !"tiaokongsheng".equals(userRole) && !"houqinsheng".equals(userRole)) {
                if ("jitisheng".equals(userRole)) {
                    return "3";
                }

                if ("anjianshi".equals(userRole)) {
                    return "4";
                }

                if (!"yunjianshi".equals(userRole) && !"yunjianshigongqu".equals(userRole) && !"jijianshi".equals(userRole) && !"jijianshigongqu".equals(userRole) && !"yingxiaoshi".equals(userRole) && !"yingxiaoshigongqu".equals(userRole) && !"xintongshi".equals(userRole) && !"xintongshigongqu".equals(userRole) && !"tiaokongshi".equals(userRole) && !"tiaokongshigongqu".equals(userRole) && !"houqinshi".equals(userRole) && !"houqinshigongqu".equals(userRole)) {
                    if ("jitishi".equals(userRole)) {
                        return "6";
                    }

                    if ("anjianxian".equals(userRole)) {
                        return "7";
                    }

                    if (!"yunjianxian".equals(userRole) && !"jijianxian".equals(userRole) && !"yingxiaoxian".equals(userRole) && !"xintongxian".equals(userRole) && !"tiaokongxian".equals(userRole) && !"houqinxian".equals(userRole)) {
                        ++i;
                        continue;
                    }

                    return "8";
                }

                return "5";
            }

            return "2";
        }

        return null;
    }
}
