package org.jiang.tools.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author Bin
 * @since 1.1.0
 */
public class StringUtils {

    private final static Pattern HUMP_PATTERN = Pattern.compile("[A-Z]");

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 布尔值
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否非空
     *
     * @param str 字符串
     * @return 布尔值
     */
    public static boolean notEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str 字符串
     * @return 布尔值
     */
    public static boolean isNumber(CharSequence str) {
        // TODO
        return false;
    }

    /**
     * 字符串左边填充
     *
     * @param str    操作字符串
     * @param length 填充后长度
     * @param c      填充字符
     * @return 字符串
     */
    public static String leftPad(String str, int length, char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = (length - str.length()); i > 0; i--) {
            sb.append(c);
        }
        return sb.append(str).toString();
    }

    /**
     * 字符串右边填充
     *
     * @param str    操作字符串
     * @param length 填充后长度
     * @param c      填充字符
     * @return 字符串
     */
    public static String rightPad(String str, int length, char c) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = (length - str.length()); i > 0; i--) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 驼峰命名转下划线命名
     *
     * @param str 字符串
     * @return 转为下划线命名的字符串
     */
    public static String humpToLine(String str) {
        Matcher matcher = HUMP_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            if (matcher.start() == 0) {
                matcher.appendReplacement(sb, matcher.group().toLowerCase());
                continue;
            }
            matcher.appendReplacement(sb, "_" + matcher.group().toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线命名转驼峰命名
     *
     * @param str 字符串
     * @return 转为驼峰命名的字符串
     */
    public static String lineToHump(String str) {
        StringBuilder camelCaseBuilder = new StringBuilder();
        boolean capitalizeNextChar = false;
        for (char c : str.toCharArray()) {
            if (c == '_') {
                capitalizeNextChar = true;
            } else {
                if (capitalizeNextChar) {
                    camelCaseBuilder.append(Character.toUpperCase(c));
                    capitalizeNextChar = false;
                } else {
                    camelCaseBuilder.append(Character.toLowerCase(c));
                }
            }
        }
        return camelCaseBuilder.toString();
    }

}
