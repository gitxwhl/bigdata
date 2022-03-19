//package com.raysdata.riskmanagementserver.aop;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.nacos.client.utils.IPUtil;
//import com.nariit.pi6000.framework.po.ResponseResult;
//import com.nariit.pi6000.framework.util.DateUtil;
//import com.nariit.pi6000.ua.session.HttpSessionManager;
//import com.nariit.uvp.common.DataConstants;
//import com.nariit.uvp.common.StatusCodeConstant;
//import com.nariit.uvp.common.UVPException;
//import com.nariit.uvp.entity.rto.BaseRTO;
//import com.nariit.uvp.service.IOpLogInfoService;
//import com.nariit.uvp.util.ExceptionUtils;
//import com.raysdata.riskcommon.bean.OpLogInfo;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.CodeSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//import java.util.Set;
//
///**
// * 概述: controller日志统一处理类
// * 功能：输出日志
// * 创建时间：2019-12-18
// */
//@Aspect
//@Component
//public class UVPLogHandler {
//
//    /**
//     * 【标准接口】用户【默认审计员】IP为【127.0.0.1】，在时间【2019-09-07 22:35:50】进行了【新增】类型的【添加用户】操作；
//     * 新增了用户名称为abc的用户且唯一标识为*******的数据；业务处理共用时:[46]毫秒；操作结果为:【操作成功】
//     */
//
//    /**
//     * 日志工具类
//     */
//    private final static Logger logger = LoggerFactory.getLogger(UVPLogHandler.class);
//
//    @Autowired
//    private OperateDetailService operateDetailService;
//    @Autowired
//    private IOpLogInfoService opLogInfoService;
//
//    /**
//     * 开始进入方法时间
//     */
//    private static long startTime = 0;
//
//    /**
//     * 结束离开方法时间
//     */
//    private static long endTime = 0;
//
//    /**
//     * 是否记录日志
//     */
//    private static final String  IGNORE_METHODS = "createOpLogInfoOpLogInfoController";
//
//    /**
//     * 将日志对象与线程绑定
//     */
//    private static final ThreadLocal<OpLogInfo> threadLocal = new ThreadLocal<OpLogInfo>(){
//        @Override
//        protected OpLogInfo initialValue(){
//            return new OpLogInfo();
//        }
//    };
//
//    //private OpLogInfo opLogInfo = threadLocal.get();
//
//    /**
//     * 定义切点
//     * execution表达式，定义切点为controller包中的所有方法
//     * 第一个*:选中的连接点的方法返回值可以是任意类型
//     * 第二个*:选中的连接点的方法所在的类可以是任意名字
//     * 第三个*:选中的连接点的方法名字可以是任意名字
//     * (..):表示选中的连接点的方法参数可以是任意参数
//     */
//    @Pointcut("execution(public * com.nariit.uvp.controller.*.*(..))")
//    public void uvpLog() {
//    }
//
//    /**
//     * 方法执行前
//     *
//     * @param joinPoint 连接点，可以获得目标方法的参数属性
//     *                  joinPoint.getTarget(): 方法所在类
//     *                  joinPoint.getSignature().getName(): 方法名称
//     */
//    @Before("uvpLog()")
//    public void doBefore(JoinPoint joinPoint) {
//        // 在controller层以外获取当前正在处理的请求
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        // 获取日志相关参数
//        // 获取用户ID、 用户名称、用户IP地址
//        String userId = String.valueOf(HttpSessionManager.getAttribute(request, HttpSessionManager.USER_ID_KEY));
//        String userName = String.valueOf(HttpSessionManager.getAttribute(request, HttpSessionManager.AUTH_USER_KEY));
//        String userIp = IPUtil.getIpAddress(request);
//        // 获取controller名
//        String controllerFullName = joinPoint.getTarget().getClass().getName();
//        String[] controllerNames = controllerFullName.split("\\.");
//        String controllerName = controllerNames[controllerNames.length - DataConstants.NUM_ONE];
//        // 方法名称
//        String methodName = joinPoint.getSignature().getName();
//        //忽略不需要记日志的接口
//        if (ignoreMethod(methodName + controllerName)){
//            return;
//        }
//        //记录进入方法的时间
//        startTime = System.currentTimeMillis();
//        Date startDate = new Date(startTime);
//        String startDateStr = DateUtil.formatLongestDate(startDate);
//
//        // 构建日志信息
//        threadLocal.get().setTransactionBeginDate(startDateStr);
//        threadLocal.get().setUserId(userId);
//        threadLocal.get().setUserIp(userIp);
//        threadLocal.get().setUserName(userName);
//        // 设置请求编码
//        threadLocal.get().setRequestTypeId(controllerName + "_" + methodName);
//    }
//
//    /**
//     * 方法返回后
//     *
//     * @param responseResult
//     */
//    @AfterReturning(pointcut = "uvpLog()", returning = "responseResult")
//    public void doAfterReturning(JoinPoint joinPoint, ResponseResult responseResult) {
//        //如果传参noLogFlag=1，则不记录日志
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        String noLogFlag = request.getParameter("noLogFlag");
//
//        if ("1".equalsIgnoreCase(StringUtils.nullToEmpty(noLogFlag))) {
//            return;
//        }
//        //记录进入方法的时间
//        endTime = System.currentTimeMillis();
//        Date endDate = new Date(endTime);
//        String endDateStr = DateUtil.formatLongestDate(endDate);
//        String className = joinPoint.getTarget().getClass().getSimpleName();
//        String methodName = joinPoint.getSignature().getName();
//        //忽略不需要记日志的接口
//        if (ignoreMethod(methodName+className)){
//            return;
//        }
//        JSONObject jsonParams = this.formatParams(joinPoint);
//        //如果参数中有pageNo且值不是-1则不记录日志
////        if (!needLog(jsonParams)){
////            return;
////        }
//
//
//        if (StringUtils.isNotEmpty(responseResult.getOpLog())){
//            threadLocal.get().setContext(responseResult.getOpLog());
//        }else{
//            String operateDetail = operateDetailService.getContent(methodName + className, jsonParams);
//            String opLog = StringUtils.nullToEmpty(responseResult.getOpLog());
//
//            threadLocal.get().setContext(operateDetail + opLog);
//        }
//
//        //po类
//        threadLocal.get().setTransactionEndDate(endDateStr);
//        threadLocal.get().setOpResult(StatusCodeConstant.OK);
//        opLogInfoService.createOpLogInfo(threadLocal.get());
//    }
//
//    /**
//     * 方法抛出异常后
//     */
//    @AfterThrowing(pointcut = "uvpLog()", throwing = "exception")
//    public void doAfterThrowing(Throwable exception) {
//        //如果是日志本身抛出的异常,则继续向外抛出
//        if("com.nariit.uvp.aop.UVPLogHandler".equals(exception.getStackTrace()[DataConstants.NUM_ZERO].getClassName())){
//            throw new UVPException(((UVPException) exception).getErrorCode(), ((UVPException) exception).getErrorDetail());
//        }
//
//        threadLocal.get().setContext(ExceptionUtils.createExceptionMsg((Exception)exception));
//
//        //记录进入方法的时间
//        endTime = System.currentTimeMillis();
//        Date endDate = new Date(endTime);
//        String endDateStr = DateUtil.formatLongestDate(endDate);
//
//        threadLocal.get().setTransactionEndDate(endDateStr);
//        threadLocal.get().setOpResult(StatusCodeConstant.BUSINESS_ERROR);
//
//        opLogInfoService.createOpLogInfo(threadLocal.get());
//    }
//
//    JSONObject formatParams(JoinPoint joinPoint){
//        JSONObject jsonParams = new JSONObject();
//        // 参数值
//        Object[] paramValues = joinPoint.getArgs();
//        // 参数名称
//        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
//
//        for (int i = 0; i < paramValues.length; i++) {
//            String paramName = paramNames[i];
//            Object paramValue = paramValues[i];
//
//            // 过滤请求、响应、校验结果对象
//            if (paramValue instanceof BindingResult || paramValue instanceof HttpServletRequest ||
//                    paramValue instanceof HttpServletResponse) {
//                continue;
//            }
//
//            // 上传文件信息特殊处理
//            if (paramValue instanceof MultipartFile) {
//                MultipartFile file = (MultipartFile) paramValue;
//                jsonParams.put("fileName", file.getOriginalFilename());
//                continue;
//            }
//
//            jsonParams.put(paramName, paramValue);
//        }
//
//        return jsonParams;
//    }
//
//    boolean ignoreMethod(String methodName){
//        if (IGNORE_METHODS.contains(methodName)){
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    /**
//     * 判断分页查询接口是否需要记录日志方法
//     * @param jsonObject
//     * @return
//     */
//    boolean needLog(JSONObject jsonObject){
//        Set<String> keys =  jsonObject.keySet();
//        if (keys.size() == 1){
//            for (String item :keys){
//                Object obj = jsonObject.get(item);
//                if (obj instanceof BaseRTO){
//                    JSONObject rto = jsonObject.getJSONObject(item);
//                    if (rto.containsKey("pageNo") && !"-1".equals(rto.getString("pageNo"))){
//                        return false;
//                    }
//                    if (rto.containsKey("pageNum") && !"-1".equals(rto.getString("pageNum"))){
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//
//}
