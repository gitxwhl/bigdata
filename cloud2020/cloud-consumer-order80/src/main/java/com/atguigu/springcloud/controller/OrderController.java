package com.atguigu.springcloud.controller;
import com.atguigu.springcloud.entitys.CommonResult;
import com.atguigu.springcloud.entitys.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;

/**
 * @auther zzyy  消费接口调支付接口  服务之间的调用
 * @create 2020-02-18 17:23
 */
@RestController
@Slf4j
public class OrderController
{
    public static final String PAYMENT_URL = "http://cloud-payment-service";

    @Resource
    private RestTemplate restTemplate;

    /**
     * 添加的接口
     * @param
     * @return
     */
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment)
    {
        return restTemplate.postForObject(PAYMENT_URL +"/payment/create",payment, CommonResult.class);
    }
    /**
     * 查询接口
     * object 返回数据为对象即json
     * @param
     * @return
     */
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id)
    {
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    /**
     *返回数据为resposeEntity对象包含响应头，响应码，状态体
     */
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id)
    {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);

        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444,"操作失败");
        }
    }


    // ====================> zipkin+sleuth
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin()
    {
        String result = restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
        return result;
    }
}
