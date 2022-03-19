package com.raysdata.riskdataanalyzeserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.aop.LogAnnotation;
import com.raysdata.riskdataanalyzeserver.service.RelationinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/fileLead")
@Api("文件导入导出")
public class FileController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(FileController.class);


    @Autowired
    private RelationinfoService relationinfoService;
    /**
     * 文件导出EXCEL
     * @param request
     * @param response
     * @create
     * @return
     */
    @ApiOperation("关系文件导出()")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/fileImport"},produces = "text/html;charset=UTF-8"
    )
//    @LogAnnotation(module="关系文件导出()",operator="导出",xtcs="fileImportRelation",AllXtcs = "安全生产大数据模块/安全风险知识图谱/知识获取/关系")
    public WrappedResult fileImport(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> relationMap = this.relationinfoService.fileImport( request,response,data);
            return WrappedResult.successWrapedResult(relationMap);
        } catch (Exception var4) {
            return WrappedResult.failedWrappedResult("关系文件导出失败!");
        }
    }



    @SneakyThrows
    @ApiOperation("关系文件导入()")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/fileRelationLead"}
    )
//    @LogAnnotation(module="关系文件导入()",operator="导入",xtcs="fileRelationLead",AllXtcs = "安全生产大数据模块/安全风险知识图谱/知识获取/关系")
    public WrappedResult fileRelationLead(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> resultMap = this.relationinfoService.fileRelationLead(request,response);
            if("200".equals(resultMap.get("code"))){
                return WrappedResult.successWrapedResult(resultMap);
            }else{
                return WrappedResult.failedWrappedResult(resultMap.get("msg"));
            }
        } catch (Exception var4) {
            this.log.error("关系文件导入失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("关系文件导入失败!");
        }
    }



    @SneakyThrows
    @ApiOperation("实例文件导入()")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/fileExample"}
    )
//    @LogAnnotation(module="实例文件导入()",operator="导入",xtcs="fileExample",AllXtcs = "安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult fileExample(HttpServletRequest request, HttpServletResponse response) {
       try {
           Object obj = this.relationinfoService.fileExample(request,response);
           if(obj.equals("1")){
              return WrappedResult.failedWrappedResult("类别名称不能为空");
           }
           if(obj.equals("2")){
              return WrappedResult.failedWrappedResult("实例名称不能为空");
           }
           if(obj.equals("3")){
              return WrappedResult.failedWrappedResult("描述不能为空");
           }
           if(obj.equals("4")){
              return WrappedResult.failedWrappedResult("知识图谱建模类别编码不能为空");
           }
           if(obj.equals("5")){
               return WrappedResult.failedWrappedResult("类别名称不正确");
           }
           if(obj.equals("6")){
               return WrappedResult.failedWrappedResult("描述内容不能超过长度50");
           }

           if(obj.equals("导入模板作业计划类型不匹配")){
               return WrappedResult.failedWrappedResult("导入模板作业计划类型不匹配");
           }
           if(obj.equals("导入模板外包单位不匹配")){
               return WrappedResult.failedWrappedResult("导入模板外包单位不匹配");
           }
           if(obj.equals("导入模板人员不匹配")){
               return WrappedResult.failedWrappedResult("导入模板人员不匹配");
           }
           if(obj.equals("导入模板风险不匹配")){
               return WrappedResult.failedWrappedResult("导入模板风险不匹配");
           }
           if(obj.equals("导入模板隐患不匹配")){
               return WrappedResult.failedWrappedResult("导入模板隐患不匹配");
           }
           if(obj.equals("导入模板事件不匹配")){
               return WrappedResult.failedWrappedResult("导入模板事件不匹配");
           }
           if(obj.equals("导入模板违章信息不匹配")){
               return WrappedResult.failedWrappedResult("导入模板违章信息不匹配");
           }
            WrappedResult wrappedResult= WrappedResult.successWrapedResult(obj);
            return wrappedResult;
        } catch (Exception var4) {
            this.log.error("实例文件导入失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("实例文件导入失败!");
        }
    }



    @ApiOperation("知识融合")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/knowledge"}
    )
    @LogAnnotation(module="知识融合",operator="查询",xtcs="two",AllXtcs = "安全生产大数据模块/安全风险知识图谱/知识融合")
    public WrappedResult knowledge(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.relationinfoService.knowledge(param));
        } catch (Exception var4) {
            this.log.error("知识融合失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识融合失败!");
        }
    }

    @ApiOperation("知识获取-关系日志模板下载")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/relationshiptemplateDownload"}
    )
//    @LogAnnotation(module="知识获取-关系日志模板下载",operator="下载",xtcs="relationshiptemplateDownload",AllXtcs = "安全生产大数据模块/安全风险知识图谱/知识获取/关系")
    public WrappedResult relationshiptemplateDownload(HttpServletRequest request, @RequestBody String param) {
        try {
            String params =param;
            if(!param.isEmpty()){
                return WrappedResult.successWrapedResult(param);
            }else {
                throw new NullPointerException("传递的值是null");
            }
        } catch (Exception var4) {
            this.log.error("知识获取-关系日志模板下载失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-关系日志模板下载失败!");
        }
    }

    @ApiOperation("知识获取-实例日志模板下载")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/exampletemplateDownload"}
    )
//    @LogAnnotation(module="知识获取-实例日志模板下载",operator="下载",xtcs="exampletemplateDownload",AllXtcs = "安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult exampletemplateDownload(HttpServletRequest request, @RequestBody String param) {

        try {
            String params =param;
            if(!param.isEmpty()){
                return WrappedResult.successWrapedResult(param);
            }else {
                throw new NullPointerException("传递的值是null");
            }
        } catch (Exception var4) {
            this.log.error("知识获取-实例日志模板下载下载 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-实例日志模板下载失败!");
        }

    }
}