package org.jiang.tools.canvas;

import java.awt.*;
import java.util.Random;

/**
 * 颜色工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class ColorUtils {

    private static final Random RANDOM = new Random();

    public static Color getRandRandom(int fc, int bc) {
        int r = fc + RANDOM.nextInt(bc - fc);
        int g = fc + RANDOM.nextInt(bc - fc);
        int b = fc + RANDOM.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public static int getRandomInt() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    public static int[] getRandomRgb() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = RANDOM.nextInt(255);
        }
        return rgb;
    }

}
