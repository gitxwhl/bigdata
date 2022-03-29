package com.atguigu.alibaba.controller;

import com.atguigu.alibaba.domain.CommonResult;
import com.atguigu.alibaba.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StorageController {
    @Autowired
   private StorageService storageService;

    @PostMapping("/storage/decrease")
    public CommonResult decrease(@RequestParam("productId")Integer productId,@RequestParam("count")Integer count){
        storageService.decrease(productId,count);
        return new CommonResult(200,"库存扣减成功");
    }

}
