package com.raysdata.riskdataanalyzeserver.controller;

import com.nariit.rmcp.common.vo.WrappedResult;
import com.raysdata.riskdataanalyzeserver.service.ImportExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({"/importFile"})
@Api("企业准入人员接口api")
public class ImportExcelController {
    private static final String SESSION_TIMEOUT_TIP = "系统登录超时，请重新登录！";
    private final Logger log = LoggerFactory.getLogger(ImportExcelController.class);

    @Autowired
    ImportExcelService importExcelService;

    @ApiOperation("模板导入")
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/importExcel"}
    )
    public WrappedResult importFile(HttpServletRequest request, HttpServletResponse response) {
        try {
            return WrappedResult.successWrapedResult(this.importExcelService.importExcel(request,response));
        } catch (Exception var4) {
            this.log.error("模板文件导入失败 :{}", var4.getMessage());
            return WrappedResult.failedWrappedResult("模板文件导入失败!");
        }
    }
}
