package com.raysdata.riskdataanalyzeserver.mapper;

import com.raysdata.riskdataanalyzeserver.bean.SrpWorkKdtbWorkplandayWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface SrpWorkKdtbWorkplandayMapper {
    /*
     * 外包单位风险画像列表
     * paramJson:{"page":"", "size":"","params":{"datareportOrg":"","constructionOrg":""}}
     * */
    Integer getCount(SrpWorkKdtbWorkplandayWithBLOBs bs);

//    黑名单统计
    Integer getCount4(String violationOrgId);

    List<Map<String,Object>> getList(SrpWorkKdtbWorkplandayWithBLOBs bs);

    Integer getCount1(Map<String, Object> map);

    Integer getCount2(String violationOrgId);

    Integer getCount3(String map3);

    /*
     *单位基本情况
     * paramJson:{"page":"", "size":"", "id":""}
     * */
    List<Map<String,Object>> getList2(String id);

    int getCount5(String id);

    List<Map<String, Object>> getList3(Map param);

    /*
     *单位安全资信
     * paramJson:{"id":""}
     * */
    List<Map<String,Object>> getList4(String id);

    List<Map<String,Object>> getList5(String id);

    Integer getCount6(String id);

    Integer getCount7(String id);

    Integer getCount8(String id);

    List<Map<String,Object>> getList6(String id);

    List<Map<String,Object>> getList7(String id);

    List<Map<String,Object>> getList8(String id);

    /*
     *全网信息联动
     * paramJson:{"id":""}
     * */
    List<Map<String,Object>> getList9(String id);

    List<Map<String,Object>> getList10(String id);

    List<Map<String,Object>> getList11(String id);


    /*
     *安全能力分析
     * paramJson:{"id":""}
     * */
    Integer getCount9(String id);

    List<Map<String,Object>> getScore(String id);

    List<Map<String,Object>> getScore1(String id);

    List<Map<String,Object>> getScore2(String id);

    List<Map<String,Object>> getScore3(String id);
}