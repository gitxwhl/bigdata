package com.raysdata.riskdataanalyzeserver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
/**
 * @author
 */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SuppressWarnings("AliMissingOverrideAnnotation")
//@EnableEurekaClient
@MapperScan(value = "com.raysdata.riskdataanalyzeserver.mapper")
//@SpringBootApplication
@EnableCaching
@ComponentScan(
        basePackages = {"com.raysdata.riskdataanalyzeserver", "com.nariit.rmcp", "com.nariit.pi6000", "com.nariit.adf"}
)
public class RiskDataAnalyzeServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RiskDataAnalyzeServerApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(new Class[]{RiskDataAnalyzeServerApplication.class});
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
