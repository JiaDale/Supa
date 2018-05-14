package com.jdy.base.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static DateFormat dateFormat = DateFormat.getInstance();

    public static Date convert(java.sql.Date date) {
        return new Date(date.getTime());
    }

    public static java.sql.Date convert(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 对日期实体格式化，统一为默认精度
     * @param date 实体日期
     * @return 格式化后的日期实体
     */
    private static Date convertor(Date date){
        return dateFormat.convert(dateFormat.dateFormat("yyyy-MM-dd", date));
    }

    /**
     * 对日期实体格式化，统一精度
     * @param date 实体日期
     * @param format 精度格式
     * @return 格式化后的日期实体
     */
    private static Date convertor(Date date, String format){
        return dateFormat.convert(dateFormat.dateFormat(format, date));
    }

    public static String dateFormat(java.sql.Date date) {
        return dateFormat(date);
    }

    /**
     * 获取默认日期个是的字符串日期
     * @param date 日期实体
     * @return 字符串日期
     */
    public static String dateFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd");
    }

    /**
     * 按照format格式化日期
     * @param date 日期
     * @param format 格式
     * @return 格式化的String日期
     */
    public static String dateFormat(Date date, String format) {
        return dateFormat.dateFormat(format, date);
    }

    public static String dateFormat(long time, String format) {
        return dateFormat(new Date(time), format);
    }

    /**
     * 将日期的object值转换成字符串日期
     * @param value 值
     * @return 字符串日期
     */
    public static String dateFormat(Object value) {
        return dateFormat.dateFormat(value);
    }

    /**
     * 将字符串的日期转换成实体日期
     * @param dateString 字符串日期
     * @return 实体日期
     */
    @Deprecated
    public static Date convert(String dateString) {
        return dateFormat.convert(dateString);
    }

    public static Date convert(String dateString, String format) {
        return dateFormat.convert(dateString, format);
    }

    /**
     * 将当前日期格式转化成一目标一样的日期格式
     * @param current 当前日期格式
     * @param target 目标日期格式
     * @return 转化后的目标格式的当前日期
     */
    public static String dateFormat(String current, String target) {
        return dateFormat(convert(current), target);
    }

    /**
     *  按默认的精确度比较两个日期的大小
     * @param current 比较前项
     * @param target 比较后项
     * @return -1 : current < target; 0  : current = target ; 1 : current  > target
     */
    public static int compare(Date current, Date target) {
        return compare(current, target, "yyyy-MM-dd");
    }

    public static int compare(long current, long target) {
        return compare(new Date(current), new Date(target));
    }

    /**
     * 比较两个日期的大小
     * @param current 比较前项
     * @param target 比较后项
     * @param format 比较的日期格式（精确度）
     * @return -1 : current < target; 0  : current = target ; 1 : current  > target
     */
    public static int compare(Date current, Date target, String format) {
        return convert(dateFormat(current, format)).compareTo(convert(dateFormat(target, format)));
//        return dateFormat(format, current).compareTo(dateFormat(format, target));
    }

    /**
     * 计算两天相差多少天
     * @param current 计算的前项
     * @param target 计算的后项
     * @return 天数
     */
    public static long calculateDValue(Date current, Date target) {
        return (convertor(current).getTime() - convertor(target).getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     *
     */
    public static long calculateDValue(long current, long target) {

        return calculateDValue(new Date(current), new Date(target));
    }

    /**
     * 获取与今天相隔next天的日期
     * @param next 天数
     * @return 日期
     */
    public static Date getNextDate(int next){
        return getNextDate(new Date(), next);
    }

    /**
     *  获取与 current 相隔 next天的日期
     * @param current 当前
     * @param next 天数
     * @return 日期
     */
    public static Date getNextDate(Date current, int next){
        return  convertor(new Date(convertor(current).getTime() + next * 24 * 60 * 60 * 1000));
    }

    /**
     *  验证日期数组的各个日期是否为format格式
     * @param format 格式
     * @param dates 字符日期
     * @return true ： 日期数组中的日期都为format格式； false ： 日期数组中至少有一个日期不是format格式
     */
    public static boolean isValidDate(String format, String... dates) {
        return dateFormat.isValidDate(format, dates);
    }


    static class DateFormat {
        private SimpleDateFormat simpleDateFormat;

        DateFormat() {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        }

        public static DateFormat getInstance() {
            return new DateFormat();
        }

        @Deprecated
        Date convert(String dateString) {
            return  convert(dateString, dateString);
        }

        Date convert(String dateString, String format) {
            try {
                simpleDateFormat.applyPattern(getFormat(format));
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        String dateFormat(String current, String target) {
            if (current == null || target == null) {
                throw new NullPointerException("The current format or target format is Null!");
            }
            return dateFormat(target, convert(current, current));
        }

        String dateFormat(String format, Date date) {
            if (format == null) {
                throw new NullPointerException("The format string is Null!");
            }
            simpleDateFormat.applyPattern(getFormat(format));
            return simpleDateFormat.format(date);
        }

        String dateFormat(Object value) {
            if (value instanceof Date) {
                return simpleDateFormat.format(value);
            }
            return "unknown";
        }

        String getFormat(String format) {
            if (format == null) {
                throw new NullPointerException("The format string is Null");
            }
            boolean result = format.matches("[0-9]+");
            char[] charArray = format.toCharArray();
            char[] array = {'y', 'M', 'd', 'H', 'm', 's'};
            int count = 0;
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < charArray.length; i++) {
                char c = charArray[i];
                if (c >= '0' && c <= '9') {
                    buffer.append(array[count]);
                } else {
                    buffer.append(c);
                    count++;
                }
                if (result && increase(i, format)) {
                    count ++;
                }
            }
            return buffer.toString();
        }

        boolean increase(int i, String format) {
            switch (format.length()) {
                case 8:
                    return i == 3 || i == 5;
                case 6:
                    return i == 1 || i == 3;
                default:
                    return false;
            }
        }

        boolean isValidDate(String format, String... dates) {
            boolean convertSuccess = true;
            if (dates == null || dates.length == 0) {
                return false;
            }
            for (String date : dates) {
                if (!isValidDate(date, format)) {
                    convertSuccess = false;
                    break;
                }
            }
            return convertSuccess;
        }

        boolean isValidDate(String date, String format) {
            boolean convertSuccess = true;
            simpleDateFormat = new SimpleDateFormat(getFormat(format), Locale.CHINA);
            try {
                simpleDateFormat.setLenient(false);
                simpleDateFormat.parse(date);
            } catch (Exception e) {
                convertSuccess = false;
            }
            return convertSuccess;
        }
    }
}
