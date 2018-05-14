package com.jdy.calendar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 农历算法
 */
public class LunarCalendar {
    public int leapMonth = 0; // 闰的是哪个月
    private boolean isBeforeChrist = false; // 公元前
    private SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);

    /**
     * 传回农历 y年的总天数 ; 农历平均每月 29.5天，以朔望月为准的，大月30天，小月29天；
     *
     * @param year 年份
     * @return 该年的总天数
     */
    private int yearDays(int year) {
        if (year < 1900 || year > 2100) {
            throw new IllegalAccessError("Could not get the year's lunar information!");
        }

        int i, sum = 12 * 29;
        for (i = 0x8000; i > 0x8; i >>= 1) { // 相当于 for(i = 2^15; i > 2^3; i = i/2)
            if (needLeap(year, i))
                sum += 1;
        }
        return (sum + leapMonthDays(year));
    }

    private boolean needLeap(int year, int flag) {
        // 按位与运算 "&"： 先将需运算的双方转换成二进制， 在一一相与： 1&1=1； 1&0 = 0； 0&1=0； 0&0=0；
        return (LunarCalendarConfig.lunarInfo[year - 1900] & flag) != 0;
    }

    /**
     * 传回农历 y年闰月的天数
     *
     * @param year 年份
     * @return 该年闰月天数
     */
    private int leapMonthDays(int year) {
        return leapMonth(year) == 0 ? 0 : needLeap(year, 0x10000) ? 30 : 29;
    }

    /**
     * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
     *
     * @param year 年份
     * @return 改年闰月的月数
     */
    private int leapMonth(int year) {
        return (int) (LunarCalendarConfig.lunarInfo[year - 1900] & 0b1111);
    }

    /**
     * 传回农历 y年m月的总天数
     *
     * @param year  年份
     * @param month 月份
     * @return 该月份的总天数
     */
    private int monthDays(int year, int month) {
        return needLeap(year, (0x10000 >> month)) ? 30 : 29;
    }

    /**
     * 传回农历 y年的生肖
     *
     * @param year 年份
     * @return 生肖
     */
    public String animalsYear(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("The year :" + year + " is invalid year!");
        }
        return LunarCalendarConfig.Animals[getIndex(isBeforeChrist, year, true)];
    }

    private int getIndex(boolean isBeforeChrist, int year, boolean b) {
        int tempGan = year % 10, tempZhi = year % 12;
        if (isBeforeChrist) { // 公元前算法
            tempGan = (11 - tempGan) % 10;
            tempZhi = (13 - tempZhi) % 12;
        }
        return b ? tempZhi : tempGan;
    }

    /**
     * 通过年份，获取该年的干支
     *
     * @param year 年分
     * @return 干支
     */
    public String getChineseEra(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("The year :" + year + " is invalid year!");
        }
        return LunarCalendarConfig.Gan[getIndex(isBeforeChrist, year, false)]
                + LunarCalendarConfig.Zhi[getIndex(isBeforeChrist, year, true)];
    }

    /**
     * 将农历日期数字转换成中国的农历日期格式的字符串
     *
     * @param day 农历日期的数值
     * @return
     */
    // final static String chineseNumber[] = { "一", "二", "三", "四", "五", "六", "七",
    // "八", "九", "十", "十一", "十二" };
    private String convetChineseDayString(int day) {
        if (day > 30)
            return "";
        int n = day % 10;
        n = n == 0 ? 10 : n;
        int m = day == 10 ? 0 : day / 10;
        return LunarCalendarConfig.LunarSpecialCall[m] + LunarCalendarConfig.ChineseNumber[n];
    }

    /**
     * 传出y年m月d日对应的农历. yearCyl:农历年与1864的相差数 monCyl4:从1900年1月31日以来,闰月数
     * dayCyl5:与1900年1月31日相差的天数,再加40
     * <p/>
     * isday: 这个参数为false---日期为节假日时，阴历日期就返回节假日 ，true---不管日期是否为节假日依然返回这天对应的阴历日期
     *
     * @return
     * @throws ParseException
     */
    public String getLunarDate(int year, int month, int day) {
        if (year < 1900 || year > 2100) {
            throw new IllegalAccessError("Could not get the year's lunar information!");
        }
        String nowadays = year + "年" + month + "月" + day + "日";
        Date baseDate = null;
        Date nowaday = null;

        try {
            baseDate = chineseDateFormat.parse("1900年1月31日");
            nowaday = chineseDateFormat.parse(nowadays);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 求出和1900年1月31日相差的天数
        int offset = (int) ((nowaday.getTime() - baseDate.getTime()) / 86400000L);
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 2101 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
        }

        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }

        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        boolean isLeap = true;

        // 效验闰月
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // isLeapMonth = leapMonth > 0 && iMonth == (leapMonth + 1);
            // 闰月
            if (isLeapMonth(iYear, iMonth) && isLeap) { // 是闰月，但还没有算
                --iMonth;
                daysOfMonth = leapMonthDays(iYear);
                isLeap = false;
            } else
                daysOfMonth = monthDays(iYear, iMonth);
            offset -= daysOfMonth;
        }

        boolean isLeapMonth = false;
        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && isLeapMonth(iYear, iMonth)) {
            if (isLeap) {
                --iMonth;
                isLeapMonth = true;
                isLeap = false;
            }
        } else if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        String solarTerm = getSolarTerm(year, month, day);
        String festivalName = getLunarHoliday(iMonth, day);
        String solarHoliday = getSolarHoliday(month, day);
        if (solarHoliday != null) {
            return solarHoliday;
        } else if (festivalName != null) {
            return festivalName;
        } else if (solarTerm != null) {
            return solarTerm;
        } else if (offset == 0)
            return (isLeapMonth ? "\u95f0" : "") + LunarCalendarConfig.ChineseMonthName[iMonth - 1];
        else
            return convetChineseDayString(offset + 1);
    }

    private String getSolarTerm(int year, int month, int day) {
        if (year < 1900 || year > 2100) {
            throw new IllegalAccessError("Could not get the year's lunar information!");
        }
        int tempx = month % 2, temp = month / 2 + tempx;
        String termInfo = LunarCalendarConfig.TermInfo[year - 1900];
        for (int j = 0, m = 1; j < termInfo.length() && m <= temp; j += 5, m++) {
            int parseInt = Integer.parseInt(termInfo.substring(j, j + 5), 16);
            if (temp == m) {
                int start = (1 - tempx) * 3, end = start + 3;
                String termInMonth = (parseInt + "").substring(start, end);
                if (day == Integer.parseInt(termInMonth.substring(1))) {
                    return LunarCalendarConfig.SolarTerm[month * 2 - 1];
                } else if (day == Integer.parseInt(termInMonth.substring(0, 1))) {
                    return LunarCalendarConfig.SolarTerm[month * 2 - 2];
                }
            }
        }
        return null;
    }

    private boolean isLeapMonth(int year, int month) {
        leapMonth = leapMonth(year); // 闰哪个月,1-12
        return leapMonth > 0 && month == (leapMonth + 1);
    }

    private String getLunarHoliday(int month, int day) {
        for (String lunarHoliday : LunarCalendarConfig.LunarHoliday) {
            String[] holidayInfo = lunarHoliday.split(" ");
            String date = holidayInfo[0];
            if (month == Integer.parseInt(date.substring(0, 2)) && Integer.parseInt(date.substring(2, 4)) == day) {
                return holidayInfo[1] + "\u8282";
            }
        }
        return null;
    }

    private String getSolarHoliday(int month, int day) {
        for (String solarHoliday : LunarCalendarConfig.SolarHoliday) {
            String[] holidayInfo = solarHoliday.split(" ");
            String date = holidayInfo[0];
            if (month == Integer.parseInt(date.substring(0, 2)) && Integer.parseInt(date.substring(2, 4)) == day) {
                return holidayInfo[1] + "\u8282";
            }
        }
        return null;
    }
}
