package com.raysdata.riskdataanalyzeserver.utils;

/**
* 日志共有参数封装工具类
*/
public class BusinessLog {
//   public static final String SECURITY_RISK_DATA_LOADING = "安全生产大数据分析/数据整合/数据加载";
//    public static final String SECURITY_RISK_DATA_TRANSFORMATION = "安全生产大数据分析/数据整合/数据转换";
//    public static final String SECURITY_RISK_DATA_CLEAN = "安全生产大数据分析/数据整合/数据清洗";
//    public static final String SECURITY_RISK_DATA_GOVERNMENT = "安全生产大数据分析/数据整合/数据治理";
//
//
//    public static final String SECURITY_RISK_WARNING_STATISTICS = "安全生产大数据分析/全网安全工器具预警分析/安全工器具数据统计";
//    public static final String SECURITY_RISK_WARNING_REAL = "安全生产大数据分析/全网安全工器具预警分析/安全工器具实时预警";
//    public static final String SECURITY_RISK_WARNING_EVALUATE = "安全生产大数据分析/全网安全工器具预警分析/安全工器具供应商评价";
//
//    public static final String SECURITY_RISK_ACCIDENT_STATISTICS = "安全生产大数据分析/全网安全事故事件分析/分类统计";
//    public static final String SECURITY_RISK_ACCIDENT_CONTRAST = "安全生产大数据分析/全网安全事故事件分析/多维度展现对比";
//    public static final String SECURITY_RISK_ACCIDENT_FORECAST = "安全生产大数据分析/全网安全事故事件分析/趋势预测分析";
//
//    public static final String SECURITY_RISK_ABILITY_BASIC = "安全生产大数据分析/全网人员安全能力分析/人员基本情况";
//    public static final String SECURITY_RISK_ABILITY_CREDIT = "安全生产大数据分析/全网人员安全能力分析/人员安全资信";
//    public static final String SECURITY_RISK_ABILITY_FORECAST = "安全生产大数据分析/全网人员安全能力分析/趋势预测分析";
//    public static final String SECURITY_RISK_ABILITY_INFORMATION = "安全生产大数据分析/全网人员安全能力分析/人员全网信息联动";
//
//    public static final String SECURITY_PORTRAIT = "安全生产大数据分析/外包单位作业风险画像";
//
//    public static final String SECURITY_RISK_ATLAS_KNOWLEDGE = "安全生产大数据分析/安全风险知识图谱/知识获取";
//    public static final String SECURITY_RISK_ATLAS_MODELING = "安全生产大数据分析/安全风险知识图谱/知识图谱建模";
//    public static final String SECURITY_RISK_ATLAS_FUSE = "安全生产大数据分析/安全风险知识图谱/知识融合";
//    public static final String SECURITY_RISK_ATLAS_APPLICATION = "安全生产大数据分析/安全风险知识图谱/知识图谱应用";
//
//    public static final String DATA_LOADING = "数据加载";
//    public static final String DATA_TRANSFORMATION = "数据转换";
//    public static final String DATA_CLEAN = "数据清洗";
//    public static final String DATA_GOVERNMENT = "数据治理";
//
//
//
//    public static final String COMPANY_INFORTION = "单位基本信息";
//    public static final String SECURITY_CREDIT = "安全资信";
//    public static final String INFORMATION_LINKAGE = "全网信息联动";
//    public static final String SAFETY_ANALYSIS = "安全能力分析";
//
//    public static final String BASC_INFORMATION = "人员基本情况";
//    public static final String SAFETY_CREDIT = "人员安全资信";
//    public static final String INFORMATION_LINKGE = "人员全网信息联动";

