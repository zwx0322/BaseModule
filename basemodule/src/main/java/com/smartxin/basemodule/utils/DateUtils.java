package com.smartxin.basemodule.utils;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: zhengwenxin
 * CreateDate  : 2021/11/2 10:08
 * Description:
 */
public class DateUtils {
    /**
     * 时间转换格式申明
     */
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 时间转换格式申明(包含时间)
     */
    public static SimpleDateFormat formatterTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 将 Long 类型转为字符串，格式为“yyyy-MM-dd”
     */
    public static String date2String(Long l) {
        if (l == null)
            return "";
        else
            return date2String(new Date(l));
    }

    /**
     * 将 Date 类型转为字符串，格式为“yyyy-MM-dd”
     */
    public static String date2String(Date date) {
        if (date == null)
            return "";
        else
            return date2String(date, "yyyy-MM-dd");
    }

    /**
     * 将 Long 类型转为字符串，格式为“yyyy-MM-dd HH:mm:ss”
     */
    public static String date2StringTime(Long l) {
        if (l == null)
            return "";
        else
            return date2StringTime(new Date(l));
    }

    /**
     * 将 Date 类型转为字符串，格式为“yyyy-MM-dd HH:mm:ss”
     */
    public static String date2StringTime(Date date) {
        if (date == null)
            return "";
        else
            return date2String(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 将 Date 类型转为字符串，格式自定义
     *
     * @param pattern 格式规范
     */
    public static String date2String(Date date, @NonNull String pattern) {
        String dateString = "";
        SimpleDateFormat formatterTime = new SimpleDateFormat(pattern);
        if (date != null) {
            dateString = formatterTime.format(date);
        }
        return dateString;
    }

    /**
     * 将 字符串类型转为Date，格式为“yyyy-MM-dd”
     *
     * @param time The formatted time string.
     * @return the date
     */
    public static Date string2Date(final String time) {
        return string2Date(time, formatter);
    }

    /**
     * 将 字符串类型转为Date，格式为“yyyy-MM-dd HH:mm:ss”
     *
     * @param time The formatted time string.
     * @return the date
     */
    public static Date stringTime2Date(final String time) {
        return string2Date(time, formatterTime);
    }

    /**
     * 将 字符串类型转为Date，格式自定义
     *
     * @param time   The formatted time string.
     * @param format The format.
     * @return the date
     */
    public static Date string2Date(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取某天0点或24点
     *
     * @param isBegin true : 获取0点  false : 获取24点
     */
    public static Long timeDateToStringBeginOrEnd(Date date, Boolean isBegin) {

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        //获取某一天的0点0分0秒 或者 23点59分59秒
        if (isBegin) {
            calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                    0, 0, 0);

        } else {
            calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                    23, 59, 59);
        }
        calendar1.set(Calendar.MILLISECOND, 0);
        return calendar1.getTime().getTime();
    }

    /**
     * 计算两天相差天数
     */
    public static int getTwoDayDifference(Long beginDay) {

        return getTwoDayDifference(new Date(beginDay), new Date());
    }

    /**
     * 计算两天相差天数
     */
    public static int getTwoDayDifference(Date beginDay, Date endDay) {

        //获取今天0点
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDay);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);

        endDay = calendar.getTime();

        Long starTime = beginDay.getTime();
        Long endTime = endDay.getTime();

        if (starTime > endTime)
            return 0;
        Long num = endTime - starTime;//时间戳相差的毫秒数

        int i = (int) (num / 24 / 60 / 60 / 1000) + 1;
        return i;
    }
}
