package com.property.controller;

import com.property.entity.LysbGzGuzbxsq;
import com.property.service.LysbGzGuzbxsqService;
import com.property.utils.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/LysbGzGuzbxsq.do")
@Slf4j
@CrossOrigin
public class LysbGzGuzbxsqController {

    @Autowired
    private LysbGzGuzbxsqService lysbGzGuzbxsqService;

    //新添加维修申请单（状态默认未提交）
    @PostMapping(value = "/addSq")
    public Object addSq(@RequestBody LysbGzGuzbxsq lysbGzGuzbxsq){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setMessage(lysbGzGuzbxsqService.addSq(lysbGzGuzbxsq).toString());
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("添加失败");
        }
        return resultMessage;
    }

    //更改状态
    @PostMapping(value = "/updateState")
    public Object updateState(@RequestBody LysbGzGuzbxsq lysbGzGuzbxsq){
        ResultMessage resultMessage = new ResultMessage();
        try {
            resultMessage.setMessage(lysbGzGuzbxsqService.updateState(lysbGzGuzbxsq).toString());
        }catch (Exception e){
            e.printStackTrace();
            resultMessage.setMessage("操作失败");
        }
        return resultMessage;
    }
}
