package com.raysdata.riskdataanalyzeserver.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface RiskAccidentAnalysisMapper {
    /*
     *设备事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","voltageCapacity":"","gridcorp":"","deviceClass":"","startTime":"","endTime":""}}
     * */
    List<Map<String,Object>> getDataFromDictionaries(String voltageCapacity);

    String getDataFromDictionarie(String gridcorp);

    int getEquipAccidentListCNT(Map param);

    List<Map<String, Object>> getEquipAccidentList(Map param);

    /*
     *人身事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","accidentClass":"","injuryDegree":"","startTime":"","endTime":""}}
     * */
    int getPersonalAccidentListCNT(Map param);

    List<Map<String, Object>> getPersonalAccidentList(Map param);






    /*
     *电网事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","voltageCapacity":"","deviceClass":"","startTime":"","endTime":""}}
     * */
    List<String> getDataFromDictionaries2(String voltageCapacity);










    int getPowerGridAccidentListCNT(Map param);

    List<Map<String, Object>> getPowerGridAccidentList(Map param);

    /*
     *网络信息事故表查询
     * paramJson:{"page":"", "size":"", "params":{"accidentGrade":"","accidentClass":"","deviceClass":"","startTime":"","endTime":""}}
     * */
    int getInformAccidentListCNT(Map param);

    List<Map<String, Object>> getInformAccidentList(Map param);

    /*
     *安全事故分类统计
     * paramJson:{"timeType":""}   废弃
     * */
    Integer getEquipAccidentListCNT1();
    Integer getEquipAccidentListCNT2();
    Integer getEquipAccidentListCNT3();
    Integer getEquipAccidentListCNT4();



   Integer getAllEquipAccidentListCNT4();

//    不同事件总数统计










    Integer getPersonalAccidentListCNT1();
    Integer getPersonalAccidentListCNT2();
    Integer getPersonalAccidentListCNT3();

    Integer getPersonalAccidentListCNT4();

    Integer getAllPersonalAccidentListCNT4();

    Integer getPowerGridAccidentListCNT1();
    Integer getPowerGridAccidentListCNT2();
    Integer getPowerGridAccidentListCNT3();


    Integer getPowerGridAccidentListCNT4();

    Integer getAllPowerGridAccidentListCNT4();

    Integer getInformAccidentListCNT1();
    Integer getInformAccidentListCNT2();
    Integer getInformAccidentListCNT3();

    Integer getInformAccidentListCNT4();

    Integer getAllInformAccidentListCNT4();

    /*
     *按类型同比统计 按类型环比统计
     * paramJson:{"type":""}
     * */
    Map getMap(@Param("type") String type);

//    Map getMap2(@Param("param")String param);

    Map getMap2(@Param("type")String type);


    /**
     * 人身事故视图表
     * 趋势预测分析 实际值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMap1();

    /**
     * 人身事故视图表
     * 趋势预测分析 预测值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMaps1();

    /**
     * 设备事故视图表
     * 趋势预测分析 实际值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMap2();

    /**
     * 设备事故视图表
     * 趋势预测分析 预测值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMaps2();

    /**
     * 电网事故视图表
     * 趋势预测分析 实际值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMap3();

    /**
     * 电网事故视图表
     * 趋势预测分析 预测值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMaps3();

    /**
     * 信息事故视图表
     * 趋势预测分析 实际值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMap4();

    /**
     * 信息事故视图表
     * 趋势预测分析 预测值
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getTendencyAnalyzeMaps4();

    /**
     * 设备事件等级
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getEquipAccidentGrade();

    /**
     * 电网事件等级
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getInformAccidentGrade();

    /**
     * 人身事件等级
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getPersonalAccidentGrade();

    /**
     * 信息事件等级
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getPowergridAccidentGrade();

    /**
     * 按按单位统计
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getAccidentGridcrop();

    /**
     * 按电压等级统计
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getAccidentVoltageLevel();

    /**
     * 按事故原因TOP10统计
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getEquipAccidentDesc();

    /**
     * 按电力事故统计
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getPowergridAccidentDesc();

    /**
     * 按人员事故统计
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getPersonalAccidentDesc();

    /**
     * 按通知异常
     *
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> getInformAccidentDesc();

    /**
     * 获取字典表定义
     *
     * @return List
     */
    List<Map<String, Object>> getSrpRiskSysTab();


}
