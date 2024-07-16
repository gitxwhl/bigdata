package com.atguigu.security.filter;

import com.alibaba.fastjson.JSON;
import com.atguigu.common.result.ResponseUtil;
import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 认证解析token过滤器
 * </p>
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;


    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public TokenAuthenticationFilter() {

    }
//执行各接口的访问首先进入过滤器
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:"+request.getRequestURI());
        //如果是登录接口，直接放行
        if("/admin/system/index/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        //如果不是登录接口，判断请求头是否有token
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(null != authentication) {
            //如果不等于空，放到安全上下文对象中
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_ERROR));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // token置于header里  请求头是否有token
        String token = request.getHeader("token");
        logger.info("token:"+token);
        if (!StringUtils.isEmpty(token)) {
            String useruame = JwtHelper.getUsername(token);
            logger.info("useruame:"+useruame);
            if (!StringUtils.isEmpty(useruame)) {
                //通过username从redis获取权限的数据
                String authString = (String) redisTemplate.opsForValue().get(useruame);
                //把redis获取的字符串权限数据转换成要求集合数据 List<SimpleGrantedAuthority>

                if(!StringUtils.isEmpty(authString)){
                    List<Map> mapList = JSON.parseArray(authString, Map.class);
                    List<SimpleGrantedAuthority> authList = new ArrayList<>();
                    for (Map map:mapList){
                        String authority = (String) map.get("authority");
                        authList.add(new SimpleGrantedAuthority(authority));
                    }
                    return new UsernamePasswordAuthenticationToken(useruame, null,authList);
                }else{
                    return new UsernamePasswordAuthenticationToken(useruame, null,new ArrayList<>());
                }

            }
        }
        return null;
    }
}
