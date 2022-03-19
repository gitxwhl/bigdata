package com.raysdata.riskdataanalyzeserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.aop.LogAnnotation;
import com.raysdata.riskdataanalyzeserver.bean.Instanceinfo;
import com.raysdata.riskdataanalyzeserver.service.AtlasService;
import com.raysdata.riskdataanalyzeserver.utils.BusinessLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping({"/atlasInformation"})
@Api("企业准入人员接口api")
public class AtlasController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(AtlasController.class);

    @Autowired
    private AtlasService atlasService;

    @ApiOperation("知识图谱建模-查询列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getModeling"}
    )
    @LogAnnotation(module="知识图谱建模-查询列表",operator="查询",xtcs="oneName",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识图谱建模")
    public WrappedResult getModeling(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getModeling(param));
        } catch (Exception var4) {
            this.log.error("获取知识图谱建模失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("获取知识图谱建模失败!");
        }
    }



    @ApiOperation("知识图谱建模-删除")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/deleteModeing"}
    )
    @LogAnnotation(module="知识图谱建模-删除",operator="删除",xtcs="deleteModeingOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识图谱建模")
    public WrappedResult deleteModeing(HttpServletRequest request, @RequestBody String param) {
        try {
            String name = this.atlasService.deleteModeing(param);
            if(name.equals("数据已被删除，请刷新重试!")){
                return WrappedResult.failedWrappedResult("数据已被删除，请刷新重试!");
            }
            return WrappedResult.successWrapedResult(name);
        } catch (Exception var4) {
            this.log.error("删除知识图谱建模失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("删除知识图谱建模失败!");
        }
    }

    @ApiOperation("知识图谱建模-新增")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/insertModeing"}
    )
    @LogAnnotation(module="知识图谱建模-新增",operator="新增",xtcs="saveinsertModeingfour",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识图谱建模")
    public WrappedResult insertModeing(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.insertModeing(param));

        } catch (Exception var4) {
            this.log.error("知识图谱建模新增失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识图谱建模新增失败!");
        }
    }


    @ApiOperation("知识图谱建模-编辑")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/updateModeling"}
    )
    @LogAnnotation(module="知识图谱建模-编辑",operator="修改",xtcs="updateModelingFive",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识图谱建模")
    public WrappedResult updateModeling(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.updateModeling(param));
        } catch (Exception var4) {
            this.log.error("知识图谱建模编辑失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识图谱建模编辑失败!");
        }
    }

    @ApiOperation("知识图谱建模-生成编号")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/generatedNumber"}
    )
    @LogAnnotation(module="知识图谱建模-生成编号",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识图谱建模")
    public WrappedResult generatedNumber(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.generatedNumber());
        } catch (Exception var4) {
            this.log.error("生成编号失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("生成编号失败!");
        }
    }

    @ApiOperation("知识图谱建模-查看详情")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getModenlingDetails"}
    )
    @LogAnnotation(module="知识图谱建模-查看详情",operator="查询",xtcs="getModenlingDetailsOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识图谱建模")
    public WrappedResult getModenlingDetails(HttpServletRequest request,@RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getModenlingDetails(param));
        } catch (Exception var4) {
            this.log.error("知识图谱建模查看详情失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识图谱建模查看详情失败!");
        }
    }

    @ApiOperation("知识获取-关系列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getRelationInfo"}
    )
    @LogAnnotation(module="知识获取-关系列表",operator="查询",xtcs="two",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/关系")
    public WrappedResult getRelationInfo(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getRelationInfo(param));
        } catch (Exception var4) {
            this.log.error("知识获取-关系列表查询失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-关系列表查询失败!");
        }
    }


    @ApiOperation("知识获取-关系新增")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/insertRelationInfo"}
    )
    @LogAnnotation(module="知识获取-关系新增",operator="新增",xtcs="insertRelationInfoSix",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/关系")
    public WrappedResult insertRelationInfo(HttpServletRequest request, @RequestBody String param) {
        try {
            Map<String, String> resultMap = this.atlasService.insertRelationInfo(param);
            if("200".equals(resultMap.get("code"))){
                return WrappedResult.successWrapedResult(resultMap);
            }else{
                return WrappedResult.failedWrappedResult(resultMap.get("msg"));
            }
        } catch (Exception var4) {
            this.log.error("知识获取-关系列表新增失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-关系新增失败!");
        }
    }

    @ApiOperation("知识获取-关系编号生成")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/relationNumber"}
    )
    @LogAnnotation(module="知识获取-关系编号生成",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/关系")
    public WrappedResult relationNumber(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.relationNumber());
        } catch (Exception var4) {
            this.log.error("生成编号失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("生成编号失败!");
        }
    }

    @ApiOperation("知识获取-实例列表")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getInstanceInfo"}
    )
    @LogAnnotation(module="知识获取-关系列表",operator="查询",xtcs="getInstanceInfofourPage",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult getInstanceInfo(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getInstanceInfo(param));
        } catch (Exception var4) {
            this.log.error("知识获取-实例列表获取失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-实例列表获取失败!");
        }
    }

    @ApiOperation("知识获取-实例编号生成")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/instanceNumber"}
    )
    @LogAnnotation(module="知识获取-实例编号生成",operator="查询",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult instanceNumber(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.instanceNumber());
        } catch (Exception var4) {
            this.log.error("知识获取-实例编号生成失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-实例编号生成失败!");
        }
    }

    @ApiOperation("知识获取-删除实例")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/deleteInstance"}
    )
    @LogAnnotation(module="知识获取-删除实例",operator="删除",xtcs="deleteInstanceOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult deleteInstance(HttpServletRequest request, @RequestBody String param) {
        try {
           String suc = this.atlasService.deleteInstance(param);
            if(suc.equals("数据已删除，请刷新重试!")){
                return WrappedResult.failedWrappedResult("数据已删除，请刷新重试!");
            }
            return WrappedResult.successWrapedResult(suc);
        } catch (Exception var4) {
            this.log.error("知识获取-删除实例失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-删除实例失败!");
        }
    }

    @ApiOperation("知识获取-查询关联属性")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getRelationInfoById"}
    )
    @LogAnnotation(module="知识获取-查询关联属性",operator="查询",xtcs="getRelationInfoByIdOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult getRelationInfoById(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getRelationInfoById(param));
        } catch (Exception var4) {
            this.log.error("知识获取-查询关联属性失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-查询关联属性失败!");
        }
    }

    @ApiOperation("知识获取-类别查询实例")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getInstanceInfoByType"}
    )
    @LogAnnotation(module="知识获取-类别查询实例",operator="查询",xtcs="getInstanceInfoTwo",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult getInstanceInfoByType(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getInstanceInfoByType(param));
        } catch (Exception var4) {
            this.log.error("知识获取-类别查询实例失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-类别查询实例失败!");
        }
    }



    @ApiOperation("知识获取-获取实例详情")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/findinstancexq"}
    )
