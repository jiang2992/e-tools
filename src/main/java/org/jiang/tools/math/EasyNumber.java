package org.jiang.tools.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * EasyNumber：提供一系列的数字科学运算方法
 *
 * @author Bin
 * @since 1.1.0
 */
public class EasyNumber {

    private BigDecimal decimal;

    private EasyNumber(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public static EasyNumber of(long number) {
        return new EasyNumber(BigDecimal.valueOf(number));
    }

    public static EasyNumber of(double number) {
        return new EasyNumber(BigDecimal.valueOf(number));
    }

    public static EasyNumber of(String number) {
        return new EasyNumber(BigDecimal.valueOf(Double.parseDouble(number)));
    }

    public static EasyNumber of(BigDecimal number) {
        return new EasyNumber(number);
    }

    public static EasyNumber of(BigInteger number) {
        return new EasyNumber(new BigDecimal(number));
    }

    public BigDecimal value() {
        return this.decimal;
    }

    // TODO 以后再写

}
