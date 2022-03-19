package com.raysdata.enterprisesadmittanceserver.servrce;

import java.util.List;
import java.util.Map;

public interface NegativeOrBlackListService {

    Map<String, Object> getBlackCntInfos();

    List<Map> getBlackCntInfosByArea(Integer inteType);

    Map<String, Object> getNegativeCntInfos();

    List<Map> getNegativeCntInfosByArea(Integer inteType);

    Map<String, Object> getBlackInfosListOrNegativeInfosList(String jsonStr);

    Integer deleteBlacklistInventory(String str);
}
