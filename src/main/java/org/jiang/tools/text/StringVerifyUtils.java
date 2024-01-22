package org.jiang.tools.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串校验工具类
 *
 * @author Bin
 * @since 1.1.2
 */
public class StringVerifyUtils {

    /**
     * 校验手机号
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isPhone(String str) {
        String regex = "^1[0-9]{10}$";
        return matchRegex(str, regex);
    }

    /**
     * 校验座机号
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isTelPhone(String str) {
        String regex = "^\\d{3,4}[-]\\d{7,8}$";
        return matchRegex(str, regex);
    }

    /**
     * 校验邮箱
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isEmail(String str) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return matchRegex(str, regex);
    }

    /**
     * 校验身份证
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isIDCard(String str) {
        String regex = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        return matchRegex(str, regex);
    }

    /**
     * 校验中文名（2-16个中文符号）
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isCnUserName(String str) {
        String regex = "^(?:[\\u4e00-\\u9fa5·]{2,16})$";
        return matchRegex(str, regex);
    }

    /**
     * 校验英文名
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isEnUserName(String str) {
        String regex = "^[a-zA-Z][a-zA-Z\\s]{0,20}[a-zA-Z]$";
        return matchRegex(str, regex);
    }

    /**
     * 校验银行卡号（10-30位数字）
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isBankCard(String str) {
        String regex = "^[1-9]\\d{9,29}$";
        return matchRegex(str, regex);
    }

    /**
     * 校验中国车牌号
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isCnLicencePlate(String str) {
        String regex = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-HJ-NP-Z][A-HJ-NP-Z0-9]{4,5}[A-HJ-NP-Z0-9挂学警港澳]$";
        return matchRegex(str, regex);
    }

    /**
     * 校验合法账号（字母开头，4-16个字母或数字字符，允许下划线）
     *
     * @param str 字符串
     * @return 是否符合
     */
    public static boolean isAccount(String str) {
        String regex = "^[a-zA-Z]\\w{3,15}$";
        return matchRegex(str, regex);
    }

    /**
     * 校验字符串
     *
     * @param str   字符串
     * @param regex 正则表达式
     * @return 是否符合
     */
    public static boolean matchRegex(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
