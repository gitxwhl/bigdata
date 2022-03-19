package com.raysdata.enterprisesadmittanceserver.servrce.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import com.raysdata.enterprisesadmittanceserver.servrce.examinationService;
import com.raysdata.enterprisesadmittanceserver.servrce.personnelInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class examinationServiceImpl implements examinationService {

    @Autowired
    private JdbcDataAccess jDataAccess;

    @Override
    public List<Map> getNetworkWide() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getNetworkWide", null);
        return allEnterpriseCnt;
    }

    @Override
    public List<Map> getUnitTestData(String Month) {
        Map<String, Object> paramMap = new HashMap();
        String filter = "'" + Month + "%'";
        paramMap.put("filter", filter);
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getUnitTestData", paramMap);
        return allEnterpriseCnt;
    }

    @Override
    public List<Map> getDatareportOrg() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getDatareportOrg", null);
        return allEnterpriseCnt;
    }

    @Override
    public List<Map> getExaminationStatus() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getExaminationStatus", null);
        return allEnterpriseCnt;
    }

    //考场小代码
    @Override
    public List<Map> getTestroomName() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getTestroomName", null);
        return allEnterpriseCnt;
    }

    //查询条件：省份  datareport_org_id，考场编号 testroom_no，考试名称 test_name，考试状态 test_state，开始时间 starttime，结束时间 begintime
    @Override
    public List<Map> getExaminationList(String Page, String Number, String datareport_org_id, String testroom_no, String test_name, String test_state, String starttime, String begintime) {
        Map<String, Object> paramMap = new HashMap();

        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        if (datareport_org_id != null && !"".equals(datareport_org_id)) {//省份
            commonFilter.append(" AND PROVINCE_CODE = '" + datareport_org_id + "'");
        }
        if (testroom_no != null && !"".equals(testroom_no)) {//考场编号
            commonFilter.append(" AND TESTROOM_NO ='" + testroom_no + "'");
        }
        if (test_name != null && !"".equals(test_name)) {//考试名称
            commonFilter.append(" AND TEST_NAME like '%" + test_name + "%'");
        }
        if (test_state != null && !"".equals(test_state)) {//考试状态
            commonFilter.append("and TEST_STATE = '" + test_state + "' ");
        }
        if (starttime != null && !"".equals(starttime)) {//开始时间-结束时间
            commonFilter.append("  AND SUBSTRING(TEST_DATE,1,16)>STR_TO_DATE('" + starttime + "' ,'%Y-%m-%d')");
        }

        if (begintime != null && !"".equals(begintime)) {//开始时间-结束时间
            commonFilter.append(" AND SUBSTRING(TEST_DATE,1,16)<STR_TO_DATE('" + begintime + "' ,'%Y-%m-%d')");
        }
        commonFilter.append("and ( DELETELOGO is null or DELETELOGO != '1' )");
        commonFilter.append(" GROUP BY testroom_name,PROVINCE_CODE,column_type_name,testroom_no, starttime,begintime,test_state");
        paramMap.put("commonFilter", commonFilter);

        List<Map> allEnterpriseCntTotal = this.jDataAccess.queryWithParamById("getExaminationList", paramMap);
        Integer Pag = Integer.valueOf(Page);
        Integer Numb = Integer.valueOf(Number);
        //页码/页数  （页码-1）*每页数量, 每页数量 ;
        commonFilter.append(" LIMIT " + (Pag-1)*Numb + "," + " " + Numb);
        paramMap.clear();
        paramMap.put("commonFilter", commonFilter);
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getExaminationList", paramMap);
        Map map = new HashMap();
        map.put("resultListCount", Number);
        map.put("pageCount", Page);
        map.put("listSize", allEnterpriseCntTotal.size());
        allEnterpriseCnt.add(0, map);
        return allEnterpriseCnt;
    }

    @Override
    public List<Map> getEssentialInformation(String testroom_no) {
        Map<String, Object> paramMap = new HashMap();
        String filter = "'" + testroom_no + "'";
        paramMap.put("filter", filter);
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getEssentialInformation", paramMap);
        return allEnterpriseCnt;
    }

    @Override
    public List<Map> getPersonnelList(String Page, String Number, String testroom_no, String test_state, String starttime, String begintime) {
        Map<String, Object> paramMap = new HashMap();
        Integer Pag = Integer.valueOf(Page);
        Integer Numb = Integer.valueOf(Number);
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");

		if (testroom_no != null && !"".equals(testroom_no)) {
            commonFilter.append(" AND a.testroom_no=  '" + testroom_no + "'");
        }
        if (starttime != null && !"".equals(starttime)) {//开始时间-结束时间
            commonFilter.append("  AND SUBSTRING(TEST_DATE,1,16)>STR_TO_DATE('" + starttime + "' ,'%Y-%m-%d')");
        }
        if (begintime != null && !"".equals(begintime)) {//开始时间-结束时间
            commonFilter.append(" AND SUBSTRING(TEST_DATE,1,16)<STR_TO_DATE('" + begintime + "' ,'%Y-%m-%d')");
        }
        if (test_state != null && !"".equals(test_state)) {//考试状态
            commonFilter.append("and TEST_STATE = '" + test_state + "' ");
        }
        commonFilter.append("and ( a.DELETELOGO is null or a.DELETELOGO != '1' )");
        

        paramMap.put("filter", commonFilter);
        List<Map> allEnterpriseCntTotal = this.jDataAccess.queryWithParamById("getPersonnelList", paramMap);
        commonFilter.append(" LIMIT " + Pag * Numb + "," + " " + Numb);
        paramMap.put("filter", commonFilter);
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getPersonnelList", paramMap);
        Map map = new HashMap();
        map.put("resultListCount", Number);
        map.put("pageCount", Page);
        map.put("listSize", allEnterpriseCntTotal.size());
        allEnterpriseCnt.add(0, map);
        return allEnterpriseCnt;
    }

    /**
     * 删除考场列表数据
     * @param jsonStr
     * @return
     */
    @Override
    public String deleteExamination(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        JSONArray ids = rowData.getJSONArray("ids");
        String s = ids.toString();
        String substring = s.substring(1, s.length() - 1);
        Map map = new HashMap();
        map.put("filter",substring);
        int deleteExamination = jDataAccess.updateWithParamById("deleteExamination", map);
        return "删除了"+deleteExamination+"条数据";
    }


    @Override
    public List<Map> getProvincialexamination(String datareport_org_id) {
        Map<String, Object> paramMap = new HashMap();
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        if (datareport_org_id != null && !"".equals(datareport_org_id)) {
            commonFilter.append(" AND PROVINCE_CODE = '" + datareport_org_id + "'");
            commonFilter.append("AND TESTROOM_NAME IS NOT NULL");
        }else{
            commonFilter.append("AND TESTROOM_NAME IS NOT NULL");
        }
        paramMap.put("commonFilter", commonFilter);
        List<Map> allEnterpriseCntTotal = this.jDataAccess.queryWithParamById("getProvincialexamination", paramMap);
        return allEnterpriseCntTotal;
    }

}
