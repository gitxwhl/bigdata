package com.raysdata.riskmanagementserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskVisualizationVo;
import com.raysdata.riskmanagementserver.dao.RiskManagementDao;
import com.raysdata.riskmanagementserver.service.RiskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * @author zyy
 */
@Service
public class RiskManagementServiceImpl implements RiskManagementService {

    @Autowired
    private RiskManagementDao riskManagementDao;

    /**
     * 各风险总数统计
     * */
    @Override
    public Map<String, Object> getRiskmanagementcnt() {
        //电网运行风险总数
        Map<String, Object> warnCnt = riskManagementDao.getSrpriskgridwarncnt();
        //电网建设风险总数
        warnCnt.putAll(riskManagementDao.getSrpriskgridconstwarncnt());
        //产业风险总数
        warnCnt.putAll(riskManagementDao.getSrpriskindusriskwarncnt());
        //网络风险总数
        warnCnt.putAll(riskManagementDao.getSrpriskeventwarningcnt());
        return warnCnt;
    }

    /**
     * 各电压等级作业风险数
     * */
    @Override
    public Map<String, Object> getSrpworkplanvoltagelevelcnt() {

        Map<String, Object> warnCnt = riskManagementDao.getSrpworkplanvoltagelevelcnt();
        return warnCnt;
    }


    /**
     * 电网运行风险等级
     * */
    @Override
    public Map<String, Object> getSrpriskgridwarnlevelcnt() {
        //电网运行风险总数
        Map<String, Object> warnCnt = riskManagementDao.getSrpriskgridwarnlevelcnt();
        return warnCnt;
    }

    /**
     * 电网建设风险等级
     * */
    @Override
    public Map<String, Object> getSrpriskgridconstwarnlevelcnt() {
        //电网建设风险等级
        Map<String, Object> warnCnt = riskManagementDao.getSrpriskgridconstwarnlevelcnt();
        return warnCnt;
    }

    /**
     * 产业风险等级
     * */
    @Override
    public Map<String, Object> getSrpriskindusriskwarnlevelcnt() {
        //产业风险等级
        Map<String, Object> warnCnt = riskManagementDao.getSrpriskindusriskwarnlevelcnt();
        return warnCnt;
    }

    /**
     * 产业风险等级
     * */
    @Override
    public Map<String, Object> getSrpriskeventwarninglevelcnt() {
        //产业风险等级
        Map<String, Object> warnCnt = riskManagementDao.getSrpriskeventwarninglevelcnt();
        return warnCnt;
    }

    /**
     * 安全风险综合情况
     * */
    @Override
    public List<Map<String, Object>> getSrpriskwarnareacnt(String paramJson) throws Throwable {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int warnType = Integer.parseInt(JSONObject.parseObject(paramJson).getString("warnType"));
        String typeGridStructure = JSONObject.parseObject(paramJson).getString("typeGridStructure");
        String warnLevel = JSONObject.parseObject(paramJson).getString("warnLevel");
        List<Map<String, Object>> warncntList = null;
        StringBuilder param = new StringBuilder();
        SrpRiskVisualizationVo srpRiskVisualization = new SrpRiskVisualizationVo();
        srpRiskVisualization.setWarnLevel(warnLevel);
        srpRiskVisualization.setTypeGridStructure(typeGridStructure);

//        if (1 == warnType) {
//            if (ObjectUtils.isNotEmpty(typeGridStructure)) {
//
//                srpRiskVisualization.setTypeGridStructure(typeGridStructure);
////                param.append(" and TYPE_GRID_STRUCTURE ='").append(typeGridStructure).append("'");
//            }
//            if (ObjectUtils.isNotEmpty(warnLevel)) {
//
//                srpRiskVisualization.setWarnLevel(warnLevel);
////                param.append(" and WARNINGLEVEL ='").append(warnLevel).append("'");
//            }
//        } else {
//            if (ObjectUtils.isNotEmpty(warnLevel)) {
//                srpRiskVisualization.setWarnType(warnLevel);
////                param.append(" and WARNINGLEVEL ='").append(warnLevel).append("'");
//            }
//        }
        switch (warnType) {
            //电网运行风险
            case 1:
                warncntList = riskManagementDao.getSrpriskgridwarnareacnt(srpRiskVisualization);
                break;
            //电网建设风险
            case 2:
//                String sqlRiskmanagementdaogetsrpriskgridconstwarnareacnt = Const.SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDCONSTWARNAREACNT.replaceAll("%param%", param.toString());
                warncntList = riskManagementDao.getSrpriskgridconstwarnareacnt(srpRiskVisualization);
                break;
            //产业风险
            case 3:

                warncntList = riskManagementDao.getSrpriskindusriskwarnareacnt(srpRiskVisualization);
//                String sqlRiskmanagementdaogetsrpriskindusriskwarnareacnt = Const.SQL_RISKMANAGEMENTDAO_GETSRPRISKINDUSRISKWARNAREACNT.replaceAll("%param%", param.toString());
//                warncntList = riskManagementDao.getSrpriskindusriskwarnareacnt(sqlRiskmanagementdaogetsrpriskindusriskwarnareacnt);
                break;
            //网络风险
            case 4:
//                String sqlRiskmanagementdaogetsrpriskeventwarningareacnt = Const.SQL_RISKMANAGEMENTDAO_GETSRPRISKEVENTWARNINGAREACNT.replaceAll("%param%", param.toString());
                warncntList = riskManagementDao.getSrpriskeventwarningareacnt(srpRiskVisualization);
                break;
            //电网运行风险
            default:
//                 sqlRiskmanagementdaogetsrpriskgridwarnareacnt = Const.SQL_RISKMANAGEMENTDAO_GETSRPRISKGRIDWARNAREACNT.replaceAll("%param%", param.toString());
                warncntList = riskManagementDao.getSrpriskgridwarnareacnt(srpRiskVisualization);
                break;
        }
        return warncntList;
    }


}
