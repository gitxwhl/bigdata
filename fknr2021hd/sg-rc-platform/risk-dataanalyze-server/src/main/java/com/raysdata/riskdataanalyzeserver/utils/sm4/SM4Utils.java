// ************************************************************************
// 文 件 名: SM4Utils.java
// 系统名称：电网统一视频监控平台
// Copyright@2003-2013 State Grid Corporation of China, All Rights Reserved
// 版本信息：V2.0
// 版 权: NARI
// 创建 人: tukai
// ************************************************************************
package com.raysdata.riskdataanalyzeserver.utils.sm4;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 概述：sm4、base64加密、解密
 * 功能：
 * 作者：tukai
 * 创建时间:2017-1-20 上午9:58:32
 */
public class SM4Utils {

    /**
     * 长度为128bit
     */
    private static String secretKey = "JeF8U9wHFOMfs2Y8";

    // private static String iv = "";

    private static boolean hexString = false;

    /**
     * <默认构造函数>
     */
    public SM4Utils() {
    }

    /**
     * <ECB模式加密>
     * 创建人：tukai
     * 创建时间:2016-12-5 下午8:28:08
     *
     * @param plainText
     * @return [参数说明]
     * @return String [返回类型说明]
     */
    public static String encryptData_ECB(String plainText) {
        if (null != plainText && !"".equals(plainText)) {
            try {
                SM4_Context ctx = new SM4_Context();
                ctx.isPadding = true;
                ctx.mode = SM4.SM4_ENCRYPT;
                byte[] keyBytes;
                if (hexString) {
                    keyBytes = Util.hexStringToBytes(secretKey);
                } else {
                    keyBytes = secretKey.getBytes();
                }
                SM4 sm4 = new SM4();
                sm4.sm4_setkey_enc(ctx, keyBytes);
                byte[] encrypted = sm4.sm4_crypt_ecb(ctx,
                        plainText.getBytes("utf-8"));
                String cipherText = new BASE64Encoder().encode(encrypted);
                if (cipherText != null && cipherText.trim().length() > 0) {
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(cipherText);
                    cipherText = m.replaceAll("");
                }
                return cipherText;
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * <EBC模式解密>
     * 创建人：tukai
     * 创建时间:2016-12-5 下午8:28:37
     *
     * @param cipherText
     * @return [参数说明]
     * @return String [返回类型说明]
     */
    public static String decryptData_ECB(String cipherText) {
        if (null != cipherText && !"".equals(cipherText)) {

            try {
                SM4_Context ctx = new SM4_Context();
                ctx.isPadding = true;
                ctx.mode = SM4.SM4_DECRYPT;

                byte[] keyBytes;
                if (hexString) {
                    keyBytes = Util.hexStringToBytes(secretKey);
                } else {
                    keyBytes = secretKey.getBytes();
                }

                SM4 sm4 = new SM4();
                sm4.sm4_setkey_dec(ctx, keyBytes);
                byte[] decrypted = sm4.sm4_crypt_ecb(ctx,
                        new BASE64Decoder().decodeBuffer(cipherText));
                return new String(decrypted, "utf-8");
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * <CBC模式加密>
     * 创建人：tukai
     * 创建时间:2016-12-5 下午8:28:56
     *
     * @param plainText
     * @return [参数说明]
     * @return String [返回类型说明]
     */
    public static String encryptData_CBC(String plainText, String iv) {
        if (null != plainText && !"".equals(plainText)) {

            try {
                SM4_Context ctx = new SM4_Context();
                ctx.isPadding = true;
                ctx.mode = SM4.SM4_ENCRYPT;

                byte[] keyBytes;
                byte[] ivBytes;
                if (hexString) {
                    keyBytes = Util.hexStringToBytes(secretKey);
                    ivBytes = Util.hexStringToBytes(iv);
                } else {
                    keyBytes = secretKey.getBytes();
                    ivBytes = iv.getBytes();
                }

                SM4 sm4 = new SM4();
                sm4.sm4_setkey_enc(ctx, keyBytes);
                byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes,
                        plainText.getBytes("utf-8"));
                String cipherText = new BASE64Encoder().encode(encrypted);
                if (cipherText != null && cipherText.trim().length() > 0) {
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(cipherText);
                    cipherText = m.replaceAll("");
                }
                return cipherText;
            } catch (Exception e) {
                return "";
            }

        } else {
            return "";
        }
    }

    /**
     * <CBC模式解密>
     * 创建人：tukai
     * 创建时间:2016-12-5 下午8:29:26
     *
     * @param cipherText
     * @return [参数说明]
     * @return String [返回类型说明]
     */
    public static String decryptData_CBC(String cipherText, String iv) {
        if (null != cipherText && !"".equals(cipherText)) {

            try {
                SM4_Context ctx = new SM4_Context();
                ctx.isPadding = true;
                ctx.mode = SM4.SM4_DECRYPT;

                byte[] keyBytes;
                byte[] ivBytes;
                if (hexString) {
                    keyBytes = Util.hexStringToBytes(secretKey);
                    ivBytes = Util.hexStringToBytes(iv);
                } else {
                    keyBytes = secretKey.getBytes();
                    ivBytes = iv.getBytes();
                }

                SM4 sm4 = new SM4();
                sm4.sm4_setkey_dec(ctx, keyBytes);
                byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes,
                        new BASE64Decoder().decodeBuffer(cipherText));
                return new String(decrypted, "utf-8");
            } catch (Exception e) {
                return "";
            }

        } else {
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(decryptData_ECB("Xx4EQrgLtdqsNlt/1TOrKg=="));
    }
}
