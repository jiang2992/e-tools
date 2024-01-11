package org.jiang.tools.math;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RoundRule {

    private final int scale;

    private final int mode;

    public RoundRule(int scale, int mode) {
        this.scale = scale;
        this.mode = mode;
    }

    public static RoundRule up(int scale) {
        return new RoundRule(scale, BigDecimal.ROUND_UP);
    }

    public static RoundRule down(int scale) {
        return new RoundRule(scale, BigDecimal.ROUND_DOWN);
    }

    public static RoundRule halfUP(int scale) {
        return new RoundRule(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static RoundRule halfDown(int scale) {
        return new RoundRule(scale, BigDecimal.ROUND_HALF_DOWN);
    }

    public static RoundRule ceiling(int scale) {
        return new RoundRule(scale, BigDecimal.ROUND_CEILING);
    }

    public static RoundRule floor(int scale) {
        return new RoundRule(scale, BigDecimal.ROUND_FLOOR);
    }

}
