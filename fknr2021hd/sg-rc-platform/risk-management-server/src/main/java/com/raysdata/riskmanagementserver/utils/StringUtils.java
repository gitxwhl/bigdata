/*
 * 文 件 名：StringUtils.java
 * 系统名称：风险管控平台
 * Copyright@2003-2019 State Grid Corporation of China, All Rights Reserved
 * 版本信息：V1.0
 * 版   权：NARI
 */

package com.raysdata.riskmanagementserver.utils;

import com.nariit.pi6000.framework.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 概述：
 * 功能：
 * 作者：Hans
 * 创建时间：2019-05-30 15:25
 */
public class StringUtils {


    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 对象的空判断
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            if (((String)obj).length() == 0) {
                return true;
            }
        } else if (obj instanceof Map) {
            if (((Map<?, ?>)obj).isEmpty()) {
                return true;
            }
        } else if (obj instanceof Collection) {
            if (((Collection<?>)obj).isEmpty()) {
                return true;
            }
        } else if (obj.getClass().isArray()) {
            if (Arrays.asList(obj).size() == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * 去除菜单扩展属性中 厂商[]前后的双引号, 再反转义
     *
     * @param ext
     * @return
     */
    public static String menuExtFormat(String ext) {
        return  StringEscapeUtils.unescapeJava(
                ext.replaceAll("\"\\[", "\\[").replaceAll("\\]\"", "\\]"));
    }

    /**
     * 菜路径中 找到\之前的字符
     *
     * @return
     */
    public static String pathCut(String path) {
        String[] array = StringUtil.split(path, "/");
        return array[0];
    }
    
    
    /**
     * 业务组织层级判断
     *
     * @return
     */
    public static int pathLevel(String path) {
        String[] array = StringUtil.split(path, "/");
        return array.length;
    }
    
	/**
	 * 判断不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }
	
    /**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}