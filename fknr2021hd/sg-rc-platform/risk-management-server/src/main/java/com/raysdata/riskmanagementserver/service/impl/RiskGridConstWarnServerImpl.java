package com.raysdata.riskmanagementserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskmanagementserver.bean.vo.AreaVo;
import com.raysdata.riskmanagementserver.bean.vo.SrpRiskGridconstwarnnoticeVo;
import com.raysdata.riskmanagementserver.dao.RiskGridConstWarnnDao;
import com.raysdata.riskmanagementserver.service.RiskGridConstWarnServer;
import com.raysdata.riskmanagementserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @author zyy
 */
@Service
public class RiskGridConstWarnServerImpl implements RiskGridConstWarnServer {

    @Autowired
    private RiskGridConstWarnnDao riskGridConstWarnnDao;

/**
*电网建设风险预警情况
* warningLevel:风险等级1-5级
* areaId：省份编码（默认全国不用传，具体下钻省份是传省份编码）
* {"warningLevel":"","areaId":""}
* */
    @Override
    public List<Map<String, Object>> getSrpriskgridconstwarnareacnt(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String warningLevel = JSONObject.parseObject(paramJson).getString("warningLevel");
        String areaId = JSONObject.parseObject(paramJson).getString("areaId");
//        StringBuilder param = new StringBuilder();
//        String sql = "";
//        if (ObjectUtils.isNotEmpty(warningLevel)) {
//            param.append(" and a.WARNINGLEVEL = '").append(warningLevel).append("'");
//        }
//        if (ObjectUtils.isNotEmpty(areaId)) {
//            param.append(" and a.DATAREPORT_ORG_ID = '").append(areaId).append("'");
//            sql = Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNAREACNT.replaceAll("%param2%", "srg.CITY_ID").replaceAll("%param3%", areaId);
//        }else{
//            sql = Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNAREACNT.replaceAll("%param2%", "srg.DATAREPORT_ORG_ID").replaceAll("%param3%", "DATAREPORT_ORG");
//        }
//        sql= sql.replaceAll("%param1%", param.toString());

        return riskGridConstWarnnDao.getSrpriskgridconstwarnareacnt(warningLevel,areaId);
    }

/**
* 风险等级3、4级汇总
* areaId：地区编码（全国地图时候可以不传，下钻省份时候传值即可）
* */
    @Override
    public Map<String, Object> getSrpriskgridconstwarnlevelcnt(String areaId) {
//        StringBuilder param = new StringBuilder();
//        String sql = "";
//        if (ObjectUtils.isNotEmpty(areaId)) {
//            param.append(" and DATAREPORT_ORG_ID = '").append(areaId).append("' ");
//            sql = Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNLEVELCNT.replaceAll("%param%", param.toString());
//        }else {
//            sql = Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNLEVELCNT.replaceAll("%param%", "");
//        }

        return riskGridConstWarnnDao.getSrpriskgridconstwarnlevelcnt(areaId);
    }

    /**
    * 作业类型风险数量统计
    * areaId：地区编码（全国地图时候可以不传，下钻省份时候传值即可）
    * */
    @Override
    public List<Map<String, Object>> getSrpriskgridconstwarnworkcnt(String areaId) {
//        StringBuilder param = new StringBuilder();
//        String sql = "";
//        if (ObjectUtils.isNotEmpty(areaId)) {
//            param.append(" and a.DATAREPORT_ORG_ID = '").append(areaId).append("' ");
//            sql = Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNWORKCNT.replaceAll("%param%", param.toString());
//        }else {
//            sql = Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNWORKCNT.replaceAll("%param%", "");
//        }
        AreaVo areaVo =new AreaVo();
        areaVo.setAreaId(areaId);
        return riskGridConstWarnnDao.getSrpriskgridconstwarnworkcnt(areaVo);
    }


