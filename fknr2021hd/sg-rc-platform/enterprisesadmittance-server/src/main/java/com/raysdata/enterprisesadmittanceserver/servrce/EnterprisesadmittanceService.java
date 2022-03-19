package com.raysdata.enterprisesadmittanceserver.servrce;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EnterprisesadmittanceService {
    Map<String, Object> getEnterpriseCnts();

    List<Map> getEnterprisesAdmittanceInfoCnt(int timeType);

    Map<String, Object> getWorkSiteinfosList(String jsonStr);

    List<Map> getSrpWorkSiteinfo(String siteinfoId);

    Map<String, Object> getSrpWorkViolationfile(String jsonStr);

    Map<String, List<JSONObject>> getSrpRiskSysTab();

    Map<String, List<JSONObject>> getDatafillOrg(String datafillOrgId);

    List<Map> getSrpworkViolationrules(String violationfileId);

    List<Map> getSrpWorkSiteinfochange(String provinceCode , String basicinfoContractor);

    List<Map> getSrpWorkSiteinfoAll(String basicinfoContractor ,String siteinfoId);

    Map<String ,Object> getSrpWorksiteworker(String jsonStr);

    String deleteEnterprise(String str);

    Object downloadEnterExcel(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchFieldException, IllegalAccessException;

    Map<String ,Object> getSrpWorkePlanDay(String jsonStr);
}
