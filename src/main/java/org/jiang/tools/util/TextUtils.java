package org.jiang.tools.util;

/**
 * 文本工具类
 *
 * @author Bin
 * @date 2020-09-16 17:28
 */
public class TextUtils {

    public static String truncation(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        if (str.length() > maxLength) {
            return str.substring(0, maxLength - 2) + "...";
        }
        return str;
    }

}
