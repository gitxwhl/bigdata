package com.canteen.controller;

import com.canteen.service.StSettlementexceptionService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StSettlementexception.do")
@Slf4j
@CrossOrigin
public class StSettlementexceptionController {

    @Autowired
    private StSettlementexceptionService stSettlementexceptionService;

    //异常列表查询
    @RequestMapping(value = "/getExceptionList", method = RequestMethod.POST)
    private PageBean getExceptionList(HttpServletRequest request, @RequestBody String param){
        return stSettlementexceptionService.getExceptionList(param);
    }

    //获取字典表定义
    @RequestMapping(value = "/dictionaries", method = RequestMethod.GET)
    private Object dictionaries(HttpServletRequest request){
        return stSettlementexceptionService.dictionaries();
    }
}
