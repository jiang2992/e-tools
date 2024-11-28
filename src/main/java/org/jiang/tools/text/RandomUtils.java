package org.jiang.tools.text;

import java.util.concurrent.ThreadLocalRandom;
import org.jiang.tools.constants.TextConstants;

/**
 * 随机字符工具类
 *
 * @author Bin
 * @since 1.1.3
 */
public class RandomUtils {

    /**
     * 生成指定字符集的字符串
     *
     * @param length     长度
     * @param characters 字符集
     * @return 字符串
     */
    public static String generate(int length, String characters) {
        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 生成包含大小写字母和数字的字符串
     *
     * @param length 长度
     * @return 字符串
     */
    public static String generate(int length) {
        return generate(length, TextConstants.CHARACTERS);
    }

    /**
     * 生成仅包含大小写字母的字符串
     *
     * @param length 长度
     * @return 字符串
     */
    public static String generateLetter(int length) {
        return generate(length, TextConstants.LETTER_CHARACTERS);
    }

    /**
     * 生成仅包含大写字母的字符串
     *
     * @param length 长度
     * @return 字符串
     */
    public static String generateUpperLetter(int length) {
        return generate(length, TextConstants.UPPER_LETTER_CHARACTERS);
    }

    /**
     * 生成仅包含小写字母的字符串
     *
     * @param length 长度
     * @return 字符串
     */
    public static String generateLowerLetter(int length) {
        return generate(length, TextConstants.LOWER_LETTER_CHARACTERS);
    }

    /**
     * 生成仅包含数字的字符串
     *
     * @param length 长度
     * @return 字符串
     */
    public static String generateNumber(int length) {
        int number = ThreadLocalRandom.current().nextInt(Double.valueOf(Math.pow(10, length)).intValue());
        return StringUtils.leftPad(String.valueOf(number), length, '0');
    }

}
