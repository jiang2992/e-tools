package org.jiang.tools.constants;

/**
 * 文本常量类
 *
 * @author Bin
 * @since 1.1.3
 */
public interface TextConstants {

    /**
     * 字符集: 数字
     */
    String NUMBER_CHARACTERS = "0123456789";

    /**
     * 字符集: 小写字母
     */
    String LOWER_LETTER_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 字符集: 大写字母
     */
    String UPPER_LETTER_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 字符集: 大小写字母
     */
    String LETTER_CHARACTERS = LOWER_LETTER_CHARACTERS + UPPER_LETTER_CHARACTERS;

    /**
     * 字符集: 大小写字母和数字
     */
    String CHARACTERS = LETTER_CHARACTERS + NUMBER_CHARACTERS;

}
