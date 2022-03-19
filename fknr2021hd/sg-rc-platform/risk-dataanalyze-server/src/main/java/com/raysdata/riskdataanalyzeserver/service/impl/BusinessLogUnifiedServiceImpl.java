//package com.raysdata.riskdataanalyzeserver.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.raysdata.riskdataanalyzeserver.dao.AtlasDao;
//import com.raysdata.riskdataanalyzeserver.dao.BusinessLogUnifiedDao;
//import com.raysdata.riskdataanalyzeserver.dao.RelationinfoDao;
//import com.raysdata.riskdataanalyzeserver.service.BusinessLogUnifiedService;
//import com.raysdata.riskdataanalyzeserver.utils.BusinessLog;
//import com.raysdata.riskdataanalyzeserver.utils.Const;
//import com.raysdata.riskdataanalyzeserver.utils.ObjectUtils;
//import com.raysdata.riskdataanalyzeserver.utils.PageBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class BusinessLogUnifiedServiceImpl implements BusinessLogUnifiedService {
//
//    @Autowired
//    private BusinessLogUnifiedDao businessLogUnifiedDao;
//    @Autowired
//    private AtlasDao atlasDao;
//    @Autowired
//    private RelationinfoDao relationinfoDao;
//    /**
//     *添加系统日志
//     */
//    @Override
//    public int saveSysLog(HttpServletRequest request , String param) {
//
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_LOADING);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    /**
//     *添加系统日志
//     */
//    @Override
//    public int saveSysErroLog(HttpServletRequest request , String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_LOADING);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *数据转换添加系统日志
//     */
//    @Override
//    public int saveConversionLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_TRANSFORMATION);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    /**
//     *数据转换添加系统日志
//     */
//    @Override
//    public int saveConversionErroLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_TRANSFORMATION);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *数据清洗添加系统日志
//     */
//    @Override
//    public int saveClearionLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_CLEAN);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *数据清洗添加系统日志
//     */
//    @Override
//    public int saveClearErroLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_CLEAN);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    /**
//     *数据治理添加系统日志
//     */
//    @Override
//    public int saveDataGovernmentLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_GOVERNMENT);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//    /**
//     *数据治理添加系统日志
//     */
//    @Override
//    public int saveDataGovernmentErroLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCommonDataLoad(request ,param,datalog,BusinessLog.DATA_GOVERNMENT);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//
//
//    /**
//     *安全工器具实时预警加系统日志 2
//     */
//    @Override
//    public int saveWarningLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getTools(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveWarningErroLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getTools(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//
//
//    /**
//     *安安全工器具供应商评价加系统日志 3
//     */
//    @Override
//    public int saveEvaluateLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getEvaluate(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    @Override
//    public int saveEvaluateErroLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getEvaluate(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    /**
//     *全网安全事故事件分析:分类统计 （设备事件）1
//     */
//    @Override
//    public int saveClassifiedStatisticsLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getClassifiedStatistics(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveClassifiedStatisticsErroLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getClassifiedStatistics(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    /**
//     *全网安全事故事件分析:分类统计(人身事件) 2
//     */
//    @Override
//    public int savePersonalEventsLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getPersonalEvents(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int savePersonalEventsErroLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getPersonalEvents(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *全网安全事故事件分析:分类统计(电网事件) 3
//     */
//    @Override
//    public int savePowerEventsLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getPowerEvents(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//    @Override
//    public int savePowerEventsErroLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getPowerEvents(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *全网安全事故事件分析:分类统计(网络信息事件) 4
//     */
//    @Override
//    public int saveGridInformationLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getNetworkInformationEvents(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveGridInformationErroLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getNetworkInformationEvents(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    /**
//     *人员基本情况
//     */
//    @Override
//    public int saveBasicInformationLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getSafetyCapabilityAnalysis(request ,param,datalog,BusinessLog.BASC_INFORMATION);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveBasicInformationErroLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getSafetyCapabilityAnalysis(request ,param,datalog,BusinessLog.BASC_INFORMATION);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *人员安全资信
//     */
//    @Override
//    public int saveSecurityCreditLog(HttpServletRequest request, String param){
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getSafetyCapabilityAnalysis(request ,param,datalog,BusinessLog.SAFETY_CREDIT);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveSecurityCreditErroLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getSafetyCapabilityAnalysis(request ,param,datalog,BusinessLog.SAFETY_CREDIT);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    /**
//     *人员全网信息联动
//     */
//    @Override
//    public int saveWholeNetworkLinkageLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getSafetyCapabilityAnalysis(request ,param,datalog,BusinessLog.INFORMATION_LINKGE);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    @Override
//    public int saveWholeNetworkLinkageErroLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getSafetyCapabilityAnalysis(request ,param,datalog,BusinessLog.INFORMATION_LINKGE);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *外包单位作业风险画像
//     */
//    @Override
//    public int saveRiskPictureLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getRiskPicture(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveRiskPictureLogErro(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getRiskPicture(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *知识图谱建模 getKnowledgeModeling
//     */
//    @Override
//    public int saveAtlaMmodelingLog(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getKnowledgeModeling(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveAtlaMmodelingLogEror(HttpServletRequest request, String param)  {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getKnowledgeModeling(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//
//    /**
//     *知识获取
//     */
//    @Override
//    public int saveKnowledgeAcquisition(HttpServletRequest request, String param)  {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getKnowledgeObtain(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveKnowledgeAcquisitionEror(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getKnowledgeObtain(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//
//
//    /**
//     *知识图谱应用  getKnowledgeTopology
//     */
//    @Override
//    public int saveKnowledgeApplication(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getKnowledgeTopology(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int saveKnowledgeApplicationEror(HttpServletRequest request, String param) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getKnowledgeTopology(request ,param,datalog);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     * 、数据转换、数据清洗、数据治理、查询公共方法调用
//     */
//    public List<Map<String, String>> getCommonDataLoad(HttpServletRequest request , String param,String datalog,String menuType){
//        String jobName = JSONObject.parseObject(param).getJSONObject("params").getString("jobName");
//        String executionDateTime = JSONObject.parseObject(param).getJSONObject("params").getString("executionDateTime");
//        String endDateTime = JSONObject.parseObject(param).getJSONObject("params").getString("endDateTime");
//        String processState = JSONObject.parseObject(param).getJSONObject("params").getString("processState");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        String  pamjobName = "任务名称:" + jobName + ",";
//        String  pamexecutionDateTime = "开始时间:" + executionDateTime + ",";
//        String  pamendDateTime = "结束时间:" + endDateTime + ",";
//        String  pamprocessState = "进程状态:" + processState + ",";
//        StringBuilder para = new StringBuilder();
//        /**
//         *
//         */
//        if(menuType.equals(BusinessLog.DATA_LOADING)){
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_DATA_LOADING);
//        }
//        /**
//         * 数据转换
//         */
//        if(menuType.equals(BusinessLog.DATA_TRANSFORMATION)){
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_DATA_TRANSFORMATION);
//        }
//        /**
//         * 数据清洗
//         */
//        if(menuType.equals(BusinessLog.DATA_CLEAN)){
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_DATA_CLEAN);
//        }
//        /**
//         * 数据治理
//         */
//        if(menuType.equals(BusinessLog.DATA_GOVERNMENT)){
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_DATA_GOVERNMENT);
//        }
//        StringBuilder reference = para.append("查询了"+ menuType + BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!jobName.isEmpty()){
//            reference.append(pamjobName);
//        }
//        if(!processState.isEmpty()){
//            reference.append(pamprocessState);
//        }
//        if(!endDateTime.isEmpty()){
//            reference.append(pamendDateTime);
//        }
//        if(!executionDateTime.isEmpty()){
//            reference.append(pamexecutionDateTime);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//
//        if(jobName.isEmpty()&&executionDateTime.isEmpty()&&endDateTime.isEmpty()&&processState.isEmpty()){
//            jk.get(0).put("logContent","查询了" + menuType +",查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//    /**
//     * 人员基本情况、人员安全资信、人员全网信息联动 公用方法
//     */
//    public List<Map<String, String>> getSafetyCapabilityAnalysis(HttpServletRequest request , String param,String datalog,String menuType){
//        String name = JSONObject.parseObject(param).getJSONObject("params").getString("name");
//        String orgName = JSONObject.parseObject(param).getJSONObject("params").getString("orgName");
//        String datareportOrgId = JSONObject.parseObject(param).getJSONObject("params").getString("datareportOrgId");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        String  pamName = "姓名:" + name + ",";
//        String  pamOrgName = "单位名称:" + orgName + ",";
//        String  pamDatareportOrgId = "省份:" + datareportOrgId + ",";
//        StringBuilder para = new StringBuilder();
//
//        /**
//         * 人员基本情况
//         */
//        if(menuType.equals(BusinessLog.BASC_INFORMATION)){
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_ABILITY_BASIC);
//        }
//        /**
//         * 人员安全资信
//         */
//        if(menuType.equals(BusinessLog.SAFETY_CREDIT)){
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_ABILITY_CREDIT);
//        }
//        /**
//         * 人员全网信息联动
//         */
//        if(menuType.equals(BusinessLog.INFORMATION_LINKGE)){
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_ABILITY_INFORMATION);
//        }
//        StringBuilder reference = para.append("查询了"+ menuType + BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!name.isEmpty()){
//            reference.append(pamName);
//        }
//        if(!orgName.isEmpty()){
//            reference.append(pamOrgName);
//        }
//        if(!datareportOrgId.isEmpty()){
//            reference.append(pamDatareportOrgId);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//
//        if(name.isEmpty()&&orgName.isEmpty()&&datareportOrgId.isEmpty()){
//            jk.get(0).put("logContent","查询了" + menuType +",查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//    /**
//     * 安全工器具实时预警
//     */
//    public List<Map<String, String>> getTools(HttpServletRequest request , String param,String datalog){
//        String dataReportOrg = JSONObject.parseObject(param).getJSONObject("params").getString("dataReportOrg");
//        String managementCode = JSONObject.parseObject(param).getJSONObject("params").getString("managementCode");
//        String toolType = JSONObject.parseObject(param).getJSONObject("params").getString("toolType");
//        String name = JSONObject.parseObject(param).getJSONObject("params").getString("name");
//        String insState = JSONObject.parseObject(param).getJSONObject("params").getString("insState");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_WARNING_REAL);
//        String  pamDataReportOrg = "单位:" + dataReportOrg + ",";
//        String  pamManagementCode = "省份:" + managementCode + ",";
//        String  pamToolType = "工器具类型:" + toolType + ",";
//        String  pamName = "工器具名称:" + name +",";
//        String  pamInsState = "当前状态:" + insState + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append(BusinessLog.SECURITY_RISK_DATA_QUERY);
//
//        if(!dataReportOrg.isEmpty()){
//            reference.append(pamDataReportOrg);
//        }
//        if(!managementCode.isEmpty()){
//            reference.append(pamManagementCode);
//        }
//        if(!toolType.isEmpty()){
//            reference.append(pamToolType);
//        }
//        if(!name.isEmpty()){
//            reference.append(pamName);
//        }
//        if(!insState.isEmpty()){
//            reference.append(pamInsState);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(dataReportOrg.isEmpty()&&managementCode.isEmpty()&&toolType.isEmpty()&&name.isEmpty()&&insState.isEmpty()){
//            jk.get(0).put("logContent","查询了安全工器具实时预警,查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//    /**
//     * 安全工器具供应商评价
//     */
//    public List<Map<String, String>> getEvaluate(HttpServletRequest request , String param,String datalog){
//        String dataReportOrg = JSONObject.parseObject(param).getJSONObject("params").getString("dataReportOrg");
//        String manName = JSONObject.parseObject(param).getJSONObject("params").getString("manName");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_WARNING_EVALUATE);
//        String  pamDataReportOrg = "省份:" + dataReportOrg + ",";
//        String  pamManName = "供应商名称:" + manName + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append(BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!dataReportOrg.isEmpty()){
//            reference.append(pamDataReportOrg);
//        }
//        if(!manName.isEmpty()){
//            reference.append(pamManName);
//        }
//
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(manName.isEmpty()&&dataReportOrg.isEmpty()){
//            jk.get(0).put("logContent","查询了安全工器具供应商评价,查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//    /**
//     * 全网安全事故事件分析:分类统计(设备事件)
//     */
//    public List<Map<String, String>> getClassifiedStatistics(HttpServletRequest request , String param,String datalog){
//        String accidentGrade = JSONObject.parseObject(param).getJSONObject("params").getString("accidentGrade");
//        String voltageCapacity = JSONObject.parseObject(param).getJSONObject("params").getString("voltageCapacity");
//        String gridcorp = JSONObject.parseObject(param).getJSONObject("params").getString("gridcorp");
//        String deviceClass = JSONObject.parseObject(param).getJSONObject("params").getString("deviceClass");
//        String startTime = JSONObject.parseObject(param).getJSONObject("params").getString("startTime");
//        String endTime = JSONObject.parseObject(param).getJSONObject("params").getString("endTime");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ACCIDENT_STATISTICS);
//        String  pamAccidentGrade = "等级:" + accidentGrade + ",";
//        String  pamVoltageCapacity = "电压容量:" + voltageCapacity + ",";
//        String  pamGridcorp = "地区:" + gridcorp + ",";
//        String  pamDeviceClass = "设备分类:" + deviceClass + ",";
//        String  pamStartTime = "计划开始时间::" + startTime + ",";
//        String  pamEndTime = "计划结束时间:" + endTime + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了分类统计(设备事件)"+BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!accidentGrade.isEmpty()){
//            reference.append(pamAccidentGrade);
//        }
//        if(!voltageCapacity.isEmpty()){
//            reference.append(pamVoltageCapacity);
//        }
//
//        if(!gridcorp.isEmpty()){
//            reference.append(pamGridcorp);
//        }
//
//        if(!deviceClass.isEmpty()){
//            reference.append(pamDeviceClass);
//        }
//
//        if(!startTime.isEmpty()){
//            reference.append(pamStartTime);
//        }
//
//        if(!endTime.isEmpty()){
//            reference.append(pamEndTime);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(accidentGrade.isEmpty()&&voltageCapacity.isEmpty()&&gridcorp.isEmpty()&&deviceClass.isEmpty()&&startTime.isEmpty()&&endTime.isEmpty()){
//            jk.get(0).put("logContent","查询了分类统计(设备事件),查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//
//    /**
//     * 全网安全事故事件分析:分类统计(人身事件) 2
//     */
//    public List<Map<String, String>> getPersonalEvents(HttpServletRequest request , String param,String datalog){
//        String accidentGrade = JSONObject.parseObject(param).getJSONObject("params").getString("accidentGrade");
//        String accidentClass = JSONObject.parseObject(param).getJSONObject("params").getString("accidentClass");
//        String injuryDegree = JSONObject.parseObject(param).getJSONObject("params").getString("injuryDegree");
//        String startTime = JSONObject.parseObject(param).getJSONObject("params").getString("startTime");
//        String endTime = JSONObject.parseObject(param).getJSONObject("params").getString("endTime");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ACCIDENT_STATISTICS);
//        String  pamAccidentGrade = "等级:" + accidentGrade + ",";
//        String  pamAccidentClass = "事故类别:" + accidentClass + ",";
//        String  pamInjuryDegree = "伤害程度:" + injuryDegree + ",";
//        String  pamStartTime = "计划开始时间::" + startTime + ",";
//        String  pamEndTime = "计划结束时间:" + endTime + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了分类统计(人身事件)"+BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!accidentGrade.isEmpty()){
//            reference.append(pamAccidentGrade);
//        }
//        if(!accidentClass.isEmpty()){
//            reference.append(pamAccidentClass);
//        }
//
//        if(!injuryDegree.isEmpty()){
//            reference.append(pamInjuryDegree);
//        }
//        if(!startTime.isEmpty()){
//            reference.append(pamStartTime);
//        }
//        if(!endTime.isEmpty()){
//            reference.append(pamEndTime);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(accidentGrade.isEmpty()&&accidentClass.isEmpty()&&injuryDegree.isEmpty()&&startTime.isEmpty()&&endTime.isEmpty()){
//            jk.get(0).put("logContent","查询了分类统计(人身事件),查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//    /**
//     * 全网安全事故事件分析:分类统计(电网事件) 3
//     */
//    public List<Map<String, String>> getPowerEvents(HttpServletRequest request , String param,String datalog){
//        String accidentGrade = JSONObject.parseObject(param).getJSONObject("params").getString("accidentGrade");
//        String voltageCapacity = JSONObject.parseObject(param).getJSONObject("params").getString("voltageCapacity");
//        String deviceClass = JSONObject.parseObject(param).getJSONObject("params").getString("deviceClass");
//        String startTime = JSONObject.parseObject(param).getJSONObject("params").getString("startTime");
//        String endTime = JSONObject.parseObject(param).getJSONObject("params").getString("endTime");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ACCIDENT_STATISTICS);
//        String  pamAccidentGrade = "等级:" + accidentGrade + ",";
//        String  pamVoltageCapacity = "电压容量:" + voltageCapacity + ",";
//        String  pamDeviceClass = "设备分类:" + deviceClass + ",";
//        String  pamStartTime = "计划开始时间::" + startTime + ",";
//        String  pamEndTime = "计划结束时间:" + endTime + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了分类统计(电网事件)"+BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!accidentGrade.isEmpty()){
//            reference.append(pamAccidentGrade);
//        }
//        if(!voltageCapacity.isEmpty()){
//            reference.append(pamVoltageCapacity);
//        }
//        if(!deviceClass.isEmpty()){
//            reference.append(pamDeviceClass);
//        }
//        if(!startTime.isEmpty()){
//            reference.append(pamStartTime);
//        }
//        if(!endTime.isEmpty()){
//            reference.append(pamEndTime);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(accidentGrade.isEmpty()&&voltageCapacity.isEmpty()&&deviceClass.isEmpty()&&startTime.isEmpty()&&endTime.isEmpty()){
//            jk.get(0).put("logContent","查询了分类统计(电网事件),查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//    /**
//     * 全网安全事故事件分析:分类统计(网络信息事件) 4
//     */
//    public List<Map<String, String>> getNetworkInformationEvents(HttpServletRequest request , String param,String datalog){
//        String accidentGrade = JSONObject.parseObject(param).getJSONObject("params").getString("accidentGrade");
//        String accidentClass = JSONObject.parseObject(param).getJSONObject("params").getString("accidentClass");
//        String deviceClass = JSONObject.parseObject(param).getJSONObject("params").getString("deviceClass");
//        String startTime = JSONObject.parseObject(param).getJSONObject("params").getString("startTime");
//        String endTime = JSONObject.parseObject(param).getJSONObject("params").getString("endTime");
//
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ACCIDENT_STATISTICS);
//        String  pamAccidentGrade = "等级:" + accidentGrade + ",";
//        String  pamAccidentClass = "事件分类:" + accidentClass + ",";
//        String  pamDeviceClass = "设备分类:" + deviceClass + ",";
//        String  pamStartTime = "计划开始时间::" + startTime + ",";
//        String  pamEndTime = "计划结束时间:" + endTime + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了分类统计(网络信息事件)"+BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!accidentGrade.isEmpty()){
//            reference.append(pamAccidentGrade);
//        }
//        if(!accidentClass.isEmpty()){
//            reference.append(pamAccidentClass);
//        }
//        if(!deviceClass.isEmpty()){
//            reference.append(pamDeviceClass);
//        }
//        if(!startTime.isEmpty()){
//            reference.append(pamStartTime);
//        }
//        if(!endTime.isEmpty()){
//            reference.append(pamEndTime);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(accidentGrade.isEmpty()&&accidentClass.isEmpty()&&deviceClass.isEmpty()&&startTime.isEmpty()&&endTime.isEmpty()){
//            jk.get(0).put("logContent","查询了分类统计(网络信息事件),查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//    /**
//     *外包单位作业风险画像
//     */
//    public List<Map<String, String>> getRiskPicture(HttpServletRequest request , String param,String datalog){
//        String datareportOrg = JSONObject.parseObject(param).getJSONObject("params").getString("datareportOrg");
//        String constructionOrg = JSONObject.parseObject(param).getJSONObject("params").getString("constructionOrg");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_PORTRAIT);
//        String  pamDatareportOrg = "省份:" + datareportOrg + ",";
//        String  pamConstructionOrg = "单位名称:" + constructionOrg + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了外包单位作业风险画像"+BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!datareportOrg.isEmpty()){
//            reference.append(pamDatareportOrg);
//        }
//        if(!constructionOrg.isEmpty()){
//            reference.append(pamConstructionOrg);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//
//        if(datareportOrg.isEmpty()&&constructionOrg.isEmpty()){
//            jk.get(0).put("logContent","查询了外包单位作业风险画像,查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//    /**
//     *知识图谱建模
//     */
//    public List<Map<String, String>> getKnowledgeModeling(HttpServletRequest request , String param,String datalog){
//        String typeName = JSONObject.parseObject(param).getString("typeName");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_MODELING);
//        String  pamTypeName = "名称:" + typeName + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了知识图谱建模"+BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!typeName.isEmpty()){
//            reference.append(pamTypeName);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//
//        if(typeName.isEmpty()){
//            jk.get(0).put("logContent","查询了知识图谱建模,查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//    /**
//    *  知识获取 实例
//     */
//    public List<Map<String, String>> getKnowledgeObtain(HttpServletRequest request , String param,String datalog){
//        String instanceName = JSONObject.parseObject(param).getJSONObject("params").getString("instanceName");
//        String modelingId = JSONObject.parseObject(param).getJSONObject("params").getString("modelingId");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_MODELING);
//        String  pamInstanceName = "名称:" + instanceName + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了知识获取");
//        /**
//         *  作业计划
//         */
//        if(modelingId.equals("1")){
//            reference.append("(实例列表，{作业计划})");
//        }
//        /**
//         *  外包单位
//         */
//        if(modelingId.equals("2")){
//            reference.append("(实例列表，{外包单位})");
//        }
//        /**
//         *  人员
//         */
//        if(modelingId.equals("3")){
//            reference.append("(实例列表，{人员})");
//        }
//        /**
//         *  风险
//         */
//        if(modelingId.equals("4")){
//            reference.append("(实例列表，{风险})");
//        }
//        /**
//         *  隐患
//         */
//        if(modelingId.equals("5")){
//            reference.append("(实例列表，{隐患})");
//        }
//        /**
//         *  事件
//         */
//        if(modelingId.equals("6")){
//            reference.append("(实例列表，{事件})");
//        }
//        /**
//         *  违章信息
//         */
//        if(modelingId.equals("7")){
//            reference.append("(实例列表，{违章信息})");
//        }
//
//        if(!instanceName.isEmpty()){
//            reference.append(BusinessLog.SECURITY_RISK_DATA_QUERY+pamInstanceName);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(instanceName.isEmpty() && modelingId.equals("1")){
//            jk.get(0).put("logContent","查询了知识获取(实例列表{作业计划}),查询条件为[]");
//        }
//
//        if(instanceName.isEmpty() && modelingId.equals("2")){
//            jk.get(0).put("logContent","查询了知识获取(实例列表{外包单位}),查询条件为[]");
//        }
//
//        if(instanceName.isEmpty() && modelingId.equals("3")){
//            jk.get(0).put("logContent","查询了知识获取(实例列表{人员}),查询条件为[]");
//        }
//
//        if(instanceName.isEmpty() && modelingId.equals("4")){
//            jk.get(0).put("logContent","查询了知识获取(实例列表{风险}),查询条件为[]");
//        }
//
//        if(instanceName.isEmpty() && modelingId.equals("5")){
//            jk.get(0).put("logContent","查询了知识获取(实例列表{隐患}),查询条件为[]");
//        }
//
//        if(instanceName.isEmpty() && modelingId.equals("6")){
//            jk.get(0).put("logContent","查询了知识获取(实例列表{事件}),查询条件为[]");
//        }
//
//        if(instanceName.isEmpty() && modelingId.equals("7")){
//            jk.get(0).put("logContent","查询了知识获取(实例列表{违章信息}),查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//    /**
//     * 知识图谱应用
//     */
//    public List<Map<String, String>> getKnowledgeTopology(HttpServletRequest request , String param,String datalog){
//        String instanceName = JSONObject.parseObject(param).getString("instanceName");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_APPLICATION);
//        String  pamInstanceName = "实例名称:" + instanceName + ",";
//        StringBuilder para = new StringBuilder();
//        StringBuilder reference = para.append("查询了知识图谱应用"+BusinessLog.SECURITY_RISK_DATA_QUERY);
//        if(!instanceName.isEmpty()){
//            reference.append(pamInstanceName);
//        }
//        reference.append("]");
//        jk.get(0).put("logContent", reference.toString());
//        if(instanceName.isEmpty()){
//            jk.get(0).put("logContent","查询了知识图谱应用,查询条件为[]");
//        }
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//
//
//
////    -------------------------------------------------------------------知识获取新增--------------------------------------------------------------------
//    /**
//     *  知识获取实例加系统日志
//     * {"params":{"modelingId":"1","instanceName":"测试423","instanceCode":"SL000313","instanceDescribe":"作业计划"},"idList":[{"instanceId":"218","relationId":"31"}]}
//     */
//    @Override
//    public int saveCommonInstanceInfoLog(HttpServletRequest request, String paramJson){
//        String modelingId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("modelingId");
//        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
//        String instanceCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceCode");
//        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
//        String datalog= BusinessLog.getDataLoadListLog();
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//
//        String typeCode = atlasDao.getTypeId(Const.SQL_DATA_GETTYPECODEBYID.replaceAll("%param%",modelingId));
//        StringBuilder param = new StringBuilder();
//        param.append("'").append(modelingId).append("','").append(instanceName)
//                .append("','").append(instanceDescribe).append("','").append(typeCode).append("','")
//                .append(instanceCode).append("'");
//        String id = atlasDao.getTypeId(Const.SQL_DATA_GETIDBYCODE.replaceAll("%param%", instanceCode));
//
//        /**
//         *  作业计划
//         */
//        if(modelingId.equals("1")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_PLAN_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  外包单位
//         */
//        if(modelingId.equals("2")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OUTSOURCING_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  人员
//         */
//        if(modelingId.equals("3")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_PERSONNEL_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  风险
//         */
//        if(modelingId.equals("4")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  隐患
//         */
//        if(modelingId.equals("5")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_HIDDEN_DANGER_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  事件
//         */
//        if(modelingId.equals("6")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EVENT_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  违章信息
//         */
//        if(modelingId.equals("7")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_VIOLATION_INSTAR + "[" + id + "]");
//        }
//        jk.get(0).put("operationType","1");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//    //        String sz = "{\"items\":"+  jk.toString() + "}";
//    //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    /**
//     *知识获取实例加系统日志
//     */
//    @Override
//    public int saveCommonInstanceInfoErroLog(HttpServletRequest request, String paramJson) {
//        String modelingId = JSONObject.parseObject(paramJson).getJSONObject("params").getString("modelingId");
//        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
//        String instanceCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceCode");
//        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
//        String datalog= BusinessLog.getDataLoadListLogError();
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        String typeCode = atlasDao.getTypeId(Const.SQL_DATA_GETTYPECODEBYID.replaceAll("%param%",modelingId));
//        StringBuilder param = new StringBuilder();
//        param.append("'").append(modelingId).append("','").append(instanceName)
//                .append("','").append(instanceDescribe).append("','").append(typeCode).append("','")
//                .append(instanceCode).append("'");
//        String id = atlasDao.getTypeId(Const.SQL_DATA_GETIDBYCODE.replaceAll("%param%", instanceCode));
//        /**
//         *  作业计划
//         */
//        if(modelingId.equals("1")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_PLAN_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  外包单位
//         */
//        if(modelingId.equals("2")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OUTSOURCING_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  人员
//         */
//        if(modelingId.equals("3")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_PERSONNEL_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  风险
//         */
//        if(modelingId.equals("4")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  隐患
//         */
//        if(modelingId.equals("5")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_HIDDEN_DANGER_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  事件
//         */
//        if(modelingId.equals("6")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EVENT_INSTAR + "[" + id + "]");
//        }
//        /**
//         *  违章信息
//         */
//        if(modelingId.equals("7")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_VIOLATION_INSTAR + "[" + id + "]");
//        }
//        jk.get(0).put("operationType","1");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//        //        String sz = "{\"items\":"+  jk.toString() + "}";
//        //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//
//
//
//
//    /**
//     *知识获取关系加系统日志
//     * {"params":{"modelingId":"1","instanceName":"测试423","instanceCode":"SL000313","instanceDescribe":"作业计划"},"idList":[{"instanceId":"218","relationId":"31"}]}
//     */
//    @Override
//    public int saveCommonRelationshipLog(HttpServletRequest request, String paramJson) {
//        String relationCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("relationCode");
//        String datalog= BusinessLog.getDataLoadListLog();
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
////        StringBuilder param = new StringBuilder();
////        param.append("'").append(relationCode).append("'");
//        String id= relationinfoDao.findRelationshipID(Const.SQL_DATA_FINDELATIONSHIPID.replaceAll("param", relationCode));
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_INSTAR + "[" + id + "]");
//        jk.get(0).put("operationType","1");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//        //        String sz = "{\"items\":"+  jk.toString() + "}";
//        //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    @Override
//    public int saveCommonRelationshipErrorLog(HttpServletRequest request, String paramJson){
//        String datalog= BusinessLog.getDataLoadListLogError();
//        String relationCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("relationCode");
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        String id= relationinfoDao.findRelationshipID(Const.SQL_DATA_FINDELATIONSHIPID.replaceAll("param", relationCode));
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_INSTAR + "[" + id + "]");
//        jk.get(0).put("operationType","1");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//        //        String sz = "{\"items\":"+  jk.toString() + "}";
//        //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     *知识图谱建模(新增)添加系统日志
//     */
//    @Override
//    public int saveKnowledgeModelingLog(HttpServletRequest request, String paramJson){
//        String typeCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeCode");
//        String datalog= BusinessLog.getDataLoadListLog();
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_MODELING);
//        String id= atlasDao.getAtlasId(Const.SQL_DATA_KNOWLEDGE_MODELING.replaceAll("param", typeCode));
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_KNOWLEDGE_MODELING_INSTAR + "[" + id + "]");
//        jk.get(0).put("operationType","1");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//        //        String sz = "{\"items\":"+  jk.toString() + "}";
//        //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    @Override
//    public int saveKnowledgeModelingLogErro(HttpServletRequest request, String paramJson){
//        String typeCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("typeCode");
//        String datalog= BusinessLog.getDataLoadListLogError();
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_MODELING);
//        String id= atlasDao.getAtlasId(Const.SQL_DATA_KNOWLEDGE_MODELING.replaceAll("param", typeCode));
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_KNOWLEDGE_MODELING_INSTAR + "[" + id + "]");
//        jk.get(0).put("operationType","1");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//        //        String sz = "{\"items\":"+  jk.toString() + "}";
//        //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    /**
//     * 单位基本信息
//     * @param request
//     * @param data
//     * @return
//     */
//    @Override
//    public int essentialInformationLog(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.COMPANY_INFORTION);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//    @Override
//    public int essentialInformationLogErro(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.COMPANY_INFORTION);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//
//
//
//    /**
//     *安全资信
//     */
//    @Override
//    public int securityCreditLog(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.SECURITY_CREDIT);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int securityCreditErroLog(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.SECURITY_CREDIT);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//    /**
//     *全网信息联动
//     */
//    @Override
//    public int informationLinkageLog(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.INFORMATION_LINKAGE);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int informationLinkageErroLog(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.INFORMATION_LINKAGE);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//    /**
//     *安全能力分析
//     */
//    @Override
//    public int abilityanAlysisLog(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.SAFETY_ANALYSIS);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        //方案一：新建表新增日志入库
////        StringBuilder paramr = new StringBuilder();
////        paramr.append("'").append(jk.get(0).get("logContent")).append("','").append(jk.get(0).get("moduleName")).append("','").append(jk.get(0).get("logIp")).append("','").append(jk.get(0).get("operationType")).append("','").append(jk.get(0).get("logOn")).append("','").append("logUserName").append("','").append("logLevel").append("','").append("dataType").append("',").append("errorType").append("'");
////        String i = Const.SQL_SYS_INSSERTLOG.replaceAll("%paramr%", paramr.toString());
////        businessLogUnifiedService.saveSysLog(jk.get(0).get("logContent"),jk.get(0).get("moduleName"),jk.get(0).get("logIp"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        //方案二：转化为对象数组日志传参
////        String sz = "{\"items\":"+  jk.toString() + "}";
////      String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int abilityanAlysisLogErro(HttpServletRequest request, String data) {
//        String datalog= BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = getCreditInformationAnalysis(request ,data,datalog,BusinessLog.SAFETY_ANALYSIS);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//
//
//    /**
//     * 外包单位作业风险画像:单位基本信息、安全资信、全网信息联动、安全能力分析
//     */
//    public List<Map<String, String>> getCreditInformationAnalysis(HttpServletRequest request , String param,String datalog,String menuType){
////        String jobName = JSONObject.parseObject(param).getJSONObject("params").getString("id");
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        /**
//         *基本信息
//          */
//        if(menuType.equals(BusinessLog.COMPANY_INFORTION)){
//            jk.get(0).put("module",BusinessLog.SECURITY_PORTRAIT);
//        }
//        /**
//         *安全资信
//         */
//
//        if(menuType.equals(BusinessLog.SECURITY_CREDIT)){
//            jk.get(0).put("module",BusinessLog.SECURITY_PORTRAIT);
//        }
//        /**
//         *全网信息联动
//         */
//        if(menuType.equals(BusinessLog.INFORMATION_LINKAGE)){
//            jk.get(0).put("module",BusinessLog.SECURITY_PORTRAIT);
//        }
//        /**
//         *安全能力分析
//         */
//        if(menuType.equals(BusinessLog.SAFETY_ANALYSIS)){
//            jk.get(0).put("module",BusinessLog.SECURITY_PORTRAIT);
//        }
//        jk.get(0).put("logContent","查询了" + menuType +",查询条件为[]");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        return jk;
//    }
//
//// --------------------------------------------------------------------编辑-------------------------------------------------------------------------------------------------------------
//    /**
//     *知识获取(实例：修改)     {"params":{"instanceId":314,"instanceName":"0423测试","instanceDescribe":"作业计划修改"},"idList":[{"instanceId":"319","relationId":"31"}],"modelingId":"1"}
//     */
//    @Override
//    public int updateCommonInstanceInfoLog(HttpServletRequest request, String paramJson){
////        String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
////        JSONArray jsonArray = JSONObject.parseObject(paramJson).getJSONArray("params");
//        String id = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceId");
//        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
//        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
//        String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
//        String datalog= BusinessLog.getDataLoadListLog();
//        //实例名称
////        String slName= atlasDao.FindInstanceInfoName(Const.SQL_DATA_EXAMPLE.replaceAll("param", id));
//        List<Map<String, Object>> listExample = atlasDao.getExampleList(Const.SQL_DATA_EXAMPLE_ALL.replaceAll("param" , id));
//        String INSTANCE_NAME=null;
//        String INSTANCE_DESCRIBE=null;
////        String INSTANCE_SOURCE=null;
////        String INSTANCE_CODE=null;
////        String UPDATE_TIME=null;
//
//        for(int i=0; i < listExample.size();i++){
//             INSTANCE_NAME =  listExample.get(0).get("INSTANCE_NAME").toString();
//             INSTANCE_DESCRIBE = listExample.get(0).get("INSTANCE_DESCRIBE").toString();
////             INSTANCE_SOURCE =listExample.get(0).get("INSTANCE_SOURCE").toString();
////             INSTANCE_CODE =listExample.get(0).get("INSTANCE_CODE").toString();
////             UPDATE_TIME =listExample.get(0).get("UPDATE_TIME").toString();
//        }
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("operationType","2");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        /**
//         *  作业计划
//         */
//        if(modelingId.equals("1")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  外包单位
//         */
//        if(modelingId.equals("2")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OUTSOURCING_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  人员
//         */
//        if(modelingId.equals("3")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_PERSONNEL_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  风险
//         */
//        if(modelingId.equals("4")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  隐患
//         */
//        if(modelingId.equals("5")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_HIDDEN_DANGER_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  事件
//         */
//        if(modelingId.equals("6")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EVENT_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  违章信息
//         */
//        if(modelingId.equals("7")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_VIOLATION_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        jk.get(0).put("operationType","2");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//        //        String sz = "{\"items\":"+  jk.toString() + "}";
//        //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//
//    @Override
//    public int updateCommonInstanceInfoLogErro(HttpServletRequest request, String paramJson) {
////        String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
////        JSONArray jsonArray = JSONObject.parseObject(paramJson).getJSONArray("params");
//        String id = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceId");
//        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
//        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
//        String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
//        String datalog= BusinessLog.getDataLoadListLogError();
//        //实例名称
////        String slName= atlasDao.FindInstanceInfoName(Const.SQL_DATA_EXAMPLE.replaceAll("param", id));
//        List<Map<String, Object>> listExample = atlasDao.getExampleList(Const.SQL_DATA_EXAMPLE_ALL.replaceAll("param" , id));
//        String INSTANCE_NAME=null;
//        String INSTANCE_DESCRIBE=null;
////        String INSTANCE_SOURCE=null;
////        String INSTANCE_CODE=null;
////        String UPDATE_TIME=null;
//
//        for(int i=0; i < listExample.size();i++){
//            INSTANCE_NAME =  listExample.get(0).get("INSTANCE_NAME").toString();
//            INSTANCE_DESCRIBE = listExample.get(0).get("INSTANCE_DESCRIBE").toString();
////            INSTANCE_SOURCE =listExample.get(0).get("INSTANCE_SOURCE").toString();
////            INSTANCE_CODE =listExample.get(0).get("INSTANCE_CODE").toString();
////            UPDATE_TIME =listExample.get(0).get("UPDATE_TIME").toString();
//        }
//        //方案一：新建表新增日志入库
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("operationType","2");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        /**
//         *  作业计划
//         */
//        if(modelingId.equals("1")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  外包单位
//         */
//        if(modelingId.equals("2")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OUTSOURCING_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  人员
//         */
//        if(modelingId.equals("3")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_PERSONNEL_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  风险
//         */
//        if(modelingId.equals("4")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  隐患
//         */
//        if(modelingId.equals("5")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_HIDDEN_DANGER_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  事件
//         */
//        if(modelingId.equals("6")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EVENT_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        /**
//         *  违章信息
//         */
//        if(modelingId.equals("7")){
//            jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_VIOLATION_MODIFY +",实例名称为:["+ INSTANCE_NAME +"],修改属性为[ "+ instanceName + "]," + "实例描述为:["+ INSTANCE_DESCRIBE +"],修改属性为[ "+ instanceDescribe + "]," +"唯一标识为" + "[" + id + "]");
//        }
//        jk.get(0).put("operationType","2");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        //方案二：转化为对象数组日志传参
//        //        String sz = "{\"items\":"+  jk.toString() + "}";
//        //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//        return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
////-----------------------------------------------------------知识获取导入--------------------------------------------------------------------------------------------------------
//    /**
//     *知识获取(实例：导入)
//     */
//    @Override
//    public int ImportCommonInstanceInfoLog(HttpServletRequest request, HttpServletResponse response) {
//         String datalog = BusinessLog.getDataLoadListLog();
//         List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//         jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_PLAN_IMPORT+BusinessLog.SECURITY_RISK_EXAMOLE_NUMBER +"条数据");
//         jk.get(0).put("operationType","6");
//         String ip = request.getRemoteAddr();
//         jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int ImportCommonInstanceInfoLogErro(HttpServletRequest request, HttpServletResponse response) {
//        String datalog = BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_PLAN_IMPORT+BusinessLog.SECURITY_RISK_EXAMOLE_NUMBER +"条数据");
//        jk.get(0).put("operationType","6");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    /**
//     *知识获取(关系：导入)
//     */
//    @Override
//    public int ImportRelationshipLog(HttpServletRequest request, HttpServletResponse response) {
//        String datalog = BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_IMPORT+BusinessLog.SECURITY_RISK_RELATIONSHIP_NUMBER +"条数据");
//        jk.get(0).put("operationType","6");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    @Override
//    public int ImportRelationshipLogErro(HttpServletRequest request, HttpServletResponse response) {
//        String datalog = BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_IMPORT+BusinessLog.SECURITY_RISK_RELATIONSHIP_NUMBER +"条数据");
//        jk.get(0).put("operationType","6");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
////-------------------------------------------------------------------模板下载----------------------------------------------------------------------------
//    /**
//     * 实例模板下载
//     * @param request
//     * @param
//     * @param paramJson
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public int exampleTemplateDownloadLog(HttpServletRequest request, String paramJson) {
//        String mbName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("mbName");
//        String datalog = BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EXAMPLE_IMPORT_TEMPLATE  + mbName + "]");
//        jk.get(0).put("operationType","12");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//    @Override
//    public int exampleTemplateDownloadLogErro(HttpServletRequest request, String paramJson){
//        String mbName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("mbName");
//        String datalog = BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EXAMPLE_IMPORT_TEMPLATE + mbName + "]");
//        jk.get(0).put("operationType","12");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//
//    /**
//     * 关系模板下载
//     * @param request
//     * @param
//     * @param
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public int relationshipTemplateDownloadLog(HttpServletRequest request,  String paramJson){
//        String mbName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("mbName");
//        String datalog = BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_IMPORT_TEMPLATE  + mbName + "]");
//        jk.get(0).put("operationType","12");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    @Override
//    public int relationshipTemplateDownloadLogErro(HttpServletRequest request,  String paramJson){
//
//        String mbName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("mbName");
//        String datalog = BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_IMPORT_TEMPLATE + mbName + "]");
//        jk.get(0).put("operationType","12");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//
//    }
//
//
//
//
////-------------------------------------------------------------------导出----------------------------------------------------------------------------
//    /**
//     *知识获取(实例：导出)
//     */
//    @Override
//    public int exportCommonInstanceInfoLog(HttpServletRequest request, HttpServletResponse response,String data) {
//        String datalog = BusinessLog.getDataLoadListLog();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        //获取文件名
////        JSONObject rowData = JSONObject.parseObject(data);
//        String fileName= JSONObject.parseObject(data).getString("fileName");
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_EXPORT+"excel表格"+"文件名称为[" + fileName +".xls]," + "导出条件为空");
//        jk.get(0).put("operationType","6");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//    @Override
//    public int exportCommonInstanceInfoLogErro(HttpServletRequest request, HttpServletResponse response,String data){
//        String datalog = BusinessLog.getDataLoadListLogError();
//        List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//        //获取文件名
////        JSONObject rowData = JSONObject.parseObject(data);
//        String fileName= JSONObject.parseObject(data).getString("fileName");
//        jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_RELATIONSHIP_EXPORT+"excel表格"+"文件名称为[" + fileName +".xls]," + "导出条件为空");
//        jk.get(0).put("operationType","6");
//        String ip = request.getRemoteAddr();
//        jk.get(0).put("logIp",ip);
//        String replace = UUID.randomUUID().toString().replace("-", "");
//        jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//        //        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        //        MultipartFile file = multipartRequest.getFile("file");
//        return businessLogUnifiedDao.saveSysLog(replace,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//    }
//
//
//
////    -----------------------------------------------------------------删除---------------------------------------------------------------------------
//    /**
//     * 知识获取（实例删除）
//     * @param request
//     * @param   {"params":[{"instanceId":313}]}    {"params":[{"instanceId":322},{"instanceId":321},{"instanceId":324}]}    {"params":[{"instanceId":340},{"instanceId":339}],"modelingId":"2"}
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public int delCommonInstanceInfoLog(HttpServletRequest request, String paramJson){
//            String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
////        String instanceName = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceName");
////        String instanceCode = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceCode");
////        String instanceDescribe = JSONObject.parseObject(paramJson).getJSONObject("params").getString("instanceDescribe");
////        List<Map<String, String>> idList = JSON.parseObject(jsonArray.toString(), List.class);
//        if(JSONObject.parseObject(paramJson).getJSONArray("params")!=null && !JSONObject.parseObject(paramJson).getJSONArray("params").isEmpty()){
//
//        for(int i = 0;i < JSONObject.parseObject(paramJson).getJSONArray("params").size();i++){
//            JSONObject jsonData = (JSONObject) JSONObject.parseObject(paramJson).getJSONArray("params").get(i);
//            String id = jsonData.getString("instanceId");
//            //实例名称
//            String slName= BusinessLog.DEL_EXAMPLE;
//            String datalog= BusinessLog.getDataLoadListLog();
//            //方案一：新建表新增日志入库
//            List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//            jk.get(0).put("operationType","3");
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//            /**
//             *  作业计划
//             */
//            if(modelingId.equals("1")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_PLAN_DEL +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  外包单位
//             */
//            if(modelingId.equals("2")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OUTSOURCING_DEL +"名称为:[:"+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  人员
//             */
//            if(modelingId.equals("3")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_PERSONNEL_DEL +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  风险
//             */
//            if(modelingId.equals("4")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_DEL +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  隐患
//             */
//            if(modelingId.equals("5")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_HIDDEN_DANGER_DEL +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  事件
//             */
//            if(modelingId.equals("6")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EVENT_DEL +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  违章信息
//             */
//            if(modelingId.equals("7")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_VIOLATION_DEL +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            jk.get(0).put("operationType","1");
//            String ip = request.getRemoteAddr();
//            jk.get(0).put("logIp",ip);
//            //方案二：转化为对象数组日志传参
//            //        String sz = "{\"items\":"+  jk.toString() + "}";
//            //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//             businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        }
//        }
//        return 1;
//    }
//
//    @Override
//    public int delCommonInstanceInfoLogErro(HttpServletRequest request, String paramJson) {
//        String modelingId = JSONObject.parseObject(paramJson).getString("modelingId");
//        for(int i = 0;i < JSONObject.parseObject(paramJson).getJSONArray("params").size();i++){
//            String datalog= BusinessLog.getDataLoadListLogError();
//            JSONObject jsonData = (JSONObject) JSONObject.parseObject(paramJson).getJSONArray("params").get(i);
//            String id = jsonData.getString("instanceId");
//            //实例名称
//            String slName= atlasDao.FindInstanceInfoName(Const.SQL_DATA_EXAMPLE.replaceAll("param", id));
//            //方案一：新建表新增日志入库
//            List<Map<String, String>> jk = JSON.parseObject(datalog, List.class);
//            jk.get(0).put("operationType","3");
//            jk.get(0).put("module",BusinessLog.SECURITY_RISK_ATLAS_KNOWLEDGE);
//            /**
//             *  作业计划
//             */
//            if(modelingId.equals("1")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OPERATION_PLAN_INSTAR  +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  外包单位
//             */
//            if(modelingId.equals("2")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_OUTSOURCING_INSTAR  +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  人员
//             */
//            if(modelingId.equals("3")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_PERSONNEL_INSTAR  +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  风险
//             */
//            if(modelingId.equals("4")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_INSTAR  +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  隐患
//             */
//            if(modelingId.equals("5")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_HIDDEN_DANGER_INSTAR  +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  事件
//             */
//            if(modelingId.equals("6")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_EVENT_INSTAR  +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            /**
//             *  违章信息
//             */
//            if(modelingId.equals("7")){
//                jk.get(0).put("logContent",BusinessLog.SECURITY_RISK_VIOLATION_INSTAR  +"名称为:["+slName +"]，唯一标识为" + "[" + id + "]");
//            }
//            jk.get(0).put("operationType","1");
//            String ip = request.getRemoteAddr();
//            jk.get(0).put("logIp",ip);
//            //方案二：转化为对象数组日志传参
//            //        String sz = "{\"items\":"+  jk.toString() + "}";
//            //        String msg = HttpUtil.sendRequestJson(BusinessLog.VISIT_ADDRESS, sz);
//            return businessLogUnifiedDao.saveSysLog(id,jk.get(0).get("logContent"),jk.get(0).get("module"),jk.get(0).get("logIp"),jk.get(0).get("operationType"),jk.get(0).get("logOn"),jk.get(0).get("logUserName"),jk.get(0).get("logLevel"),jk.get(0).get("dataType"),jk.get(0).get("errorType"));
//        }
//        return 1;
//
//    }
//    /**
//     * 根据数据id 获取数据日志操作信息
//     * {
//     * "id":"29f47e4ae796464a879de88dd9635040",
//     * "page":"1",
//     * "size":"10"
//     * }
//     * @param paramJson
//     * @return
//     */
//    @Override
//    public PageBean<Map<String, Object>> findLogById(String paramJson) {
//        int page = Integer.parseInt(JSONObject.parseObject(paramJson).getString("page"));
//        int size = Integer.parseInt(JSONObject.parseObject(paramJson).getString("size"));
//        String id = JSONObject.parseObject(paramJson).getString("id");
//        if (page <= 0) {
//            throw new RuntimeException("当前页数必须大于1");
//        } else if (size <= 0) {
//            throw new RuntimeException("每页大小必须大于1");
//        } else{
//            StringBuilder param = new StringBuilder();
//            if(ObjectUtils.isNotEmpty(id)){
//                param.append("id = '").append(id).append("'");
//            }
//            int totalSize = this.businessLogUnifiedDao.getSysLogCNT(Const.SQL_SYS_FINDLOGCUT.replaceAll("%param%",param.toString()));
//            if (totalSize == 0) {
//                return PageBean.<Map<String, Object>>builder()
//                        .content(new ArrayList<>())
//                        .elementTotalSize(0)
//                        .page(0)
//                        .size(0)
//                        .totalPage(0)
//                        .totalSize(0)
//                        .build();
//            }else {
//                int offset = (page - 1) * size;
//                param.append(" limit " + size + " offset " + offset);
//                return PageBean.<Map<String, Object>>builder()
//                        .content(this.businessLogUnifiedDao.getSysLogList(Const.SQL_SYS_FINDLOG.replaceAll("%param%", param.toString())))
//                        .elementTotalSize(this.businessLogUnifiedDao.getSysLogList(Const.SQL_SYS_FINDLOG.replaceAll("%param%", param.toString())).size())
//                        .totalSize(totalSize)
//                        .totalPage(totalSize % size == 0 ? totalSize / size : totalSize / size + 1)
//                        .page(page)
//                        .size(size)
//                        .build();
//            }
//        }
//    }
//
//    /**
//     * 查询省份信息
//     * @param
//     * @return
//     */
//    @Override
//    public List<Map<String, Object>> getProvinceList() {
//        return businessLogUnifiedDao.getProvinceList();
//    }
//
//}
