package com.raysdata.enterprisesadmittanceserver.servrce.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import com.raysdata.enterprisesadmittanceserver.entity.PersonnePoi;
import com.raysdata.enterprisesadmittanceserver.servrce.personnelInformationService;
import com.raysdata.enterprisesadmittanceserver.util.DaoBySql;
import com.raysdata.enterprisesadmittanceserver.util.ExcelUtils;
import com.raysdata.enterprisesadmittanceserver.util.IDCardUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class personnelInformationServiceImpl implements personnelInformationService {

    @Autowired
    private JdbcDataAccess jDataAccess;


    /**
     * 获取登录认证token
     * @param token
     * @return
     */
    @Override
    public List<Map> loginToken(String token) {
        Map<String, Object> paramMap = new HashMap();
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        commonFilter.append(" AND USER_TOKEN = '" + token + "'");
        paramMap.put("commonFilter", commonFilter);
        List<Map> PersoneToken = this.jDataAccess.queryWithParamById("loginToken", paramMap);
        return PersoneToken;
    }



    /**
     *人员模板导出
     * @return
     */
    @Override
    public Object downloadPerExcel(HttpServletRequest request, HttpServletResponse response) throws NoSuchFieldException, IllegalAccessException, IOException {
        Map<String, Object> paramMap = new HashMap();
        //拼装sql
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        paramMap.put("commonFilter", commonFilter);
        List<Map> allPersonneCount = this.jDataAccess.queryWithParamById("getPersonneCount", paramMap);

        //将List集合中的map对象转为List<对象>
        List list1 = DaoBySql.mapToEntyList(allPersonneCount, PersonnePoi.class);

        //反射和注解导出
        XSSFWorkbook workbooks = ExcelUtils.createExcel(list1);

        String fileName = "人员信息.xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        workbooks.write(response.getOutputStream());
        workbooks.close();
        return false;
    }


    //查询：作业人员总数，业主人数，集体人数，外包人数，  当前有效准入人数，本月新增准入人数，本月取消资格人数。
    @Override
    public List<Map> getTotalStaff(String Month) {
        Map<String, Object> paramMap = new HashMap();
        String filter = "'" + Month + "%'";
        paramMap.put("filter", filter);
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getTotalStaff", paramMap);
        return allEnterpriseCnt;
    }

    //根据地区，人员性质  查询  业主，集体，外包 人数
    @Override
    public List<Map> getRegionalMonthAdd() {
        Map<String, Object> paramMap = new HashMap();
        //自动设置当前年月
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String filter = "'" + df.format(new Date()) + "%'";
        paramMap.put("filter", filter);
        //查询返回相应数据
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getRegionalMonthAdd", paramMap);
        //向人员临时表增加数据前先清空表
        this.jDataAccess.updateWithParamById("deletTable", paramMap);
        //向人员临时表添加数据  循环结果集放入新对象
        HashSet keySet = new HashSet();

        for (Map map : allEnterpriseCnt) {
            map.put("time_now" , df.format(new Date()));
            map.put("id" ,UUID.randomUUID().toString().replaceAll("-", ""));
            //key对应数据库所有字段
            List valueList = new ArrayList();
            StringBuilder value = new StringBuilder();
            for (Object key : map.keySet()) {

                keySet.add(key);
                valueList.add(map.get(key));
            }

            //转换单独省份list集合为字符串
            for(int i=0;i<valueList.size();i++){
                    value.append("'" + valueList.get(i) + "'");
                    value.append(",");
            }
            String s = keySet.toString();
            String ss = s.substring(1);
            paramMap.put("keysFilter" ,ss.substring(0,ss.length()-1));

            String v = value.toString();
            paramMap.put("valuesFilter" ,v.substring(0,v.length()-1));
            this.jDataAccess.updateWithParamById("addTable", paramMap);
        }
        return null;
    }

    @Override
    public List<Map> getRegionalMonth(String Month) {
        Map<String, Object> paramMap = new HashMap();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
        String i = df.format(new Date());
        String filter = "'" + Month + "%'";
        paramMap.put("filter", filter);
        //查询返回相应数据
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getRegionalMonth", paramMap);
        return allEnterpriseCnt;
    }

    @Override
    public Map<String ,Object> getPersonnelIn(String jsonStr){
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
//        jsonStr = rowData.getString("dataStr");
        Map<String, Object> paramMap = createWorkSiteinfoFilter(jsonStr);

        List<Map> resultList = this.jDataAccess.queryWithParamById("getPersonnelInTotal", paramMap);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        Map<String, Object> cntParamMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("getPersonnelInTotalCount", paramMap);
        resultMap.put("listSize", cntResult.get(0).get("worksiteinfo_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("worksiteinfo_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;

    }

    private Map<String, Object> createWorkSiteinfoFilter(String jsonStr) {

        JSONObject page = JSONObject.parseObject(jsonStr);
        JSONObject rowData = JSONObject.parseObject(page.getString("dataStr"));
        //
        StringBuilder commonFilter = new StringBuilder(" 1 = 1 ");
        StringBuilder pageFilter = new StringBuilder(" 1=1 ");
        StringBuilder limitFilter = new StringBuilder();
        //
        List jsonList = new ArrayList();
        for (Object key :rowData.keySet()) {
            if(!"".equals(rowData.get(key))){
                jsonList.add(rowData.get(key));
            }
        }
        if (jsonList.size() == 0){  //人员列表初始默认查询北京

            commonFilter.append(" AND PROVINCE_CODE = '110000' ");
            pageFilter.append(" AND PROVINCE_CODE = '110000' ORDER BY CREATE_TIME DESC LIMIT " + page.getString("pageNo") + "," + page.getString("pageSize"));

        }else {
            if (rowData.getString("siteworkerinfoId") != null && !"".equals(rowData.getString("siteworkerinfoId"))) {
                commonFilter.append(" AND SITEWORKERINFO_ID = '" + rowData.getString("siteworkerinfoId") + "'");
            }
            if (rowData.getString("Name") != null && !"".equals(rowData.getString("Name"))) {//姓名
                commonFilter.append(" AND NAME like '%" + rowData.getString("Name") + "%'");
            }
            if (rowData.getString("OrgId") != null && !"".equals(rowData.getString("OrgId"))) {//单位
                commonFilter.append(" AND ORG_NAME like'%" + rowData.getString("OrgId") + "%'");
            }
            if (rowData.getString("DatareportOrg") != null && !"".equals(rowData.getString("DatareportOrg"))) {//所属省份id
                commonFilter.append(" AND PROVINCE_CODE = '" + rowData.getString("DatareportOrg") + "'");
            }
            if (rowData.getString("profession") != null && !"".equals(rowData.getString("profession"))) {//专业
                commonFilter.append("and PROFESSION = '" + rowData.getString("profession") + "' ");
            }
            if (rowData.getString("WorkerNature") != null && !"".equals(rowData.getString("WorkerNature"))) {//人员性质
                commonFilter.append(" AND WORKER_NATURE ='" + rowData.getString("WorkerNature") + "'");
            }
            if (rowData.getString("ThreekindsIdentification") != null && !"".equals(rowData.getString("ThreekindsIdentification"))) {//三种人标识
                commonFilter.append(" AND THREEKINDS_IDENTIFICATION ='" + rowData.getString("ThreekindsIdentification") + "'");
            }
            if (rowData.getString("AccessState") != null && !"".equals(rowData.getString("AccessState"))) {//联系单位
                if("03".equals(rowData.getString("AccessState"))){
                    commonFilter.append(" AND (CASE WHEN ACCESS_STATE IS NULL OR ACCESS_STATE NOT IN ( '01','02' ) THEN 1 ELSE NULL END )=1");
                }else {
                    commonFilter.append(" AND ACCESS_STATE ='" + rowData.getString("AccessState") + "'");
                }
            }
            if (rowData.getString("starttime") != null && !"".equals(rowData.getString("starttime"))) {//开始时间-结束时间
                commonFilter.append(" AND a.CREATE_TIME>='" + rowData.getString("starttime") + "'");
            }

            if (rowData.getString("begintime") != null && !"".equals(rowData.getString("begintime"))) {//开始时间-结束时间
                commonFilter.append(" AND a.CREATE_TIME<='" + rowData.getString("begintime") + "'");
            }

            //拼装limit分页
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


    //省份小代码
    @Override
    public List<Map> getDatareportOrg() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getDatareportOrg", null);
        return allEnterpriseCnt;
    }

    //根据省份查询单位
    @Override
    public List<Map> getOrgId(String OrgId) {
        Map<String, Object> paramMap = new HashMap();
        String filter = "column_code='" + OrgId + "'";
        paramMap.put("filter", filter);
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getOrgId", paramMap);
        return allEnterpriseCnt;
    }

    //专业小代码
    @Override
    public List<Map> getProfession() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getProfession", null);
        return allEnterpriseCnt;
    }

    //企业性质小代码
    @Override
    public List<Map> getOrgNature() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getOrgNature", null);
        return allEnterpriseCnt;
    }

    //三种人标识小代码
    @Override
    public List<Map> getThreekindsIdentification() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getThreekindsIdentification", null);
        return allEnterpriseCnt;
    }

    //是否有效小代码
    @Override
    public List<Map> getAccessState() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getAccessState", null);
        return allEnterpriseCnt;
    }

    //性别
    @Override
    public List<Map> getSex() {
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getSex", null);
        return allEnterpriseCnt;
    }

    //根据id查询人员
    @Override
    public List<Map> getOrgids(String siteworkerinfoId) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", "'" + siteworkerinfoId + "'");
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getOrgids", paramMap);
        for (Map map: allEnterpriseCnt) {
            String age = (String) map.get("id_card");
            //如果当前人员有年龄则直接取age
            if (map.get("age") != null && !map.get("age").equals("")){
                map.put("age" , map.get("age"));
            }else if ( age != null && age.length() >= 15){
                //如果身份证号不为空 并且数据质量合格 则调用工具包计算数据并返回
                map.put("age" , IDCardUtil.getAge((String) map.get("id_card")));
            }
        }
        return allEnterpriseCnt;
    }


    //根据id查询考试成绩
    @Override
    public List<Map> getExamination(String siteworkerinfoId) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", "'" + siteworkerinfoId + "'");
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getExamination", paramMap);
        return allEnterpriseCnt;
    }

    //根据id查询违章记录
    @Override
    public List<Map> getViolationfile(String siteworkerinfoId) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", "'" + siteworkerinfoId + "'");
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getViolationfile", paramMap);
        return allEnterpriseCnt;
    }

    /**
     * 根据人员id查询作业计划
     * @param
     * @return
     */
    @Override
    public Map<String ,Object> getSrpWorkePlanDay(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        int pageSize = Integer.parseInt(rowData.getString("pageSize"));
        int pageNo = Integer.parseInt(rowData.getString("pageNo"));
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> resultList = this.jDataAccess.queryWithParamById("getSrpWorkePlanDay", paramMap);
        resultMap.put("resultList", resultList);
        resultMap.put("resultListCount", resultList.size());

        Map<String, Object> cntParamMap = createWorkSiteinfoFilter(jsonStr);
        List<Map> cntResult = this.jDataAccess.queryWithParamById("getSrpWorkePlanDayCot", cntParamMap);
        resultMap.put("listSize", cntResult.get(0).get("worksiteinfo_cnt"));
        Double pageCountDouble = Double.parseDouble(cntResult.get(0).get("worksiteinfo_cnt").toString()) / pageSize;
        Double pageCount = Math.ceil(pageCountDouble);
        resultMap.put("pageCount", pageCount);
        return resultMap;



//        Map<String, Object> paramMap = new HashMap();
//        paramMap.put("filter", "'" + siteworkerinfoId + "'");
//        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getSrpWorkePlanDay", paramMap);
//        return allEnterpriseCnt;
    }

    /**
     * 人员头像展示接口
     * @param siteworkerinfoId
     * @return
     */
    @Override
    public List<Map> getTmxAttachment(String siteworkerinfoId) {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("filter", "'" + siteworkerinfoId + "'");
            List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getTmxAttachment", paramMap);
            return allEnterpriseCnt;

    }


    //人员变更记录
    @Override
    public List<Map> getDatareport(String siteworkerinfoId) {
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("filter", "'" + siteworkerinfoId + "'");
        List<Map> allEnterpriseCnt = this.jDataAccess.queryWithParamById("getDatareport", paramMap);
        return allEnterpriseCnt;
    }

    /**
     * 删除人员列表数据
     * @param jsonStr
     * @return
     */
    @Override
    public String deletePersonnel(String jsonStr) {
        JSONObject rowData = JSONObject.parseObject(jsonStr);
        JSONArray ids = rowData.getJSONArray("ids");
        String s = ids.toString();
        String substring = s.substring(1, s.length() - 1);
        Map map = new HashMap();
        map.put("filter",substring);
        int deletePersonnel = jDataAccess.updateWithParamById("deletePersonnel", map);
        return "删除了"+deletePersonnel+"条数据";
    }

}
