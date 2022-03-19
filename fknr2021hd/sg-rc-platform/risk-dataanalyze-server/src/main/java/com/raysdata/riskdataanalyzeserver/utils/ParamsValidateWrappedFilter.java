package com.raysdata.riskdataanalyzeserver.utils;
import com.alibaba.fastjson.JSON;
import com.nariit.pi6000.framework.data.JdbcDataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 参数转换过滤器
 */
@Order(2)
@WebFilter(urlPatterns = "/*")
@Component
public class ParamsValidateWrappedFilter implements Filter {
    private static Logger log = LoggerFactory.getLogger(ParamsValidateWrappedFilter.class);

    @Autowired
    private JdbcDataAccess jdbcDataAccess;

	@Value("${signValidateFlag:true}")
	private boolean signValidateFlag;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	String requestURI = "";
        try {
        	HttpServletRequest req = (HttpServletRequest) request;
        	requestURI = req.getRequestURI();
        	//首先判断是否开启签名验证
        	String re = getSignOpen();
        	if("false".equals(re)) {
        		chain.doFilter(request, response);
        	}else if(requestURI.indexOf("/mockRedicts") != -1 // 上传、登录方法不走该处
        			|| requestURI.indexOf("/getUserId") != -1
//        			|| requestURI.indexOf("/export") != -1
					|| requestURI.indexOf("/template") != -1
					|| requestURI.indexOf("/downloadTemplate") != -1
					// 附件的上传和下载走单独的校验 , 旧的是全部不拦截|| requestURI.indexOf("/annexfj)!= -1
					|| requestURI.indexOf("/annexfj/upload") != -1
					|| requestURI.indexOf("/annexfj/download") != -1
					|| requestURI.indexOf("/annexfj/ryszdownload") != -1
					|| requestURI.indexOf("/annexfj/attachment") != -1
        			// app接口不走这里
        			|| requestURI.indexOf("/rmcp-service-interface-out") != -1
        			// 导入数据
					|| requestURI.indexOf("/upload") != -1
					// 安监5期登陆退出时删除旧用户信息
					|| requestURI.indexOf("/weblogin/deleteCache") != -1
					|| requestURI.indexOf("/weblogin/ajfloginout") != -1
					// 微服务退出不走该方法
					|| requestURI.indexOf("/weblogin/loginout") != -1
					|| requestURI.indexOf("/logAudit/insertLog") != -1
					|| requestURI.indexOf("/fileLead/fileRelationLead") != -1
					|| requestURI.indexOf("/fileLead/fileExample") != -1
        			) {
                chain.doFilter(request, response);
        	}else {
        		ParamsValidateWrappedServletRequest requestWrapper = new ParamsValidateWrappedServletRequest((HttpServletRequest) request);
        		// 入参为空校验结果返回
//    			if(!requestWrapper.emptyParamsVerifyResult) {
//    				 response.setContentType("application/json;charset=UTF-8");
//    				 Map<String, String> result = new HashMap<>();
//    				 result.put("status", "401");
//    				 result.put("type", "03");
//    				 result.put("message", "入参不能为空！");
//                     response.getWriter().write(JSON.toJSONString(result));
//                     return;
//    			}
                // MD5完整性校验结果返回
    			if(!requestWrapper.md5VerifyResult && signValidateFlag) {
    				 response.setContentType("application/json;charset=UTF-8");
    				 Map<String, String> result = new HashMap<>();
    				 result.put("status", "401");
    				 result.put("type", "03");
    				 result.put("message", "数据丢失或被篡改！");
                     response.getWriter().write(JSON.toJSONString(result));
                     //response.getWriter().write(JsonUtils.toJson(WrappedResult.failedWrappedResult("数据丢失或被篡改！")));
                     return;
    			}
    			 // 参数格式校验结果返回
    			if(!requestWrapper.paramsFormatVerifyResult) {
    				 response.setContentType("application/json;charset=UTF-8");
                     //response.getWriter().write(JsonUtils.toJson(WrappedResult.failedWrappedResult("参数格式错误！")));
    				 Map<String, String> result = new HashMap<>();
    				 result.put("status", "401");
    				 result.put("type", "03");
    				 result.put("message", "参数格式错误！");
                     response.getWriter().write(JSON.toJSONString(result));
                     return;
    			}
    			// 非法内容校验结果返回
    			if(!requestWrapper.illegalCharVerifyResult) {
    			 response.setContentType("application/json;charset=UTF-8");
    			 Map<String, String> result = new HashMap<>();
				 result.put("status", "401");
				 result.put("type", "03");
				 result.put("message", "输入内容包含非法字符,请检查！");
                 response.getWriter().write(JSON.toJSONString(result));
                 //response.getWriter().write(JsonUtils.toJson(WrappedResult.failedWrappedResult("输入内容包含非法字符,请检查！")));
                    return;
        		}
    			// 这里doFilter传入我们实现的子类
                chain.doFilter(requestWrapper, response);
        	}
        } catch (Exception e) {
        	if(requestURI.indexOf("/weblogin/loginout") != -1){
        		//response.getWriter().write(JsonUtils.toJson(WrappedResult.successWrapedResult(aj5QuitUrl)));
        	}else {
        		 Map<String, String> result = new HashMap<>();
				 result.put("status", "401");
				 result.put("type", "03");
				 result.put("message", "系统异常，请稍后重试");
                response.getWriter().write(JSON.toJSONString(result));
        		//response.getWriter().write(JsonUtils.toJson(WrappedResult.failedWrappedResult("系统异常，请稍后重试")));
        	}
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public void destroy() {

    }

    private String getSignOpen() {
    	String result = "true";
    	String sql = "SELECT CONFIG_VALUE FROM srp_config_table WHERE CONFIG_NAME = 'signOpen'";
    	List<Map> list = jdbcDataAccess.query(sql);
    	if(list.size() > 0) {
			result = list.get(0).get("config_value").toString();
		}
		return result;
    }

}