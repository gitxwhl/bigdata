package com.raysdata.riskdataanalyzeserver.aop;
import com.alibaba.fastjson.JSON;
import com.raysdata.riskdataanalyzeserver.bean.*;
import com.raysdata.riskdataanalyzeserver.mapper.AtlasMapper;
import com.raysdata.riskdataanalyzeserver.mapper.OpLogInfoMapper;
import com.raysdata.riskdataanalyzeserver.utils.BusinessLog;
import com.raysdata.riskdataanalyzeserver.utils.DataConstants;
import com.raysdata.riskdataanalyzeserver.utils.IPUtil;
import com.raysdata.riskdataanalyzeserver.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Autowired
   private OpLogInfoMapper opLogInfoMapper;
    @Autowired
    private AtlasMapper atlasMapper;

    @Value(value = "${logSwitch}")
    private boolean logSwitch;

    @Pointcut("execution(public * com.raysdata.riskdataanalyzeserver.controller.*.*(..))")
    public void uvpLog() {
    }

    @Pointcut("@annotation(com.raysdata.riskdataanalyzeserver.aop.LogAnnotation)")
    public void pt(){}


    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        Object obj= joinPoint.proceed(joinPoint.getArgs());
//        stopWatch.stop();
//        long cost= stopWatch.getTotalTimeMillis();
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        String methodName= signature.getDeclaringTypeName()+ "." + signature.getName();
//        System.out.println("--------------------->>>>>>>  >执行" +methodName +"方法，用时"+cost+"ms<<<<<<<<<-------------------" );
//        System.out.println("--------------------->>>>>>>>执行" +methodName +"方法，用时"+cost+"ms<<<<<<<<<-------------------" );
//        System.out.println("--------------------->>>>>>>>执行" +methodName +"方法，用时"+cost+"ms<<<<<<<<<-------------------" );
//        System.out.println("--------------------->>>>>>>>执行" +methodName +"方法，用时"+cost+"ms<<<<<<<<<-------------------" );
//   ================================================================================================

        boolean logSwitchs=logSwitch;
        //保存日志
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = joinPoint.proceed();
        //执行时长
        long time= System.currentTimeMillis() - beginTime;
