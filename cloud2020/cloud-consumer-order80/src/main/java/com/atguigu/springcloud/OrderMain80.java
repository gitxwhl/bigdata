package com.atguigu.springcloud;
import com.atguigu.springcloud.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @auther
 * @create 2020-02-18 17:20
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="cloud-payment-service",configuration= MySelfRule.class)
public class OrderMain80
{
    public static void main(String[] args) {
            SpringApplication.run(OrderMain80.class, args);
    }

//    @Bean
////    @LoadBalanced
//    public RestTemplate getRestTemplate()
//    {
//        return new RestTemplate();
//    }



}
