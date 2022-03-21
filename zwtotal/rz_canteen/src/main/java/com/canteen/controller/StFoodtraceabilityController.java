package com.canteen.controller;

import com.canteen.service.StFoodtraceabilityService;
import com.canteen.utils.PageBean;
import com.canteen.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/StFoodtraceability.do")
@Slf4j
@CrossOrigin
public class StFoodtraceabilityController {
    @Autowired
    private StFoodtraceabilityService stFoodtraceabilityService;

    //菜品溯源列表查询
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    private Object getList(HttpServletRequest request, @RequestBody String param){
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setData(stFoodtraceabilityService.getList(param));
        return resultMessage;
    }
}
