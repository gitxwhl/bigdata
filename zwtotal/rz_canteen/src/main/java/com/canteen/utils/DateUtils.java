package com.canteen.utils;


import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @program:
 * @description: 日期工具类
 * @author: duquan
 * @create: 2019-12-31 16:00
 **/

public class DateUtils  {

    /**
     * 根据开始日期和结束日期, 计算日期差
     *
     * @param smdate 开始日期
     * @param bdate   结束日期
     * @return 两个日期之间的天数
     */
    public static int getDays(Date smdate,Date bdate) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate=sdf.parse(sdf.format(smdate));
            bdate=sdf.parse(sdf.format(bdate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=Math.abs((time2-time1)/(1000*3600*24));
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 将日期格式化为指定格式字符串
     *
     * @param date    日期对象
     * @param pattern 字符串格式
     * @return 日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static String now() {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return format(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return format(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return format(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return format(new Date(), "E");
    }

    /**
     * 获取当前日期的是哪个季度
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     */
    public static int getSeason(Date date) {
        int season = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;

        }
        return season;

    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date1 = format.parse("2014-10-04 10:00:00");
        Date date2 = new Date();
        int days = getDays(date1, date2);
        System.out.println(days);
    }
}
