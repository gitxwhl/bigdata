package com.canteen.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface StMaterialplanService {

    //原料需求查询
    Object getMaterialPlan(String paramJson);

    //导出原料需求查询列表
    Object exportMaterialPlan(HttpServletRequest request, HttpServletResponse response, String paramJson) throws UnsupportedEncodingException;
}
