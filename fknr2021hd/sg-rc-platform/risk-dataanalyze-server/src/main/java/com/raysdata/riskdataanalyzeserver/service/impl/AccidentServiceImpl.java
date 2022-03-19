package com.raysdata.riskdataanalyzeserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.raysdata.riskdataanalyzeserver.bean.SrpWorkKdtbWorkplandayWithBLOBs;
import com.raysdata.riskdataanalyzeserver.mapper.SrpWorkKdtbWorkplandayMapper;
import com.raysdata.riskdataanalyzeserver.service.AccidentService;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccidentServiceImpl implements AccidentService {

//    @Autowired
//    private AccidentDao accidentDao;

    @Autowired
    private SrpWorkKdtbWorkplandayMapper mapper;

    /*
     * 外包单位风险画像列表
     * paramJson:{"page":"", "size":"","params":{"datareportOrg":"","constructionOrg":""}}
     * */
    @Override
    public PageBean<Map<String, Object>> getRiskPortrait(String paramJson) {
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        JSONObject params = rowData.getJSONObject("params");
        String datareportOrg = JSONObject.parseObject(paramJson).getJSONObject("params").getString("datareportOrg");
        String constructionOrg = JSONObject.parseObject(paramJson).getJSONObject("params").getString("constructionOrg");


        if(page <= 0){
            throw new RuntimeException("当前页数必须大于1");
        }else if(size <= 0){
            throw new RuntimeException("每页大小必须大于1");
        }else {
//            StringBuilder param = new StringBuilder();
//            if(ObjectUtils.isNotEmpty(datareportOrg)){
//                param.append(" and DATAREPORT_ORG = '").append(datareportOrg).append("'");
//            }
//            if(ObjectUtils.isNotEmpty(constructionOrg)){
//                param.append(" and CONSTRUCTION_ORG = '").append(constructionOrg).append("'");
//            }
//            int totalSize = accidentDao.getCount(Const.SQL_DATA_GETRISKPORTRAITCNT.replaceAll("%param%", param.toString()));
            SrpWorkKdtbWorkplandayWithBLOBs bs = new SrpWorkKdtbWorkplandayWithBLOBs();
            bs.setDatareportOrg(datareportOrg);
            bs.setConstructionOrg(constructionOrg);
            bs.setPage(page);
            bs.setLimit(size);
            int totalSize = mapper.getCount(bs);
            if(totalSize == 0){
                return PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
            }else {
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                List<Map<String, Object>> list = accidentDao.getList(Const.SQL_DATA_GETRISKPORTRAITLIST.replaceAll("%param%",param.toString()));
                List<Map<String, Object>> list = mapper.getList(bs);

                for (Map<String, Object> stringObjectMap : list) {
                    Object id = stringObjectMap.get("CONSTRUCTION_ORG_ID");
//                    param = new StringBuilder();
//                    param.append("and CONTRACTOR_ORG_ID = '").append(id).append("'");
                    //查询故障数
//                    Integer cnt1 = accidentDao.getCount(Const.SQL_DATA_ACCIDENTCNT.replaceAll("%param%", param.toString()));
//                    stringObjectMap.put("accidentCount",cnt1);

                    Map<String,Object> map =new HashMap<>();
                    map.put("contractorOrgId", id);
                    Integer cnt1 = mapper.getCount1(map);
                    stringObjectMap.put("accidentCount",cnt1);

//                    param = new StringBuilder();
//                    param.append("and VIOLATION_ORG_ID = '").append(id).append("'");
//                    //查询违章数
//                    Integer cnt2 = accidentDao.getCount(Const.SQL_DATA_VIOLATIONCNT.replaceAll("%param%", param.toString()));
//                    stringObjectMap.put("violationCount",cnt2);

                    Map<String,Object> map2 =new HashMap<>();
//                    map.put("violationOrgId", id);
                        Integer cnt2 = mapper.getCount2(id.toString());
                        stringObjectMap.put("violationCount",cnt2);
//                    param = new StringBuilder();
//                    param.append("and SITEINFO_ID = '").append(id).append("'");
//                    //查询负面清单数
//                    Integer cnt3 = accidentDao.getCount(Const.SQL_DATA_NEGATIVELISTCNT.replaceAll("%param%", param.toString()));
//                    stringObjectMap.put("negativeListCount",cnt3);

                    Map<String,Object> map3 =new HashMap<>();
//                    map.put("siteinfoId", id);
                    Integer cnt3 = mapper.getCount3(id.toString());
                    stringObjectMap.put("negativeListCount",cnt3);

                    //查询黑名单数
//                    Integer cnt4 = accidentDao.getCount(Const.SQL_DATA_BLACKLISTCNT.replaceAll("%param%", param.toString()));
                    Integer cnt4 = mapper.getCount4(id.toString());
                    stringObjectMap.put("blackListCount",cnt4);
                }
                return PageBean.<Map<String, Object>>builder()
                        .content(list)
                        .elementTotalSize(list.size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
            }

        }
    }

    /*
     *单位基本情况
     * paramJson:{"page":"", "size":"", "id":""}
     * */
    @Override
    public Map<String, List> basicInformation(String paramJson) {
        Map<String,List> map = new LinkedHashMap<>();
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
        String id = JSONObject.parseObject(paramJson).getString("id");
        StringBuilder param1 = new StringBuilder();
        param1.append("and SITEINFO_ID = '").append(id).append("'");
//        List<Map<String, Object>> list1 = accidentDao.getList(Const.SQL_DATA_OUTSOURCINGUNIT.replaceAll("%param%", param1.toString()));
        try {
            List<Map<String, Object>> list1 = mapper.getList2(id);
            map.put("OUTSOURCINGUNIT",list1);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if (page <= 0) {
            throw new RuntimeException("当前页数必须大于1");
        } else if (size <= 0) {
            throw new RuntimeException("每页大小必须大于1");
        } else {
            StringBuilder param = new StringBuilder();
            param.append("and CONTRACTOR_ORG_ID = '").append(id).append("'");
//            int totalSize = this.accidentDao.getCount(Const.SQL_DATA_PROJECTMANAGEMENTCNT.replaceAll("%param%", param.toString()));
            int totalSize = this.mapper.getCount5(id);
            List list2 = new ArrayList();
            if (totalSize == 0) {
                PageBean<Map<String, Object>> build = PageBean.<Map<String, Object>>builder()
                        .content(new ArrayList<>())
                        .elementTotalSize(0)
                        .page(0)
                        .size(0)
                        .totalPage(0)
                        .totalSize(0)
                        .build();
                list2.add(build);
            } else {
//                int totalPage = totalSize % size == 0 ? totalSize / size : totalSize / size + 1;
                int offset = (page - 1) * size;
                param.append(" limit " + size + " offset " + offset);
                Map paramMap=new HashMap();
                paramMap.put("limit", size);
                paramMap.put("offset",offset );
                paramMap.put("id",id );
//                List<Map<String, Object>> projectList = this.accidentDao.getList(Const.SQL_DATA_PROJECTMANAGEMENT.replaceAll("%param%", param.toString()));
                PageBean<Map<String, Object>> build = PageBean.<Map<String, Object>>builder()
//                        .content(this.accidentDao.getList(Const.SQL_DATA_PROJECTMANAGEMENT.replaceAll("%param%", param.toString())))
//                        .elementTotalSize(this.accidentDao.getList(Const.SQL_DATA_PROJECTMANAGEMENT.replaceAll("%param%", param.toString())).size())
                        .content(this.mapper.getList3(paramMap))
                        .elementTotalSize(this.mapper.getList3(paramMap).size())
                        .totalSize(totalSize)
                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
                        .page(page)
                        .size(size)
                        .build();
                list2.add(build);
            }
            map.put("PROJECTMANAGEMENT",list2);
        }
        return map;
    }


    /*
     *单位安全资信
     * paramJson:{"id":""}
     * */
    @Override
    public Map<String, List> securityInformation(String paramJson) {
        Map<String,List> map = new LinkedHashMap<>();
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String id = JSONObject.parseObject(paramJson).getString("id");
        StringBuilder param = new StringBuilder();
        param.append("and VIOLATION_ORG_ID = '").append(id).append("'");
        //查询一般违章、严重违章的数量
//        List<Map<String, Object>> list1 = accidentDao.getList(Const.SQL_DATA_VIOLATIONCNTBYLEVEL.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list1 = mapper.getList4(id);
        map.put("violationCountByLevel",list1);

        //查询本年违章情况
//        List<Map<String, Object>> list3 = accidentDao.getList(Const.SQL_DATA_VIOLATION.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list3 = mapper.getList5(id);
        map.put("violationThisYear",list3);

        List list2 = new ArrayList();
        Map<String,Integer> map1 = new HashMap<>();
        //查询违章数
//        Integer cnt1 = accidentDao.getCount(Const.SQL_DATA_VIOLATIONCNT.replaceAll("%param%", param.toString()));
        Integer cnt1 = mapper.getCount6(id);

        param = new StringBuilder();
        param.append("and SITEINFO_ID = '").append(id).append("'");
        //查询负面清单数
//        Integer cnt2 = accidentDao.getCount(Const.SQL_DATA_NEGATIVELISTCNT.replaceAll("%param%", param.toString()));
        Integer cnt2 = mapper.getCount7(id);

        //查询黑名单数
//        Integer cnt3 = accidentDao.getCount(Const.SQL_DATA_BLACKLISTCNT.replaceAll("%param%", param.toString()));
        Integer cnt3 = mapper.getCount8(id);
        map1.put("violationCount",cnt1);
        map1.put("negativeListCount",cnt2);
        map1.put("blackListCount",cnt3);
        list2.add(map1);
        map.put("count",list2);

        //查询负面清单情况
//        List<Map<String, Object>> list4 = accidentDao.getList(Const.SQL_DATA_NEGATIVELIST.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list4 = mapper.getList6(id);
        map.put("negativeList",list4);

        //查询黑名单情况
//        List<Map<String, Object>> list5 = accidentDao.getList(Const.SQL_DATA_BLACKLIST.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list5 = mapper.getList7(id);
        map.put("blackList",list5);

        //项目安全情况
        param = new StringBuilder();
        param.append("and CONTRACTOR_ORG_ID = '").append(id).append("'");
//        List<Map<String, Object>> list6 = accidentDao.getList(Const.SQL_DATA_SAFETYSITUATION.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list6 = mapper.getList8(id);
        map.put("safetySituation",list6);
        return map;
    }


    /*
     *全网信息联动
     * paramJson:{"id":""}
     * */
    @Override
    public Map<String, List> networkInformation(String paramJson) {
        Map<String,List> map = new LinkedHashMap<>();
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String id = JSONObject.parseObject(paramJson).getString("id");
        StringBuilder param = new StringBuilder();
        param.append("and SITEINFO_ID = '").append(id).append("'");
        //查询准入情况
//        List<Map<String, Object>> list1 = accidentDao.getList(Const.SQL_DATA_ACCESS.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list1 = mapper.getList9(id);
        for (Map<String, Object> stringObjectMap : list1) {
            String beginTime = stringObjectMap.get("REPORT_CARD_BEGINTIME").toString();
            String endTime = stringObjectMap.get("REPORT_CARD_ENDTIME").toString();
            beginTime = beginTime.substring(0,10);
            endTime = endTime.substring(0,10);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date beginDate = sdf.parse(beginTime);
                Date endDate = sdf.parse(endTime);
                long accessPeriod = (endDate.getTime() - beginDate.getTime())/(24*3600*1000);
                stringObjectMap.put("accessPeriod",accessPeriod);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        map.put("access",list1);

        param = new StringBuilder();
        param.append("and VIOLATION_ORG_ID = '").append(id).append("'");
        //查询违章情况
//        List<Map<String, Object>> list2 = accidentDao.getList(Const.SQL_DATA_ILLEGALSITUATION.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list2 = mapper.getList10(id);
        map.put("illegalSituation",list2);

        param = new StringBuilder();
        param.append("and CONTRACTOR_ORG_ID = '").append(id).append("'");
        //查询事故情况
//        List<Map<String, Object>> list3 = accidentDao.getList(Const.SQL_DATA_ACCIDENTCONDITIONS.replaceAll("%param%", param.toString()));
        List<Map<String, Object>> list3 = mapper.getList11(id);
        map.put("accidentConditions",list3);
        return map;
    }


    /*
     *安全能力分析
     * paramJson:{"id":""}
     * */
    @Override
    public List safetyAnalysis(String paramJson) {
        Map<String,Object> map1 = new LinkedHashMap<>();
        Map<String,Object> map2 = new LinkedHashMap<>();
        Map<String,Object> map3 = new LinkedHashMap<>();
        Map<String,Object> map4 = new LinkedHashMap<>();
//        JSONObject rowData = JSONObject.parseObject(paramJson);
        String id = JSONObject.parseObject(paramJson).getString("id");
        StringBuilder param = new StringBuilder();
        param.append("and CONTRACTOR_ORG_ID = '").append(id).append("'");
//        Integer cnt1 = accidentDao.getCount(Const.SQL_DATA_ACCIDENTCNT.replaceAll("%param%", param.toString()));//查询故障数
        Integer cnt1 = mapper.getCount9( id);//查询故障数
        param = new StringBuilder();
        param.append("and VIOLATION_ORG_ID = '").append(id).append("'");
//        Integer cnt2 = accidentDao.getCount(Const.SQL_DATA_VIOLATIONCNT.replaceAll("%param%", param.toString()));//查询违章数
        Integer cnt2 = mapper.getCount6(id);//查询违章数
        param = new StringBuilder();
        param.append("and SITEINFO_ID = '").append(id).append("'");
//        Integer cnt3 = accidentDao.getCount(Const.SQL_DATA_NEGATIVELISTCNT.replaceAll("%param%", param.toString())); //查询负面清单数
//        Integer cnt4 = accidentDao.getCount(Const.SQL_DATA_BLACKLISTCNT.replaceAll("%param%", param.toString()));//查询黑名单数
        Integer cnt3 = mapper.getCount7(id); //查询负面清单数
        Integer cnt4 = mapper.getCount8(id);//查询黑名单数
        NumberFormat nf = NumberFormat.getInstance();//创建一个数值格式化对象
        nf.setMaximumFractionDigits(2);//设置精确到小数点后2位
         int sum=cnt1 + cnt2 + cnt3 + cnt4;
        String re1= null;
        String re2= null;
        String re3= null;
        String re4 = null;
        if(sum==0){
            map1.put("proportion","0%");
            map2.put("proportion","0%");
            map3.put("proportion","0%");
            map4.put("proportion","0%");
        }else {
            String result1 = nf.format((float)cnt1/(float)(cnt1 + cnt2 + cnt3 + cnt4)*100)+"%";//故障占比
            re1 = nf.format((float) cnt1 / (float) (cnt1 + cnt2 + cnt3 + cnt4)*100);
            String result2 = nf.format((float)cnt2/(float)(cnt1 + cnt2 + cnt3 + cnt4)*100)+"%"; //违章占比
            re2 = nf.format((float) cnt2 / (float) (cnt1 + cnt2 + cnt3 + cnt4)*100);
            String result3 = nf.format((float)cnt3/(float)(cnt1 + cnt2 + cnt3 + cnt4)*100)+"%";//负面清单占比
            re3 = nf.format((float) cnt3 / (float) (cnt1 + cnt2 + cnt3 + cnt4)*100);
            String result4 = nf.format((float)cnt4/(float)(cnt1 + cnt2 + cnt3 + cnt4)*100)+"%";//黑名单占比
            re4 = nf.format((float) cnt4 / (float) (cnt1 + cnt2 + cnt3 + cnt4)*100);
            map1.put("proportion",result1);
            map2.put("proportion",result2);
            map3.put("proportion",result3);
            map4.put("proportion",result4);
        }
        param = new StringBuilder();
        param.append("and BASICINFO_CONTRACTOR_ID = '").append(id).append("'");
        List<Map<String,Object>> score1 = mapper.getScore(id);//查询故障评分
        List<Map<String,Object>> score2 = mapper.getScore1(id);//查询违章评分
        List<Map<String,Object>> score3 = mapper.getScore2(id);//查询负面清单评分
        List<Map<String,Object>> score4 = mapper.getScore3(id); //查询黑名单评分

        map1.put("type","故障");
        map1.put("number",cnt1);
        map1.put("score",score1);

        map2.put("type","违章");
        map2.put("number",cnt2);
        map2.put("score",score2);

        map3.put("type","负面清单");
        map3.put("number",cnt3);
        map3.put("score",score3);

        map4.put("type","黑名单");
        map4.put("number",cnt4);
        map4.put("score",score4);

        List list1 = new ArrayList();
        list1.add(map1);
        list1.add(map2);
        list1.add(map3);
        list1.add(map4);
        Map dataMap1 = new LinkedHashMap();
        Map dataMap2 = new LinkedHashMap();
        Map dataMap3 = new LinkedHashMap();
        Map dataMap4 = new LinkedHashMap();

        if(re1 !=null){
            dataMap1.put("percent",Double.valueOf(re1)/100);
            dataMap1.put("value",cnt1);
            dataMap1.put("name","故障");
        }else {
            dataMap1.put("percent",Double.valueOf(0)/100);
            dataMap1.put("value",cnt1);
            dataMap1.put("name","故障");
        }
        if(re2 !=null){
            dataMap2.put("percent",Double.valueOf(re2)/100);
            dataMap2.put("value",cnt2);
            dataMap2.put("name","违章");
        }else {
            dataMap2.put("percent",Double.valueOf(0)/100);
            dataMap2.put("value",cnt2);
            dataMap2.put("name","违章");
        }

        if(re3 !=null){
            dataMap3.put("percent",Double.valueOf(re3)/100);
            dataMap3.put("value",cnt3);
            dataMap3.put("name","负面清单");
        }else {
            dataMap3.put("percent",Double.valueOf(0)/100);
            dataMap3.put("value",cnt3);
            dataMap3.put("name","负面清单");
        }

        if(re4 !=null){
            dataMap4.put("percent",Double.valueOf(re4)/100);
            dataMap4.put("value",cnt4);
            dataMap4.put("name","黑名单");
        }else {
            dataMap4.put("percent",Double.valueOf(0)/100);
            dataMap4.put("value",cnt4);
            dataMap4.put("name","黑名单");
        }
        List list2 = new ArrayList();
        list2.add(dataMap1);
        list2.add(dataMap2);
        list2.add(dataMap3);
        list2.add(dataMap4);
        List list = new ArrayList();
        list.add(list2);
        list.add(list1);
        return list;
    }
}
