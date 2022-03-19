package com.raysdata.scheduleserver.service;

import com.nariit.rmcp.common.constant.ZyglbmTypeEnum;
import com.nariit.rmcp.common.vo.DicItems;

import java.util.List;

public interface WPWorkPlanBizc {
    List<DicItems> getAllDicts(String var1, boolean var2);

    List<DicItems> getAllDicts(String var1);

    String getDataLimitCondition(String var1);

    String getWorkPlanCode(String var1, String var2);

    ZyglbmTypeEnum getUserGlzy(String var1);

    String getAuthUserCodey(String var1);
}
