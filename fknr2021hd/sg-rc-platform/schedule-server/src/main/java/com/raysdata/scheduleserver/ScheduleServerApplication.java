package com.raysdata.scheduleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@EnableEurekaClient
@SpringBootApplication
@EnableCaching
@ComponentScan(
        basePackages = {"com.raysdata.scheduleserver", "com.nariit.rmcp", "com.nariit.pi6000", "com.nariit.adf"}
)
public class ScheduleServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleServerApplication.class, args);
    }


    public ScheduleServerApplication() {
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(new Class[]{ScheduleServerApplication.class});
        return super.configure(builder);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}
