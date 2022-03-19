package com.raysdata.riskdataanalyzeserver.service;

import com.raysdata.riskdataanalyzeserver.utils.PageBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface BusinessLogUnifiedService {
    /**
     *数据加载添加系统日志 1
     */
    int saveSysLog(HttpServletRequest request , String param);
    int saveSysErroLog(HttpServletRequest request , String param);
    /**
     *数据转换添加系统日志 2
     */
    int saveConversionLog(HttpServletRequest request , String param);
    int saveConversionErroLog(HttpServletRequest request , String param);

    /**
     *数据清洗添加系统日志 3
     */
    int saveClearionLog(HttpServletRequest request , String param);
    int saveClearErroLog(HttpServletRequest request , String param);

    /**
     *数据治理添加系统日志 4
     */
    int saveDataGovernmentLog(HttpServletRequest request , String param);
    int saveDataGovernmentErroLog(HttpServletRequest request , String param);


    /**
     *安全工器具数据统计加系统日志 1
     */
//    int saveDataStatisticsLog(HttpServletRequest request , String param) throws Exception;
//    int saveDataStatisticsErroLog(HttpServletRequest request , String param) throws Exception;

    /**
     *安全工器具实时预警加系统日志 2
     */
    int saveWarningLog(HttpServletRequest request , String param);
    int saveWarningErroLog(HttpServletRequest request , String param);


    /**
     *安安全工器具供应商评价加系统日志 3
     */
    int saveEvaluateLog(HttpServletRequest request , String param);
    int saveEvaluateErroLog(HttpServletRequest request , String param);
    /**
     *全网安全事故事件分析:分类统计(设备事件) 1
     */
    int saveClassifiedStatisticsLog(HttpServletRequest request , String param);
    int saveClassifiedStatisticsErroLog(HttpServletRequest request , String param);

    /**
     *全网安全事故事件分析:分类统计(人身事件) 2
     */
    int savePersonalEventsLog(HttpServletRequest request , String param);
    int savePersonalEventsErroLog(HttpServletRequest request , String param);

    /**
     *全网安全事故事件分析:分类统计(电网事件) 3
     */
    int savePowerEventsLog(HttpServletRequest request , String param);
    int savePowerEventsErroLog(HttpServletRequest request , String param);

    /**
     *全网安全事故事件分析:分类统计(网络信息事件) 4
     */
    int saveGridInformationLog(HttpServletRequest request , String param);
    int saveGridInformationErroLog(HttpServletRequest request , String param);
    /**
     *人员基本情况
     */
    int saveBasicInformationLog(HttpServletRequest request , String param);
    int saveBasicInformationErroLog(HttpServletRequest request , String param);

    /**
     *人员安全资信
     */
    int saveSecurityCreditLog(HttpServletRequest request , String param);
    int saveSecurityCreditErroLog(HttpServletRequest request , String param);

    /**
     *人员全网信息联动
     */
    int saveWholeNetworkLinkageLog(HttpServletRequest request , String param);
    int saveWholeNetworkLinkageErroLog(HttpServletRequest request , String param);

    /**
     *外包单位作业风险画像
     */
    int saveRiskPictureLog(HttpServletRequest request , String param);
    int saveRiskPictureLogErro(HttpServletRequest request , String param);
    /**
     *知识图谱建模
     */
    int saveAtlaMmodelingLog(HttpServletRequest request , String param);
    int saveAtlaMmodelingLogEror(HttpServletRequest request , String param);

    /**
     *知识获取
     */
    int saveKnowledgeAcquisition(HttpServletRequest request , String param);
    int saveKnowledgeAcquisitionEror(HttpServletRequest request , String param);

    /**
     *知识融合
     */
//    int saveKnowledgeFusion(HttpServletRequest request , String param) throws Exception;
//    int saveKnowledgeFusionEror(HttpServletRequest request , String param) throws Exception;
    /**
     *知识图谱应用
     */
    int saveKnowledgeApplication(HttpServletRequest request , String param);
    int saveKnowledgeApplicationEror(HttpServletRequest request , String param);

    /**
     *知识获取实例添加系统日志
     */
    int saveCommonInstanceInfoLog(HttpServletRequest request , String param);
    int saveCommonInstanceInfoErroLog(HttpServletRequest request , String param);

    /**
     *知识获取关系添加系统日志
     */
    int saveCommonRelationshipLog(HttpServletRequest request , String param);
    int saveCommonRelationshipErrorLog(HttpServletRequest request , String param);
    /**
     *知识图谱建模(新增)添加系统日志
     */
    int saveKnowledgeModelingLog(HttpServletRequest request , String param);
    int saveKnowledgeModelingLogErro(HttpServletRequest request , String param);

    /**
     *知识获取(实例)
     */
    int delCommonInstanceInfoLog(HttpServletRequest request , String param);
    int delCommonInstanceInfoLogErro(HttpServletRequest request , String param);
    /**
     *知识获取(实例：修改)
     */
    int updateCommonInstanceInfoLog(HttpServletRequest request , String param);
    int updateCommonInstanceInfoLogErro(HttpServletRequest request , String param);

    /**
     *知识获取(实例：导入)
     */
    int ImportCommonInstanceInfoLog(HttpServletRequest request, HttpServletResponse response);
    int ImportCommonInstanceInfoLogErro(HttpServletRequest request, HttpServletResponse response);

    /**
     *知识获取(关系：导入)
     */
    int ImportRelationshipLog(HttpServletRequest request, HttpServletResponse response);
    int ImportRelationshipLogErro(HttpServletRequest request, HttpServletResponse response);
    /**
     *知识获取(实例：导出)
     */
    int exportCommonInstanceInfoLog(HttpServletRequest request, HttpServletResponse response,String data);
    int exportCommonInstanceInfoLogErro(HttpServletRequest request, HttpServletResponse response,String data);

    /**
     *知识获取(实例：模板下载)
     */
    int exampleTemplateDownloadLog(HttpServletRequest request,String data);
    int exampleTemplateDownloadLogErro(HttpServletRequest request,String data);

    /**
     *知识获取(关系：模板下载)
     */
    int relationshipTemplateDownloadLog(HttpServletRequest request,String data);
    int relationshipTemplateDownloadLogErro(HttpServletRequest request,String data);
    /**
     *基本信息
     */
    int essentialInformationLog(HttpServletRequest request,String data);
    int essentialInformationLogErro(HttpServletRequest request,String data);
    /**
     *安全资信
     */
    int securityCreditLog(HttpServletRequest request,String data);
    int securityCreditErroLog(HttpServletRequest request,String data);

    /**
     *全网信息联动
     */
    int informationLinkageLog(HttpServletRequest request,String data);
    int informationLinkageErroLog(HttpServletRequest request,String data);
    /**
     *安全能力分析
     */
    int abilityanAlysisLog (HttpServletRequest request,String data);
    int abilityanAlysisLogErro (HttpServletRequest request,String data);


    /**
     * 根据id获取数据操作日志信息
     */
    PageBean<Map<String,Object>> findLogById(String paramJson);

    /**
     * 查询省份信息
     */
    List<Map<String,Object>> getProvinceList();
}
