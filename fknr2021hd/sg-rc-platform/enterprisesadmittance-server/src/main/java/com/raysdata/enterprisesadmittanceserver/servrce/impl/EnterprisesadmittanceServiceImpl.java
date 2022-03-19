package com.raysdata.enterprisesadmittanceserver.servrce.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import com.nariit.pi6000.framework.po.MapPageResult;
import com.raysdata.enterprisesadmittanceserver.entity.PersonnePoi;
import com.raysdata.enterprisesadmittanceserver.servrce.EnterprisesadmittanceService;
import com.raysdata.enterprisesadmittanceserver.util.DaoBySql;
import com.raysdata.enterprisesadmittanceserver.util.ExcelUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnterprisesadmittanceServiceImpl implements EnterprisesadmittanceService {

    private static final int ALL_CNT_TYPE = 1;
    private static final int CY_CNT_TYPE = 2;
    private static final int WB_CNT_TYPE = 3;
    private static final int ZR_CNT_TYPE = 4;
    private static final int HMDORFM_CNT_TYPE = 5;


    @Autowired
    private JdbcDataAccess jDataAccess;

    /**
     *企业模板导出
     * @return
     */
    @Override
    public Object downloadEnterExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchFieldException, IllegalAccessException {
        Map<String, Object> paramMap = new HashMap();
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        paramMap.put("commonFilter", commonFilter);
        List<Map> allEnterCount = this.jDataAccess.queryWithParamById("getEnterCount", paramMap);

        //将List集合中的map对象转为List<对象>
        List list1 = DaoBySql.mapToEntyList(allEnterCount, PersonnePoi.class);

        //反射和注解导出
        XSSFWorkbook workbooks = ExcelUtils.createExcel(list1);

        String fileName = "企业信息.xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        workbooks.write(response.getOutputStream());
        workbooks.close();
        return false;
    }

    /**
     * 作业计划
     * @param jsonStr
     * @return
     */
    @Override
    public Map<String, Object> getSrpWorkePlanDay(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> resultList = this.jDataAccess.queryWithParamById("GetSrpWorkePlanDayList", paramMap);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        Map<String, Object> cntParamMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("getSrpWorkePlanDayCount", cntParamMap);
        resultMap.put("listSize", cntResult.get(0).get("worksiteinfo_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("worksiteinfo_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;
    }


    //  全网准入单位情况
    @Override
    public Map<String, Object> getEnterpriseCnts() {
        Map<String, Object> resultMap = new HashMap<>();

        Map<String, Object> paramMap = createCommonFilter(ALL_CNT_TYPE);
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("GetEnterpriseCnt", paramMap);

        paramMap = createCommonFilter(CY_CNT_TYPE);
        List<Map> cyEnterpriseCnt = this.jDataAccess.queryWithParamById("GetEnterpriseCnt", paramMap);

        paramMap = createCommonFilter(WB_CNT_TYPE);
        List<Map> wbEnterpriseCnt = this.jDataAccess.queryWithParamById("GetEnterpriseCnt", paramMap);

        paramMap = createCommonFilter(ZR_CNT_TYPE);
        List<Map> zrEnterpriseCnt = this.jDataAccess.queryWithParamById("GetEnterpriseCnt", paramMap);

        paramMap = createCommonFilter(HMDORFM_CNT_TYPE);
        List<Map> hmdEnterpriseCnt = this.jDataAccess.queryWithParamById("GetBlacklistOrgCnt", paramMap);

        paramMap = createCommonFilter(HMDORFM_CNT_TYPE);
        List<Map> fmqdEnterpriseCnt = this.jDataAccess.queryWithParamById("GetNegativelistOrgCnt", paramMap);

        resultMap.put("allEnterpriseCnt", allEnterpriseCnt.get(0)); // 当前有效准入人员总数
        resultMap.put("cyEnterpriseCnt", cyEnterpriseCnt.get(0));//产业
        resultMap.put("wbEnterpriseCnt", wbEnterpriseCnt.get(0));//外包：
        resultMap.put("zrEnterpriseCnt", zrEnterpriseCnt.get(0));//本月新增准入人数（创建时间）
        resultMap.put("hmdEnterpriseCnt", hmdEnterpriseCnt.get(0));//本月黑名单
        resultMap.put("fmqdEnterpriseCnt", fmqdEnterpriseCnt.get(0));//本月负面清单

        return resultMap;
    }

    //各单位准入情况
    @Override
    public List<Map> getEnterprisesAdmittanceInfoCnt(int timeType) {
        Map<String, Object> paramMap = createTimeFilter(timeType, "REPORT_CARD_ENDTIME", null, null);
        List<Map> enterpriseCntList = this.jDataAccess.queryWithParamById("GetEnterprisesAdmittanceInfoCnt", paramMap);
        return enterpriseCntList;
    }


    //企业信息查询列表
    @Override
    public Map<String, Object> getWorkSiteinfosList(String jsonStr) {

        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = createWorkSiteinfoFilter(jsonStr);

        List<Map> resultList = this.jDataAccess.queryWithParamById("GetWorkSiteinfoList", paramMap);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        Map<String, Object> cntParamMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("GetWorkSiteinfoCount", cntParamMap);
        resultMap.put("listSize", cntResult.get(0).get("worksiteinfo_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("worksiteinfo_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;
    }


    //现场作业单位信息查询
    @Override
    public List<Map> getSrpWorkSiteinfo(String siteinfoId) {
        Map<String, Object> paramMap = new HashMap();
        String filter = " a.SITEINFO_ID = '" + siteinfoId + "'";
        paramMap.put("filter", filter);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("GetSrpWorkSiteinfo", paramMap);
        return cntResult;
    }


    //企业违章信息查询
    @Override
    public Map<String, Object> getSrpWorkViolationfile(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
//        jsonStr = rowData.getString("dataStr");
        Map<String, Object> paramMap = createWorkSiteinfoFilter(jsonStr);
//        MapPageResult resultObj = this.jDataAccess.queryWithParamById("GetSrpWorkViolationfile", paramMap, pageSize, pageNo);
//        List<Map> resultList = resultObj.getRows();

        List<Map> resultList = this.jDataAccess.queryWithParamById("GetSrpWorkViolationfile", paramMap);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        Map<String, Object> cntParamMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("GetSrpWorkViolationfileCount", cntParamMap);
        resultMap.put("listSize", cntResult.get(0).get("worksiteinfo_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("worksiteinfo_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;

    }

    //企业违章详情记分规则
    @Override
    public List<Map> getSrpworkViolationrules(String violationfileId) {//违章档案id
        Map<String, Object> paramMap = new HashMap();
        String filter = " a.VIOLATIONFILE_ID = '" + violationfileId + "'";
        paramMap.put("filter", filter);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("GetSrpworkViolationrules", paramMap);
        return cntResult;
    }

    //作业单位准入变更信息
    @Override
    public List<Map> getSrpWorkSiteinfochange(String provinceCode , String basicinfoContractor) {
       Map<String, Object> paramMap = new HashMap();
        List<Map> cntResult=new ArrayList<>();
        //企业名称  省编码
        String filter = " b.BASICINFO_CONTRACTOR = '" + basicinfoContractor + "'" + " AND a.PROVINCE_CODE  = '" + provinceCode + "'";
        paramMap.put("filter", filter);
        cntResult= this.jDataAccess.queryWithParamById("GetSrpWorkSiteinfochange", paramMap);
        return cntResult;
    }


    //准入情况
    @Override
    public List<Map> getSrpWorkSiteinfoAll(String basicinfoContractor ,String siteinfoId) {
        List<Map> cntResult =new ArrayList<>();
        Map<String, Object> paramMap = new HashMap();
           String filter = " a.SITEINFO_ID = '" + siteinfoId + "'" + " AND a.BASICINFO_CONTRACTOR = '" + basicinfoContractor + "'";
           paramMap.put("filter", filter);
           cntResult = this.jDataAccess.queryWithParamById("GetSrpWorkSiteinfoAll", paramMap);
        return cntResult;
    }


    //工作负责人\外包人员
    @Override
    public Map<String ,Object> getSrpWorksiteworker(String jsonStr) {//企业社会信用代码
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
//        jsonStr = rowData.getString("dataStr");
        Map<String, Object> paramMap = createWorkSiteinfoFilter(jsonStr);
//        MapPageResult resultObj = this.jDataAccess.queryWithParamById("GetSrpWorksiteworker", paramMap, pageSize, pageNo);
////        List<Map> resultList = resultObj.getRows();

        List<Map> resultList = this.jDataAccess.queryWithParamById("GetSrpWorksiteworker", paramMap);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        Map<String, Object> cntParamMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("GetSrpWorksiteworkerCount", cntParamMap);
        resultMap.put("listSize", cntResult.get(0).get("worksiteinfo_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("worksiteinfo_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;



        //        Map<String, Object> paramMap = new HashMap();
//        List<Map> cntResult=new ArrayList<>();
//        if ("1".equals(workerType)) {//工作负责人
//            String filter = " THREEKINDS_IDENTIFICATION ='01' AND a.SITEINFO_ID = '" + siteinfoId + "'" + " AND a.PROVINCE_CODE = '" + provinceCode + "'";
//            paramMap.put("filter", filter);
//        } else {//外包
//            String filter = " WORKER_NATURE ='03' AND a.SITEINFO_ID = '" + siteinfoId + "'" + " AND a.PROVINCE_CODE = '" + provinceCode + "'";
//            paramMap.put("filter", filter);
//        }
//        cntResult = this.jDataAccess.queryWithParamById("GetSrpWorksiteworker", paramMap);
//        return cntResult;
    }

    /**
     * 删除企业信息列表数据
     * @param jsonStr
     * @return
     */
    @Override
    public String deleteEnterprise(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        JSONArray ids = rowData.getJSONArray("ids");
        String s = ids.toString();
        String substring = s.substring(1, s.length() - 1);
        Map map = new HashMap();
        map.put("filter",substring);
        int deleteEnterprise = jDataAccess.updateWithParamById("deleteEnterprise", map);
        return "删除了"+deleteEnterprise+"条数据";
    }


    //获取字典定义
    @Override
    public Map<String, List<JSONObject>> getSrpRiskSysTab() {
        Map<String, List<JSONObject>> resultMap = new HashMap<>();

        List<JSONObject> orgNature = new ArrayList<>();//企业性质
        List<JSONObject> datareportOrg = new ArrayList<>();//所属省份


        Map<String, Object> paramMap = createSrpRiskSysTabFilter(null);
        List<Map> resultList = this.jDataAccess.queryWithParamById("GetSrpRiskSysTab", paramMap);
        resultList.stream().forEach(map -> {
            JSONObject jsonObject = JSON.parseObject("{'id':'" + map.get("column_type_code") + "','text':'" + map.get("column_type_name") + "'}");
            if ("ORG_NATURE".equals(map.get("column_code"))) {

                orgNature.add(jsonObject);
            }
            if ("DATAREPORT_ORG".equals(map.get("column_code"))) {
                datareportOrg.add(jsonObject);
            }


        });
        resultMap.put("orgNature", orgNature);
        resultMap.put("voltageLevel", datareportOrg);
        return resultMap;
    }


    //省份和单位级联查询
    @Override
    public Map<String, List<JSONObject>> getDatafillOrg(String datafillOrgId) {
        Map<String, List<JSONObject>> resultMap = new HashMap<>();
        List<JSONObject> datafillOrg = new ArrayList<>();//企业性质

        Map<String, Object> paramMap = createSrpRiskSysTabFilter(datafillOrgId);
        List<Map> resultList = this.jDataAccess.queryWithParamById("GetSrpRiskSysTab", paramMap);
        resultList.stream().forEach(map -> {
            JSONObject jsonObject = JSON.parseObject("{'id':'" + map.get("column_type_code") + "','text':'" + map.get("column_type_name") + "'}");

            datafillOrg.add(jsonObject);


        });
        resultMap.put("datafillOrg", datafillOrg);

        return resultMap;
    }


//-------------------------------------------------------------------------------------------------------------------------------------------

    //全网准入单位情况数据统计工具方法
    private Map<String, Object> createCommonFilter(int strType) {
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1");
        if (strType == 1) {//  当前有效准入单位总数：
            commonFilter.append(" AND ACCESS_STATE ='01' ");
        } else if (strType == 2) {//产业单位：
            commonFilter.append(" AND ORG_NATURE in('09') ");
        } else if (strType == 3) {//外包
            commonFilter.append(" AND ORG_NATURE in('04') AND ACCESS_STATE ='01'");
        } else if (strType == 4) {//本月新增准入单位数
            commonFilter.append(" AND ACCESS_STATE ='01'AND DATE_FORMAT( CREATE_TIME, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' ) ");
        } else if (strType == 5) {//本月黑名单数 负面清单数
            commonFilter.append(" AND DATE_FORMAT( RELEASE_DATE, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )");
        }

        Map<String, Object> paramMap = new HashMap();
        String filter = commonFilter.toString();
        paramMap.put("filter", filter);
        return paramMap;
    }


    //时间查询串拼接
    private Map<String, Object> createTimeFilter(int timeType, String timeStr, String beginTime, String endTime) {
        //拼装sql
        StringBuilder commonFilter = new StringBuilder("1 = 1");
        if (timeType == 0) {//今日
            commonFilter.append(" AND to_days(" + timeStr + ") = to_days(now()) ");
        } else if (timeType == 1) {//明日
            commonFilter.append(" AND TO_DAYS(" + timeStr + ") - TO_DAYS(NOW( )) >= 1");
        } else if (timeType == 2) {//本周
            commonFilter.append(" AND YEARWEEK(date_format(" + timeStr + ",'%Y-%m-%d'),1) = YEARWEEK(now(),1)");
        } else if (timeType == 3) {//本月
            commonFilter.append(" AND DATE_FORMAT( " + timeStr + ", '%Y%m' ) >= DATE_FORMAT( CURDATE( ) , '%Y%m' )");
        } else if (timeType == 4) {//本年
            commonFilter.append(" AND YEAR(" + timeStr + ") >= YEAR(NOW())");
        } else if (timeType == 5) {//自定义时间段
            commonFilter.append(" AND   ACCESS_STATE ='01'");
        }

        Map<String, Object> paramMap = new HashMap();
        String filter = commonFilter.toString();
        paramMap.put("filter", filter);
        return paramMap;
    }


    //企业信息查询条件拼接
    private Map<String, Object> createWorkSiteinfoFilter(String jsonStr) {
        JSONObject page = JSONObject.parseObject(jsonStr);
        JSONObject rowData = JSONObject.parseObject(page.getString("dataStr"));

        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        StringBuilder pageFilter = new StringBuilder(" 1=1 ");
        StringBuilder limitFilter = new StringBuilder();

        List jsonList = new ArrayList();
        for (Object key :rowData.keySet()) {
            if(!"".equals(rowData.get(key))){
                jsonList.add(rowData.get(key));
            }
        }
        if (jsonList.size() == 0){  //初始化刷新全局

            pageFilter.append(" ORDER BY CREATE_TIME DESC LIMIT " + page.getString("pageNo") + "," + page.getString("pageSize"));
        }else {

            if (rowData.getString("siteinfoId") != null && !"".equals(rowData.getString("siteinfoId"))) {//企业id
                commonFilter.append(" AND SITEINFO_ID = '" + rowData.getString("siteinfoId") + "'");
            }

            if (rowData.getString("workerType") != null && !"".equals(rowData.getString("workerType"))) {//企业id
                if ("1".equals(rowData.getString("workerType"))) {//工作负责人
                    commonFilter.append( " AND THREEKINDS_IDENTIFICATION ='01' ");
                } else {//外包
                    commonFilter.append( " AND WORKER_NATURE ='03' ");
                }
                if (rowData.getString("provinceCode") != null && !"".equals(rowData.getString("provinceCode"))) {//企业id
                    commonFilter.append(" AND a.PROVINCE_CODE = '" + rowData.getString("provinceCode") + "'");
                }
            }

            if (rowData.getString("basicinfoContractor") != null && !"".equals(rowData.getString("basicinfoContractor"))) {//企业名称
                commonFilter.append(" AND BASICINFO_CONTRACTOR like '%" + rowData.getString("basicinfoContractor") + "%'");
            }
            if (rowData.getString("orgNature") != null && !"".equals(rowData.getString("orgNature"))) {//企业性质
                commonFilter.append(" AND ORG_NATURE ='" + rowData.getString("orgNature") + "'");
            }
            if (rowData.getString("datareportOrgId") != null && !"".equals(rowData.getString("datareportOrgId"))) {//所属省份id
                commonFilter.append(" AND PROVINCE_CODE = '" + rowData.getString("datareportOrgId") + "'");
            }
            if (rowData.getString("isEnableEnter") != null && !"".equals(rowData.getString("isEnableEnter"))) {//准入状态:
                if("03".equals(rowData.getString("isEnableEnter"))){
                    commonFilter.append("AND (CASE WHEN ACCESS_STATE IS NULL OR ACCESS_STATE NOT IN ( '01','02' ) THEN 1 ELSE NULL END )=1");
                }else {
                    commonFilter.append("AND ACCESS_STATE = '" + rowData.getString("isEnableEnter") + "' ");
                }
            }
            if (rowData.getString("datafillOrgId") != null && !"".equals(rowData.getString("datafillOrgId"))) {//联系单位
                commonFilter.append(" AND ACCEPT_ORG like'%" + rowData.getString("datafillOrgId") + "%'");
            }

            //
            limitFilter.append("  LIMIT " + Integer.parseInt(page.getString("pageNo")) + "," + Integer.parseInt(page.getString("pageSize")));

        }



        Map<String, Object> paramMap = new HashMap();
        String  pageNow = pageFilter.toString();
        paramMap.put("page" ,pageNow);
        String filter = commonFilter.toString();
        paramMap.put("filter", filter);
        String limit = limitFilter.toString();
        paramMap.put("limit", limit);

        return paramMap;
    }


    //页面下拉框选项
    private Map<String, Object> createSrpRiskSysTabFilter(String datafillOrgId) {
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        if (null != datafillOrgId && !"".equals(datafillOrgId)) {
            commonFilter.append(" AND column_code = '" + datafillOrgId + "'");
        }


        Map<String, Object> paramMap = new HashMap();
        String filter = commonFilter.toString();
        paramMap.put("filter", filter);
        return paramMap;
    }


}
