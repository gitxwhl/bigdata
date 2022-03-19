package com.raysdata.riskmanagementserver.aop;

import com.raysdata.riskmanagementserver.bean.OpLogInfo;
import com.raysdata.riskmanagementserver.bean.PtUaUser;
import com.raysdata.riskmanagementserver.dao.OpLogInfoMapper;
import com.raysdata.riskmanagementserver.utils.*;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
//@Transactional(rollbackFor = { Exception.class })
@Component
@Aspect
@Slf4j
public class LogAspect {
    @Autowired
   private OpLogInfoMapper opLogInfoMapper;

    @Value(value = "${logSwitch}")
    private boolean logSwitch;

    @Pointcut("execution(public * com.raysdata.riskmanagementserver.controller.*.*(..))")
    public void uvpLog() {
    }

@Pointcut("@annotation(com.raysdata.riskmanagementserver.aop.LogAnnotation)")
public void pt(){}

    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
        boolean logSwitchs=logSwitch;
         //开始时间
        long beginTime = System.currentTimeMillis();
         //执行方法
        Object result = joinPoint.proceed();
        //执行时长
        long time= System.currentTimeMillis() - beginTime;
        //保存日志
        if(logSwitch){
            recordLog(joinPoint,time,result,beginTime);
        }
        return result;
    }
    @Transactional
    public void recordLog(ProceedingJoinPoint joinPoint,long time,Object result,long beginTime){
//      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userId = UserUtil.getUserId(request);
//        System.out.println("=========================================================================>" + userId);
//        String userName= UserUtil.getUserName(request);
//        String userId = "0914a7add15611e688cc54ee7536cc52";
        String userIp = IPUtil.getIpAddress(request);
        //获取用户信息
        PtUaUser ptUaUser = opLogInfoMapper.findUser(userId);
//        JwtTokenUtil.getUser好d("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4MDQiLCJpYXQiOjE2MzAwMDMxMDcsInN1YiI6ImJSdDFWU2s0THYzTjdjdDIyckZkTGc9PSIsImF1ZCI6ImZwenJvRUxZSHlzL2l6MHY1b2F6OHRXV1owaWdCVkZsZGZYd1ZjY1Z2cnc9IiwiZXhwIjoxNjMwMDA2NzA3fQ.WshIMjE7rDcIjc1e5hR0Gj1NvpozaxpIF12gEn26F_w",JwtTokenUtil.BASE64_SECRET);
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
//        String startDateStr = DateUtil.formatLongestDate(startDate);
        opLogInfo.setOpTime(startDateStr);
        opLogInfo.setUpdateTime(startDateStr);
        opLogInfo.setCreateTime(startDateStr);
        opLogInfo.setTransactionBeginDate(startDateStr);
        opLogInfo.setTransactionEndDate(startDateStr);
        opLogInfo.setOptType("2");
        opLogInfo.setActionType("0");
        //类名——方法名
        opLogInfo.setRequestTypeId(controllerName + "_" + methodName);
        Method method   = signature.getMethod();
        // 参数值
        Object[] paramValues = joinPoint.getArgs();
        // 参数名称
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String,Object> csMap = new HashMap<>();

        boolean flag= JSONObject.fromObject(result).getBoolean("successful");
        if(flag){
            //返回结果
            opLogInfo.setOpResult("200");
            opLogInfo.setActionLevel("0");
            //日志类型
            opLogInfo.setLogType("0");
            opLogInfo.setOpResultZw("操作成功");
        }else {
            opLogInfo.setOpResult("500");
            opLogInfo.setActionLevel("3");
            opLogInfo.setLogType("1");
            opLogInfo.setOpResultZw("操作失败");
        }
        StringBuilder param = new StringBuilder();
        if(paramValues.length==2){
            for (int i = 0; i < 1; i++) {
                Object cs = paramValues[1];
                LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                if(logAnnotation.xtcs().equals("five")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String warningLevel = jsonObject.getString("warningLevel");
                    String systremApplication = jsonObject.getString("systremApplication");
                    String netoprationStatus = jsonObject.getString("netoprationStatus");
                    String startTime = jsonObject.getString("startTime");
                    String endTime = jsonObject.getString("endTime");

                    if(!warningLevel.isEmpty()){
                        switch (warningLevel){
                            case "01":
                                warningLevel="一级";
                                break;
                            case "02":
                                warningLevel="二级";
                                break;
                            case "03":
                                warningLevel="三级";
                                break;
                            case "04":
                                warningLevel="四级";
                                break;
                            case "05":
                                warningLevel="五级";
                                break;
                        }

                        String  pamWarningLevel = "预警等级:" + warningLevel + ",";
                        param.append(pamWarningLevel);
                    }
                    if(!systremApplication.isEmpty()){
                        switch (systremApplication){
                            case "":
                                systremApplication="系统应用情况";
                                break;
                            case "01":
                                systremApplication="ERP系统";
                                break;
                            case "02":
                                systremApplication="生产管理";
                                break;
                            case "03":
                                systremApplication="营销管理";
                                break;
                            case "04":
                                systremApplication="协同办公";
                                break;
                            case "05":
                                systremApplication="统计管理";
                                break;
                        }

                        String  pamSystremApplication = "系统应用:" + systremApplication + ",";
                        param.append(pamSystremApplication);
                    }

                    if(!netoprationStatus.isEmpty()){
                        switch (netoprationStatus){
                            case "01":
                                netoprationStatus="正常";
                                break;
                            case "02":
                                netoprationStatus="预警";
                                break;
                            case "03":
                                netoprationStatus="告警";
                                break;
                            case "04":
                                netoprationStatus="停机检修";
                                break;
                            case "05":
                                netoprationStatus="非停机检修";
                                break;
                        }
                        String  pamNetoprationStatus = "系统状态:" + netoprationStatus + ",";
                        param.append(pamNetoprationStatus);
                    }
                    if(!startTime.isEmpty()){
                        String  pamStartTime = "开始时间:" + startTime + ",";
                        param.append(pamStartTime);
                    }
                    if(!endTime.isEmpty()){
                        String  pamEndTime = "结束时间:" + endTime + ",";
                        param.append(pamEndTime);
                    }
                }

                if(logAnnotation.xtcs().equals("riskWarnAreaCNTFive")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String warningLevel = jsonObject.getString("warningLevel");
                    String areaId = jsonObject.getString("areaId");
                    String workType = jsonObject.getString("workType");
                    String startTime = jsonObject.getString("startTime");
                    String endTime = jsonObject.getString("endTime");

                    if(!warningLevel.isEmpty()){
                        if(warningLevel.equals("01")){
                            warningLevel="极高风险";
                        }
                        if(warningLevel.equals("02")){
                            warningLevel="高度风险";
                        }
                        if(warningLevel.equals("03")){
                            warningLevel="显著风险";
                        }
                        if(warningLevel.equals("04")){
                            warningLevel="一般风险";
                        }
                        if(warningLevel.equals("05")){
                            warningLevel="稍有风险";
                        }
                        String  pamWarningLevel = "风险等级:" + warningLevel + ",";
                        param.append(pamWarningLevel);
                    }
                    if(!areaId.isEmpty()){
                        String  pamareaId = "省份编码:" + areaId + ",";
                        param.append(pamareaId);
                    }
                    if(!workType.isEmpty()){
                        if(workType.equals("01")){
                            workType="生产检修";
                        }
                        if(workType.equals("02")){
                            workType="大修挤改";
                        }
                        if(workType.equals("03")){
                            workType="基建工程";
                        }
                        if(workType.equals("04")){
                            workType="营销作业";
                        }
                        if(workType.equals("06")){
                            workType="信息通信";
                        }
                        if(workType.equals("07")){
                            workType="外部施工";
                        }

                        String  pamworkType = "作业类型:" + workType + ",";
                        param.append(pamworkType);
                    }
                    if(!startTime.isEmpty()){
                        String  pamStartTime = "开始时间:" + startTime + ",";
                        param.append(pamStartTime);
                    }
                    if(!endTime.isEmpty()){
                        String  pamEndTime = "结束时间:" + endTime + ",";
                        param.append(pamEndTime);
                    }
                }


                if(logAnnotation.xtcs().equals("srpRiskIndusRiskWarnAreaCNTThree")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String industryType = jsonObject.getString("industryType");
                    String startTime = jsonObject.getString("startTime");
                    String endTime = jsonObject.getString("endTime");
                    if(!industryType.isEmpty()){

                        switch (industryType){
                            case "01":
                                industryType="直属产业";
                                break;
                            case "02":
                                industryType="水电厂";
                                break;
                            case "03":
                                industryType="抽水蓄能";
                                break;
                            case "04":
                                industryType="大坝";
                                break;
                            case "05":
                                industryType="电工制造企业";
                                break;
                            case "06":
                                industryType="风力发电";
                                break;
                            case "07":
                                industryType="太阳能发电";
                                break;
                            case "08":
                                industryType="储能发电";
                                break;
                        }




                        String  pamIndustryType = "产业类型:" + industryType + ",";
                        param.append(pamIndustryType);
                    }
                    if(!startTime.isEmpty()){
                        String  pamStartTime = "开始时间:" + startTime + ",";
                        param.append(pamStartTime);
                    }
                    if(!endTime.isEmpty()){
                        String  pamEndTime = "结束时间:" + endTime + ",";
                        param.append(pamEndTime);
                    }
                }

                if(logAnnotation.xtcs().equals("two")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String areaId = jsonObject.getString("areaId");
                    String warningLevel = jsonObject.getString("warningLevel");
                    if(!areaId.isEmpty()){
                        String  pamareaId = "区域id:" + areaId + ",";
                        param.append(pamareaId);
                    }

                    if(!warningLevel.isEmpty()){

                        switch (warningLevel){
                            case "01":
                                warningLevel="一级";
                                break;
                            case "02":
                                warningLevel="二级";
                                break;
                            case "03":
                                warningLevel="三级";
                                break;
                            case "04":
                                warningLevel="四级";
                                break;
                        }
                        String  pamWarningLevel = "风险等级:" + warningLevel + ",";
                        param.append(pamWarningLevel);
                    }
                }


                if(logAnnotation.xtcs().equals("srpRiskWorkWarnAreaCNTTwo")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String areaId = jsonObject.getString("areaId");
//                    Integer warnType = Integer.parseInt(jsonObject.getString("warnType"));
                    if(!areaId.isEmpty()){
                        String  pamareaId = "地区编码:" + areaId + ",";
                        param.append(pamareaId);
                    }

//                    if(!warnType.toString().isEmpty()){
//                       String warnTypes = warnType.toString();
//                        if(warnTypes.equals("1")){
//                            warnTypes="生产检修";
//                        }
//                        String  pamwarnType = "作业类型:" + warnTypes + ",";
//                        param.append(pamwarnType);
//                    }
                }

                if(logAnnotation.xtcs().equals("riskWarnAreaCNTTwo")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String warningLevel = jsonObject.getString("warningLevel");
                    Integer warnType =  (ObjectUtils.isNotEmpty(jsonObject.getString("warnType"))? Integer.parseInt(jsonObject.getString("warnType")):0);

                    if(!warningLevel.isEmpty()){
                        switch (warningLevel){
                            case "1":
                                warningLevel="一级";
                                break;
                            case "2":
                                warningLevel="二级";
                                break;
                            case "3":
                                warningLevel="三级";
                                break;
                            case "4":
                                warningLevel="四级";
                                break;
                            case "5":
                                warningLevel="五级";
                                break;
                            case "6":
                                warningLevel="六级";
                                break;
                            case "7":
                                warningLevel="七级";
                                break;
                            case "8":
                                warningLevel="八级";
                                break;
                        }
                        String  pamwarningLevel = "风险等级:" + warningLevel + ",";
                        param.append(pamwarningLevel);
                    }

                    if(!warnType.toString().isEmpty()){
                        String wan = warnType.toString();
                        switch (wan){
                            case "1":
                                wan="电网风险";
                                break;
                            case "2":
                                wan="作业风险";
                                break;
                            case "4":
                                wan="产业风险";
                                break;
                            case "5":
                                wan="网络风险";
                                break;
                        }
                        String  pamWarnType = "风险类型:" + wan + ",";
                        param.append(pamWarnType);
                    }
                }


                if(logAnnotation.xtcs().equals("one")){
                    if(!cs.toString().isEmpty()){
                        String  paramareaId = "区域id:" + cs;
                        param.append(paramareaId);
                    }
                }

                if(logAnnotation.xtcs().equals("srpRiskWorkWarnWorkStateCNTOne")){
                    if(!cs.toString().isEmpty()){
                        String  paramareaId = "地区编码:" + cs;
                        param.append(paramareaId);
                    }
                }

                if(logAnnotation.xtcs().equals("srpRiskWorkWarnForAllWorkTypeCNTOne")){
                    if(!cs.toString().isEmpty()){
                        String  paramareaId = "地区编码:" + cs;
                        param.append(paramareaId);
                    }
                }

                if(logAnnotation.xtcs().equals("workPlanIdOne")){
                    if(!cs.toString().isEmpty()){
                        String  paramworkPlanId = "输变电工程施工风险预警通知单ID:" + cs;
                        param.append(paramworkPlanId);
                    }
                }
                if(logAnnotation.xtcs().equals("warningLevelOne")){
                    if(!cs.toString().isEmpty()){
                        String lev = cs.toString();
                        switch (lev){
                            case "01":
                                cs="一级";
                                break;
                            case "02":
                                cs="二级";
                                break;
                            case "03":
                                cs="三级";
                                break;
                            case "04":
                                cs="四级";
                                break;
                            case "05":
                                cs="五级";
                                break;
                            case "06":
                                cs="六级";
                                break;
                            case "07":
                                cs="七级";
                                break;
                            case "08":
                                cs="八级";
                                break;
                        }
                        String  paramwarningLevelOneId = "预警等级:" + cs;
                        param.append(paramwarningLevelOneId);
                    }
                }
                if(logAnnotation.xtcs().equals("dateTypelOne")){
                    if(!cs.toString().isEmpty()){
                        switch (cs.toString()){
                            case "1":
                                cs="日";
                                break;
                            case "2":
                                cs="月";
                                break;
                            case "3":
                                cs="年";
                                break;
                        }
                        String  paramdateTypelOneId = "时间类型:" + cs;
                        param.append(paramdateTypelOneId);
                    }
                }
                if(logAnnotation.xtcs().equals("gridWarnNoticeIdOne")){
                    if(!cs.toString().isEmpty()){
                        String  paramdateTypelOneId = "电网运行风险预警通知单id:" + cs;
                        param.append(paramdateTypelOneId);
                    }
                }

                if(logAnnotation.xtcs().equals("srpRiskWorkPlanInfoOne")){
                    if(!cs.toString().isEmpty()){
                        String  paramworkPlanId = "作业计划id:" + cs;
                        param.append(paramworkPlanId);
                    }
                }

                if(logAnnotation.xtcs().equals("onePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String cityId = jsonObject.getJSONObject("params").getString("cityID");
                    if(!cityId.isEmpty()){
                        String  paramareaId = "区域id:" + cityId;
                        param.append(paramareaId);
                    }

                }


                if(logAnnotation.xtcs().equals("ninePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);

                    String title = jsonObject.getJSONObject("params").getString("title");
                    String warningLevel = jsonObject.getJSONObject("params").getString("warningLevel");
                    String warnNum = jsonObject.getJSONObject("params").getString("warnNum");
                    String publishOrg = jsonObject.getJSONObject("params").getString("publishOrg");
                    String warnStatus = jsonObject.getJSONObject("params").getString("warnStatus");
                    String startTime = jsonObject.getJSONObject("params").getString("startTime");
                    String endTime = jsonObject.getJSONObject("params").getString("endTime");

                    if(!title.isEmpty()){
                        String  paramtitle = "标题:" + title+",";
                        param.append(paramtitle);
                    }
                    if(!warningLevel.isEmpty()){
                        if(warningLevel.equals("01")){
                            warningLevel="一级";
                        }
                        if(warningLevel.equals("02")){
                            warningLevel="二级";
                        }
                        if(warningLevel.equals("03")){
                            warningLevel="三级";
                        }
                        if(warningLevel.equals("04")){
                            warningLevel="四级";
                        }
                        if(warningLevel.equals("05")){
                            warningLevel="五级";
                        }

                        if(warningLevel.equals("06")){
                            warningLevel="六级";
                        }

                        if(warningLevel.equals("07")){
                            warningLevel="七级";
                        }
                        if(warningLevel.equals("08")){
                            warningLevel="八级";
                        }
                        String  paramwarningLevel = "预警等级:" + warningLevel+",";
                        param.append(paramwarningLevel);
                    }
                    if(!warnNum.isEmpty()){
                        String  paramwarnNum = "预警编号:" + warnNum+",";
                        param.append(paramwarnNum);
                    }
                    if(!publishOrg.isEmpty()){
                        String  parampublishOrg = "发布单位:" + publishOrg+",";
                        param.append(parampublishOrg);
                    }

                    if(!warnStatus.isEmpty()){
                        if(warnStatus.equals("01")){
                            warnStatus="已发布";
                        }
                        if(warnStatus.equals("02")){
                            warnStatus="告知";
                        }
                        if(warnStatus.equals("03")){
                            warnStatus="实施";
                        }
                        if(warnStatus.equals("04")){
                            warnStatus="解除";
                        }
                        if(warnStatus.equals("05")){
                            warnStatus="延期";
                        }
                        if(warnStatus.equals("06")){
                            warnStatus="作废";
                        }

                        String  paramwarnStatus = "预警状态:" + warnStatus+",";
                        param.append(paramwarnStatus);
                    }

                    if(!startTime.isEmpty()){
                        String  pamStartTime = "预警计划始划时间:" + startTime + ",";
                        param.append(pamStartTime);
                    }
                    if(!endTime.isEmpty()){
                        String  pamEndTime = "预警计划结束时间:" + endTime + ",";
                        param.append(pamEndTime);
                    }
                }

                if(logAnnotation.xtcs().equals("risRiskIndusRiskWarnListNinePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String title = jsonObject.getJSONObject("params").getString("title");
                    String warningLevel = jsonObject.getJSONObject("params").getString("warningLevel");
                    String warnNum = jsonObject.getJSONObject("params").getString("warnNum");
                    String publishOrg = jsonObject.getJSONObject("params").getString("publishOrg");
                    String warnStatus = jsonObject.getJSONObject("params").getString("warnStatus");
                    String startTime = jsonObject.getJSONObject("params").getString("startTime");
                    String endTime = jsonObject.getJSONObject("params").getString("endTime");

                    if(!title.isEmpty()){
                        String  paramtitle = "标题:" + title+",";
                        param.append(paramtitle);
                    }
                    if(!warningLevel.isEmpty()){
                        String  paramwarningLevel = "预警等级:" + warningLevel+",";
                        param.append(paramwarningLevel);
                    }
                    if(!warnNum.isEmpty()){
                        String  paramwarnNum = "预警编号:" + warnNum+",";
                        param.append(paramwarnNum);
                    }
                    if(!publishOrg.isEmpty()){
                        String  parampublishOrg = "发布单位:" + publishOrg+",";
                        param.append(parampublishOrg);
                    }
                    if(!warnStatus.isEmpty()){
                        String  paramwarnStatus = "预警状态:" + warnStatus+",";
                        param.append(paramwarnStatus);
                    }

                    if(!startTime.isEmpty()){
                        String  pamStartTime = "预警计开始划时间:" + startTime + ",";
                        param.append(pamStartTime);
                    }
                    if(!endTime.isEmpty()){
                        String  pamEndTime = "预警计结束时间:" + endTime + ",";
                        param.append(pamEndTime);
                    }
                }

                if(logAnnotation.xtcs().equals("riskWarnAreaCNTThree")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    Integer warnType = Integer.parseInt(jsonObject.getString("warnType"));
                    String typeGridStructure = jsonObject.getString("typeGridStructure");
                    String warnLevel = jsonObject.getString("warnLevel");

                    if(!warnType.toString().isEmpty()){
                        String wanr= warnType.toString();
                        if(wanr.equals("1")){
                            switch (wanr){
                                case "1":
                                    wanr ="电网运行";
                                    break;
                            }
                        }
                        switch (wanr){
                            case "01":
                                wanr ="电网运行";
                            break;
                            case "2":
                                wanr ="电网建设";
                                break;
                            case "3":
                                wanr ="产业风险";
                                break;
                            case "4":
                                wanr ="网络风险";
                                break;
                        }
                        String  paramwarnType = "类型:" + wanr+",";
                        param.append(paramwarnType);
                    }
                    if(!typeGridStructure.isEmpty()){
                        switch (typeGridStructure){
                            case "01":
                                typeGridStructure ="变电站";
                                break;
                            case "02":
                                typeGridStructure ="换流站";
                                break;
                            case "03":
                                typeGridStructure ="抽水蓄能电站";
                                break;
                        }
                        String  paramtypeGridStructure = "结构:" + typeGridStructure+",";
                        param.append(paramtypeGridStructure);
                    }else if (typeGridStructure.isEmpty()&&!warnType.toString().equals("2")&&!warnType.toString().equals("3")&&!warnType.toString().equals("4")){
                        String  paramtypeGridStructure = "结构:" + "输电线路"+",";
                        param.append(paramtypeGridStructure);

                    }
                    if(!warnLevel.isEmpty()){
                        switch (warnLevel){
                            case "01":
                                warnLevel ="一级";
                                break;
                            case "02":
                                warnLevel ="二级";
                                break;
                            case "03":
                                warnLevel ="三级";
                                break;
                            case "04":
                                warnLevel ="四级";
                                break;
                            case "05":
                                warnLevel ="五级";
                                break;
                            case "06":
                                warnLevel ="六级";
                                break;
                            case "07":
                                warnLevel ="七级";
                                break;
                            case "08":
                                warnLevel ="八级";
                                break;
                        }
                        String  paramwarnLevel = "等级:" + warnLevel+",";
                        param.append(paramwarnLevel);
                    }
                }

                if(logAnnotation.xtcs().equals("srpRiskGridConstWorkListThreePage")){
                    JSONObject jsonObject = JSONObject.fromObject(cs);
                    String cityId = jsonObject.getJSONObject("params").getString("cityID");
                    if(!cityId.toString().isEmpty()){
                        String  paramcityId = "地区编码:" + cityId;
                        param.append(paramcityId);
                    }
                }
            }
            LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
                opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH + BusinessLog.TO+ logAnnotation.AllXtcs()+BusinessLog.PAGE+BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p + BusinessLog.CZ +BusinessLog.TYPE_QUERY + param +BusinessLog.FH + BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
        }else{
            LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
            opLogInfo.setContext(BusinessLog.QUERY+ptUaUser.getUserName()+BusinessLog.FH+BusinessLog.IP + userIp + BusinessLog.FH + BusinessLog.ZSJ + startDateStr + BusinessLog.FH+ BusinessLog.TO+ logAnnotation.AllXtcs() + BusinessLog.PAGE + BusinessLog.ON +logAnnotation.operator() + BusinessLog.TYPE_p+ BusinessLog.CZ + BusinessLog.CZRESULT +opLogInfo.getOpResultZw()+ BusinessLog.FH);
        }
        //保存日志
        opLogInfoMapper.saveOpLogInfo(opLogInfo);
    }
}
