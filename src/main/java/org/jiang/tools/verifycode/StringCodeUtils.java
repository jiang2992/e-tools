package org.jiang.tools.verifycode;

import org.jiang.tools.util.StringUtils;

import java.util.Random;

/**
 * 验证码工具类
 *
 * @author Bin
 * @date 2021/12/24 17:21
 */
public class StringCodeUtils {

    /**
     * 生成数字验证码
     *
     * @param length 长度
     * @return 字符串
     */
    public static String generateNumber(int length) {
        int number = new Random().nextInt(Double.valueOf(Math.pow(10, length)).intValue());
        return StringUtils.leftPad(String.valueOf(number), length, '0');
    }

}
