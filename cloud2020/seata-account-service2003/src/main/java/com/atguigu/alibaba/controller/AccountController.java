package com.atguigu.alibaba.controller;

import com.atguigu.alibaba.domain.CommonResult;
import com.atguigu.alibaba.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {
    @Autowired
   private AccountService accountService;

    @PostMapping("/account/decrease")
    public CommonResult decrease(Long userId, BigDecimal money){
        accountService.decrease(userId,money);
        return new CommonResult(200,"扣减成功");
            }
}
