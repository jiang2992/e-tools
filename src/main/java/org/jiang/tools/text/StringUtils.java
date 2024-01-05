package org.jiang.tools.text;

/**
 * 字符串工具类
 *
 * @author Bin
 * @since 1.1.0
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 布尔值
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否非空
     *
     * @param str 字符串
     * @return 布尔值
     */
    public static boolean notEmpty(String str) {
        return !isEmpty(str);
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

}
