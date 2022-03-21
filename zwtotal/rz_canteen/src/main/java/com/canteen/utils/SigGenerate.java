package com.canteen.utils;

import com.canteen.entity.TokenModel;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.HttpClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SigGenerate {
    public static void main(String[] args) {
        String param = "v=3&d=2020-08-25 16:16:23&format=json&shop=101&term=102&oper=8295&id=8";
        System.out.println(param);
        String[] split = param.split("&");
        System.out.println(Arrays.toString(split));
        String key = "8779f024eafd4a6dbb4fbc76202780a2";
        String sig = SigGenerate.getSig(key, split);
        System.out.println(sig);
    }

    public static String getSig(String key,String[] strs){
        // 按 ASCII 序排序
        Arrays.sort(strs , String.CASE_INSENSITIVE_ORDER);
        // 拼接
        String str = "";
        for(int i=0; i < strs.length; i++) str += strs[i];
        // 追加 key
        str += key;
        String sig= getMD5(str);
        return sig;
    }

    public static String getMD5(String params) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(params.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个 byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;//
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的 md5 加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 带参数的post请求（参数类型为x-www-form-urlencoded）
     * @param url 参数
     * @param tokenModel token实体类
     * @return 返回token
     */
    public static String getToken(String url, TokenModel tokenModel,org.apache.commons.httpclient.NameValuePair[] data){
        // 创建Httpclient对象
        HttpClient httpClient = new HttpClient();
        int response;
        String resultString = "";
        try{
            // 创建Http Post请求
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //参数设置
            /*org.apache.commons.httpclient.NameValuePair[] data = {
                    new org.apache.commons.httpclient.NameValuePair("LoginAccount",tokenModel.getD()),
                    new org.apache.commons.httpclient.NameValuePair("LoginName",tokenModel.getDate()),
                    new org.apache.commons.httpclient.NameValuePair("DepartmentName",tokenModel.getEd())
            };*/
            postMethod.setRequestBody(data);
            response = httpClient.executeMethod(postMethod); // 执行POST方法
            resultString = postMethod.getResponseBodyAsString();
        } catch(Exception e){
            e.printStackTrace();
        }
        return resultString;
    }
}
