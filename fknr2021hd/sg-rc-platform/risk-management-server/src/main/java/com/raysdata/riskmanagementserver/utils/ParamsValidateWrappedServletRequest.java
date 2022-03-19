package com.raysdata.riskmanagementserver.utils;
import com.nariit.rmcp.common.util.ParamsUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.security.MessageDigest;
import java.util.Locale;

/**
 * 重写HttpServletRequestWrapper方法 对get巧合post请求做参数转换
 *
 * @author zhurendong
 *
 */
public class ParamsValidateWrappedServletRequest extends HttpServletRequestWrapper {
    private static Logger log = LoggerFactory.getLogger(ParamsValidateWrappedServletRequest.class);

    private byte[] bytes;
    // post请求参数
    private WrappedServletInputStream wrappedServletInputStream;
    // md5完整性校验结果
    public boolean md5VerifyResult = true;
    // 非法字符校验结果
    public boolean illegalCharVerifyResult = true;
    // 空参数校验结果
    public boolean emptyParamsVerifyResult = true;
    // 空参格式验结果
    public boolean paramsFormatVerifyResult = true;

    public ParamsValidateWrappedServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        HttpServletRequest req = (HttpServletRequest) request;
        String requestURI = req.getRequestURI();
         String flag= getMethod().toUpperCase(Locale.ENGLISH);
        if ("POST".equals(getMethod().toUpperCase(Locale.ENGLISH))) {
            // 转换参数
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();

            char[] buf = new char['?'];
            int rd;
            while ((rd = reader.read(buf)) != -1) {
                sb.append(buf, 0, rd);
            }
            reader.close();
            String params = sb.toString();
            if (StringUtils.isNotEmpty(params)) {
                //LoginUser loginUser = LoginUtils.getLoginUserFromIsc(request);
//                String userId = ParamsUtil.getUserId(req);
                String requestURITemp = requestURI.substring(requestURI.indexOf("/api/"), requestURI.length());
                String newparm = requestURITemp + UriUtils.encode(params, "utf-8") + "userId" + "c32ad1415f6c89fee76d8457c31efb4b";
                String sign = request.getParameter("sign");
                String md5Results = DigestUtils.md5Hex(newparm);
                if (StringUtils.isEmpty(sign) || !sign.equals(md5Results)) {
                    md5VerifyResult = false;
                    return;
                }

                // 读取输入流里的请求参数，并保存到bytes里
                bytes = params.getBytes();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                this.wrappedServletInputStream = new WrappedServletInputStream(byteArrayInputStream);
                // 很重要，把post参数重新写入请求流
                reWriteInputStream();
            }else {
                emptyParamsVerifyResult = false;
            }
        } else {
            if (requestURI.indexOf("/export") != -1) { // 导出前台为 href 走单独的校验处理
                String sign = request.getParameter("mdSign");
                if (StringUtils.isEmpty(sign)) {
                    emptyParamsVerifyResult = false;
                    return;
                }
                String userId = ParamsUtil.getUserId(req);
                String md5Results = DigestUtils.md5Hex("&userId=" + userId);
                if (StringUtils.isEmpty(sign) || !sign.equals(md5Results)) {
                    md5VerifyResult = false;
                }
            } else {
                // 获取原始参数
                String sign = request.getParameter("sign");
//				log.info("requestURI=" + requestURI + " sign=" + sign);
                if (StringUtils.isEmpty(sign)) {
                    emptyParamsVerifyResult = false;
                    return;
                }
//				log.info("requestURI=" + requestURI + " getQueryString=" + request.getQueryString());
                String queryStr = ObjectUtils.toString(request.getQueryString());
//				log.info("requestURI=" + requestURI + " queryStr=" + queryStr);

                String sortParams = SignUtil.getSortParams(request, null);

                String userId = ParamsUtil.getUserId(req);

                String requestURITemp = requestURI.substring(requestURI.indexOf("/api/"), requestURI.length());
                if (requestURI.indexOf("?") != -1) {
                    requestURITemp = requestURITemp.substring(0, requestURI.indexOf("?"));
                }
                //String urlTemp_new = requestURITemp + UriUtils.encode(sortParams, "utf-8") + "userId" + userId;
                String urlTemp_new = requestURITemp + sortParams+ "userId" + "c32ad1415f6c89fee76d8457c31efb4b";
//				log.info("requestURI=" + requestURI + " urlTemp_new=" + urlTemp_new);
                try {
                    MessageDigest md5 = MessageDigest.getInstance("MD5");
                    byte[] bytes = md5.digest(urlTemp_new.getBytes("utf-8"));
                    String md5Results = byteToHexString(bytes);
                    //String md5Results = DigestUtils.md5Hex(urlTemp_new);
//					log.info("requestURI=" + requestURI + " md5Results=" + md5Results);
                    if (StringUtils.isEmpty(sign) || !sign.equalsIgnoreCase(md5Results)) {
                        urlTemp_new = requestURITemp + sortParams + "userId" + "c32ad1415f6c89fee76d8457c31efb4b";
                        md5Results = DigestUtils.md5Hex(urlTemp_new);
                        if (StringUtils.isEmpty(sign) || !sign.equals(md5Results)) {
                            md5VerifyResult = false;
                        }
                    }
                }catch(Exception e) {

                }

//				int toIndex = queryStr.indexOf("&sign");
//		        if (toIndex > 0) {
//		            String urlTemp = queryStr.substring(0, toIndex);
//		            log.info("requestURI=" + requestURI + " urlTemp=" + urlTemp);
//		            LoginUser loginUser = LoginUtils.getLoginUserFromIsc(request);
//		            String userId = "";
//		            if(loginUser != null) {
//		            	userId = loginUser.getUserId();
//		            }
//		            log.info("requestURI=" + requestURI + " userId=" + userId);
//		            String urlTemp_new = UriUtils.encode(urlTemp, "utf-8") + "&userId=" + userId;
//		            log.info("requestURI=" + requestURI + " urlTemp_new=" + urlTemp_new);
//		            String md5Results = DigestUtils.md5Hex(urlTemp_new);
//		            log.info("requestURI=" + requestURI + " md5Results=" + md5Results);
//					if (StringUtils.isEmpty(sign) || !sign.equals(md5Results)) {
//						md5VerifyResult = false;
//					}
//		        }
            }
        }
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

    /**
     * 把参数重新写进请求里
     */
    public void reWriteInputStream() {
        wrappedServletInputStream.setStream(new ByteArrayInputStream(bytes != null ? bytes : new byte[0]));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return wrappedServletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(wrappedServletInputStream));
    }

    /**
     * 获取post参数，可以自己再转为相应格式
     */
    public String getRequestParams() throws IOException {
        return new String(bytes, this.getCharacterEncoding());
    }

    private class WrappedServletInputStream extends ServletInputStream {

        public void setStream(InputStream stream) {
            this.stream = stream;
        }

        private InputStream stream;

        public WrappedServletInputStream(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}