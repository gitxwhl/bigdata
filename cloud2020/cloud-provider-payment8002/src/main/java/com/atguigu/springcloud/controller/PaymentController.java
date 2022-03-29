package com.atguigu.springcloud.controller;
import com.atguigu.springcloud.entitys.CommonResult;
import com.atguigu.springcloud.entitys.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
/**
 * @auther zzyy
 * @create 2020-02-18 10:43
 */
@RestController
@Slf4j
public class PaymentController
{
    @Resource
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPost;


    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment)
    {
        //根据返回唯一主键：即：返回的是插入数据的主键
        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);
        if(result > 0)
        {
            return new CommonResult(200,"插入数据库成功: ",result);
        }else{
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id)
    {
        Payment payment = paymentService.getPaymentById(id);
        System.out.println("adfsafafasdfasdfaa");
        if(payment != null)
        {
            return new CommonResult(200,"查询成功:  servePort" + serverPost,payment);
        }else{
            return new CommonResult(444,"没有对应记录,查询ID: "+id,null);
        }
    }


    /**
     * 端口访问
     * @return
     */
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPost;
    }
}
