package com.raysdata.enterprisesadmittanceserver.servrce;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface personnelInformationService {
    List<Map> getTotalStaff(String Month);//查询：作业人员总数，业主人数，集体人数，外包人数，  当前有效准入人数，本月新增准入人数，本月取消资格人数。

    List<Map> getRegionalMonthAdd(); //根据地区，人员性质  查询  业主，集体，外包 人数

    List<Map> getRegionalMonth(String Month); //根据地区，人员性质  查询  业主，集体，外包 人数

//    List<Map> getPersonnelIn(String Page, String Number, String Name, String OrgId, String DatareportOrg, String profession, String WorkerNature,
//                             String ThreekindsIdentification, String starttime, String begintime, String AccessState); //页码 数量 姓名 单位 省份 专业 企业性质 三种人标识 开始时间 结束时间  是否有效

    Map<String, Object> getPersonnelIn(String jsonStr);//列表分页


    List<Map> getDatareportOrg();  //省份小代码

    List<Map> getOrgId(String OrgId);  //根据省份查询单位

    List<Map> getProfession();  //专业小代码

    List<Map> getOrgNature(); //企业性质小代码

    List<Map> getThreekindsIdentification(); //三种人标识小代码

    List<Map> getAccessState();  //是否有效小代码

    List<Map> getSex();  //性别

    List<Map> getOrgids(String siteworkerinfoId); //根据id查询人员

    List<Map> getExamination(String siteworkerinfoId); //根据id查询考试成绩

    List<Map> getViolationfile(String siteworkerinfoId); //根据id查询违章

    List<Map> getDatareport(String siteworkerinfoId); //人员变更记录

    String deletePersonnel(String str);

    Object downloadPerExcel(HttpServletRequest request, HttpServletResponse response) throws NoSuchFieldException, IllegalAccessException, IOException;

    List<Map> loginToken(String token);

    Map<String ,Object> getSrpWorkePlanDay(String jsonStr);

    List<Map> getTmxAttachment(String siteworkerinfoId);
}
