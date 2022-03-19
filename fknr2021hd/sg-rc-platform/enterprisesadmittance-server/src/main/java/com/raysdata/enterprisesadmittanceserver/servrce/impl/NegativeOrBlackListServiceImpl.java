package com.raysdata.enterprisesadmittanceserver.servrce.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import com.nariit.pi6000.framework.po.MapPageResult;
import com.raysdata.enterprisesadmittanceserver.servrce.NegativeOrBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NegativeOrBlackListServiceImpl implements NegativeOrBlackListService {

    @Autowired
    private JdbcDataAccess jDataAccess;

    //黑名单情况
    @Override
    public Map<String, Object> getBlackCntInfos() {

        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> paramMap = createCommonFilter(null);
        List<Map> blackCntInfos = this.jDataAccess.queryWithParamById("GetBlackCntInfos", paramMap);

        paramMap = createCommonFilter(null);//本月纳入黑名单数
        List<Map> blackCntInfosByTimMonth = this.jDataAccess.queryWithParamById("GetBlackCntInfosByTimMonth", paramMap);

        paramMap = createCommonFilter(null);//本年纳入黑名单数
        List<Map> blackCntInfosByTimYear = this.jDataAccess.queryWithParamById("GetBlackCntInfosByTimYear", paramMap);

        resultMap.put("blackCntInfos", blackCntInfos.get(0)); // 黑名单等级统计
        resultMap.put("blackCntInfosByTimMonth", blackCntInfosByTimMonth.get(0));//本月纳入
        resultMap.put("blackCntInfosByTimYear", blackCntInfosByTimYear.get(0));//本年纳入

        return resultMap;
    }

    //各单位纳入黑名单数及解除黑名单数情况
    @Override
    public List<Map> getBlackCntInfosByArea(Integer inteType) {

        Map<String, Object> paramMap = createCommonFilter(null);
        List<Map> blackCntInfos=new ArrayList<>();
        if (inteType==1){//全部数据
            blackCntInfos = this.jDataAccess.queryWithParamById("GetBlackCntInfosByAreaAll", paramMap);
            return blackCntInfos;
        }
        if (inteType==2){//本年数据
            blackCntInfos = this.jDataAccess.queryWithParamById("GetBlackCntInfosByAreaYear", paramMap);
            return blackCntInfos;
        }
        if (inteType==3){//本月数据
            blackCntInfos = this.jDataAccess.queryWithParamById("GetBlackCntInfosByAreaMonth", paramMap);
            return blackCntInfos;
        }
        return blackCntInfos;
    }

    //负面清单情况
    @Override
    public Map<String, Object> getNegativeCntInfos() {

        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> paramMap = createCommonFilter(null);
        List<Map> negativeCntInfos = this.jDataAccess.queryWithParamById("GetNegativeCntInfos", paramMap);

        paramMap = createCommonFilter(null);//本月纳入负面清单数
        List<Map> getNegativeCntInfosByTimMonth = this.jDataAccess.queryWithParamById("GetNegativeCntInfosByTimMonth", paramMap);

        paramMap = createCommonFilter(null);//本年纳入负面清单数
        List<Map> getNegativeCntInfosByTim = this.jDataAccess.queryWithParamById("GetNegativeCntInfosByTimYear", paramMap);

        resultMap.put("negativeCntInfos", negativeCntInfos.get(0)); // 负面清单总数：
        resultMap.put("negativeCntInfosByAreaMonth", getNegativeCntInfosByTimMonth.get(0));//本月纳入负面清单：
        resultMap.put("negativeCntInfosByAreaYear", getNegativeCntInfosByTim.get(0));//本年纳入负面清单：

        return resultMap;
    }


    //各单位本月纳入负面清单数及本月解除负面清单数情况
    @Override
    public List<Map> getNegativeCntInfosByArea(Integer inteType) {

        Map<String, Object> paramMap = createCommonFilter(null);

        List<Map> negativeCntInfosByArea=new ArrayList<>();
        if (inteType==1){//全部数据
            negativeCntInfosByArea = this.jDataAccess.queryWithParamById("GetNegativeCntInfosByAreaAll", paramMap);
            return negativeCntInfosByArea;
        }
        if (inteType==2){//本年数据
            negativeCntInfosByArea = this.jDataAccess.queryWithParamById("GetNegativeCntInfosByAreaYear", paramMap);
            return negativeCntInfosByArea;
        }
        if (inteType==3){//本月数据
            negativeCntInfosByArea = this.jDataAccess.queryWithParamById("GetNegativeCntInfosByAreaMonth", paramMap);
            return negativeCntInfosByArea;
        }
        return negativeCntInfosByArea;
    }

    //黑名单及负面清单列表
    @Override
    public Map<String, Object> getBlackInfosListOrNegativeInfosList(String jsonStr) {


        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int selType = Integer.parseInt(rowData.getString("selType"));
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
        jsonStr = rowData.getString("dataStr");
        Map<String, Object> paramMap = createListinfoFilter(selType, jsonStr);

        if (0 == selType) {
            MapPageResult resultObj = this.jDataAccess.queryWithParamById("GetBlackInfosList", paramMap, pageSize, pageNo);
            List<Map> resultList = resultObj.getRows();
            resultMap.put("resultList", resultList);
            resultMap.put("resultListCount", resultList.size());


            List<Map> cntResult = this.jDataAccess.queryWithParamById("GetBlackInfosCnt", paramMap);
            resultMap.put("listSize", cntResult.get(0).get("black_cnt"));
            Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("black_cnt").toString()) / pageSize;
            Double pageCount = Math.ceil(pageCountDouble);
            resultMap.put("pageCount", pageCount);
        } else {
            MapPageResult resultObj = this.jDataAccess.queryWithParamById("GetNegativeInfosList", paramMap, pageSize, pageNo);
            List<Map> resultList = resultObj.getRows();
            resultMap.put("resultList", resultList);
            resultMap.put("resultListCount", resultList.size());

            List<Map> cntResult = this.jDataAccess.queryWithParamById("GetNegativeInfosCnt", paramMap);
            resultMap.put("listSize", cntResult.get(0).get("negative_cnt"));
            Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("negative_cnt").toString()) / pageSize;
            Double pageCount = Math.ceil(pageCountDouble);
            resultMap.put("pageCount", pageCount);
        }


        return resultMap;
    }

    /**
     * 黑名单及负面清单列表数据删除
     * @param jsonStr
     * @return
     */
    @Override
    public Integer deleteBlacklistInventory(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        JSONArray ids = rowData.getJSONArray("ids");
        int selType = Integer.parseInt(rowData.getString("selType"));
        String s = ids.toString();
        String substring = s.substring(1, s.length() - 1);
        Map map = new HashMap();
        map.put("filter",substring);
        int i = 0;
        if(0 == selType){
            //删除黑名单列表数据
            i=  jDataAccess.updateWithParamById("deleteBlacklist", map);
        }else {
            //删除负面清单列表数据
            i=  jDataAccess.updateWithParamById("deleteInventory", map);
        }
        return i;
    }


    //-----------------------------------------------------------------------------------------------------------------------------

    private Map<String, Object> createCommonFilter(Integer strType) {
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");

        Map<String, Object> paramMap = new HashMap();
        String filter = commonFilter.toString();
        paramMap.put("filter", filter);
        return paramMap;
    }


    private Map<String, Object> createListinfoFilter(int selType, String jsonStr) {

        JSONObject rowData = JSONObject.parseObject(jsonStr);
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        commonFilter.append("AND ( a.DELETELOGO is null or a.DELETELOGO != '1' )");
        if (0 == selType) {
            if (rowData.getString("siteinfoName") != null && !"".equals(rowData.getString("siteinfoName"))) {//企业名称
                commonFilter.append(" AND a.SITEINFO_NAME like '%" + rowData.getString("siteinfoName") + "%'");
            }
            if (rowData.getString("blacklistLevel") != null && !"".equals(rowData.getString("blacklistLevel"))) {//黑名单等级：
                commonFilter.append(" AND a.BLACKLIST_LEVEL ='" + rowData.getString("blacklistLevel") + "'");
            }
            if (rowData.getString("datareportOrgId") != null && !"".equals(rowData.getString("datareportOrgId"))) {//所属省份id
                commonFilter.append(" AND a.PROVINCE_CODE = '" + rowData.getString("datareportOrgId") + "'");
            }
            if (rowData.getString("isEnableEnter") != null && !"".equals(rowData.getString("isEnableEnter"))) {//黑名单状态：
                if ("01".equals(rowData.getString("isEnableEnter").trim())) {
                    commonFilter.append("and (NOW() <=  BLACKLISTREL_DATE or BLACKLISTREL_DATE is null)");
                } else {
                    commonFilter.append("and NOW() > BLACKLISTREL_DATE ");
                }
            }
            if (rowData.getString("beginTim") != null && !"".equals(rowData.getString("beginTim")) && rowData.getString("endTim") != null && !"".equals(rowData.getString("endTim"))) {//企业禁入有效区间：
                //commonFilter.append(" AND RELEASE_DATE >= STR_TO_DATE('" + rowData.getString("beginTim") + "', '%Y-%m-%d %H:%i:%s')");
                //commonFilter.append(" AND RELEASE_DATE <= STR_TO_DATE('" + rowData.getString("endTim") + "', '%Y-%m-%d %H:%i:%s')");
                commonFilter.append(" AND a.RELEASE_DATE between STR_TO_DATE('" + rowData.getString("beginTim") + "', '%Y-%m-%d %H:%i:%s')");
                commonFilter.append(" AND STR_TO_DATE('" + rowData.getString("endTim") + "', '%Y-%m-%d %H:%i:%s')");
            }
        } else {
            if (rowData.getString("siteinfoName") != null && !"".equals(rowData.getString("siteinfoName"))) {//企业名称
                commonFilter.append(" AND a.ORG_NAME like '%" + rowData.getString("siteinfoName") + "%'");
            }
            if (rowData.getString("datareportOrgId") != null && !"".equals(rowData.getString("datareportOrgId"))) {//所属省份id
                commonFilter.append(" AND a.PROVINCE_CODE = '" + rowData.getString("datareportOrgId") + "'");
            }
            if (rowData.getString("beginTim") != null && !"".equals(rowData.getString("beginTim")) && rowData.getString("endTim") != null && !"".equals(rowData.getString("endTim"))) {//企业禁入有效区间：
                commonFilter.append(" AND a.RELEASE_DATE between STR_TO_DATE('" + rowData.getString("beginTim") + "', '%Y-%m-%d %H:%i:%s')");
                commonFilter.append(" AND STR_TO_DATE('" + rowData.getString("endTim") + "', '%Y-%m-%d %H:%i:%s')");
            }
        }


        Map<String, Object> paramMap = new HashMap();
        String filter = commonFilter.toString();
        paramMap.put("filter", filter);

        return paramMap;
    }
}
