package com.mashibing.apipassenger.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     * JwtInterceptor  在spring的bean实例化之前初始化的，这个JwtInterceptor初始化的时候bean还没初始化
     * 需要再拦截器执行之前，把bean给初始化
     * 在拦截器初始化的时候bean就已经有了，拦截器里面bean就会一个一个给注入进去
     * @return
     */
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtInterceptor())
        //拦截的路径
        .addPathPatterns("/**")
        //不拦截的路径
        .excludePathPatterns("/noauthTest")
        .excludePathPatterns("/verification-code")
        .excludePathPatterns("/verification-code-check")
        .excludePathPatterns("/refresh-tooken")
    ;
    }
}
