package com.raysdata.enterprisesadmittanceserver.servrce;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface examinationService {
    List<Map> getNetworkWide();//查询全网考试情况

    List<Map> getUnitTestData(String Month); //查询各单位考试情况

    List<Map> getDatareportOrg(); //查询省份小代码

    List<Map> getExaminationStatus(); //查询考试状态小代码

    List<Map> getTestroomName(); //查询考场名称小代码

    List<Map> getExaminationList(String Page, String Number, String datareport_org_id, String testroom_no, String test_name, String test_state, String starttime, String begintime);

    List<Map> getEssentialInformation(String testroom_no);

    List<Map> getPersonnelList(String Page, String Number, String testroom_no,String test_state,String starttime,String begintime);

    String deleteExamination(String str);

    List<Map> getProvincialexamination(String datareport_org_id);//省份联动考场数据
}
