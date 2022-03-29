package com.atguigu.springcloud.config;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/**
 * @auther zzyy
 * @create 2020-02-19 15:20
 */
@Configuration
public class ApplicationContextConfig
{
    /**
     * restTemplate模板调用   负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}