    /*
    public static final String SECURITY_RISK_OPERATION_PLAN_INSTAR= "新建了[知识获取]的实例信息作业计划，唯一标识为";
    public static final String SECURITY_RISK_OUTSOURCING_INSTAR= "新建了[知识获取]的实例信息外包单位，唯一标识为";
    public static final String SECURITY_RISK_PERSONNEL_INSTAR= "新建了[知识获取]的实例信息人员，唯一标识为";
    public static final String SECURITY_RISK_INSTAR= "新建了[知识获取]的实例信息风险，唯一标识为";
    public static final String SECURITY_RISK_HIDDEN_DANGER_INSTAR= "新建了[知识获取]的实例信息隐患，唯一标识为";
    public static final String SECURITY_RISK_EVENT_INSTAR= "新建了[知识获取]的实例信息事件，唯一标识为";
    public static final String SECURITY_RISK_VIOLATION_INSTAR= "新建了[知识获取]的实例违章信息，唯一标识为";
    public static final String SECURITY_RISK_OPERATION_PLAN_DEL= "删除了[知识获取]的实例信息作业计划";
    public static final String SECURITY_RISK_OUTSOURCING_DEL= "删除了[知识获取]的实例信息外包单位";
    public static final String SECURITY_RISK_PERSONNEL_DEL= "删除了[知识获取]的实例信息人员";
    public static final String SECURITY_RISK_DEL= "删除了[知识获取]的实例信息风险";
    public static final String SECURITY_RISK_HIDDEN_DANGER_DEL= "删除了[知识获取]的实例信息隐患";
    public static final String SECURITY_RISK_EVENT_DEL= "删除了[知识获取]的实例信息事件";
    public static final String SECURITY_RISK_VIOLATION_DEL= "删除了[知识获取]的实例违章信息";
    public static final String SECURITY_RISK_OPERATION_MODIFY = "编辑了[知识获取]的实例信息作业计划";
    public static final String SECURITY_RISK_OUTSOURCING_MODIFY= "编辑了[知识获取]的实例信息外包单位";
    public static final String SECURITY_RISK_PERSONNEL_MODIFY= "编辑了[知识获取]的实例信息人员";
    public static final String SECURITY_RISK_MODIFY= "编辑了[知识获取]的实例信息风险";
    public static final String SECURITY_RISK_HIDDEN_DANGER_MODIFY= "编辑了[知识获取]的实例信息隐患";
    public static final String SECURITY_RISK_EVENT_MODIFY= "编辑了[知识获取]的实例信息事件";
    public static final String SECURITY_RISK_VIOLATION_MODIFY= "编辑了[知识获取]的实例违章信息";
    public static final String SECURITY_RISK_RELATIONSHIP_INSTAR = "新建了[知识获取]的关系信息，唯一表示为";
    public static final String SECURITY_RISK_KNOWLEDGE_MODELING_INSTAR = "新建了[知识图谱建模]，唯一表示为";
    public static final String SECURITY_RISK_RELATIONSHIP_DEL = "删除了[知识获取]的关系信息，唯一表示为";
    public static final String SECURITY_RISK_DATA_QUERY = ",查询条件为[";
    public static final String SECURITY_RISK_UPDATE= "编辑了[知识获取]的实例信息";
    public static final String SECURITY_RISK_RELATIONSHIP_UPDATE = "编辑了[知识获取]的关系信息";

    public static String SECURITY_RISK_EXAMOLE_NUMBER = "";
    public static String SECURITY_RISK_RELATIONSHIP_NUMBER = "";

    public static final String SECURITY_RISK_OPERATION_PLAN_IMPORT= "导入了知识获取实例列表数据,共导入";
    public static final String SECURITY_RISK_RELATIONSHIP_IMPORT= "导入了知识获取关系列表数据,共导入";
    public static final String SECURITY_RISK_RELATIONSHIP_EXPORT = "导出了知识获取关系列";

    public static final String SECURITY_RISK_EXAMPLE_IMPORT_TEMPLATE = "下载了知识获取模板实例的导入模板，名称为[";
    public static final String SECURITY_RISK_RELATIONSHIP_IMPORT_TEMPLATE = "下载了知识获取模板实例的导入模板，名称为[";

    //实例删除名称
    public static String DEL_EXAMPLE = "";






    *//**
     * 外部系统请求
     *//*
    public static final String VISIT_ADDRESS = "http://172.16.20.85:8080/AJuap/AJSevenInterface/rest/appLog/saveAppLog";

    public static final ObjectMapper mapper = new ObjectMapper();
    *//**
     * 查询
     * @return
     * @throws Exception
     *//*
    @SneakyThrows
    public static String getDataLoadListLog(){
        Map<String, String> inputParams = new HashMap<String, String>();
        List<Map<String, String>> list = new ArrayList<>();
        inputParams.put("module", "");
        inputParams.put("logContent", "");
        inputParams.put("operationType", "4");
        inputParams.put("logOn", "1");
        inputParams.put("logType", "2");
        inputParams.put("logLevel", "0");
        inputParams.put("logIp", "");
        inputParams.put("logName", "");
        inputParams.put("operationType", "4");
        inputParams.put("logOn", "1");
        inputParams.put("loguserId", "");
        inputParams.put("loguserName", "");
        inputParams.put("logLevel", "0");
        inputParams.put("logType", "0");
        inputParams.put("dataType", "0");
        inputParams.put("errorType", "0");
        inputParams.put("logTime", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
//        String inputParamsStr = mapper.writeValueAsString(inputParams);
        list.add(inputParams);
        String aa = mapper.writeValueAsString(list);
        return aa;
//        return "{" + "\"items\":" + aa + "}";
    }

    *//**
     * 查询异常
     * @return
     * @throws Exception
     *//*
    @SneakyThrows
    public static String getDataLoadListLogError(){
        Map<String, String> inputParams = new HashMap<String, String>();
        List<Map<String, String>> list = new ArrayList<>();
        inputParams.put("module", "");
        inputParams.put("logContent", "");
        inputParams.put("operationType", "4");
        inputParams.put("logOn", "1");
        inputParams.put("logType", "2");
        inputParams.put("logLevel", "0");
        inputParams.put("logIp", "");
        inputParams.put("logName", "");
        inputParams.put("operationType", "4");
        inputParams.put("logOn", "0");
        inputParams.put("loguserId", "");
        inputParams.put("loguserName", "");
        inputParams.put("logLevel", "1");
        inputParams.put("logType", "0");
        inputParams.put("dataType", "1");
        inputParams.put("errorType", "1");
        inputParams.put("logTime", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()));
//        String inputParamsStr = mapper.writeValueAsString(inputParams);
        list.add(inputParams);
        String aa = mapper.writeValueAsString(list);
        return aa;
    }*/

