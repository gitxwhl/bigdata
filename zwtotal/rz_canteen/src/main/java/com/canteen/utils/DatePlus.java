package com.canteen.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DatePlus {
    /**
     * 根据传入的日期返回当前日期的周一到周末的时间
     * @param datetime
     * @return
     * @throws ParseException
     */
    public Map weekDate(String datetime) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(simpleDateFormat.parse(datetime));
        int week = 0;
       week = weekFormat(datetime);
       Map<String,String> map = new HashMap<>();
       switch (week){
           case 1 :
               map.put("weekStart",datetime);
               calendar.add(Calendar.DATE,6);
               map.put("weekEnd",simpleDateFormat.format(calendar.getTime()));
               break;
           case 2:
               calendar.add(Calendar.DATE,-1);
               map.put("weekStart",simpleDateFormat.format(calendar.getTime()));
               calendar.add(Calendar.DATE,6);
               map.put("weekEnd",simpleDateFormat.format(calendar.getTime()));
               break;
           case 3:
               calendar.add(Calendar.DATE,-2);
               map.put("weekStart",simpleDateFormat.format(calendar.getTime()));
               calendar.add(Calendar.DATE,6);
               map.put("weekEnd",simpleDateFormat.format(calendar.getTime()));
               break;
           case 4:
               calendar.add(Calendar.DATE,-3);
               map.put("weekStart",simpleDateFormat.format(calendar.getTime()));
               calendar.add(Calendar.DATE,6);
               map.put("weekEnd",simpleDateFormat.format(calendar.getTime()));
               break;
           case 5:
               calendar.add(Calendar.DATE,-4);
               map.put("weekStart",simpleDateFormat.format(calendar.getTime()));
               calendar.add(Calendar.DATE,6);
               map.put("weekEnd",simpleDateFormat.format(calendar.getTime()));
               break;
           case 6:
               calendar.add(Calendar.DATE,-5);
               map.put("weekStart",simpleDateFormat.format(calendar.getTime()));
               calendar.add(Calendar.DATE,6);
               map.put("weekEnd",simpleDateFormat.format(calendar.getTime()));
               break;
           case 7:
               calendar.add(Calendar.DATE,-6);
               map.put("weekStart",simpleDateFormat.format(calendar.getTime()));
               calendar.add(Calendar.DATE,6);
               map.put("weekEnd",simpleDateFormat.format(calendar.getTime()));
               break;
       }
       return map;
    }

    public int weekFormat(String datetime) throws ParseException {
    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    Date date = formater.parse(datetime);
    Calendar c = Calendar.getInstance();
        c.setTime(date);
    int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
        dayForWeek = 7;
    }else{
        dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
    }
        return dayForWeek;
            }

    /**
     * 根据传入的时间返回当前月份开始 日期和结束日期
     *
     * @param datetime
     * @return
     */
    public Map monthFormat(String datetime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date date1 = format.parse(datetime);
        String date2 = format.format(date1);
        String date = format.format(now);
      Map<String,String> map = new HashMap<>();
        String stdate = "";// 开始日期
        String endate = "";// 结束日期

        //当前月
        if(date.equals(date2)){
            stdate = date + "-01";
            endate = date + "-" + getMonthMaxDay(date);
        }else {//非当前月
            String monthMaxDay = getMonthMaxDay(date2);
            stdate = date2 + "-01";
            endate = date2 + "-" + monthMaxDay;
        }
       map.put("startDate",stdate);
        map.put("endDate",endate);
        return map;

    }

    /**
     * 获取月末最后一天
     * @param sDate
     * @return
     */
    private static String getMonthMaxDay(String sDate) {
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf_full.parse(sDate + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        int last = cal.getActualMaximum(Calendar.DATE);
        return String.valueOf(last);
    }
}