    /**
    *各市公司本周开始及解除风险数量统计
     * areaId：地区编码（全国地图时候可以不传，下钻省份时候传值即可）
     * */
    @Override
    public Map<String,Object> getSrpriskgridconstwarnbeginorendcnt(String areaId) {
        Map<String,Object> resultMap = new HashMap<>();

       /* StringBuilder param = new StringBuilder();
        String sql = Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWARNBEGINORENDCNT;
        if (ObjectUtils.isNotEmpty(areaId)) {
            param.append(" and a.DATAREPORT_ORG_ID = '").append(areaId).append("' ");
            sql = sql.replaceAll("%param2%","srg.CITY_ID").replaceAll("%param3%", areaId);
        }else {
            sql = sql.replaceAll("%param2%","srg.DATAREPORT_ORG_ID").replaceAll("%param3%","DATAREPORT_ORG");
        }
        sql = sql.replaceAll("%param%", param.toString());*/
        AreaVo areaVo =new AreaVo();
        areaVo.setAreaId(areaId);
        resultMap.put("constWarnBeginCNT",riskGridConstWarnnDao.getSrpriskgridconstwarnbeginorendcnt(areaVo));
        resultMap.put("constWarnEndCNT",riskGridConstWarnnDao.getSrpriskgridconstwarnbeginorendcnt(areaVo));
        return resultMap;
    }


    /**
    *年度历史同期值风险数量统计
     * areaId：地区编码（全国地图时候可以不传，下钻省份时候传值即可）
     * */
    @Override
    public Map<String,Object> getSrpriskgridconstwarnyearcnt(String areaId) {
        Map<String,Object> resultMap = new LinkedHashMap<>();
//        StringBuilder param = new StringBuilder();
//        if (ObjectUtils.isNotEmpty(areaId)) {
//            param.append(" and DATAREPORT_ORG_ID = '").append(areaId).append("' ");
//        }
        Calendar cal = Calendar.getInstance();
        Integer thisYear = cal.get(Calendar.YEAR);
        //减一年
        cal.add(Calendar.YEAR, -1);
        Integer lastYear = cal.get(Calendar.YEAR);
        List<String> yearList=new ArrayList<>();
        List<String> lastList=new ArrayList<>();
        int month = 0;
        for (int i=0;i <=12;i++){
            month++;
            if(month<=10){
                yearList.add(thisYear + "-0" + (month-1));
                lastList.add(lastYear + "-0" + (month-1));
            }else {
                yearList.add(thisYear + "-" + (month-1));
                lastList.add(lastYear + "-" + (month-1));
            }

        }
        AreaVo areaVo =new AreaVo();
        areaVo.setAreaId(areaId);
        Map<String, Object> thismap = riskGridConstWarnnDao.getSrpriskgridconstwarnyearcnt(areaVo,yearList);
        Map<String, Object> lastmap =riskGridConstWarnnDao.getSrpriskgridconstwarnyearcnt(areaVo,lastList);
        resultMap.put("thisYearCNT",thismap);
        resultMap.put("lastYearCNT",lastmap);
        return resultMap;
    }

/**
*地市作业计划列表
* paramJson:{"page":"", "size":"", "params":{"cityID":""}}
* */
    @Override
    public PageBean<Map<String, Object>> getSrpriskgridconstworklist(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = JSONObject.parseObject(paramJson).getJSONObject("params");
        String cityId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("cityID");
        SrpRiskGridconstwarnnoticeVo srpRiskGridconstwarnnoticeVo = new SrpRiskGridconstwarnnoticeVo();
        srpRiskGridconstwarnnoticeVo.setCityId(cityId);
        srpRiskGridconstwarnnoticeVo.setSize(size);
        srpRiskGridconstwarnnoticeVo.setPage(page);
        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {

            int totalSize = this.riskGridConstWarnnDao.getSrpriskgridconstworklistcnt(srpRiskGridconstwarnnoticeVo);
            if (totalSize == 0) {
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            } else {
                StringBuilder param = new StringBuilder();
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;

                int offset = (page - 1) * size;
                srpRiskGridconstwarnnoticeVo.setOffset(offset);
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> srpRiskGridWarnList = this.riskGridConstWarnnDao.getSrpriskgridconstworklist(Const.SQL_RISKGRIDCONSTWARNNDAO_GETSRPRISKGRIDCONSTWORKLIST.replaceAll("%param%", param.toString()),cityId);
                return PageBean.<Map<String, Object>>builder()
                        .content(this.riskGridConstWarnnDao.getSrpriskgridconstworklist(srpRiskGridconstwarnnoticeVo))
                        .elementTotalSize(this.riskGridConstWarnnDao.getSrpriskgridconstworklist(srpRiskGridconstwarnnoticeVo).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }
        }
    }


    /**
    * 作业计划详情查询
    * workPlanId作业计划id
    * */
    @Override
    public Map<String, Object> getSrpriskgridconstworkplaninfo(String workPlanId) {
        return riskGridConstWarnnDao.getSrpriskgridconstworkplaninfo(workPlanId);
    }
}
