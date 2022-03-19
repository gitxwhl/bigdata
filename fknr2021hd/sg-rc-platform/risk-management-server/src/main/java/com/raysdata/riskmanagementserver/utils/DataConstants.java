/*
 * 文 件 名：DataConstants.java
 * 系统名称：电网统一视频监控平台
 * Copyright@2003-2019 State Grid Corporation of China, All Rights Reserved
 * 版本信息：V1.0
 * 版   权：NARI
 */

package com.raysdata.riskmanagementserver.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 概述：
 * 功能：
 * 作者：Hans
 * 创建时间：2019-05-30 09:55
 */
public class DataConstants {

    public static final String SM4_KEY = "JeF8U9wHFOMfs2Y8";

    public static final int NUM_ZERO = 0;
    public static final int NUM_ONE = 1;
    public static final int NUM_TWO = 2;
    public static final int NUM_THREE = 3;
    public static final int NUM_FOUR = 4;
    public static final int NUM_FIVE = 5;
    public static final int NUM_SIX = 6;
    public static final int NUM_SEVEN = 7;
    public static final int NUM_EIGHT = 8;
    public static final int NUM_NINE = 9;
    public static final int NUM_TEN = 10;
    public static final int NUM_MINUS_ONE = -1;
    public static final int THRITY_TWO = 32;

    public static final int START_PAGE_SIZE = 10000;

    public static final int START_PAGE_NO = 0;

    public static final String STRING_ZERO = "0";
    public static final String STRING_ONE = "1";
    public static final String STRING_TWO = "2";
    public static final String STRING_THREE = "3";
    /**
     * 本平台
     */
    public static final String SELF_PLAT = "0";

    /**
     * 功能分组类型
     */
    public static final int GROUP_TYPE = 1;

    /**
     * Map key
     */
    public static final String K = "key";

    /**
     * Map value
     */
    public static final String V = "value";

    /**
     * 地域类型
     */
    public static final String AREA_TYPE = "AREA";

    /**
     * 地域类型编码
     */
    public static final String AREA_TYPE_CODE = "000000";

    /**
     * 具体场景类型
     */
    public static final String SCENE_TYPE = "SCENE";

    /**
     * 场景类型
     */
    public static final String SCENE_TYPE_TYPE = "SCENE_TYPE";

    /**
     * 场景类型编码
     */
    public static final String SCENE_TYPE_CODE = "0000";

    /**
     * 事件类型
     */
    public static final Map<String,String> ACTION_TYPE = new HashMap<String, String>(){{
        put("0","业务级");
        put("1","系统级");
    }};

    /**
     * 事件级别
     */
    public static final Map<String,String> ACTION_LEVEL = new HashMap<String, String>(){{
        put("0","成功");
        put("1","严重");
        put("2","告警");
        put("3","一般");
    }};

    public static final Map<String,String> LOG_TYPE = new HashMap<String, String>(){{
        put("0","运行");
        put("1","错误");
        put("2","告警");
        put("3","调试");
    }};

    public static final Map<String,String> OP_RESULT = new HashMap<String, String>(){{
        put("200","成功");
        put("500","失败");
    }};

    /**
     * 添加操作
     */
    public static final int CREATE_OPERATION = 1;

    /**
     * 删除操作
     */
    public static final int DELETE_OPERATION = 2;

    /**
     * 修改操作
     */
    public static final int UPDATE_OPERATION = 3;

    /**
     * 视频采集设备
     */
    public static final String[] VIDEO_DEVICES = {"01", "02", "03", "04", "05", "06", "07", "08", "53"};

    /**
     * 类型 1:前端；2;平台；7；服务；4；设备
     */
    public static final Integer RESOURCE_TYPE = 4;

    /**
     * 数据库中存在而pi6000中没有的用户
     */
    public static final String UNKONWN_USER = "未知用户";



    /**
     * 业务系统编码key
     */
    public static final String BUSINESS_APP_CODE = "businessAppCode";


    /**
     * 日志导出方式，1：根据ID导出
     */
    public static final Integer EXPORT_BY_ID = 1;

    /**
     * 日志导出方式，2：根据行数范围导出
     */
    public static final Integer EXPORT_BY_RANGE = 2;
}
