package com.raysdata.riskdataanalyzeserver.utils;

import com.nariit.rmcp.common.util.JsonUtils;
import com.nariit.rmcp.common.util.ParamsUtil;
import com.nariit.rmcp.common.vo.WrappedResult;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.MessageDigest;
//@Component
public class SignatureFilter implements Filter {

	@Value("${signValidateFlag:true}")
	private boolean signValidateFlag;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		if(signValidateFlag && !signValidate(req)) {
            String message = "签名验证失败";
            printErrorMsg(response, message);
            return;
		}
		chain.doFilter(request, response);
	}
	
    private boolean signValidate(HttpServletRequest req) {
    	
    	String userId = ParamsUtil.getUserId(req);
        String sign = req.getParameter("_sign_value");
        String uri = req.getRequestURI().toString();
        int index = uri.indexOf("/", 1);
        String queryStr = ObjectUtils.toString(req.getQueryString());
        String url = uri.substring(index);
        if (StringUtils.isNotEmpty(queryStr)) {
            url = url + "?" + queryStr;
        }
        int toIndex = 0;
        toIndex=url.indexOf("?_sign_value");
        if(toIndex <= 0) {
        toIndex=url.indexOf("&_sign_value");
        }
        if (toIndex > 0) {
        	// 把 ' 转换成了%27导致鉴权过不去
            String urlTemp = url.substring(0, toIndex).replace("%27", "'");
            //String userId = getUserId(req);
           
            String urlTemp_new = urlTemp + "&userId=" + userId;
            //String urlTemp_new=urlTemp+userId;
            // MD5加密处理
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                byte[] bytes = md5.digest(urlTemp_new.getBytes("utf-8"));
                String urlTempMd5 = byteToHexString(bytes);
                boolean flag =  urlTempMd5.equalsIgnoreCase(sign);
                if(!flag){
                	urlTemp_new = urlTemp + "&userId=";
                	bytes = md5.digest(urlTemp_new.getBytes("utf-8"));
                	urlTempMd5 = byteToHexString(bytes);
                	flag =  urlTempMd5.equalsIgnoreCase(sign);
                }
                return flag;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
    
    private static String byteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }
    
	private void printErrorMsg(ServletResponse response, String message) throws IOException, ServletException {
        response.reset();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtils.toJson(WrappedResult.failedWrappedResult(message)));
    }
	

}