    public static final String QUERY = "用户【";
    public static final String IP = "ip为【";
    public static final String ONTIME = "ip为【";
    public static final String ON = "进行了【";
    public static final String TYPE_QUERY = "查询条件为【";
    public static final String TYPE_p = "】";
    public static final String ALL_TIME = "操作;业务处理共用时:[";
    public static final String HM = "]毫秒；";
    public static final String CZRESULT = "操作结果为:【";
    public static final String FH = "】";
    public static final String ZSJ = " ,在时间【";
    public static final String GLSXON = "进行了关联属性的【";
    public static final String WYBS ="对唯一标识为";

    //实例删除名称
    public static String DEL_EXAMPLE = "";
    //id
    public static String INSTANCEID = "";

    public static String SAVE_PARAMTER = "";

    public static final String TYPE_SAVE = " 新增了【";
    public static final String XZS = " 的数据；";

    public static final String TYPE_DEL = " 删除了【";
    public static final String TYPE_MODIFY = " 修改了【";
    public static String TYPE_EXPORTS = " 导出了【";
    public static String TYPE_IMPORT = " 导入了【";
    public static String DONLOD_IMPORT = " 下载了【";
    public static String Import_FILENAME = "";

    public static String TYPE_EXPORT = "";
    public static String Import_EXAMPLE_FILENAME = "";
    public static String Import_TYPENAME = "";
    public static final String CZ = "的操作,";
    public static final String TO = " 对";
    public static final String PAGE = "页面,";
    public static String SHEET = "";
    public static String EventType= "";
    public static String TimeFormat="";

    public static String modify="";

    public static String csts="";
    public static String csts1="";
    public static String csts11="";
    public static String csts12="";
    public static String csts13="";
    public static String csts14="";
    public static String csts15="";
    public static String csts16="";
    public static String csts17="";












    public static String instanceInfos="";

    public static String INSTANCE_NAME="";
    public static String RELATION_ID="";
    public static String INSTANCE_ID="";
    public static String Instance="";
    public static String WEIYIBIAOSHI="";
    public static String XIUGAI="";

    public static String oldName="";
    public static String oldDsc="";
    public static String glgxName="";
    public static String ylinsName="";




}
