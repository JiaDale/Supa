package com.jdy.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    private CheckUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static synchronized boolean isNull(String str) {
        return null == str || str.trim().length() <= 0;
    }

    public static boolean notNull(String str) {
        return !isNull(str);
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @return
     */
    public static String doNullStr(String str) {
        return doNullStr(str, "");
    }


    public static String doNullStr(String str, String defaut) {
        return isNull(str) ? defaut : str;
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isEmail(String email) {
        String regex = "\\w+(\\.\\w+)*@\\w+(\\.\\w+)+";
        //String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Regular(email, regex);
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *              <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *              数字之后是空格分隔的国家（地区）代码。</p>
     *              <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     *              对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     *              <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPhone(String phone) {
        //String PHONE = "(^(\\d{2,4}[-_锛嶁�]?)?\\d{3,8}([-_锛嶁�]?\\d{3,8})?([-_锛嶁�]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Regular(phone, regex);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *               <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *               、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *               <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *               <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobile(String mobile) {
        //String MOBILE = "^(13[0-9]|15[0-9]|18[0-9])\\d{8}$";
        String regex = "(\\+\\d+)?1[3458]\\d{9}$";
        return Regular(mobile, regex);
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isUrl(String url) {
        //		String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?"
        //				+ "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*"
        //				+ "(\\w*:)*(\\w*\\+)*(\\w*\\.)*"
        //				+ "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Regular(url, regex);
    }

    /**
     * 验证数字
     *
     * @param number 格式：100000.000
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isNumber(String number) {
        String regex = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
        return Regular(number, regex);
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Regular(digit, regex);
    }

    /**
     * 验证整数
     *
     * @param intager 整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isInteger(String intager) {
        String regex = "^-?(([1-9]\\d*$)|0)";
        return Regular(intager, regex);
    }

    /**
     * 验证负整数
     *
     * @param negativeInteger 负整数 ：-1、 -2；
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isINTEGER_NEGATIVE(String negativeInteger) {
        String regex = "^[1-9]\\d*|0$";
        return Regular(negativeInteger, regex);
    }

    /**
     * 验证正整数
     *
     * @param positiveInteger 正整数 ：1、 2；
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isINTEGER_POSITIVE(String positiveInteger) {
        String regex = "^-[1-9]\\d*|0$";
        return Regular(positiveInteger, regex);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Regular(decimals, regex);
    }

    /**
     * 验证double的小数
     *
     * @param target 1.00
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDouble(String target) {
        String regex = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
        return Regular(target, regex);
    }

    /**
     * 验证负double小数
     *
     * @param target double -1.00
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDOUBLE_NEGATIVE(String target) {
        String regex = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
        return Regular(target, regex);
    }


    /**
     * 验证正double小数
     *
     * @param target double 1.00
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDOUBLE_POSITIVE(String target) {
        String regex = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
        return Regular(target, regex);
    }


    /**
     * 验证日期（年月日）
     *
     * @param target 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDate(String target) {
        String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)"
                + "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)"
                + "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)"
                + "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)"
                + "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)"
                + "(0?2)([-\\/\\._]?)(29)$)"
                + "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)"
                + "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|"
                + "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";
        //String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Regular(target, DATE_ALL);
    }

    /**
     * 验证是否未默认格式 YYYY-MM-DD 的日期
     *
     * @param target 日期，格式：1992-09-03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isDefaultDate(String target) {
        String DATE_FORMAT1 = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|" +
                "[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|" +
                "[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        return Regular(target, DATE_FORMAT1);
    }

    /**
     * 验证年龄
     *
     * @param target 年龄
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isAge(String target) {
        String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
        return Regular(target, AGE);
    }

    /**
     * 验证是否超长
     *
     * @param target 验证对象
     * @param leng   长度
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isLengOut(String target, int leng) {
        return notNull(target) && target.trim().length() > leng;
    }

    /**
     * 验证是否未身份证号码
     *
     * @param target 身份证号
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIdCard(String target) {
        if (isNull(target))
            return false;
        if (target.trim().length() == 15 || target.trim().length() == 18) {
            String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})"
                    + "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}"
                    + "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
            return Regular(target, IDCARD);
        } else {
            return false;
        }
    }

    /**
     * 验证 验证码
     * @param target
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isCode(String target) {
        String CODE = "[0-9]\\d{5}(?!\\d)";
        return Regular(target, CODE);
    }

    /**
     * 验证英文
     * @param str 英文字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isEnglish(String str) {
        String STR_ENG = "^[A-Za-z]+$";
        return Regular(str, STR_ENG);
    }

    /**
     * 英文加数字
     * @param str 对象
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isENG_NUM(String str) {
        String STR_ENG_NUM = "^[A-Za-z0-9]+";
        return Regular(str, STR_ENG_NUM);
    }

    /**
     * 英文数字
     * @param str 对象
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isENG_NUM_(String str) {
        String STR_ENG_NUM_ = "^\\w+$";
        return Regular(str, STR_ENG_NUM_);
    }

    /**
     * 过滤string
     * @param str 过滤对象
     * @return 过滤结果
     */
    public static String filterStr(String str) {
        String STR_SPECIAL = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~锛丂#锟�鈥︹�&*锛堬級鈥斺�+|{}銆愩�鈥橈紱锛氣�鈥溾�銆傦紝銆侊紵]";
        Pattern p = Pattern.compile(STR_SPECIAL);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 验证机构代码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isJigouCode(String str) {
        String JIGOU_CODE = "^[A-Z0-9]{8}-[A-Z0-9]$";
        return Regular(str, JIGOU_CODE);
    }

    /**
     * 验证纯数字
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isSTR_NUM(String str) {
        String STR_NUM = "^[0-9]+$";
        return Regular(str, STR_NUM);
    }

    /**
     * 匹配中国邮政编码
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Regular(postcode, regex);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Regular(ipAddress, regex);
    }

    /**
     * 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Regular(chinese, regex);
    }

    /**
     * 验证空白字符
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Regular(blankSpace, regex);
    }

    /**
     * 正则验证
     * @param str    验证对象
     * @param pattern 验证规则
     * @return 验证成功返回true，验证失败返回false
     */
    private static boolean Regular(String str, String pattern) {
        if (null == str || str.trim().length() <= 0)
            return false;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
