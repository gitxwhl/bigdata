package com.atguigu.security.config;

import com.atguigu.security.custom.CustomMd5PasswordEncoder;
import com.atguigu.security.filter.TokenAuthenticationFilter;
import com.atguigu.security.filter.TokenLoginFiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //@EnableWebSecurity是开启SpringSecurity的默认行为
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启基于方法的安全机制，可以在方法上加注解@PreAuthorize，也就是说在web层controller 启用注解机制安全确认
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //根据用户名称获取用户信息
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomMd5PasswordEncoder customMd5PasswordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;


    //得到AuthenticationManager对象
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
  //配置哪些接口开启防护，哪些绕过防护
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
        http
                //关闭csrf跨站请求伪造
                .csrf().disable()
                // 开启跨域以便前端调用接口
                .cors().and()
                .authorizeRequests()
                // 指定某些接口不需要通过验证即可访问。登陆接口肯定是不需要认证的
                .antMatchers("/admin/system/index/login").permitAll()
                // 这里意思是其它所有接口需要认证才能访问
                .anyRequest().authenticated()
                .and()
                //TokenAuthenticationFilter放到UsernamePasswordAuthenticationFilter的前面，这样做就是为了除了登录的时候去查询数据库外，其他时候都用token进行认证。
                .addFilterBefore(new TokenAuthenticationFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new TokenLoginFiler(authenticationManager(),redisTemplate));

        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 指定UserDetailService和加密器
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(customMd5PasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     * 排除swagger相关请求
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html");
    }
}