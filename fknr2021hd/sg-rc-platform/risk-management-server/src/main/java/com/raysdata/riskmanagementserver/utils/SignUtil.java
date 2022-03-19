package com.raysdata.riskmanagementserver.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by  EalenXie on 2018/6/13 9:31
 */
public class SignUtil {

	 private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    /**
     * @param
     * @return 得到签名
     */
    public static String getSign(HttpServletRequest request, Map<String, String> postParams) {
    	String sortParams = getSortParams(request, postParams);
        return DigestUtils.md5Hex(sortParams).toUpperCase(Locale.ENGLISH);
    }
    
    /**
     * @param
     * @return 得到签名
     */
    @SuppressWarnings("rawtypes")
	public static String getSortParams(HttpServletRequest request, Map<String, String> postParams) {
        StringBuilder sb = new StringBuilder();
    	SortedMap<String, String> params = getAllParams(request, postParams);
        for (Map.Entry entry : params.entrySet()) {
            if (!entry.getKey().equals("sign")) { //拼装参数,排除sign
				if (!StringUtils.isEmpty(entry.getKey())) {
                    sb.append(entry.getKey()).append(entry.getValue());
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param params 所有的请求参数都会在这里进行排序加密
     * @return 验证签名结果
     */
    public static boolean verifySign(HttpServletRequest request, SortedMap<String, String> params) {
        if (params == null || StringUtils.isEmpty(params.get("sign"))) {
            return false;
        }
        String sign = getSign(request, params);
        logger.info("verify Sign : {}", sign);
        return !StringUtils.isEmpty(sign) && params.get("sign").equals(sign);
    }
    
    //将请求参数转换成Map
    public static Map<String, String> getUrlParams(HttpServletRequest request) {
        String param = "";
        param = request.getQueryString();
//        try {
//            param = URLDecoder.decode(request.getQueryString(), "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        Map<String, String> result = new HashMap<>();
        String[] params = param.split("&");
        for (String s : params) {
            Integer index = s.indexOf("=");
            if(index==-1) {
            	result.put(s,"");
            }else {
            	result.put(s.substring(0, index), s.substring(index + 1));
            }
            
        }
        return result;
    }

    //从请求中获取所有参数
    public static SortedMap<String, String> getAllParams(HttpServletRequest request, Map<String, String> postParams) {
        SortedMap<String, String> result = new TreeMap<>();
        Map<String, String> urlParams = getUrlParams(request);
        for (Map.Entry entry : urlParams.entrySet()) {
            result.put((String) entry.getKey(), (String) entry.getValue());
        }
        if (postParams != null) {
            for (Map.Entry entry : postParams.entrySet()) {
                result.put((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return result;
    }

}