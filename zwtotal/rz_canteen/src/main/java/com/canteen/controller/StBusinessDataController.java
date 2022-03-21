package com.canteen.controller;

import com.canteen.service.StBusinessDataService;
import com.canteen.utils.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/selectStBusinessData.do")
@Slf4j
@CrossOrigin
public class StBusinessDataController {
    @Autowired
    StBusinessDataService stBusinessDataService;

    /**
     * 查询营业数据
     * @param request
     * @param param
     * @return
     */
    @RequestMapping(value = "/selectStBusinessData",method = RequestMethod.POST)
    private PageBean selectStBusinessData(HttpServletRequest request, @RequestBody String param){
        return stBusinessDataService.selectStBusinessData(param);
    }

}
