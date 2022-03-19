package com.raysdata.riskmanagementserver.utils;

import com.raysdata.riskmanagementserver.bean.vo.SrpWorkSiteworkerinfoVO;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//import com.nariit.pi6000.framework.util.JWTUtil;

/**
 * * Copyright ©2003- 2020   State Grid Corporation of China,All Rights Reserved.
 * Copyright: NARI
 *
 * @Project: rmcp_mirco
 * @Version：V1.0
 * @Package: com.nariit.rmcp.product.utils
 * @Author: gengshuai
 * @Create: 2020/5/28 11:06
 * @Description: this file is used for 从pi6000部分移植，与风控组编码规范不一致
 * @History: modify
 * *
 **/
public class UserUtil {

    private static final Log logger = LogFactory.getLog(UserUtil.class);

    /**
     * 根据请求信息获取用户id
     *
     * @param request 请求
     * @return 用户ID
     */
    public static String getUserId(HttpServletRequest request) {
        String token = request.getHeader("_at");
        if (StringUtils.isEmpty(token)) {
            Cookie cookie = getCookie(request, "_at");
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        if (StringUtils.isEmpty(token)) {
            throw new RmcpException(ErrorCodeEnums.USER_NOT_LOGIN_EXCEPTION);
        }
//        Claims claims = JWTUtil.parseJWT(token);
        Claims claims = JWTUtil.parseJwt(token);

        String userId = claims.getId();
        return userId;
    }


    public static String getUserName(HttpServletRequest request) {
        String token = request.getHeader("_at");
        if (StringUtils.isEmpty(token)) {
            Cookie cookie = getCookie(request, "_at");
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        if (StringUtils.isEmpty(token)) {
            throw new RmcpException(ErrorCodeEnums.USER_NOT_LOGIN_EXCEPTION);
        }

//        Claims claims = JWTUtil.parseJWT(token);
        Claims claims = JWTUtil.parseJwt(token);

        String userName= claims.getSubject();
        return userName;
    }
    
    /**
     * @Method: 统一返回风控用户表的siteworkerinfoId
     * @Description: 
     * @Params  * @param null : 
     * @Return null
     * @Author: chenchao
     * @DateTime: 2021/6/8 18:19
     * @History :
     */
    public static String getRmcpUserId(String userId){
        //若isc账号登录时，查库统一返回userId
        RmcpAcUserMapper rmcpAcUserMapper = ApplicationBeanUtil.getBean(RmcpAcUserMapper.class);
        List<SrpWorkSiteworkerinfoVO> userInfoList = rmcpAcUserMapper.getUserInfoById(userId);
        if (CollectionUtils.isNotEmpty(userInfoList)&&userInfoList.size()>1){
            throw new RmcpException(ErrorCodeEnums.USER_REPEAT_EXCEPTION);
        }else if (CollectionUtils.isNotEmpty(userInfoList)){
            //统一使用siteworkerinfoId
            userId = userInfoList.get(0).getSiteworkerinfoId();
        }
        return userId;
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request, "Request must not be null");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
