    //package com.raysdata.riskmanagementserver.jasypt;
    //
    //
    //import com.nariit.pi6000.framework.crypto.SM4Utils;
    //import org.jasypt.encryption.StringEncryptor;
    //import org.slf4j.Logger;
    //import org.slf4j.LoggerFactory;
    //
    ///**
    // * @ClassName SM4Encryptor
    // * @Description TODO
    // * @Author xm
    // * @Date 2020/6/3 13:44
    // * @Version 1.0
    // */
    //public class SM4Encryptor implements StringEncryptor {
    //
    //    private static final Logger LOG = LoggerFactory.getLogger(SM4Encryptor.class);
    //
    //    /**
    //     * 加密流程
    //     * 1.SM4加密
    //     * 2.Base64编码
    //     * @param message
    //     * @return
    //     */
    //    @Override
    //    public String encrypt(String message) {
    //        try {
    //            //SM4加密------->base64编码
    //            String encryptMsg = SM4Utils.encryptData_CBC(message);
    //            return  encryptMsg;
    //        } catch (Exception e) {
    ////            e.printStackTrace();
    //            return message;
    //        }
    //    }
    //
    //    /**
    //     * 解密流程
    //     * 1.Base64反编码
    //     * 2.SM4解密
    //     * @param encryptedMessage
    //     * @return
    //     */
    //    @Override
    //    public String decrypt(String encryptedMessage) {
    //        try {
    //            //1.base64反编码
    //            String decryptMsg = SM4Utils.decryptData_CBC(encryptedMessage);
    //            return decryptMsg;
    //        } catch (Exception e) {
    ////            e.printStackTrace();
    //            return encryptedMessage;
    //        }
    //    }
    //
    //    public static void main(String args[]) {
    //        SM4Encryptor sm4Encryptor = new SM4Encryptor();
    ////        String str = "Nariit@123";
    ////        String str = "Nariit@123";
    //        String str = "zpproot";
    //        String ss = sm4Encryptor.encrypt(str);
    //        System.out.println(sm4Encryptor.encrypt(str));
    //
    //    }
    //
    //
    //}