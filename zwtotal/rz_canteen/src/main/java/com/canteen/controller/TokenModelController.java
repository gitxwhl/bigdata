package com.canteen.controller;


import com.canteen.entity.TokenModel;
import com.canteen.service.TokenModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TokenModel.do")
@Slf4j
@CrossOrigin
public class TokenModelController {

    @Autowired
    private TokenModelService tokenModelService;

    @RequestMapping(value = "/insertFood",method = RequestMethod.POST)
    public Object insertFood(@RequestBody TokenModel tokenModel){
        return tokenModelService.openWater(tokenModel);
    }
}
