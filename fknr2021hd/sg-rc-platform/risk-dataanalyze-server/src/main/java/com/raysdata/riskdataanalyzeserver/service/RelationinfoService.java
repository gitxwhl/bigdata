package com.raysdata.riskdataanalyzeserver.service;

import com.raysdata.riskdataanalyzeserver.utils.PageBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface RelationinfoService {

    /**
     * 关系文件导出
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    Map<String, Object> fileImport(HttpServletRequest request, HttpServletResponse response , String data ) throws IOException;


    /**
     * 关系文件导入
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    Map<String,String> fileRelationLead(HttpServletRequest request, HttpServletResponse response) throws Throwable;

    /**
     * 实例文件导入
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    Object fileExample(HttpServletRequest request, HttpServletResponse response) throws Throwable;

    /**
     * 查询知识融合列表
     * @param
     * @return
     */
    PageBean<Map<String,Object>> knowledge(String paramJson);

    /**
     * 查询知识融合列表
     * @param
     * @return
     */
//    PageBeans<SrpBdkgKnowlwdgeFusion> knowledge(Map<String, Object> map);
}



