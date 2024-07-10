package org.jiang.tools.math;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 舍入规则
 *
 * @author Bin
 * @since 1.1.0
 */
@Getter
public class RoundRule {

    private final int scale;

    private final RoundingMode mode;

    /**
     * 默认构造方法
     *
     * @param scale 精度
     * @param mode  规则
     */
    public RoundRule(int scale, RoundingMode mode) {
        this.scale = scale;
        this.mode = mode;
    }

    /**
     * 构造指定精度的 ROUND_UP 规则
     * ROUND_UP: 任意多余的精度都会进1
     *
     * @param scale 精度
     * @return 舍入规则
     */
    public static RoundRule up(int scale) {
        return new RoundRule(scale, RoundingMode.UP);
    }

    /**
     * 构造指定精度的 ROUND_DOWN 规则
     * ROUND_DOWN: 任意多余的精度都会舍弃
     *
     * @param scale 精度
     * @return 舍入规则
     */
    public static RoundRule down(int scale) {
        return new RoundRule(scale, RoundingMode.DOWN);
    }

    /**
     * 构造指定精度的 ROUND_HALF_UP 规则
     * ROUND_HALF_UP: 四舍五入
     *
     * @param scale 精度
     * @return 舍入规则
     */
    public static RoundRule halfUp(int scale) {
        return new RoundRule(scale, RoundingMode.HALF_UP);
    }

    /**
     * 构造指定精度的 ROUND_HALF_DOWN 规则
     * ROUND_HALF_DOWN: 五舍六入
     *
     * @param scale 精度
     * @return 舍入规则
     */
    public static RoundRule halfDown(int scale) {
        return new RoundRule(scale, RoundingMode.HALF_DOWN);
    }

    /**
     * 构造指定精度的 ROUND_CEILING 规则
     * ROUND_CEILING: 正数时总会进1，负数时总会舍弃(舍入后的数永远会更大)
     *
     * @param scale 精度
     * @return 舍入规则
     */
    public static RoundRule ceiling(int scale) {
        return new RoundRule(scale, RoundingMode.CEILING);
    }

    /**
     * 构造指定精度的 ROUND_FLOOR 规则
     * ROUND_FLOOR: 正数时总会舍弃，负数时总会进1(舍入后的数永远会更小)
     *
     * @param scale 精度
     * @return 舍入规则
     */
    public static RoundRule floor(int scale) {
        return new RoundRule(scale, RoundingMode.FLOOR);
    }

}
