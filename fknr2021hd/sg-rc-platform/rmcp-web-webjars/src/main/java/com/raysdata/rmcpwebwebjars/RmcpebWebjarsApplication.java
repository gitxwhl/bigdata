package com.raysdata.rmcpwebwebjars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

//@EnableEurekaClient
@SpringBootApplication
@EnableCaching
@ComponentScan(
        basePackages = {"com.raysdata.rmcpwebwebjars", "com.nariit.rmcp", "com.nariit.pi6000", "com.nariit.adf"}
)
public class RmcpebWebjarsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RmcpebWebjarsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(new Class[]{RmcpebWebjarsApplication.class});
        return super.configure(builder);
    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }

}
