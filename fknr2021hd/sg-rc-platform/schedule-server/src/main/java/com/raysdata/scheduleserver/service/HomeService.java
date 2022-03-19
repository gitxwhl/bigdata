package com.raysdata.scheduleserver.service;


import com.nariit.rmcp.base.vo.PeccancyManage;
import com.nariit.rmcp.base.vo.WorkPlanMapVO;
import com.nariit.rmcp.base.vo.WpWorkPlanDay;

import java.util.List;
import java.util.Map;

public interface HomeService {
    List<Map> getWorkPlanList(String var1);

    Map<String, Object> getZyaqRisk(String var1);

    List<Map> getExeState(String var1);

    Map<String, Object> getDwfxRisk(String var1);

    Map<String, Object> getSgRisk(String var1);

    List<WorkPlanMapVO> getWorkplanMap(String var1, String var2);

    WpWorkPlanDay getWorkplanMapById(String var1, String var2);

    List<Map> getPeccancyList(String var1);

    PeccancyManage getPeccancyById(String var1, String var2);

    Object getPeccancyAmount(String var1);

    Object getZyglbmType(String var1);
}
