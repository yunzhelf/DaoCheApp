package com.yifactory.daocheapp.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String getTime(Date date) {
        return DEFAULT_FORMAT.format(date);
    }

    /**
     * 获取当前时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @return 时间字符串
     */
    public static String getNowString() {
        return millis2String(System.currentTimeMillis(), DEFAULT_FORMAT);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(long millis) {
        return millis2String(millis, DEFAULT_FORMAT);
    }

    public static String millis2String2(long millis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(millis * 1000L));
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, DateFormat format) {
        return format.format(new Date(millis));
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time) {
        return string2Millis(time, DEFAULT_FORMAT);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time, DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 获取昨天的时间戳
     * @return
     */
    public static String getYesterdayTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return yesterday;
    }

    /**
     * 获取当前月第一天字符串
     * @return
     */
    public static String getMonthOneTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date sTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(sTime) + " 00:00:00";
    }

    /**
     * 获取当前月最后一天字符串
     * @return
     */
    public static String getMonthEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date eTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(eTime) + " 23:59:59";
    }


    /**
     * 获取昨天的时间戳
     * @return
     */
    public static String getTodayTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   0);
        String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return today;
    }

    /**
     * 获取明天的时间戳
     * @return
     */
    public static String getTomorrowTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   1);
        String tomorrow = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        return tomorrow;
    }

    /**
     * 获取周一的时间字符串
     * @return
     */
    public static String getMondayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date    sTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(sTime) + " 00:00:00";
    }

    /**
     * 获取周日的时间字符串
     * @return
     */
    public static String getSundayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        Date eTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(eTime) + " 23:59:59";
    }

    /**
     * 根据日期字符串获取时间戳
     * @param timeString
     * @return
     */
    public static long getTime(String timeString){
        if (!TextUtils.isEmpty(timeString)) {
            long l = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d;
            try{
                d = sdf.parse(timeString);
                l = d.getTime();
            } catch(ParseException e){
                e.printStackTrace();
            }
            return l;
        }
        return 0;
    }
}
