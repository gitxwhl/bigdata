package com.raysdata.riskdataanalyzeserver.service;

import com.raysdata.riskdataanalyzeserver.bean.Instanceinfo;
import com.raysdata.riskdataanalyzeserver.utils.PageBean;

import java.util.List;
import java.util.Map;

public interface AtlasService {
    //-------------------------------知识图谱建模页面-----------------------------------------------
    //

    /**
     * 查询建模表
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getModeling(String paramJson);

    /**
     * 删除建模
     * @param paramJson
     * @return
     */
    String deleteModeing(String paramJson);

    /**
     * 新增建模
     * @param paramJson
     * @return
     */
    String insertModeing(String paramJson);

    //

    /**
     * 生成编号
     * @return
     */
    String generatedNumber();


    /**
     * 编辑建模
     * @param paramJson
     * @return
     */
    String updateModeling(String paramJson);


    /**
     * 查看建模详情
     * @param paramJson
     * @return
     */
    Map<String,Object> getModenlingDetails(String paramJson);



    //-----------------------------------------------知识获取-----------------------------------------------------------

    /**
     * 查询实例信息
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getInstanceInfo(String paramJson);

    /**
     * 关系列表展示
     * @param paramJson
     * @return
     */
    PageBean<Map<String,Object>> getRelationInfo(String paramJson);

    /**
     * 关系表生成编号
     * @return
     */
    String relationNumber();

    /**
     * 新增关系
     * @param paramJson
     * @return
     */
//    String insertRelationInfo(String paramJson);
    /**
     * 新增关系
     * @param paramJson
     * @return
     */
    Map<String,String> insertRelationInfo(String paramJson);

    /**
     * 实例表生成编号
     * @return
     */
    String instanceNumber();


    /**
     * 删除实例
     * @param paramJson
     * @return
     */
    String deleteInstance(String paramJson);


    /**
     * 新增实例信息
     * @param paramJson
     * @return
     */
//    Map<String, List> insertInstanceInfo(String paramJson);
    Map<String, String> insertInstanceInfo(String paramJson);

    /**
     * 根据ID查询关系表
     * @param paramJson
     * @return
     */
    List<Map<String,Object>> getRelationInfoById(String paramJson);

    /**
     * 根据类别查询实例
     * @param paramJson
     * @return
     */
    List<Map<String,Object>> getInstanceInfoByType(String paramJson);

    /**
     * 根据ID查询实例详情
     * @param paramJson
     * @return
     */
    List<Map<String,Object>> getInstanceInfoById(String paramJson);


    /**
     * 根据实例ID和关系ID查询实例关系
     * @param paramJson
     * @return
     */
    Map<String,List> getInstancerelationinfoById(String paramJson);


//    /**
//     * 编辑实例
//     * @param paramJson
//     * @return
//     */
//    String updateInstanceInfo(String paramJson);

    /**
     * 不含关联关系实例编辑
     * @param paramJson
     * @return
     */
    String updateInstanceInfoInclude(String paramJson);


//    String InsertInstanceInfoInclude(String paramJson);


    /**
     * 实例关系拓扑图
     * @param paramJson
     * @return
     */
    Map<String,List> getGplot(String paramJson);

    /**
     * 获取实例关联关系
     * @return
     */
    List<Map<String,String>> findTopology();
    /**
     * 根据名称查询拓扑图
     * @param param
     * @return
     */
    Object getGplotName(String param);


    /**
     * 新增实例
     * @param
     * @return
     */
    String insertInstanceInfobd(String paramJson);
    /**
     * 根据id获取实例详情
     * @param
     * @return
     */
    Instanceinfo findinstancexq(Instanceinfo instanceinfo);

    /**
     * 新增实例（关联关系）
     * @param
     * @return
     */
//    String InsertInstanceInfoIncludegl(String paramJson);



}