//    @LogAnnotation(module="查询",operator="查询",xtcs="getInstanceInfoTwo",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult findinstancexq(HttpServletRequest request, @RequestBody Instanceinfo instanceinfo) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.findinstancexq(instanceinfo));
        } catch (Exception var4) {
            this.log.error("知识获取-获取实例详情失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-获取实例详情失败!");
        }
    }






    @ApiOperation("知识获取-新增实例")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/insertInstanceInfo"}
    )
    @LogAnnotation(module="知识获取-新增实例",operator="新增",xtcs="insertInstanceInfoFour",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult insertInstanceInfo(HttpServletRequest request, @RequestBody String param) {
        try {
            Map<String, String> resultMap = this.atlasService.insertInstanceInfo(param);
            if(BusinessLog.csts.equals("实例名称不能大于10")){
                return WrappedResult.failedWrappedResult("实例名称不能大于10");
            }
            if(BusinessLog.csts1.equals("描述不能大于100")){
                return WrappedResult.failedWrappedResult("描述不能大于100");
            }
            if("500".equals(resultMap.get("code"))){
                return WrappedResult.failedWrappedResult(resultMap.get("msg"));
            }
            return WrappedResult.successWrapedResult(resultMap);
        } catch (Exception var4) {
            this.log.error("知识获取-新增实例失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-新增实例失败!");
        }
    }
    @ApiOperation("知识获取-实例详情")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getInstanceInfoById"}
    )
    @LogAnnotation(module="知识获取-实例详情",operator="查询",xtcs="getInstanceInfoByIdOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult getInstanceInfoById(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getInstanceInfoById(param));
        } catch (Exception var4) {
            this.log.error("知识获取-实例详情失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-实例详情失败!");
        }
    }

    @ApiOperation("知识获取-根据ID查询实例关系")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getInstancerelationinfoById"}
    )
    @LogAnnotation(module="知识获取-根据ID查询实例关系",operator="查询",xtcs="getInstancerelationinfoByIdThree",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult getInstancerelationinfoById(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getInstancerelationinfoById(param));
        } catch (Exception var4) {
            this.log.error("知识获取-根据ID查询实例关系失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-根据ID查询实例关系失败!");
        }
    }

    @ApiOperation("知识获取-编辑实例(分开编辑)")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/updateInstanceInfo"}
    )
    @LogAnnotation(module="知识获取-编辑实例",operator="修改",xtcs="updateInstanceInfoOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例")
    public WrappedResult updateInstanceInfoInclde(HttpServletRequest request, @RequestBody String param) {
        try {
            String model = this.atlasService.updateInstanceInfoInclude(param);
           if(model.equals("实例名称重复")){
               return WrappedResult.failedWrappedResult("实例名称重复");
           }
            if(model.equals("实例名称不能为null")){
                return WrappedResult.failedWrappedResult("实例名称不能为null");
            }
            return WrappedResult.successWrapedResult(model);
        } catch (Exception var4) {
            this.log.error("知识获取-编辑实例失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-编辑实例失败!");
        }
    }
    @ApiOperation("知识获取-拓扑图")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getGplot"}
    )
    @LogAnnotation(module="知识获取-拓扑图",operator="查询",xtcs="getGplotOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识获取/实例/详情")
    public WrappedResult getGplot(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getGplot(param));
        } catch (Exception var4) {
            this.log.error("知识获取-拓扑图失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-拓扑图失败!");
        }
    }

    @ApiOperation("知识获取-拓扑图根据名称查询")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/getGplotName"}
    )
    @LogAnnotation(module="知识获取-拓扑图根据名称查询",operator="查询",xtcs="getGplotNameOne",AllXtcs = "安全生产风险管控平台/安全生产大数据模块/安全风险知识图谱/知识图谱应用")
    public WrappedResult getGplotName(HttpServletRequest request, @RequestBody String param) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.getGplotName(param));
        } catch (Exception var4) {
            this.log.error("知识获取-根据名称查询拓扑图失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-根据名称查询拓扑图失败!");
        }
    }

    @ApiOperation("知识获取-获取实例关联关系")
    @RequestMapping(method = {RequestMethod.POST},value = {"/findTopology"})
    public WrappedResult findTopology(HttpServletRequest request) {
        try {
            return WrappedResult.successWrapedResult(this.atlasService.findTopology());
        } catch (Exception var4) {
            this.log.error("知识获取-获取实例关联关系失败", var4.getMessage());
            return WrappedResult.failedWrappedResult("知识获取-获取实例关联关系!");
        }
    }



}
