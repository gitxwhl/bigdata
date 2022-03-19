package com.raysdata.riskdataanalyzeserver.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface RelationinfoMapper {
    /**
     * 关系文件导出
     *
     * @param
     */
    List<Map<String,Object>> ServiceAll();

    Integer getAllCount();

    List<Map<String,Object>> getList(String param);

    Integer getCodeCount();

    int fileRelationLead(Map<String, Object> map);

    int instKnowlwdgeFusion(Map<String, Object> map1);

    /**
     * 实例文件导入
     */
    Integer serviceAlls();

    Integer getInstanceCount();

    void saveExample(Map<String, Object> map1);

    /**
     * 查询知识融合列表
     */
    Integer getCount();

    List<Map<String,Object>> getCounts(Map param);

//    List<SrpBdkgKnowlwdgeFusion> getCounts(Map param);

    int fileExample(Map<String, Object> map);
}
