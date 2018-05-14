package com.jdy.calendar.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date getDate(int year, int month, int day) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month - 1, day);
        return instance.getTime();
    }


    private static int get(int field) {
        return get(0, 0, 0, field);
    }

    private static int get(Date date, int field) {
        return get(date,0, 0, 0, field);
    }

    private static int get(Date date, int year, int month, int day, int field) {
        Calendar instance = Calendar.getInstance();
        if (date != null) {
            instance.setTime(date);
        } else if (year > 0 && month > 0 && day > 0) {//设置时间为这一天
            instance.set(year, month - 1, day);
        }
        return instance.get(field);
    }

    private static int get(int year, int month, int day, int field) {
        return get(null, year, month, day, field);
    }

    /**
     * 获取当前年份
     */
    public static int getYear() {
        return get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     */
    public static int getMonth() {
        return get(Calendar.MONTH) + 1;
    }

    public static int getMonth(long time) {
        return get(new Date(time), Calendar.MONTH) + 1;
    }

    public static int getMonth(Date time) {
        return get(time, Calendar.MONTH) + 1;
    }

    /**
     * 获取今天是这个月的多少号
     */
    public static int getCurrentDayOfMonth() {
        return get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfMonth(long time) {
        return get(new Date(time), Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfMonth(Date time) {
        return get(time, Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取今天是这周的第几天, TODO 注意这里是以星期天为一周的第一天
     */
    public static int getCurrentDayOfWeek() {
        return get(Calendar.DAY_OF_WEEK);
    }

    public static int getHour() {
        return get(Calendar.HOUR_OF_DAY);// 二十四小时制
    }

    public static int getMinute() {
        return get(Calendar.MINUTE);
    }

    public static int getSecond() {
        return get(Calendar.SECOND);
    }

    /**
     * 获取 year年 month月的第一天是星期几
     */
    public static int getFirstDayMonthOfWeek(int year, int month) {
        return get(year, month, 1, Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek(int year, int month, int day) {
        return get(year, month, day, Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek(long date) {
        return get(new Date(date), Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek(Date date) {
        return get(date, Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据传入的年份和月份，判断上一个有多少天
     */
    public static int getLastDaysOfMonth(int year, int month) {
        int lastDaysOfMonth;
        if (month == 1) {
            lastDaysOfMonth = getDaysOfMonth(year - 1, 12);
        } else {
            lastDaysOfMonth = getDaysOfMonth(year, month - 1);
        }
        return lastDaysOfMonth;
    }


    /**
     * 根据传入的年份和月份，获取该年该月的天数
     *
     * @param year  年
     * @param month 月
     * @return 该年该月的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (isLeap(year)) {
                    return 29;
                } else {
                    return 28;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
        }
        return -1;
    }

    /**
     * 根据传过来的 year值， 判断是否未闰年
     *
     * @param year 年
     * @return true 是闰年; false ： 不是闰年
     */
    public static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
}