//        System.out.println(methodName  +"---------------》》》》》》》切面方法执行时长《《《《《《《《《-------------- "+ time+"ms");
        if(logSwitch) {
            recordLog(joinPoint, time, result, beginTime);
        }
        return result;
    }

    @Transactional
    public void recordLog(ProceedingJoinPoint joinPoint,long time,Object result,long beginTime){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String userId = UserUtil.getUserId(request);
        String userId = "0914a7add15611e688cc54ee7536cc52";
//        String   userName= UserUtil.getUserName(request);
        String userIp = IPUtil.getIpAddress(request);
        //获取用户信息
        PtUaUser ptUaUser = opLogInfoMapper.findUser(userId);
        OpLogInfo opLogInfo = new  OpLogInfo();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String controllerFullName = joinPoint.getTarget().getClass().getName();
        String[] controllerNames = controllerFullName.split("\\.");
        String controllerName = controllerNames[controllerNames.length - DataConstants.NUM_ONE];
        // 方法名称
        String methodName = joinPoint.getSignature().getName();
        String replace = UUID.randomUUID().toString().replace("-", "");
        opLogInfo.setId(replace);
        //用户id
        opLogInfo.setUserId(userId);
        opLogInfo.setBusinessCode(ptUaUser.getUserNumber());
        //用户ip
        opLogInfo.setUserIp(userIp);
        //开始时间
        Date startDate = new Date(beginTime);

        LocalDateTime startDateSt = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startDateStr = dtf2.format(startDateSt);
        opLogInfo.setOpTime(startDateStr);
        opLogInfo.setUpdateTime(startDateStr);
        opLogInfo.setCreateTime(startDateStr);
        opLogInfo.setTransactionBeginDate(startDateStr);
        opLogInfo.setTransactionEndDate(startDateStr);

        Method method   = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        if(logAnnotation.operator().equals("新增")){
            opLogInfo.setOptType("1");
        }else if (logAnnotation.operator().equals("修改")){
            opLogInfo.setOptType("3");
        }else if (logAnnotation.operator().equals("删除")){
            opLogInfo.setOptType("4");
        }else if (logAnnotation.operator().equals("导出")){
            opLogInfo.setOptType("11");
        }else if (logAnnotation.operator().equals("下载")){
            opLogInfo.setOptType("6");
        }else if (logAnnotation.operator().equals("导入")){
            opLogInfo.setOptType("15");
        }else {
            opLogInfo.setOptType("2");
        }

        opLogInfo.setActionType("0");
        //类名——方法名
        opLogInfo.setRequestTypeId(controllerName + "_" + methodName);

        // 参数值
        Object[] paramValues = joinPoint.getArgs();
        // 参数名称
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String,Object> csMap = new HashMap<>();
        boolean flag= JSONObject.fromObject(result).getBoolean("successful");
        if(flag){
            //返回结果
            opLogInfo.setOpResult("200");
            //日志类型
            opLogInfo.setLogType("0");
            opLogInfo.setActionLevel("0");
            opLogInfo.setOpResultZw("操作成功");
        }else {
            opLogInfo.setOpResult("500");
            opLogInfo.setLogType("1");
            opLogInfo.setActionLevel("3");
            opLogInfo.setOpResultZw("操作失败");
        }
        StringBuilder param = new StringBuilder();
        if(paramValues.length==2){
            for (int i = 0; i < 1; i++) {
                Object cs = paramValues[1];
//-----------------------------------------------------------------------------------------大数据分析查询-----------------------------------------------------
                if(logAnnotation.xtcs().equals("one")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String workerId = jsonObject.getString("workerId");
                    if(!workerId.toString().isEmpty()){
                        String  paramworkerId = "人员ID:" + workerId;
                        param.append(paramworkerId);
                    }
                }


                if(logAnnotation.xtcs().equals("accidentStatisticsOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    Integer  timeType = Integer.parseInt(jsonObject.getString("timeType"));
                    if(!timeType.toString().isEmpty()){
                        if(timeType.toString().equals("0")){
                            BusinessLog.TimeFormat="日";
                        }
                        if(timeType.toString().equals("1")){
                            BusinessLog.TimeFormat="周";
                        }
                        if(timeType.toString().equals("2")){
                            BusinessLog.TimeFormat="月";
                        }
                        if(timeType.toString().equals("3")){
                            BusinessLog.TimeFormat="年";
                        }
                        String  paramworkerId = "时间类型:" + BusinessLog.TimeFormat;
                        param.append(paramworkerId);
                    }
                }

                if(logAnnotation.xtcs().equals("accidentStatistics1One")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    Integer  timeType = Integer.parseInt(jsonObject.getString("timeType"));
                    if(timeType.toString().equals("0")){
                        BusinessLog.TimeFormat="日";
                    }
                    if(timeType.toString().equals("1")){
                        BusinessLog.TimeFormat="周";
                    }
                    if(timeType.toString().equals("2")){
                        BusinessLog.TimeFormat="月";
                    }
                    if(timeType.toString().equals("3")){
                        BusinessLog.TimeFormat="年";
                    }

                    if(!timeType.toString().isEmpty()){
                        String  paramworkerId = "时间类型:" + BusinessLog.TimeFormat;
                        param.append(paramworkerId);
                    }
                }



                if(logAnnotation.xtcs().equals("accidentCountByMonthOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    Integer  type = Integer.parseInt(jsonObject.getString("type"));
                    if(!type.toString().isEmpty()){
                        if(type.toString().equals("0")){
                            BusinessLog.EventType="设备事件";
                        }
                        if(type.toString().equals("1")){
                            BusinessLog.EventType="人身事件";
                        }
                        if(type.toString().equals("2")){
                            BusinessLog.EventType="电网事件";
                        }
                        if(type.toString().equals("3")){
                            BusinessLog.EventType="网络信息事件";
                        }
                        String  paramtype = "事件类型:" + BusinessLog.EventType;
                        param.append(paramtype);
                    }
                }


                if(logAnnotation.xtcs().equals("accidentGradeOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    Integer  type = Integer.parseInt(jsonObject.getString("type"));
                    if(!type.toString().isEmpty()){
                        if(type.toString().equals("0")){
                            BusinessLog.EventType="设备事件";
                        }
                        if(type.toString().equals("1")){
                            BusinessLog.EventType="人身事件";
                        }
                        if(type.toString().equals("2")){
                            BusinessLog.EventType="电网事件";
                        }
                        if(type.toString().equals("3")){
                            BusinessLog.EventType="网络信息事件";
                        }
                        String  paramtype = "事件类型:" + BusinessLog.EventType;
                        param.append(paramtype);
                    }
                }

                if(logAnnotation.xtcs().equals("accidentDescOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    Integer  type = Integer.parseInt(jsonObject.getString("type"));
                    if(!type.toString().isEmpty()){
                        if(type.toString().equals("0")){
                            BusinessLog.EventType="设备事件";
                        }
                        if(type.toString().equals("1")){
                            BusinessLog.EventType="人身事件";
                        }
                        if(type.toString().equals("2")){
                            BusinessLog.EventType="电网事件";
                        }
                        if(type.toString().equals("3")){
                            BusinessLog.EventType="网络信息事件";
                        }
                        String  paramtype = "事件类型:" + BusinessLog.EventType;
                        param.append(paramtype);
                    }
                }

                if(logAnnotation.xtcs().equals("wbfxhxOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String id = jsonObject.getString("id");
                    if(!id.toString().isEmpty()){
                        String  paramId = "单位id:" + id;
                        param.append(paramId);
                    }
                }


                if(logAnnotation.xtcs().equals("getInstanceInfoByIdOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String instanceId = jsonObject.getString("instanceId");
                    if(!instanceId.toString().isEmpty()){
                        String  paraminstanceId = "实例ID:" + instanceId;
                        param.append(paraminstanceId);
                    }
                }

                if(logAnnotation.xtcs().equals("oneName")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String typeName = jsonObject.getString("typeName");
                    if(!typeName.toString().isEmpty()){
                        String  paramtypeName = "名称:" + typeName;
                        param.append(paramtypeName);
                    }
                }

                if(logAnnotation.xtcs().equals("getModenlingDetailsOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    if(jsonObject.size()==0){
                        return;
                    }
                    String typeId = jsonObject.getString("typeId");


                    String typeName = atlasMapper.getSrpModeling(typeId);
                    if(!typeName.toString().isEmpty()){
                        if(typeId.equals("3")){
                            typeName="人员";
                        }
                        if(typeName.equals("4")){
                            typeName="风险";
                        }

                        if(typeName.equals("5")){
                            typeName="隐患";
                        }

                        if(typeName.equals("6")){
                            typeName="事件";
                        }

                        if(typeName.equals("7")){
                            typeName="违章";
                        }

                        if(typeName.equals("2")){
                            typeName="外包单位";
                        }

                        if(typeName.equals("1")){
                            typeName="作业计划";
                        }
                        String  paramtypeId = "类型:" + typeName;
                        param.append(paramtypeId);
                    }
                }

                if(logAnnotation.xtcs().equals("getRelationInfoByIdOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String modelingId = jsonObject.getString("modelingId");

                    if(!modelingId.toString().isEmpty()){
                        if(modelingId.equals("1")){
                            modelingId="作业计划";
                        }
                        if(modelingId.equals("2")){
                            modelingId="外包单位";
                        }
                        if(modelingId.equals("3")){
                            modelingId="人员";
                        }
                        if(modelingId.equals("4")){
                            modelingId="风险";
                        }
                        if(modelingId.equals("5")){
                            modelingId="隐患";
                        }
                        if(modelingId.equals("6")){
                            modelingId="事件";
                        }
                        if(modelingId.equals("7")){
                            modelingId="违章信息";
                        }

                        if(!modelingId.toString().isEmpty()){
                            String  parammodelingId = "类别为:" + modelingId;
                            param.append(parammodelingId);
                        }
                    }

                }

                if(logAnnotation.xtcs().equals("getGplotOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String instanceId = jsonObject.getString("instanceId");
                    if(!instanceId.toString().isEmpty()){
                        String  paramminstanceId = "实例id:" + instanceId;
                        param.append(paramminstanceId);
                    }
                }

                if(logAnnotation.xtcs().equals("getGplotNameOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String instanceName = jsonObject.getString("instanceName");
                    if(!instanceName.toString().isEmpty()){
                        String  paraminstanceName = "实例名称:" + instanceName;
                        param.append(paraminstanceName);
                    }
                }

//                if(logAnnotation.xtcs().equals("two")){
//                    JSONObject jsonObject = JSONObject.fromObject(cs);
//                    Integer page = Integer.parseInt(jsonObject.getString("page"));
//                    Integer size = Integer.parseInt(jsonObject.getString("size"));
//
//                    if(!page.toString().isEmpty()){
//                        String  paramPage = "起始页:" + page;
//                        param.append(paramPage);
//                    }
//                    if(!size.toString().isEmpty()){
//                        String  paramSize = "每页数量:" + size;
//                        param.append(paramSize);
//                    }
//                }

                if(logAnnotation.xtcs().equals("getInstanceInfoTwo")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String targetType = jsonObject.getString("targetType");
                    String relationId = jsonObject.getString("relationId");
                    if(!targetType.toString().isEmpty()){
                        String  paramtargetType = "类别:" + targetType+",";
                        param.append(paramtargetType);
                    }
                    if(!relationId.toString().isEmpty()){
                        String  paramrelationId = "关系id:" + relationId+",";
                        param.append(paramrelationId);
                    }
                }

                if(logAnnotation.xtcs().equals("threePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String workerId = jsonObject.getJSONObject("params").getString("workerId");
                    if(!workerId.toString().isEmpty()){
                        String  paramworkerId = "人员ID:" + workerId;
                        param.append(paramworkerId);
                    }
                }

                if(logAnnotation.xtcs().equals("basicInformationThreePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String id = jsonObject.getString("id");
                    if(!id.toString().isEmpty()){
                        String  paramId = "单位id:" + id;
                        param.append(paramId);
                    }
                }

                if(logAnnotation.xtcs().equals("getInstancerelationinfoByIdThree")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String instanceId = jsonObject.getString("instanceId");
                    String targetType = jsonObject.getString("targetType");
                    String relationId = jsonObject.getString("relationId");
                    if(!instanceId.toString().isEmpty()){
                        String  paraminstanceId = "实例id:" + instanceId+",";
                        param.append(paraminstanceId);
                    }

                    if(!targetType.toString().isEmpty()){

                        if(targetType.equals("1")){
                            targetType="作业计划";
                        }
                        if(targetType.equals("2")){
                            targetType="外包单位";
                        }
                        if(targetType.equals("3")){
                            targetType="人员";
                        }
                        if(targetType.equals("4")){
                            targetType="风险";
                        }
                        if(targetType.equals("5")){
                            targetType="隐患";
                        }
                        if(targetType.equals("6")){
                            targetType="事件";
                        }
                        if(targetType.equals("7")){
                            targetType="违章信息";
                        }
                        String  paramtargetType = "类别:" + targetType+",";
                        param.append(paramtargetType);
                    }
                    if(!relationId.toString().isEmpty()){
                        String  paramrelationId = "关系id:" + relationId+",";
                        param.append(paramrelationId);
                    }
                }


                if(logAnnotation.xtcs().equals("forPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String dataReportOrg = jsonObject.getJSONObject("params").getString("dataReportOrg");
                    String manName = jsonObject.getJSONObject("params").getString("manName");
                    if(!dataReportOrg.toString().isEmpty()){
                        if(dataReportOrg.equals("1")){
                            dataReportOrg="北京";
                        }

                        if(dataReportOrg.equals("2")){
                            dataReportOrg="天津";
                        }

                        if(dataReportOrg.equals("3")){
                            dataReportOrg="河北";
                        }

                        if(dataReportOrg.equals("4")){
                            dataReportOrg="冀北";
                        }


                        if(dataReportOrg.equals("5")){
                            dataReportOrg="山西";
                        }

                        if(dataReportOrg.equals("6")){
                            dataReportOrg="山东";
                        }

                        if(dataReportOrg.equals("7")){
                            dataReportOrg="上海";
                        }

                        if(dataReportOrg.equals("8")){
                            dataReportOrg="江苏";
                        }

                        if(dataReportOrg.equals("9")){
                            dataReportOrg="浙江";
                        }

                        if(dataReportOrg.equals("10")){
                            dataReportOrg="安徽";
                        }

                        if(dataReportOrg.equals("11")){
                            dataReportOrg="福建";
                        }

                        if(dataReportOrg.equals("12")){
                            dataReportOrg="湖北";
                        }
                        if(dataReportOrg.equals("13")){
                            dataReportOrg="湖南";
                        }
                        if(dataReportOrg.equals("12")){
                            dataReportOrg="湖北";
                        }
                        if(dataReportOrg.equals("13")){
                            dataReportOrg="湖南";
                        }
                        if(dataReportOrg.equals("14")){
                            dataReportOrg="河南";
                        }
                        if(dataReportOrg.equals("15")){
                            dataReportOrg="江西";
                        }
                        if(dataReportOrg.equals("16")){
                            dataReportOrg="四川";
                        }
                        if(dataReportOrg.equals("17")){
                            dataReportOrg="重庆";
                        }
                        if(dataReportOrg.equals("18")){
                            dataReportOrg="辽宁";
                        }

                        if(dataReportOrg.equals("19")){
                            dataReportOrg="吉林";
                        }
                        if(dataReportOrg.equals("20")){
                            dataReportOrg="黑龙江";
                        }
                        if(dataReportOrg.equals("21")){
                            dataReportOrg="蒙东";
                        }
                        if(dataReportOrg.equals("22")){
                            dataReportOrg="陕西";
                        }
                        if(dataReportOrg.equals("23")){
                            dataReportOrg="甘肃";
                        }
                        if(dataReportOrg.equals("24")){
                            dataReportOrg="青海";
                        }
                        if(dataReportOrg.equals("25")){
                            dataReportOrg="宁夏";
                        }
                        if(dataReportOrg.equals("26")){
                            dataReportOrg="新疆";
                        }
                        if(dataReportOrg.equals("27")){
                            dataReportOrg="西藏";
                        }


                        String  paramdataReportOrg = "省份:" + dataReportOrg+",";
                        param.append(paramdataReportOrg);
                    }
                    if(!manName.toString().isEmpty()){
                        String  parammanagementCode = "供应商名称:" + manName+",";
                        param.append(parammanagementCode);
                    }

                }

                if(logAnnotation.xtcs().equals("getRiskPortraitforePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);

                    String datareportOrg = jsonObject.getJSONObject("params").getString("datareportOrg");
                    String constructionOrg = jsonObject.getJSONObject("params").getString("constructionOrg");

                    if(!datareportOrg.toString().isEmpty()){
                        if(datareportOrg.equals("1")){
                            datareportOrg="北京";
                        }

                        if(datareportOrg.equals("2")){
                            datareportOrg="天津";
                        }

                        if(datareportOrg.equals("3")){
                            datareportOrg="河北";
                        }

                        if(datareportOrg.equals("4")){
                            datareportOrg="冀北";
                        }


                        if(datareportOrg.equals("5")){
                            datareportOrg="山西";
                        }

                        if(datareportOrg.equals("6")){
                            datareportOrg="山东";
                        }

                        if(datareportOrg.equals("7")){
                            datareportOrg="上海";
                        }

                        if(datareportOrg.equals("8")){
                            datareportOrg="江苏";
                        }

                        if(datareportOrg.equals("9")){
                            datareportOrg="浙江";
                        }

                        if(datareportOrg.equals("10")){
                            datareportOrg="安徽";
                        }

                        if(datareportOrg.equals("11")){
                            datareportOrg="福建";
                        }

                        if(datareportOrg.equals("12")){
                            datareportOrg="湖北";
                        }
                        if(datareportOrg.equals("13")){
                            datareportOrg="湖南";
                        }
                        if(datareportOrg.equals("12")){
                            datareportOrg="湖北";
                        }
                        if(datareportOrg.equals("13")){
                            datareportOrg="湖南";
                        }
                        if(datareportOrg.equals("14")){
                            datareportOrg="河南";
                        }
                        if(datareportOrg.equals("15")){
                            datareportOrg="江西";
                        }
                        if(datareportOrg.equals("16")){
                            datareportOrg="四川";
                        }
                        if(datareportOrg.equals("17")){
                            datareportOrg="重庆";
                        }
                        if(datareportOrg.equals("18")){
                            datareportOrg="辽宁";
                        }

                        if(datareportOrg.equals("19")){
                            datareportOrg="吉林";
                        }
                        if(datareportOrg.equals("20")){
                            datareportOrg="黑龙江";
                        }
                        if(datareportOrg.equals("21")){
                            datareportOrg="蒙东";
                        }
                        if(datareportOrg.equals("22")){
                            datareportOrg="陕西";
                        }
                        if(datareportOrg.equals("23")){
                            datareportOrg="甘肃";
                        }
                        if(datareportOrg.equals("24")){
                            datareportOrg="青海";
                        }
                        if(datareportOrg.equals("25")){
                            datareportOrg="宁夏";
                        }
                        if(datareportOrg.equals("26")){
                            datareportOrg="新疆";
                        }
                        if(datareportOrg.equals("27")){
                            datareportOrg="西藏";
                        }


                        String  paramdatareportOrg = "省份:" + datareportOrg+",";
                        param.append(paramdatareportOrg);
                    }
                    if(!constructionOrg.toString().isEmpty()){
                        String  paramconstructionOrg = "单位名称:" + constructionOrg+",";
                        param.append(paramconstructionOrg);
                    }


                }

                if(logAnnotation.xtcs().equals("getInstanceInfofourPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);

                    String modelingId = jsonObject.getJSONObject("params").getString("modelingId");
                    String instanceName = jsonObject.getJSONObject("params").getString("instanceName");
                    if(!modelingId.toString().isEmpty()){
                        if(modelingId.equals("1")){
                            modelingId="作业计划";
                        }
                        if(modelingId.equals("2")){
                            modelingId="外包单位";
                        }
                        if(modelingId.equals("3")){
                            modelingId="人员";
                        }
                        if(modelingId.equals("4")){
                            modelingId="风险";
                        }
                        if(modelingId.equals("5")){
                            modelingId="隐患";
                        }
                        if(modelingId.equals("6")){
                            modelingId="事件";
                        }
                        if(modelingId.equals("7")){
                            modelingId="违章信息";
                        }



                        String  parammodelingId = "类别:" + modelingId + ",";
                        param.append(parammodelingId);
                    }
                    if(!instanceName.toString().isEmpty()){
                        String  paraminstanceName = "实例名称:" + instanceName+",";
                        param.append(paraminstanceName);
                    }
                }

                if(logAnnotation.xtcs().equals("fivePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String name = jsonObject.getJSONObject("params").getString("name");
                    String orgName = jsonObject.getJSONObject("params").getString("orgName");
                    String datareportOrgId = jsonObject.getJSONObject("params").getString("datareportOrgId");
                    if(!name.toString().isEmpty()){
                        String  paramname = "姓名:" + name+",";
                        param.append(paramname);
                    }
                    if(!orgName.toString().isEmpty()){
                        String  paramorgName = "单位:" + orgName+",";
                        param.append(paramorgName);
                    }

                    if(datareportOrgId.equals("1")){
                        datareportOrgId="北京";
                    }

                    if(datareportOrgId.equals("2")){
                        datareportOrgId="天津";
                    }

                    if(datareportOrgId.equals("3")){
                        datareportOrgId="河北";
                    }

                    if(datareportOrgId.equals("4")){
                        datareportOrgId="冀北";
                    }


                    if(datareportOrgId.equals("5")){
                        datareportOrgId="山西";
                    }

                    if(datareportOrgId.equals("6")){
                        datareportOrgId="山东";
                    }

                    if(datareportOrgId.equals("7")){
                        datareportOrgId="上海";
                    }

                    if(datareportOrgId.equals("8")){
                        datareportOrgId="江苏";
                    }

                    if(datareportOrgId.equals("9")){
                        datareportOrgId="浙江";
                    }

                    if(datareportOrgId.equals("10")){
                        datareportOrgId="安徽";
                    }

                    if(datareportOrgId.equals("11")){
                        datareportOrgId="福建";
                    }

                    if(datareportOrgId.equals("12")){
                        datareportOrgId="湖北";
                    }
                    if(datareportOrgId.equals("13")){
                        datareportOrgId="湖南";
                    }
                    if(datareportOrgId.equals("12")){
                        datareportOrgId="湖北";
                    }
                    if(datareportOrgId.equals("13")){
                        datareportOrgId="湖南";
                    }
                    if(datareportOrgId.equals("14")){
                        datareportOrgId="河南";
                    }
                    if(datareportOrgId.equals("15")){
                        datareportOrgId="江西";
                    }
                    if(datareportOrgId.equals("16")){
                        datareportOrgId="四川";
                    }
                    if(datareportOrgId.equals("17")){
                        datareportOrgId="重庆";
                    }
                    if(datareportOrgId.equals("18")){
                        datareportOrgId="辽宁";
                    }

                    if(datareportOrgId.equals("19")){
                        datareportOrgId="吉林";
                    }
                    if(datareportOrgId.equals("20")){
                        datareportOrgId="黑龙江";
                    }
                    if(datareportOrgId.equals("21")){
                        datareportOrgId="蒙东";
                    }
                    if(datareportOrgId.equals("22")){
                        datareportOrgId="陕西";
                    }
                    if(datareportOrgId.equals("23")){
                        datareportOrgId="甘肃";
                    }
                    if(datareportOrgId.equals("24")){
                        datareportOrgId="青海";
                    }
                    if(datareportOrgId.equals("25")){
                        datareportOrgId="宁夏";
                    }
                    if(datareportOrgId.equals("26")){
                        datareportOrgId="新疆";
                    }
                    if(datareportOrgId.equals("27")){
                        datareportOrgId="西藏";
                    }

                    if(!datareportOrgId.toString().isEmpty()){
                        String  paramdatareportOrgId = "省份:" + datareportOrgId+",";
                        param.append(paramdatareportOrgId);
                    }
                }


                if(logAnnotation.xtcs().equals("sixPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String jobName = jsonObject.getJSONObject("params").getString("jobName");
                    String executionDateTime = jsonObject.getJSONObject("params").getString("executionDateTime");
                    String endDateTime = jsonObject.getJSONObject("params").getString("endDateTime");
                    String processState = jsonObject.getJSONObject("params").getString("processState");

                    if(!jobName.toString().isEmpty()){
                        String  paramjobName = "任务名称:" + jobName+",";
                        param.append(paramjobName);
                    }
                    if(!processState.toString().isEmpty()){

                        if(processState.equals("01")){
                            processState="成功";
                        }
                        if(processState.equals("02")){
                            processState="失败";
                        }
                        String  paramprocessState = "进程状态:" + processState+",";
                        param.append(paramprocessState);
                    }

                    if(!executionDateTime.toString().isEmpty()){
                        String  paramexecutionDateTime = "开始时间:" + executionDateTime+",";
                        param.append(paramexecutionDateTime);
                    }
                    if(!endDateTime.toString().isEmpty()){
                        String  paramendDateTime = "结束时间:" + endDateTime+",";
                        param.append(paramendDateTime);
                    }

                }

                if(logAnnotation.xtcs().equals("sevenPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String dataReportOrg = jsonObject.getJSONObject("params").getString("dataReportOrg");
                    String managementCode = jsonObject.getJSONObject("params").getString("managementCode");
                    String toolType = jsonObject.getJSONObject("params").getString("toolType");
                    String name = jsonObject.getJSONObject("params").getString("name");
                    String insState = jsonObject.getJSONObject("params").getString("insState");

                    if(!managementCode.toString().isEmpty()){
                        String  paramdataReportOrg = "单位:" + managementCode+",";
                        param.append(paramdataReportOrg);
                    }

                    if(!dataReportOrg.toString().isEmpty()){

                        if(dataReportOrg.equals("1")){
                            dataReportOrg="北京";
                        }

                        if(dataReportOrg.equals("2")){
                            dataReportOrg="天津";
                        }

                        if(dataReportOrg.equals("3")){
                            dataReportOrg="河北";
                        }

                        if(dataReportOrg.equals("4")){
                            dataReportOrg="冀北";
                        }


                        if(dataReportOrg.equals("5")){
                            dataReportOrg="山西";
                        }

                        if(dataReportOrg.equals("6")){
                            dataReportOrg="山东";
                        }

                        if(dataReportOrg.equals("7")){
                            dataReportOrg="上海";
                        }

                        if(dataReportOrg.equals("8")){
                            dataReportOrg="江苏";
                        }

                        if(dataReportOrg.equals("9")){
                            dataReportOrg="浙江";
                        }

                        if(managementCode.equals("10")){
                            managementCode="安徽";
                        }

                        if(dataReportOrg.equals("11")){
                            dataReportOrg="福建";
                        }

                        if(dataReportOrg.equals("12")){
                            dataReportOrg="湖北";
                        }
                        if(dataReportOrg.equals("13")){
                            dataReportOrg="湖南";
                        }
                        if(dataReportOrg.equals("12")){
                            dataReportOrg="湖北";
                        }
                        if(dataReportOrg.equals("13")){
                            dataReportOrg="湖南";
                        }
                        if(dataReportOrg.equals("14")){
                            dataReportOrg="河南";
                        }
                        if(dataReportOrg.equals("15")){
                            dataReportOrg="江西";
                        }
                        if(dataReportOrg.equals("16")){
                            dataReportOrg="四川";
                        }
                        if(dataReportOrg.equals("17")){
                            dataReportOrg="重庆";
                        }
                        if(dataReportOrg.equals("18")){
                            dataReportOrg="辽宁";
                        }

                        if(dataReportOrg.equals("19")){
                            dataReportOrg="吉林";
                        }
                        if(dataReportOrg.equals("20")){
                            dataReportOrg="黑龙江";
                        }
                        if(dataReportOrg.equals("21")){
                            dataReportOrg="蒙东";
                        }
                        if(dataReportOrg.equals("22")){
                            dataReportOrg="陕西";
                        }
                        if(dataReportOrg.equals("23")){
                            dataReportOrg="甘肃";
                        }
                        if(dataReportOrg.equals("24")){
                            dataReportOrg="青海";
                        }
                        if(dataReportOrg.equals("25")){
                            dataReportOrg="宁夏";
                        }
                        if(dataReportOrg.equals("26")){
                            dataReportOrg="新疆";
                        }
                        if(dataReportOrg.equals("27")){
                            dataReportOrg="西藏";
                        }

                        String  paramdataReportOrg = "省份:" + dataReportOrg+",";
                        param.append(paramdataReportOrg);
                    }


                    if(!toolType.toString().isEmpty()){
                        String  paramtoolType = "工器具类型:" + toolType+",";
                        param.append(paramtoolType);
                    }
                    if(!name.toString().isEmpty()){
                        String  paramname = "工器具名称:" + name+",";
                        param.append(paramname);
                    }
                    if(!insState.toString().isEmpty()){
                        String  paraminsState = "当前状态:" + insState+",";
                        param.append(paraminsState);
                    }
                }

                if(logAnnotation.xtcs().equals("personalAccidentListSevenPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String accidentGrade = jsonObject.getJSONObject("params").getString("accidentGrade");
                    String accidentClass = jsonObject.getJSONObject("params").getString("accidentClass");
                    String injuryDegree = jsonObject.getJSONObject("params").getString("injuryDegree");
                    String startTime = jsonObject.getJSONObject("params").getString("startTime");
                    String endTime = jsonObject.getJSONObject("params").getString("endTime");

                    param.append("人身事件");
                    if(!accidentGrade.toString().isEmpty()){
                        String  paramaccidentGrade = "等级:" + accidentGrade+",";
                        param.append(paramaccidentGrade);
                    }
                    if(!accidentClass.toString().isEmpty()){
                        String  paramaccidentClass = "事故类别:" + accidentClass+",";
                        param.append(paramaccidentClass);
                    }
                    if(!injuryDegree.toString().isEmpty()){
                        String  paraminjuryDegree = "伤害程度:" + injuryDegree+",";
                        param.append(paraminjuryDegree);
                    }
                    if(!startTime.toString().isEmpty()){
                        String  paramstartTime = "计划开始时间:" + startTime+",";
                        param.append(paramstartTime);
                    }
                    if(!endTime.toString().isEmpty()){
                        String  paramendTime = "计划结束时间:" + endTime+",";
                        param.append(paramendTime);
                    }

                }


                if(logAnnotation.xtcs().equals("powerGridAccidentListSevenPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);

                    String accidentGrade = jsonObject.getJSONObject("params").getString("accidentGrade");
                    String voltageCapacity = jsonObject.getJSONObject("params").getString("voltageCapacity");
                    String deviceClass = jsonObject.getJSONObject("params").getString("deviceClass");
                    String startTime = jsonObject.getJSONObject("params").getString("startTime");
                    String endTime = jsonObject.getJSONObject("params").getString("endTime");

                    param.append("电网事件");
                    if(!accidentGrade.toString().isEmpty()){
                        String  paramaccidentGrade = "等级:" + accidentGrade+",";
                        param.append(paramaccidentGrade);
                    }
                    if(!voltageCapacity.toString().isEmpty()){
                        String  paramvoltageCapacity = "电压容量:" + voltageCapacity+",";
                        param.append(paramvoltageCapacity);
                    }
                    if(!deviceClass.toString().isEmpty()){
                        String  paramdeviceClass = "设备分类:" + deviceClass+",";
                        param.append(paramdeviceClass);
                    }
                    if(!startTime.toString().isEmpty()){
                        String  paramstartTime = "计划开始时间:" + startTime+",";
                        param.append(paramstartTime);
                    }
                    if(!endTime.toString().isEmpty()){
                        String  paramendTime = "计划结束时间:" + endTime+",";
                        param.append(paramendTime);
                    }

                }

                if(logAnnotation.xtcs().equals("informAccidentListSevenPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String accidentGrade = jsonObject.getJSONObject("params").getString("accidentGrade");
                    String accidentClass = jsonObject.getJSONObject("params").getString("accidentClass");
                    String deviceClass = jsonObject.getJSONObject("params").getString("deviceClass");
                    String startTime = jsonObject.getJSONObject("params").getString("startTime");
                    String endTime = jsonObject.getJSONObject("params").getString("endTime");
                    param.append("网络信息事件");
                    if(!accidentGrade.toString().isEmpty()){
                        String  paramaccidentGrade = "等级:" + accidentGrade +",";
                        param.append(paramaccidentGrade);
                    }

                    if(!accidentClass.toString().isEmpty()){
                        String  paramaccidentClass = "事件分类:" + accidentClass +",";
                        param.append(paramaccidentClass);
                    }

                    if(!deviceClass.toString().isEmpty()){
                        String  paramdeviceClass = "设备分类:" + deviceClass +",";
                        param.append(paramdeviceClass);
                    }
                    if(!startTime.toString().isEmpty()){
                        String  paramstartTime = "计划开始时间:" + startTime +",";
                        param.append(paramstartTime);
                    }
                    if(!endTime.toString().isEmpty()){
                        String  paramendTime = "计划结束时间:" + endTime+",";
                        param.append(paramendTime);
                    }

                }

                if(logAnnotation.xtcs().equals("eightPage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);

                    String accidentGrade = jsonObject.getJSONObject("params").getString("accidentGrade");
                    String voltageCapacity = jsonObject.getJSONObject("params").getString("voltageCapacity");
                    String gridcorp = jsonObject.getJSONObject("params").getString("gridcorp");
                    String deviceClass = jsonObject.getJSONObject("params").getString("deviceClass");
                    String startTime = jsonObject.getJSONObject("params").getString("startTime");
                    String endTime = jsonObject.getJSONObject("params").getString("endTime");
                    param.append("设备事件");
                    if(!accidentGrade.toString().isEmpty()){
                        String  paramaccidentGrade = "等级:" + accidentGrade+",";
                        param.append(paramaccidentGrade);
                    }
                    if(!voltageCapacity.toString().isEmpty()){
                        String  paramvoltageCapacity = "电压容量:" + voltageCapacity+",";
                        param.append(paramvoltageCapacity);
                    }
                    if(!gridcorp.toString().isEmpty()){
                        String  paramgridcorp = "地区:" + gridcorp+",";
                        param.append(paramgridcorp);
                    }
                    if(!deviceClass.toString().isEmpty()){
                        String  paradeviceClass = "设备分类:" + deviceClass+",";
                        param.append(paradeviceClass);
                    }
                    if(!startTime.toString().isEmpty()){
                        String  paramstartTime = "计划开始时间:" + startTime+",";
                        param.append(paramstartTime);
                    }
                    if(!endTime.toString().isEmpty()){
                        String  paramendTime = "计划结束时间:" + endTime+",";
                        param.append(paramendTime);
                    }

                }

//-------------------------------------------------------新增------------------------------------------------------------------
                if(logAnnotation.xtcs().equals("saveinsertModeingfour")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String typeName = jsonObject.getJSONObject("params").getString("typeName");
                    String typeCode = jsonObject.getJSONObject("params").getString("typeCode");
                    String typeDescribe = jsonObject.getJSONObject("params").getString("typeDescribe");
                    String typeIcon = jsonObject.getJSONObject("params").getString("typeIcon");
                    String typeId= atlasMapper.getsrgMing(typeCode);
                    if(typeId==null){
                        return;
                    }
                    if(!typeName.toString().isEmpty()){
                        if(typeName.equals("1")){
                            typeName="作业计划";
                        }
                        if(typeName.equals("2")){
                            typeName="外包单位";
                        }
                        if(typeName.equals("3")){
                            typeName="人员";
                        }
                        if(typeName.equals("4")){
                            typeName="风险";
                        }
                        if(typeName.equals("5")){
                            typeName="隐患";
                        }
                        if(typeName.equals("6")){
                            typeName="事件";
                        }
                        if(typeName.equals("7")){
                            typeName="违章信息";
                        }
                        String  paramtypeName = "名称为:" + typeName +",且唯一标识为:"+ typeId +",";
                        param.append(paramtypeName);
                    }
                    if(!typeCode.toString().isEmpty()){
                        String  paramtypeCode = "编码:" + typeCode+",";
                        param.append(paramtypeCode);
                    }
                    if(!typeIcon.toString().isEmpty()){
                        String  paramtypeCode = "图标:" + typeIcon+",";
                        param.append(paramtypeCode);
                    }
                    if(!typeDescribe.toString().isEmpty()){
                        String  paramtypeDescribe = "描述:" + typeDescribe+",";
                        param.append(paramtypeDescribe);
                    }
                }
                if(logAnnotation.xtcs().equals("insertRelationInfoSix")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);

                    String relationName = jsonObject.getJSONObject("params").getString("relationName");
                    String relationCode = jsonObject.getJSONObject("params").getString("relationCode");
                    String sourceType = jsonObject.getJSONObject("params").getString("sourceType");
                    String targetType = jsonObject.getJSONObject("params").getString("targetType");
                    String relationType = jsonObject.getJSONObject("params").getString("relationType");
                    String relationDescribe = jsonObject.getJSONObject("params").getString("relationDescribe");
                    String relationId= atlasMapper.findRelationInfo(relationCode);

                    if(relationId.isEmpty()){
                        return;
                    }

                    if(!relationName.toString().isEmpty()){
                        if(relationName.equals("1")){
                            relationName="下发";
                        }
                        if(relationName.equals("2")){
                            relationName="隐含";
                        }
                        if(relationName.equals("3")){
                            relationName="存在";
                        }
                        if(relationName.equals("4")){
                            relationName="导致";
                        }
                        if(relationName.equals("5")){
                            relationName="巡视";
                        }
                        if(relationName.equals("6")){
                            relationName="资质等级";
                        }
                        if(relationName.equals("7")){
                            relationName="引发";
                        }
                        if(relationName.equals("8")){
                            relationName="承担";
                        }
                        if(relationName.equals("9")){
                            relationName="操作不当";
                        }
                        if(relationName.equals("10")){
                            relationName="被管理";
                        }
                        if(relationName.equals("11")){
                            relationName="属于";
                        }
                        String  paramrelationName = "名称:" + relationName +",且唯一标识为:"+relationId+",";
                        param.append(paramrelationName);
                    }
                    if(!relationCode.toString().isEmpty()){
                        String  paramrelationCode = "编码:" + relationCode+",";
                        param.append(paramrelationCode);
                    }
                    if(!sourceType.toString().isEmpty()){
                        if(sourceType.equals("1")){
                            sourceType="作业计划";
                        }
                        if(sourceType.equals("2")){
                            sourceType="外包单位";
                        }
                        if(sourceType.equals("3")){
                            sourceType="人员";
                        }
                        if(sourceType.equals("4")){
                            sourceType="风险";
                        }
                        if(sourceType.equals("5")){
                            sourceType="隐患";
                        }
                        if(sourceType.equals("6")){
                            sourceType="事件";
                        }
                        if(sourceType.equals("7")){
                            sourceType="违章信息";
                        }
                        String  paramsourceType = "源类别:" + sourceType+",";
                        param.append(paramsourceType);
                    }
                    if(!targetType.toString().isEmpty()){
                        if(targetType.equals("1")){
                            targetType="作业计划";
                        }
                        if(targetType.equals("2")){
                            targetType="外包单位";
                        }
                        if(targetType.equals("3")){
                            targetType="人员";
                        }
                        if(targetType.equals("4")){
                            targetType="风险";
                        }
                        if(targetType.equals("5")){
                            targetType="隐患";
                        }
                        if(targetType.equals("6")){
                            targetType="事件";
                        }
                        if(targetType.equals("7")){
                            targetType="违章信息";
                        }

                        String  paramtargetType = "目标类别:" + targetType +",";
                        param.append(paramtargetType);
                    }
                    if(!relationType.toString().isEmpty()){
                        if(relationType.equals("1")){
                            relationType="一对一";
                        }
                        if(relationType.equals("2")){
                            relationType="一对多";
                        }
                        String  paramrelationType = "关联关系类型:" + relationType +",";
                        param.append(paramrelationType);
                    }
                    if(!relationDescribe.toString().isEmpty()){
                        String  paramrelationDescribe = "描述:" + relationDescribe+",";
                        param.append(paramrelationDescribe);
                    }
                }
                //死的新增实例关联关系
//                if(logAnnotation.xtcs().equals("insertInstanceInfoFour")){
//                    JSONObject jsonObject = JSONObject.fromObject(cs);
//                    String modelingId = jsonObject.getJSONObject("params").getString("modelingId");
//                    String instanceName = jsonObject.getJSONObject("params").getString("instanceName");
//                    String instanceCode = jsonObject.getJSONObject("params").getString("instanceCode");
//                    String instanceDescribe = jsonObject.getJSONObject("params").getString("instanceDescribe");
//                    String insceId = atlasMapper.findInscebyid(instanceCode);
//
//                    if(insceId==null){
//                        return;
//                    }
//                    /**
//                     * 工作计划
//                     */
//                    if(modelingId.equals("1")){
//                        String  parammodelingId = "类别为:" +"工作计划,";
//                        param.append(parammodelingId);
//                    }
//                    /**
//                     * 外包单位
//                     */
//                    if(modelingId.equals("2")){
//                        String  parammodelingId = "类别为:" +"外包单位,";
//                        param.append(parammodelingId);
//                    }
//                    /**
//                     * 人员
//                     */
//                    if(modelingId.equals("3")){
//                        String  parammodelingId = "类别为:" +"人员,";
//                        param.append(parammodelingId);
//                    }
//                    /**
//                     * 风险
//                     */
//                    if(modelingId.equals("4")){
//                        String  parammodelingId = "类别为:" +"风险,";
//                        param.append(parammodelingId);
//                    }
//                    /**
//                     * 隐患
//                     */
//                    if(modelingId.equals("5")){
//                        String  parammodelingId = "类别为:" +"隐患,";
//                        param.append(parammodelingId);
//                    }
//                    /**
//                     * 事件
//                     */
//                    if(modelingId.equals("6")){
//                        String  parammodelingId = "类别为:" +"事件,";
//                        param.append(parammodelingId);
//                    }
//                    /**
//                     * 违章信息
//                     */
//                    if(modelingId.equals("7")){
//                        String  parammodelingId = "类别为:" +"违章信息,";
//                        param.append(parammodelingId);
//                    }
//
//
//
//                    if(!instanceName.toString().isEmpty()){
//                        String  paraminstanceName = "名称:" + instanceName+",且唯一标识为:"+ insceId + ",";
//                        param.append(paraminstanceName);
//                    }
//                    if(!instanceCode.toString().isEmpty()){
//                        String  paraminstanceCode = "编号:" + instanceCode+",";
//                        param.append(paraminstanceCode);
//                    }
//                    if(!instanceDescribe.toString().isEmpty()){
//                        String  paraminstanceDescribe = "描述:" + instanceDescribe+",";
//                        param.append(paraminstanceDescribe);
//                    }
//
//                    JSONArray jSONArray = jsonObject.getJSONArray("idList");
//                    String glgxs="";
//                    String  glsxName="";
//                    String bh="";
//                    Relationship re =new Relationship();
//                    int flags= 0;
//                    int z=0;
//                    if(jSONArray.size()!=0){
//                        for(int y=0; y< jSONArray.size(); y++){
//                            String jsons = jSONArray.getString(y);
//                            JSONObject jsonsObj = JSONObject.fromObject(jsons);
//                            String instanceIds = jsonsObj.getString("instanceId");
//                            String relationId = jsonsObj.getString("relationId");
//
//                            if(relationId.equals("6")){
//                                glsxName = jsonsObj.getString("glsxName");
//                                String ss = re.getFlag();
//                                if(!ss.equals("6")){
//                                    glgxs="";
//                                    re.setFlag("6");
//                                }
//                                glgxs+= atlasMapper.getInstanceInfoListName(instanceIds);
//                                re.setGlgxsOperationInitiator(glgxs);
//                                re.setOperationInitiator(glsxName);
//                            }
//
//                            if(relationId.equals("11")){
//                                glsxName = jsonsObj.getString("glsxName");
//                                String ss = re.getFlag();
//                                if(!ss.equals("11")){
//                                    glgxs="";
//                                    re.setFlag("11");
//                                }
//                                glgxs+= atlasMapper.getInstanceInfoListName(instanceIds);
//                                re.setDistributionEvent(glsxName);
//                                re.setGlgxDistributionEvent(glgxs);
//                            }
//
//                            if(relationId.equals("1")){
//                                glsxName = jsonsObj.getString("glsxName");
//                                String ss = re.getFlag();
//                                if(!ss.equals("1")){
//                                    glgxs="";
//                                    re.setFlag("1");
//                                }
//                                glgxs+= atlasMapper.getInstanceInfoListName(instanceIds);
//                                re.setWbDwyhyy(glsxName);
//                                re.setWbDwyhyyGlgx(glgxs);
//                            }
//
//                            if(relationId.equals("7")){
//                                glsxName = jsonsObj.getString("glsxName");
//                                String ss = re.getFlag();
//                                if(!ss.equals("7")){
//                                    glgxs="";
//                                    re.setFlag("7");
//                                }
//                                glgxs+= atlasMapper.getInstanceInfoListName(instanceIds);
//                                re.setWbDwCdfx(glsxName);
//                                re.setWbDwCdfxGlgx(glgxs);
//                            }
//
//                        }
//                    }
//                    StringBuilder pam = new StringBuilder();
//                    if(glgxs!=null && glgxs !=""){
//                        if(re.getGlgxsOperationInitiator()!=null && re.getGlgxsOperationInitiator()!=""){
//                            pam.append(re.getOperationInitiator() + ":"+re.getGlgxsOperationInitiator()+",");
//                        }
//                        if(re.getGlgxDistributionEvent()!=null && re.getGlgxDistributionEvent()!=""){
//                            pam.append(re.getDistributionEvent() + ":"+re.getGlgxDistributionEvent()+",");
//                        }
//                        if(re.getWbDwCdfxGlgx()!=null && re.getWbDwCdfxGlgx()!=""){
//                            pam.append(re.getWbDwCdfx() + ":"+re.getWbDwCdfxGlgx()+",");
//                        }
//                        if(re.getWbDwyhyyGlgx()!=null && re.getWbDwyhyyGlgx()!=""){
//                            pam.append(re.getWbDwyhyy() + ":"+re.getWbDwyhyyGlgx()+",");
//                        }
//                        String  paramglgxs = "关联属性:"+ pam;
//                        param.append(paramglgxs);
//                    }
//
//                }
                //活的实例的新增实例关联关系
                if(logAnnotation.xtcs().equals("insertInstanceInfoFour")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String modelingId = jsonObject.getJSONObject("params").getString("modelingId");
                    String instanceName = jsonObject.getJSONObject("params").getString("instanceName");
                    String instanceCode = jsonObject.getJSONObject("params").getString("instanceCode");
                    String instanceDescribe = jsonObject.getJSONObject("params").getString("instanceDescribe");
                    String insceId = atlasMapper.findInscebyid(instanceCode);

                    if(insceId==null){
                        return;
                    }
                    /**
                     * 工作计划
                     */
                    if(modelingId.equals("1")){
                        String  parammodelingId = "类别为:" +"作业计划,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 外包单位
                     */
                    if(modelingId.equals("2")){
                        String  parammodelingId = "类别为:" +"外包单位,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 人员
                     */
                    if(modelingId.equals("3")){
                        String  parammodelingId = "类别为:" +"人员,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 风险
                     */
                    if(modelingId.equals("4")){
                        String  parammodelingId = "类别为:" +"风险,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 隐患
                     */
                    if(modelingId.equals("5")){
                        String  parammodelingId = "类别为:" +"隐患,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 事件
                     */
                    if(modelingId.equals("6")){
                        String  parammodelingId = "类别为:" +"事件,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 违章信息
                     */
                    if(modelingId.equals("7")){
                        String  parammodelingId = "类别为:" +"违章信息,";
                        param.append(parammodelingId);
                    }



                    if(!instanceName.toString().isEmpty()){
                        String  paraminstanceName = "名称:" + instanceName+",且唯一标识为:"+ insceId + ",";
                        param.append(paraminstanceName);
                    }
                    if(!instanceCode.toString().isEmpty()){
                        String  paraminstanceCode = "编号:" + instanceCode+",";
                        param.append(paraminstanceCode);
                    }
                    if(!instanceDescribe.toString().isEmpty()){
                        String  paraminstanceDescribe = "描述:" + instanceDescribe+",";
                        param.append(paraminstanceDescribe);
                    }

                    JSONArray jSONArray = jsonObject.getJSONArray("idList");
                    if(jSONArray.size()>0){
                    String glgxs="";
                    String  glsxName="";
                    Relationship re =new Relationship();
                    List<Map<String,String>> los = null;
                    Map<String,String> mapTj = new HashMap();
                    String relationId1="";
                    String glsxNamesb="";

                    if(jSONArray.size()!=0){
                        for(int y=0; y< jSONArray.size(); y++){
                            String jsons = jSONArray.getString(y);
                            JSONObject jsonsObj = JSONObject.fromObject(jsons);
                            String instanceIds = jsonsObj.getString("instanceId");
                            String relationId = jsonsObj.getString("relationId");
                            String glsxNames = jsonsObj.getString("glsxName");
                            if(y!=0){
                                String jsons1 = jSONArray.getString(y-1);
                                JSONObject jsonsObj1 = JSONObject.fromObject(jsons1);
                                relationId1 = jsonsObj1.getString("relationId");
                                 glsxNamesb = jsonsObj.getString("glsxName");
                            }
                            if(y==0){
                                relationId1=relationId;
                                glsxNamesb=glsxNames;
                            }
                            los=new ArrayList<>();
                            if(relationId.equals(relationId1) && glsxNames.equals(glsxNamesb)){
                                glsxName = jsonsObj.getString("glsxName");
                                glgxs+= atlasMapper.getInstanceInfoListName(instanceIds);
                                mapTj.put(glsxName,glsxName + ":" + glgxs+",");
                                los.add(mapTj);
                            }
                            else {
                                glgxs="";
                                glsxName = jsonsObj.getString("glsxName");
                                glgxs+= atlasMapper.getInstanceInfoListName(instanceIds);
                            }
                        }
                    }

                    StringBuilder pam = new StringBuilder();
                    String lx="";
                    if(glgxs!=null && glgxs !=""){
                        for(String key :mapTj.keySet()){
                            lx +=mapTj.get(key);
                        }
                        String  paramglgxs = "关联属性:"+ lx;
                        param.append(paramglgxs);
                    }
                    }
                }












//-------------------------------------------------------删除------------------------------------------------------------------


                if(logAnnotation.xtcs().equals("deleteInstanceOne")){

                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String modelingId = jsonObject.getString("modelingId");
                    /**
                     * 工作计划
                     */
                    if(modelingId.equals("1")){
                        String  parammodelingId = "类别为:" +"作业计划,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 外包单位
                     */
                    if(modelingId.equals("2")){
                        String  parammodelingId = "类别为:" +"外包单位,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 人员
                     */
                    if(modelingId.equals("3")){
                        String  parammodelingId = "类别为:" +"人员,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 风险
                     */
                    if(modelingId.equals("4")){
                        String  parammodelingId = "类别为:" +"风险,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 隐患
                     */
                    if(modelingId.equals("5")){
                        String  parammodelingId = "类别为:" +"隐患,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 事件
                     */
                    if(modelingId.equals("6")){
                        String  parammodelingId = "类别为:" +"事件,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 违章信息
                     */
                    if(modelingId.equals("7")){
                        String  parammodelingId = "类别为:" +"违章信息,";
                        param.append(parammodelingId);
                    }

                    if(!BusinessLog.DEL_EXAMPLE.isEmpty()){
                        String  paraminstanceId = "名称为:" + BusinessLog.DEL_EXAMPLE +",";
                        param.append(paraminstanceId);
                    }
                    if(!BusinessLog.INSTANCEID.isEmpty()){
                        String  paraminstanceId = "且唯一标识为:" + BusinessLog.INSTANCEID;
                        param.append(paraminstanceId);
                    }
                }

                if(logAnnotation.xtcs().equals("deleteModeingOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String typeId = jsonObject.getString("typeId");
                    if(!BusinessLog.Import_TYPENAME.isEmpty()){
                        String  paramImport_TYPENAME = "名称为:" + BusinessLog.Import_TYPENAME +",";
                        param.append(paramImport_TYPENAME);
                    }
                    if(!typeId.isEmpty()){
                        String  paramtypeId = "且唯一标识为:"+ typeId +",";
                        param.append(paramtypeId);
                    }

                }

//-------------------------------------------------------下载------------------------------------------------------------------
//                {"params":{"mbName":"实例模板20210827.xls"}}
                if(logAnnotation.xtcs().equals("exampletemplateDownload")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String mbName = jsonObject.getJSONObject("params").getString("mbName");
                    if(!mbName.isEmpty()){
                        String  parammbName = mbName +",";
                        param.append(parammbName);
                    }
                }

                if(logAnnotation.xtcs().equals("relationshiptemplateDownload")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String mbName = jsonObject.getJSONObject("params").getString("mbName");
                    if(!mbName.isEmpty()){
                        String  parammbName = mbName +",";
                        param.append(parammbName);
                    }
                }




//-------------------------------------------------------导入------------------------------------------------------------------

                if(logAnnotation.xtcs().equals("fileRelationLead")){
                    if(!BusinessLog.Import_FILENAME.isEmpty()){
                        String  paramImport_FILENAME = BusinessLog.Import_FILENAME +",";
                        param.append(paramImport_FILENAME);
                    }
                }

                if(logAnnotation.xtcs().equals("fileExample")){
                    if(!BusinessLog.Import_EXAMPLE_FILENAME.isEmpty()){
                        String  paramImport_FILENAME = BusinessLog.Import_EXAMPLE_FILENAME +",";
                        param.append(paramImport_FILENAME);
                    }
                }
                //-------------------------------------------------------编辑------------------------------------------------------------------
//暂时关闭编辑
                if(logAnnotation.xtcs().equals("updateInstanceInfoOne")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String instanceId = jsonObject.getJSONObject("params").getString("instanceId");
                    String instanceName =jsonObject.getJSONObject("params").getString("instanceName");
                    String instanceDescribe = jsonObject.getJSONObject("params").getString("instanceDescribe");
                    String modelingId = jsonObject.getString("modelingId");
                    String oldName= BusinessLog.oldName;
                    String  dsc=BusinessLog.oldDsc;
                    String instanceInfos = BusinessLog.instanceInfos;

//                    Instanceinfo jkince= JSON.parseObject(instanceInfos,Instanceinfo.class);
//                    String glgxName=BusinessLog.glgxName;
                    if(!(instanceName.equals(oldName) && instanceDescribe.equals(dsc))){
                    /**
                     * 工作计划
                     */
                    if(modelingId.equals("1")){
                        String  parammodelingId = "类别为:" +"作业计划,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 外包单位
                     */
                    if(modelingId.equals("2")){
                        String  parammodelingId = "类别为:" +"外包单位,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 人员
                     */
                    if(modelingId.equals("3")){
                        String  parammodelingId = "类别为:" +"人员,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 风险
                     */
                    if(modelingId.equals("4")){
                        String  parammodelingId = "类别为:" +"风险,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 隐患
                     */
                    if(modelingId.equals("5")){
                        String  parammodelingId = "类别为:" +"隐患,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 事件
                     */
                    if(modelingId.equals("6")){
                        String  parammodelingId = "类别为:" +"事件,";
                        param.append(parammodelingId);
                    }
                    /**
                     * 违章信息
                     */
                    if(modelingId.equals("7")){
                        String  parammodelingId = "类别为:" +"违章信息,";
                        param.append(parammodelingId);
                    }

                    if(!instanceName.toString().isEmpty()&& !instanceName.equals(oldName)){
                        String  paraminstanceName = "名称为:" + instanceName+",唯一标识为"+ instanceId +",";
                        param.append(paraminstanceName);
                        String  paramInstanceNames = "将名称:" + oldName +",修改为"+ instanceName  +",";
                        param.append(paramInstanceNames);
                    }else {
                        String  paraminstanceName = "名称为:" + instanceName+",唯一标识为"+ instanceId +",";
                        param.append(paraminstanceName);
                    }


                    if(!instanceDescribe.toString().isEmpty() && !dsc.equals(instanceDescribe)){
                        String  paraminstanceDescribe = "将描述:" + dsc +",修改为"+ instanceDescribe;
                        param.append(paraminstanceDescribe);
                    }
                    }else {

                        /**
                         * 工作计划
                         */
                        if(modelingId.equals("1")){
                            String  parammodelingId = "类别为:" +"作业计划,";
                            param.append(parammodelingId);
                        }
                        /**
                         * 外包单位
                         */
                        if(modelingId.equals("2")){
                            String  parammodelingId = "类别为:" +"外包单位,";
                            param.append(parammodelingId);
                        }
                        /**
                         * 人员
                         */
                        if(modelingId.equals("3")){
                            String  parammodelingId = "类别为:" +"人员,";
                            param.append(parammodelingId);
                        }
                        /**
                         * 风险
                         */
                        if(modelingId.equals("4")){
                            String  parammodelingId = "类别为:" +"风险,";
                            param.append(parammodelingId);
                        }
                        /**
                         * 隐患
                         */
                        if(modelingId.equals("5")){
                            String  parammodelingId = "类别为:" +"隐患,";
                            param.append(parammodelingId);
                        }
                        /**
                         * 事件
                         */
                        if(modelingId.equals("6")){
                            String  parammodelingId = "类别为:" +"事件,";
                            param.append(parammodelingId);
                        }
                        /**
                         * 违章信息
                         */
                        if(modelingId.equals("7")){
                            String  parammodelingId = "类别为:" +"违章信息,";
                            param.append(parammodelingId);
                        }

                        String ry="唯一标识为"+ instanceId +",";
                        param.append(ry);
                    }
                    JSONArray jSONArray = jsonObject.getJSONArray("oldIdList");
                    JSONArray jSONArray1 = jsonObject.getJSONArray("newIdList");
                    String news="";
                    String olds="";
                    String jsonsc="";
                    String jsonsc1="";
                    String glsxNamec="";
                    String xuw="";
                    String glsxNamep="";
                    Map<String,String> oldsMap=new HashMap<>();
                    Map<String,String> bdMap=new HashMap<>();
                    Map<String,String> xw=new HashMap<>();
                    if(jSONArray1.size()!=0 && jSONArray.size()!=0){
                        for(int t=0;t< jSONArray.size();t++){
                        String jsons = jSONArray.getString(t);
                        JSONObject jsonsObj=JSONObject.fromObject(jsons);
                        String glsxNameo = jsonsObj.getString("glsxName");
                        if(t>0){
                            jsonsc = jSONArray.getString(t-1);
                            JSONObject ob1  = JSONObject.fromObject(jsonsc);
                            glsxNamec = ob1.getString("glsxName");
                        }
                        if(glsxNamec.equals(glsxNameo)){
                            String nameOld = jsonsObj.getString("glsxName");
//                            bdMap.put();
                            //修改之前的数据
                            olds+=jsonsObj.getString("selfname");
                            oldsMap.put(nameOld,nameOld+"将"+olds);
                        }else {
                            olds="";
                            String oldnameb = jsonsObj.getString("glsxName");
                            olds+=jsonsObj.getString("selfname");
                            oldsMap.put(oldnameb,oldnameb+"将"+olds);
                        }
                    }

                    for(int c=0;c < jSONArray1.size();c++){
                        String jsons1 = jSONArray1.getString(c);
                        JSONObject jsonsObj1 = JSONObject.fromObject(jsons1);
                        String glsxName1 = jsonsObj1.getString("glsxName");
                        if(c>0){
                            jsonsc1 = jSONArray1.getString(c-1);
                            JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
                            glsxNamec = jsonsObjs1.getString("glsxName");
                        }
                        if(glsxNamec.equals(glsxName1)){
                            //修改之后的数据
                            String namenew = jsonsObj1.getString("glsxName");
                            news += jsonsObj1.getString("selfname");
                            oldsMap.put(namenew+"s","修改为:"+ news);

                        }else {
                            news="";
                            String  newName = jsonsObj1.getString("glsxName");
                            news += jsonsObj1.getString("selfname");
                            oldsMap.put(newName+"s","修改为:"+ news);
                        }
                    }

                    //其中一个为空的情况

                    if(jSONArray1.size()!=0 && jSONArray.size()!=0){
                        String lx="";
                        String lw="";
                        StringBuilder cspam = new StringBuilder();
                        int cout=oldsMap.size();
                        int fla=0;
                        String keyfz="";
                        List<String> lis=new ArrayList<>();
                        for (String key:oldsMap.keySet()){
                            if(!"s".equals(key.substring(key.length()-1))){
                                lx= oldsMap.get(key);
                                lis.add(key+"s");
                                lw= oldsMap.get(key+"s");
                               if(lw==null){
                                   lw="修改为【】";
                               }
                                    cspam.append(lx).append(lw);
                            }else {
                                lw= oldsMap.get(key);
                               String jqps = key.substring(0,key.length()-1);
                                if(!lis.contains(key)){
                                    cspam.append(jqps + "将【】" + lw);
                                }
                            }

                        }
                        String knssName = "关联属性:"+ cspam;
                        param.append(knssName);
                    }
                    }
                    //多个里面含有为一个为空的情况
//                    if(){
//
//                    }







                    //                     由选择到无
                    if(jSONArray1.size()==0 && jSONArray.size()!=0){
                        for(int y=0;y < jSONArray.size();y++){
                            String jsonsss = jSONArray.getString(y);
                            JSONObject jsonsObjss = JSONObject.fromObject(jsonsss);
                            String glsxNamess = jsonsObjss.getString("glsxName");
                            if(y>0){
                                jsonsc1 = jSONArray.getString(y-1);
                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
                                glsxNamep = jsonsObjs1.getString("glsxName");
                            }
                            if(glsxNamep.equals(glsxNamess)){
                                //修改之后的数据
                                String namenewss = jsonsObjss.getString("glsxName");
                                xuw += jsonsObjss.getString("selfname");
                                xw.put(namenewss,namenewss+"由"+xuw+"修改为【】");
                            }else {
                                xuw="";
                                xuw += jsonsObjss.getString("selfname");
                                String  xwssname = jsonsObjss.getString("glsxName");
                                xw.put(xwssname+"s",xwssname+"由"+xuw+"修改为【】");
                            }
                        }


                        String aa="";
                        String bb="";
                        int flas=0;
                        if(xw.size()<=2){
                            for(String key :xw.keySet()){
                                 ++flas;
                                switch (flas){
                                    case 1:
                                        aa=key;
                                        break;
                                    case 2:
                                        bb=key;
                                        break;
                                }
                            }
                        }



                        String lx="";
                        String lc="";
                        StringBuilder cspamxw = new StringBuilder();


                        if(xw.size()>2){
                            int fla=0;
                            for (String key:xw.keySet()){
                                ++fla;
                                if(fla==1){
                                    lx= xw.get(key);
                                    cspamxw.append(lx);
                                }else {
                                    if(!"s".equals(key.substring(key.length()-1))){
                                        lx= xw.get(key);
                                        cspamxw.append(lx);
                                    }
                                }
                            }
                            String knssName = "关联属性:"+ cspamxw;
                            param.append(knssName);
                        }else {
                            if(aa !=""){
                                lx= xw.get(aa);
                                cspamxw.append(lx);
                            }
                            if(bb!=""){
                                lc= xw.get(bb);
                                cspamxw.append(lx);
                            }
                            String knssName = "关联属性:"+ cspamxw;
                            param.append(knssName);
                        }
                    }

//                    由无到选择
                    if(jSONArray1.size()!=0 && jSONArray.size()==0){
                        for(int y=0;y < jSONArray1.size();y++){
                            String jsonsss = jSONArray1.getString(y);
                            JSONObject jsonsObjss = JSONObject.fromObject(jsonsss);
                            String glsxNamess = jsonsObjss.getString("glsxName");
                            if(y>0){
                                jsonsc1 = jSONArray1.getString(y-1);
                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
                                glsxNamep = jsonsObjs1.getString("glsxName");
                            }
                            if(glsxNamep.equals(glsxNamess)){
                                //修改之后的数据
                                String namenewss = jsonsObjss.getString("glsxName");
                                xuw += jsonsObjss.getString("selfname");
                                xw.put(namenewss,namenewss+"由【】"+"修改为"+xuw);
                            }else {
                                xuw="";
                                xuw += jsonsObjss.getString("selfname");
                                String  xwssname = jsonsObjss.getString("glsxName");
                                xw.put(xwssname+"s",xwssname+"由【】"+"修改为"+xuw);
                            }
                        }

                        String aa="";
                        String bb="";
                        int flas=0;
                        if(xw.size()<=2){
                            for(String key :xw.keySet()){
                                     ++flas;
                                switch (flas){
                                    case 1:
                                        aa=key;
                                        break;
                                    case 2:
                                        bb=key;
                                        break;
                                }
                            }
                        }

                        String lx="";
                        String lc="";
                        StringBuilder cspamxw = new StringBuilder();
                        if(xw.size()>2){
                            int fla = 0;
                            for (String key:xw.keySet()){
                                ++fla;
                                if(fla==1){
                                    lx= xw.get(key);
                                    cspamxw.append(lx);
                                }
                                else {
                                    if(!"s".equals(key.substring(key.length()-1))){
                                        lx= xw.get(key);
                                        cspamxw.append(lx);
                                    }
                                }
                            }
                            String knssName = "关联属性:"+ cspamxw;
                            param.append(knssName);
                        }
                        else {

                            if(aa !=""){
                                lx= xw.get(aa);
                                cspamxw.append(lx);
                            }
                            if(bb!=""){
                                lc= xw.get(bb);
                                cspamxw.append(lx);
                            }
                            String knssName = "关联属性:"+ cspamxw;
                            param.append(knssName);

                        }
                    }
                }
//                if(logAnnotation.xtcs().equals("updateInstanceInfoOne")){
//                    JSONObject jsonObject = JSONObject.fromObject(cs);
//                    String instanceId = jsonObject.getJSONObject("params").getString("instanceId");
//                    String instanceName =jsonObject.getJSONObject("params").getString("instanceName");
//                    String instanceDescribe = jsonObject.getJSONObject("params").getString("instanceDescribe");
//                    String modelingId = jsonObject.getString("modelingId");
//                    String oldName= BusinessLog.oldName;
//                    String  dsc=BusinessLog.oldDsc;
//                    String instanceInfos = BusinessLog.instanceInfos;
//                    Instanceinfo jkince= JSON.parseObject(instanceInfos,Instanceinfo.class);
//                    String glgxName=BusinessLog.glgxName;
//                    if(!(instanceName.equals(oldName) && instanceDescribe.equals(dsc))){
//                        /**
//                         * 工作计划
//                         */
//                        if(modelingId.equals("1")){
//                            String  parammodelingId = "类别为:" +"作业计划,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 外包单位
//                         */
//                        if(modelingId.equals("2")){
//                            String  parammodelingId = "类别为:" +"外包单位,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 人员
//                         */
//                        if(modelingId.equals("3")){
//                            String  parammodelingId = "类别为:" +"人员,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 风险
//                         */
//                        if(modelingId.equals("4")){
//                            String  parammodelingId = "类别为:" +"风险,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 隐患
//                         */
//                        if(modelingId.equals("5")){
//                            String  parammodelingId = "类别为:" +"隐患,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 事件
//                         */
//                        if(modelingId.equals("6")){
//                            String  parammodelingId = "类别为:" +"事件,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 违章信息
//                         */
//                        if(modelingId.equals("7")){
//                            String  parammodelingId = "类别为:" +"违章信息,";
//                            param.append(parammodelingId);
//                        }
//
//                        if(!instanceName.toString().isEmpty()&& !instanceName.equals(oldName)){
//                            String  paraminstanceName = "名称为:" + instanceName+",唯一标识为"+ instanceId +",";
//                            param.append(paraminstanceName);
//                            String  paramInstanceNames = "将名称:" + oldName +",修改为"+ instanceName  +",";
//                            param.append(paramInstanceNames);
//                        }else {
//                            String  paraminstanceName = "名称为:" + instanceName+",唯一标识为"+ instanceId +",";
//                            param.append(paraminstanceName);
//                        }
//
//
//                        if(!instanceDescribe.toString().isEmpty() && !dsc.equals(instanceDescribe)){
//                            String  paraminstanceDescribe = "将描述:" + dsc +",修改为"+ instanceDescribe;
//                            param.append(paraminstanceDescribe);
//                        }
//                    }else {
//
//                        /**
//                         * 工作计划
//                         */
//                        if(modelingId.equals("1")){
//                            String  parammodelingId = "类别为:" +"作业计划,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 外包单位
//                         */
//                        if(modelingId.equals("2")){
//                            String  parammodelingId = "类别为:" +"外包单位,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 人员
//                         */
//                        if(modelingId.equals("3")){
//                            String  parammodelingId = "类别为:" +"人员,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 风险
//                         */
//                        if(modelingId.equals("4")){
//                            String  parammodelingId = "类别为:" +"风险,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 隐患
//                         */
//                        if(modelingId.equals("5")){
//                            String  parammodelingId = "类别为:" +"隐患,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 事件
//                         */
//                        if(modelingId.equals("6")){
//                            String  parammodelingId = "类别为:" +"事件,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 违章信息
//                         */
//                        if(modelingId.equals("7")){
//                            String  parammodelingId = "类别为:" +"违章信息,";
//                            param.append(parammodelingId);
//                        }
//
//                        String ry="唯一标识为"+ instanceId +",";
//                        param.append(ry);
//                    }
//
//                    JSONArray jSONArray = jsonObject.getJSONArray("oldIdList");
//                    JSONArray jSONArray1 = jsonObject.getJSONArray("newIdList");
//                    String news="";
//                    String olds="";
//                    String jsonsc="";
//                    String jsonsc1="";
//                    String glsxNamec="";
//                    String xuw="";
//                    String wux="";
//                    String glsxNamep="";
//                    Map<String,String> oldsMap=new HashMap<>();
//                    Map<String,String> xw=new HashMap<>();
//                    Map<String,String> wx=new HashMap<>();
//                    if(jSONArray1.size()!=0 && jSONArray.size()!=0){
//                        for(int t=0;t< jSONArray.size();t++){
//                            String jsons = jSONArray.getString(t);
//                            JSONObject jsonsObj=JSONObject.fromObject(jsons);
//                            String glsxNameo = jsonsObj.getString("glsxName");
//                            if(t>0){
//                                jsonsc = jSONArray.getString(t-1);
//                                JSONObject ob1  = JSONObject.fromObject(jsonsc);
//                                glsxNamec = ob1.getString("glsxName");
//                            }
//                            if(glsxNamec.equals(glsxNameo)){
//                                String nameOld = jsonsObj.getString("glsxName");
//                                //修改之前的数据
//                                olds+=jsonsObj.getString("selfname");
//                                oldsMap.put(nameOld,nameOld+"将"+olds);
//
//
//                            }else {
//                                olds="";
//                                String oldnameb = jsonsObj.getString("glsxName");
//                                olds+=jsonsObj.getString("selfname");
//                                oldsMap.put(oldnameb,oldnameb+"将"+olds);
//                            }
//                        }
//
//                        for(int c=0;c < jSONArray1.size();c++){
//                            String jsons1 = jSONArray1.getString(c);
//                            JSONObject jsonsObj1 = JSONObject.fromObject(jsons1);
//                            String glsxName1 = jsonsObj1.getString("glsxName");
//                            if(c>0){
//                                jsonsc1 = jSONArray1.getString(c-1);
//                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
//                                glsxNamec = jsonsObjs1.getString("glsxName");
//                            }
//                            if(glsxNamec.equals(glsxName1)){
//                                //修改之后的数据
//                                String namenew = jsonsObj1.getString("glsxName");
//                                news += jsonsObj1.getString("selfname");
//                                oldsMap.put(namenew+"s","修改为:"+ news);
//
//                            }else {
//                                news="";
//                                String  newName = jsonsObj1.getString("glsxName");
//                                news += jsonsObj1.getString("selfname");
//                                oldsMap.put(newName+"s","修改为:"+ news);
//                            }
//                        }
//
//
//
//                        if(jSONArray1.size()!=0 && jSONArray.size()!=0){
//                            String lx="";
//                            String lw="";
//                            StringBuilder cspam = new StringBuilder();
//                            int cout=oldsMap.size();
//                            int fla = 0;
//                            for (String key:oldsMap.keySet()){
//                                lx= oldsMap.get(key);
//                                lw= oldsMap.get(key+"s");
//                                ++fla;
//                                if(fla<=cout/2){
//                                    cspam.append(lx).append(lw);
//                                }
//                            }
//                            String knssName = "关联属性:"+ cspam;
//                            param.append(knssName);
//                        }
//                    }
//                    //                     由选择到无
//                    if(jSONArray1.size()==0 && jSONArray.size()!=0){
//                        for(int y=0;y < jSONArray.size();y++){
//                            String jsonsss = jSONArray.getString(y);
//                            JSONObject jsonsObjss = JSONObject.fromObject(jsonsss);
//                            String glsxNamess = jsonsObjss.getString("glsxName");
//                            if(y>0){
//                                jsonsc1 = jSONArray.getString(y-1);
//                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
//                                glsxNamep = jsonsObjs1.getString("glsxName");
//                            }
//                            if(glsxNamep.equals(glsxNamess)){
//                                //修改之后的数据
//                                String namenewss = jsonsObjss.getString("glsxName");
//                                xuw += jsonsObjss.getString("selfname");
//                                xw.put(namenewss,namenewss+"由"+xuw+"修改为【】");
//                            }else {
//                                xuw="";
//                                xuw += jsonsObjss.getString("selfname");
//                                String  xwssname = jsonsObjss.getString("glsxName");
//                                xw.put(xwssname+"s",xwssname+"由"+xuw+"修改为【】");
//                            }
//                        }
//                        String lx="";
//                        String lw="";
//                        StringBuilder cspamxw = new StringBuilder();
//                        int cout=xw.size();
//                        int fla = 0;
//                        for (String key:xw.keySet()){
//                            lx= xw.get(key);
//                            lw= xw.get(key+"s");
//                            ++fla;
//                            if(fla<=cout/2){
//
//                                cspamxw.append(lx).append(lw);
//                            }
//                        }
//                        String knssName = "关联属性:"+ cspamxw;
//                        param.append(knssName);
//                    }
//
////                    由选择到取消
//                    if(jSONArray1.size()!=0 && jSONArray.size()==0){
//                        for(int y=0;y < jSONArray1.size();y++){
//                            String jsonsss = jSONArray1.getString(y);
//                            JSONObject jsonsObjss = JSONObject.fromObject(jsonsss);
//                            String glsxNamess = jsonsObjss.getString("glsxName");
//                            if(y>0){
//                                jsonsc1 = jSONArray1.getString(y-1);
//                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
//                                glsxNamep = jsonsObjs1.getString("glsxName");
//                            }
//                            if(glsxNamep.equals(glsxNamess)){
//                                //修改之后的数据
//                                String namenewss = jsonsObjss.getString("glsxName");
//                                xuw += jsonsObjss.getString("selfname");
//                                xw.put(namenewss,namenewss+"由【】"+"修改为"+xuw);
//                            }else {
//                                xuw="";
//                                xuw += jsonsObjss.getString("selfname");
//                                String  xwssname = jsonsObjss.getString("glsxName");
//                                xw.put(xwssname+"s",xwssname+"由【】"+"修改为"+xuw);
//                            }
//                        }
//                        String lx="";
//                        String lw="";
//                        String zg="";
//                        StringBuilder cspamxw = new StringBuilder();
//                        int cout=xw.size();
//                        int fla = 0;
//                        for (String key:xw.keySet()){
//                            lx= xw.get(key);
//                            lw= xw.get(key+"s");
//                            ++fla;
//                            if(fla<=cout/2){
//
//                                cspamxw.append(lx).append(lw);
//                            }
//                        }
//                        String knssName = "关联属性:"+ cspamxw;
//                        param.append(knssName);
//                    }
//
//                }






                //双层for循环取值
//                if(logAnnotation.xtcs().equals("updateInstanceInfoOne")){
//                    JSONObject jsonObject = JSONObject.fromObject(cs);
//                    String instanceId = jsonObject.getJSONObject("params").getString("instanceId");
//                    String instanceName =jsonObject.getJSONObject("params").getString("instanceName");
//                    String instanceDescribe = jsonObject.getJSONObject("params").getString("instanceDescribe");
//                    String modelingId = jsonObject.getString("modelingId");
//                    String oldName= BusinessLog.oldName;
//                    String  dsc=BusinessLog.oldDsc;
//                    String instanceInfos = BusinessLog.instanceInfos;
//                    Instanceinfo jkince= JSON.parseObject(instanceInfos,Instanceinfo.class);
//                    String glgxName=BusinessLog.glgxName;
//                    if(!(instanceName.equals(oldName) && instanceDescribe.equals(dsc))){
//                        /**
//                         * 工作计划
//                         */
//                        if(modelingId.equals("1")){
//                            String  parammodelingId = "类别为:" +"作业计划,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 外包单位
//                         */
//                        if(modelingId.equals("2")){
//                            String  parammodelingId = "类别为:" +"外包单位,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 人员
//                         */
//                        if(modelingId.equals("3")){
//                            String  parammodelingId = "类别为:" +"人员,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 风险
//                         */
//                        if(modelingId.equals("4")){
//                            String  parammodelingId = "类别为:" +"风险,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 隐患
//                         */
//                        if(modelingId.equals("5")){
//                            String  parammodelingId = "类别为:" +"隐患,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 事件
//                         */
//                        if(modelingId.equals("6")){
//                            String  parammodelingId = "类别为:" +"事件,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 违章信息
//                         */
//                        if(modelingId.equals("7")){
//                            String  parammodelingId = "类别为:" +"违章信息,";
//                            param.append(parammodelingId);
//                        }
//
//                        if(!instanceName.toString().isEmpty()&& !instanceName.equals(oldName)){
//                            String  paraminstanceName = "名称为:" + instanceName+",唯一标识为"+ instanceId +",";
//                            param.append(paraminstanceName);
//                            String  paramInstanceNames = "将名称:" + oldName +",修改为"+ instanceName  +",";
//                            param.append(paramInstanceNames);
//                        }else {
//                            String  paraminstanceName = "名称为:" + instanceName+",唯一标识为"+ instanceId +",";
//                            param.append(paraminstanceName);
//                        }
//
//
//                        if(!instanceDescribe.toString().isEmpty() && !dsc.equals(instanceDescribe)){
//                            String  paraminstanceDescribe = "将描述:" + dsc +",修改为"+ instanceDescribe;
//                            param.append(paraminstanceDescribe);
//                        }
//                    }else {
//
//                        /**
//                         * 工作计划
//                         */
//                        if(modelingId.equals("1")){
//                            String  parammodelingId = "类别为:" +"作业计划,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 外包单位
//                         */
//                        if(modelingId.equals("2")){
//                            String  parammodelingId = "类别为:" +"外包单位,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 人员
//                         */
//                        if(modelingId.equals("3")){
//                            String  parammodelingId = "类别为:" +"人员,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 风险
//                         */
//                        if(modelingId.equals("4")){
//                            String  parammodelingId = "类别为:" +"风险,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 隐患
//                         */
//                        if(modelingId.equals("5")){
//                            String  parammodelingId = "类别为:" +"隐患,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 事件
//                         */
//                        if(modelingId.equals("6")){
//                            String  parammodelingId = "类别为:" +"事件,";
//                            param.append(parammodelingId);
//                        }
//                        /**
//                         * 违章信息
//                         */
//                        if(modelingId.equals("7")){
//                            String  parammodelingId = "类别为:" +"违章信息,";
//                            param.append(parammodelingId);
//                        }
//
//                        String ry="唯一标识为"+ instanceId +",";
//                        param.append(ry);
//                    }
//
//                    JSONArray jSONArray = jsonObject.getJSONArray("oldIdList");
//                    JSONArray jSONArray1 = jsonObject.getJSONArray("newIdList");
//                    String news="";
//                    String olds="";
//                    String jsonsc="";
//                    String jsonsc1="";
//                    String glsxNamec="";
//                    String xuw="";
//                    String wux="";
//                    String glsxNamep="";
//                    Map<String,String> oldsMap=new HashMap<>();
//                    Map<String,String> xw=new HashMap<>();
//                    Map<String,String> wx=new HashMap<>();
//                    if(jSONArray1.size()!=0 && jSONArray.size()!=0){
//                        for(int t=0;t< jSONArray.size();t++){
//                            String jsons = jSONArray.getString(t);
//                            JSONObject jsonsObj=JSONObject.fromObject(jsons);
//                            String glsxNameo = jsonsObj.getString("glsxName");
//                            if(t>0){
//                                jsonsc = jSONArray.getString(t-1);
//                                JSONObject ob1  = JSONObject.fromObject(jsonsc);
//                                glsxNamec = ob1.getString("glsxName");
//                            }
//                            if(glsxNamec.equals(glsxNameo)){
//                                String nameOld = jsonsObj.getString("glsxName");
//                                //修改之前的数据
//                                olds+=jsonsObj.getString("selfname");
//                                oldsMap.put(nameOld,nameOld+"将"+olds);
//
//
//                            }else {
//                                olds="";
//                                String oldnameb = jsonsObj.getString("glsxName");
//                                olds+=jsonsObj.getString("selfname");
//                                oldsMap.put(oldnameb,oldnameb+"将"+olds);
//                            }
//                        }
//
//                        for(int c=0;c < jSONArray1.size();c++){
//                            String jsons1 = jSONArray1.getString(c);
//                            JSONObject jsonsObj1 = JSONObject.fromObject(jsons1);
//                            String glsxName1 = jsonsObj1.getString("glsxName");
//                            if(c>0){
//                                jsonsc1 = jSONArray1.getString(c-1);
//                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
//                                glsxNamec = jsonsObjs1.getString("glsxName");
//                            }
//                            if(glsxNamec.equals(glsxName1)){
//                                //修改之后的数据
//                                String namenew = jsonsObj1.getString("glsxName");
//                                news += jsonsObj1.getString("selfname");
//                                oldsMap.put(namenew+"s","修改为:"+ news);
//
//                            }else {
//                                news="";
//                                String  newName = jsonsObj1.getString("glsxName");
//                                news += jsonsObj1.getString("selfname");
//                                oldsMap.put(newName+"s","修改为:"+ news);
//                            }
//                        }
//
//
//
//                        if(jSONArray1.size()!=0 && jSONArray.size()!=0){
//                            String lx="";
//                            String lw="";
//                            StringBuilder cspam = new StringBuilder();
//                            int cout=oldsMap.size();
//                            int fla = 0;
//                            for (String key:oldsMap.keySet()){
//
//                                lx= oldsMap.get(key);
//                                lw= oldsMap.get(key+"s");
//                                ++fla;
//                                if(lx!=null && lw !=null){
//                                    if(fla<=cout/2){
//                                        cspam.append(lx).append(lw);
//                                    }
//                                }
//                            }
//                            String knssName = "关联属性:"+ cspam;
//                            param.append(knssName);
//                        }
//                    }
//
//
//
//                    //                     由选择到无
//                    if(jSONArray1.size()==0 && jSONArray.size()!=0){
//                        for(int y=0;y < jSONArray.size();y++){
//                            String jsonsss = jSONArray.getString(y);
//                            JSONObject jsonsObjss = JSONObject.fromObject(jsonsss);
//                            String glsxNamess = jsonsObjss.getString("glsxName");
//                            if(y>0){
//                                jsonsc1 = jSONArray.getString(y-1);
//                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
//                                glsxNamep = jsonsObjs1.getString("glsxName");
//                            }
//                            if(glsxNamep.equals(glsxNamess)){
//                                //修改之后的数据
//                                String namenewss = jsonsObjss.getString("glsxName");
//                                xuw += jsonsObjss.getString("selfname");
//                                xw.put(namenewss,namenewss+"由"+xuw+"修改为【】");
//                            }else {
//                                xuw="";
//                                xuw += jsonsObjss.getString("selfname");
//                                String  xwssname = jsonsObjss.getString("glsxName");
//                                xw.put(xwssname+"s",xwssname+"由"+xuw+"修改为【】");
//                            }
//                        }
//                        String lx="";
//                        String lw="";
//                        StringBuilder cspamxw = new StringBuilder();
//                        int cout=xw.size();
//                        int fla = 0;
//                        for (String key:xw.keySet()){
//                            lx= xw.get(key);
//                            lw= xw.get(key+"s");
//                            ++fla;
//                            if(fla<=cout/2){
//
//                                cspamxw.append(lx).append(lw);
//                            }
//                        }
//                        String knssName = "关联属性:"+ cspamxw;
//                        param.append(knssName);
//                    }
//
////                    由选择到取消
//                    if(jSONArray1.size()!=0 && jSONArray.size()==0){
//                        for(int y=0;y < jSONArray1.size();y++){
//                            String jsonsss = jSONArray1.getString(y);
//                            JSONObject jsonsObjss = JSONObject.fromObject(jsonsss);
//                            String glsxNamess = jsonsObjss.getString("glsxName");
//                            if(y>0){
//                                jsonsc1 = jSONArray1.getString(y-1);
//                                JSONObject jsonsObjs1 = JSONObject.fromObject(jsonsc1);
//                                glsxNamep = jsonsObjs1.getString("glsxName");
//                            }
//                            if(glsxNamep.equals(glsxNamess)){
//                                //修改之后的数据
//                                String namenewss = jsonsObjss.getString("glsxName");
//                                xuw += jsonsObjss.getString("selfname");
//                                xw.put(namenewss,namenewss+"由【】"+"修改为"+xuw);
//                            }else {
//                                xuw="";
//                                xuw += jsonsObjss.getString("selfname");
//                                String  xwssname = jsonsObjss.getString("glsxName");
//                                xw.put(xwssname+"s",xwssname+"由【】"+"修改为"+xuw);
//                            }
//                        }
//                        String lx="";
//                        String lw="";
//                        String zg="";
//                        StringBuilder cspamxw = new StringBuilder();
//                        int cout=xw.size();
//                        int fla = 0;
//                        for (String key:xw.keySet()){
//                            lx= xw.get(key);
//                            lw= xw.get(key+"s");
//                            ++fla;
//                            if(fla<=cout/2){
//
//                                cspamxw.append(lx).append(lw);
//                            }
//                        }
//                        String knssName = "关联属性:"+ cspamxw;
//                        param.append(knssName);
//                    }
//
//                }







                //分开保存
                if(logAnnotation.xtcs().equals("InsertInstanceInfoIncludeThree")) {
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String instanceId = jsonObject.getString("instanceId");

                    BusinessLog.WEIYIBIAOSHI=instanceId;
                    BusinessLog.XIUGAI="1";

                    String instanceName = jsonObject.getString("instanceName");
                    String INSTANCE_ID =BusinessLog.INSTANCE_ID;
                    String INSTANCE_NAME =BusinessLog.INSTANCE_NAME;
                    String RELATION_ID =BusinessLog.RELATION_ID;
                    String glgxName=BusinessLog.glgxName;
                    String  Instances= BusinessLog.Instance;
                    Knowledge kns=null;
                    List<String> knlsit=null;
                    if(!Instances.isEmpty()){
                         kns = JSON.parseObject(Instances, Knowledge.class);
                         knlsit =kns.getInstanceNameList();


                    //没有修改之前的数据
                    String knss="";
                    String kc="";
                    for(int t=0;t < knlsit.size();t++){
                        knss+=knlsit.get(t)+",";
                    }
                    for(int t=0;t < knlsit.size();t++){
                        kc+=knlsit.get(t);
                    }

                    //绑定关联关系 修改之后的数据为空
                    JSONArray jSONArray = jsonObject.getJSONArray("idList");
                    if (jSONArray.size() == 0) {
                        if (!instanceName.toString().isEmpty()) {
                            String paraminstanceName =  glgxName +",将" + knss + "修改为【】";
                            param.append(paraminstanceName);
                        }
                    } else {
                    String jsons = jSONArray.getString(0);
                    JSONObject jsonsObj = JSONObject.fromObject(jsons);
                    String instanceIds = jsonsObj.getString("instanceId");
                    String relationId = jsonsObj.getString("relationId");
                    //根据instanceIdh获取名称
                    Map<String,Object> map1=new HashMap<>();
                    map1.put("instanceId", instanceId);
                    map1.put("relationId", relationId);
                    //修改之后的数据
                    List<Map<String, Object>> selectList = atlasMapper.getInstanceRelationInfo(map1);

                        String xgzh="";
                        for (Map<String,Object> mt:selectList){
                            xgzh+=mt.get("INSTANCE_NAME").toString();
                        }

                        if(!kc.equals(xgzh)) {
//                    类别id
                            String modelingId = jsonObject.getString("modelingId");

                            /**
                             * 工作计划
                             */
//                            if (modelingId.equals("1")) {
//                                String parammodelingId = "类别为:" + "工作计划,";
//                                param.append(parammodelingId);
//                            }
//                            /**
//                             * 外包单位
//                             */
//                            if (modelingId.equals("2")) {
//                                String parammodelingId = "类别为:" + "外包单位,";
//                                param.append(parammodelingId);
//                            }
//                            /**
//                             * 人员
//                             */
//                            if (modelingId.equals("3")) {
//                                String parammodelingId = "类别为:" + "人员,";
//                                param.append(parammodelingId);
//                            }
//                            /**
//                             * 风险
//                             */
//                            if (modelingId.equals("4")) {
//                                String parammodelingId = "类别为:" + "风险,";
//                                param.append(parammodelingId);
//                            }
//                            /**
//                             * 隐患
//                             */
//                            if (modelingId.equals("5")) {
//                                String parammodelingId = "类别为:" + "隐患,";
//                                param.append(parammodelingId);
//                            }
//                            /**
//                             * 事件
//                             */
//                            if (modelingId.equals("6")) {
//                                String parammodelingId = "类别为:" + "事件,";
//                                param.append(parammodelingId);
//                            }
//                            /**
//                             * 违章信息
//                             */
//                            if (modelingId.equals("7")) {
//                                String parammodelingId = "类别为:" + "违章信息,";
//                                param.append(parammodelingId);
//                            }
//                            if (!instanceName.toString().isEmpty()) {
//                                String paraminstanceName = "名称为:" + instanceName + ",唯一标识为:" + instanceId + ",";
//                                param.append(paraminstanceName);
//                            }
//                    if(!instanceId.toString().isEmpty()){
//                        String  paraminstanceId = "唯一标识为:" + instanceId +",";
//                        param.append(paraminstanceId);
//                    }

//                    if (!instanceIds.toString().isEmpty()) {
//                        String paraminstanceIds = "实例绑定id:" + instanceIds + ",";
//                        param.append(paraminstanceIds);
//                    }
                            if (knlsit.size() > 0) {
                                String knssName = glgxName + ":将," + knss + "修改为:" + xgzh;
                                param.append(knssName);
                            }
                        }else {
                            String paramInstanceId = "唯一标识为:" + instanceId;
                            param.append(paramInstanceId);
                        }
//                    if (!relationIds.toString().isEmpty()) {
//                        String paraminsrelationIds = "关系绑定id:" + relationIds + ",";
//                        param.append(paraminsrelationIds);
//                    }
                }
                    }else {
                        JSONArray jSONArray = jsonObject.getJSONArray("idList");
                        String jsons = jSONArray.getString(0);
                        JSONObject jsonsObj = JSONObject.fromObject(jsons);
                        String instanceIds = jsonsObj.getString("instanceId");
                        String relationId = jsonsObj.getString("relationId");
                        //根据instanceIdh获取名称
                        Map<String,Object> map1=new HashMap<>();
                        map1.put("instanceId", instanceId);
                        map1.put("relationId", relationId);
                        //修改之后的数据
                        List<Map<String, Object>> selectList = atlasMapper.getInstanceRelationInfo(map1);

                        String xgzh="";
                        for (Map<String,Object> mt:selectList){
                            xgzh+=mt.get("INSTANCE_NAME").toString();
                        }
                        String paramInstanceId = glgxName +"将【】修改为:" +xgzh;
                        param.append(paramInstanceId);
                    }

                }


                if(logAnnotation.xtcs().equals("updateModelingFive")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String typeId = jsonObject.getJSONObject("params").getString("typeId");
                    String typeName = jsonObject.getJSONObject("params").getString("typeName");
                    String typeCode = jsonObject.getJSONObject("params").getString("typeCode");
                    String typeDescribe = jsonObject.getJSONObject("params").getString("typeDescribe");
                    String typeIcon = jsonObject.getJSONObject("params").getString("typeIcon");
                    String srpModeling = BusinessLog.modify;
                    SrpModeling jk= JSON.parseObject(srpModeling,SrpModeling.class);
                    if(!(jk.getTypeDescribe().equals(typeDescribe) && jk.getTypeIcon().equals(typeIcon))){
                    if(!typeName.toString().isEmpty()){
                        if(typeName.equals("1")){
                            typeName="作业计划";
                        }
                        if(typeName.equals("2")){
                            typeName="外包单位";
                        }
                        if(typeName.equals("3")){
                            typeName="人员";
                        }
                        if(typeName.equals("4")){
                            typeName="风险";
                        }
                        if(typeName.equals("5")){
                            typeName="隐患";
                        }
                        if(typeName.equals("6")){
                            typeName="事件";
                        }
                        if(typeName.equals("7")){
                            typeName="违章信息";
                        }
                        String  paramtypeName = "名称为:" + typeName +",且唯一标识为："+typeId +"的数据,";
                        param.append(paramtypeName);
                    }

                    if(!typeCode.toString().isEmpty()&& !typeCode.equals(jk.getTypeCode())){
                        String  paramtypeCode = "将编码:"+jk.getTypeCode()+"修改为" + typeCode +",";
                        param.append(paramtypeCode);
                    }
                    if(!typeDescribe.toString().isEmpty() && !typeDescribe.equals(jk.getTypeDescribe())){
                        String  paramtypeDescribe = "将描述:"+jk.getTypeDescribe()+"修改为"  + typeDescribe +",";
                        param.append(paramtypeDescribe);
                    }
                    if(!typeIcon.toString().isEmpty()&& !typeIcon.equals(jk.getTypeIcon())){
                        String  paramtypeIcon = "将图标:"+jk.getTypeIcon()+"修改为" + typeIcon +",";
                        param.append(paramtypeIcon);
                    }
                    }else {
                        param.append("唯一标识为："+typeId +"的数据");
                    }
                }
            }
            if(logAnnotation.operator().equals("新增")){
                opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p + BusinessLog.CZ +BusinessLog.TYPE_SAVE + param +BusinessLog.FH +BusinessLog.XZS+ BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
            }else if (logAnnotation.operator().equals("查询")){
                opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p + BusinessLog.CZ +BusinessLog.TYPE_QUERY + param +BusinessLog.FH + BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
            }else if (logAnnotation.operator().equals("删除")){
                opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p+ BusinessLog.CZ +BusinessLog.TYPE_DEL + param +BusinessLog.FH +BusinessLog.XZS+ BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
            }else if (logAnnotation.operator().equals("修改")){
                 String up = BusinessLog.XIUGAI;
                 if(up.equals("1")){
             opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE + BusinessLog.WYBS+BusinessLog.WEIYIBIAOSHI +BusinessLog.XZS+  BusinessLog.GLSXON +logAnnotation.operator() +  BusinessLog.FH +BusinessLog.CZ  +BusinessLog.TYPE_MODIFY + param +BusinessLog.FH +BusinessLog.XZS+ BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
                 }else {
                     opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() +  BusinessLog.FH +BusinessLog.CZ  +BusinessLog.TYPE_MODIFY + param +BusinessLog.FH +BusinessLog.XZS+ BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
                 }
            }else if (logAnnotation.operator().equals("导入")){
                opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p+ logAnnotation.module() + BusinessLog.FH + BusinessLog.CZ +BusinessLog.TYPE_IMPORT + param +BusinessLog.FH +BusinessLog.XZS + BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
            }else if (logAnnotation.operator().equals("下载")){
                opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p+ logAnnotation.module() + BusinessLog.FH + BusinessLog.CZ +BusinessLog.DONLOD_IMPORT + param +BusinessLog.FH +BusinessLog.XZS + BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
            }

        }else if (paramValues.length==3){
            for (int i = 0; i < 1; i++) {
                Object cs = paramValues[0];
                if(logAnnotation.xtcs().equals("fileImportRelation")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);

                    String fileName = jsonObject.getString("fileName");
                    if(!fileName.toString().isEmpty()){
                        String  paramPfileName = "文件名称:" + fileName+ ",";
                        param.append(paramPfileName);
                    }

                }
            }
            opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p+ logAnnotation.module() + BusinessLog.FH +BusinessLog.TYPE_EXPORTS + param +BusinessLog.FH +BusinessLog.XZS+ BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
        }
        else{
            opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+ BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p + BusinessLog.CZ +BusinessLog.TYPE_QUERY+BusinessLog.FH + BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
        }
        //保存日志
        opLogInfoMapper.saveOpLogInfo(opLogInfo);
    }
}
