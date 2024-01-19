package org.jiang.tools.math;

import org.jiang.tools.exception.BadArgumentException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

/**
 * EasyNumber：提供一系列的数字科学运算方法
 *
 * @author Bin
 * @since 1.1.0
 */
public class EasyNumber implements Comparable<EasyNumber> {

    private final static String SYMBOL_ADD = "+";
    private final static String SYMBOL_SUBTRACT = "-";
    private final static String SYMBOL_MULTIPLY = "×";
    private final static String SYMBOL_DIVIDE = "÷";
    private final static String SYMBOL_REMAINDER = "%";
    private final static String SYMBOL_POW = "^";

    private BigDecimal decimal;
    private BigDecimal remainedDecimal;
    private RoundRule roundRule = RoundRule.down(2);
    private StringBuilder courseString;
    private int leftBracketIndex = -1;

    private EasyNumber(BigDecimal decimal) {
        this.decimal = decimal;
    }

    public static EasyNumber zero() {
        return of(0);
    }

    /**
     * 从Long类型的数值构造对象
     *
     * @param number 数值
     * @return EasyNumber
     */
    public static EasyNumber of(long number) {
        return of(BigDecimal.valueOf(number));
    }

    /**
     * 从Double类型的数值构造对象
     *
     * @param number 数值
     * @return EasyNumber
     */
    public static EasyNumber of(double number) {
        return of(BigDecimal.valueOf(number));
    }

    /**
     * 从字符串类型的数值构造对象
     *
     * @param number 字符串数值
     * @return EasyNumber
     */
    public static EasyNumber of(String number) {
        return of(BigDecimal.valueOf(Double.parseDouble(number)));
    }

    /**
     * 从BigInteger类型的数值构造对象
     *
     * @param number 数值
     * @return EasyNumber
     */
    public static EasyNumber of(BigInteger number) {
        return of(new BigDecimal(number));
    }

    /**
     * 从BigDecimal类型的数值构造对象
     *
     * @param number 数值
     * @return EasyNumber
     */
    public static EasyNumber of(BigDecimal number) {
        return new EasyNumber(number);
    }

    /**
     * 从16进制字符串构造对象
     *
     * @param hexString 16进制字符串
     * @return EasyNumber
     */
    public static EasyNumber ofHexString(String hexString) {
        return EasyNumber.of(Long.parseLong(hexString, 16));
    }

    /**
     * 从浮点型16进制字符串构造对象
     *
     * @param doubleHexString 浮点型16进制字符串
     * @return EasyNumber
     */
    public static EasyNumber ofDoubleHexString(String doubleHexString) {
        long longBits = Long.parseLong(doubleHexString, 16);
        return EasyNumber.of(Double.longBitsToDouble(longBits));
    }

    /**
     * 设置计算所使用的精度及舍入规则
     *
     * @param roundRule 精度及舍入规则
     * @return EasyNumber
     * @see RoundRule
     */
    public EasyNumber rule(RoundRule roundRule) {
        this.roundRule = roundRule;
        return this;
    }

    /**
     * 设置计算所使用的精度及舍入规则为整数
     *
     * @return EasyNumber
     * @see RoundRule
     */
    public EasyNumber integerRule() {
        this.roundRule = RoundRule.down(0);
        return this;
    }

    /**
     * 返回计算结果值
     *
     * @return 数值
     */
    public BigDecimal value() {
        return this.decimal.setScale(this.roundRule.getScale(), this.roundRule.getMode());
    }

    /**
     * 返回计算结果的int值
     *
     * @return 数值
     */
    public int toInt() {
        return this.value().intValue();
    }

    /**
     * 返回计算结果的long值
     *
     * @return 数值
     */
    public long toLong() {
        return this.value().longValue();
    }

    /**
     * 返回计算结果的float值
     *
     * @return 数值
     */
    public float toFloat() {
        return this.value().floatValue();
    }

    /**
     * 返回计算结果的double值
     *
     * @return 数值
     */
    public double toDouble() {
        return this.value().doubleValue();
    }

    /**
     * 返回计算结果的字符串值
     *
     * @return 数值
     */
    @Override
    public String toString() {
        return this.value().toString();
    }

    /**
     * 返回计算结果的科学计数格式字符串
     *
     * @return 字符串
     */
    public String toFormatString() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(this.value());
    }

    /**
     * 返回计算过程及结果、余数的字符串
     * 示例：(2+3)×(1+1)÷3=3(~1)
     *
     * @return 字符串
     */
    public String course() {
        if (this.courseString == null) {
            return this.toString();
        }
        StringBuilder result = new StringBuilder(this.courseString);
        result.append("=");
        result.append(this);
        if (this.remainedDecimal != null && this.remainedDecimal.doubleValue() > 0) {
            result.append("(");
            result.append("~");
            result.append(this.remainder());
            result.append(")");
        }
        return result.toString();
    }

    /**
     * 返回除法运算中产生的余数
     * 只能在最后一次运算是除法运算时使用
     *
     * @return 余数
     */
    public BigDecimal remainder() {
        if (this.remainedDecimal == null) {
            throw new BadArgumentException("没有预先的除法运算");
        }
        return this.remainedDecimal.setScale(this.roundRule.getScale(), this.roundRule.getMode());
    }

    /**
     * 返回正负号
     *
     * @return 正负号（-1,0,1）
     */
    public int signum() {
        return this.decimal.signum();
    }

    /**
     * 返回16进制字符串
     * 使用该方法会丢失小数部分，请务必保证值为整数
     * 如果需要将浮点数转为16进制字符串，请使用 toDoubleHexString
     *
     * @return 16进制字符串
     */
    public String toHexString() {
        return Long.toHexString(this.toLong());
    }

    /**
     * 返回16进制字符串（64位浮点型）
     * 该方法会先将浮点值转为 LongBits 再进行进制转换
     * 注意：该方法不能与常规的转换混用，如果需要转回十进制，请使用 ofDoubleHexString
     *
     * @return 16进制字符串
     */
    public String toDoubleHexString() {
        long longBits = Double.doubleToLongBits(this.decimal.doubleValue());
        return Long.toHexString(longBits);
    }

    /**
     * 加法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber add(long number) {
        return this.add(of(number));
    }

    /**
     * 加法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber add(double number) {
        return this.add(of(number));
    }

    /**
     * 加法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber add(BigInteger number) {
        return this.add(of(number));
    }

    /**
     * 加法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber add(BigDecimal number) {
        return this.add(of(number));
    }

    /**
     * 减法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber subtract(long number) {
        return this.subtract(of(number));
    }

    /**
     * 减法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber subtract(double number) {
        return this.subtract(of(number));
    }

    /**
     * 减法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber subtract(BigInteger number) {
        return this.subtract(of(number));
    }

    /**
     * 减法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber subtract(BigDecimal number) {
        return this.subtract(of(number));
    }

    /**
     * 乘法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber multiply(long number) {
        return this.multiply(of(number));
    }

    /**
     * 乘法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber multiply(double number) {
        return this.multiply(of(number));
    }

    /**
     * 乘法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber multiply(BigInteger number) {
        return this.multiply(of(number));
    }

    /**
     * 乘法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber multiply(BigDecimal number) {
        return this.multiply(of(number));
    }

    /**
     * 除法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber divide(long number) {
        return this.divide(of(number));
    }

    /**
     * 除法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber divide(double number) {
        return this.divide(of(number));
    }

    /**
     * 除法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber divide(BigInteger number) {
        return this.divide(of(number));
    }

    /**
     * 除法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber divide(BigDecimal number) {
        return this.divide(of(number));
    }

    /**
     * 取余运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber remainder(long number) {
        return this.remainder(of(number));
    }

    /**
     * 取余运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber remainder(double number) {
        return this.remainder(of(number));
    }

    /**
     * 取余运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber remainder(BigInteger number) {
        return this.remainder(of(number));
    }

    /**
     * 取余运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber remainder(BigDecimal number) {
        return this.remainder(of(number));
    }


    /**
     * 与目标值对比，取最大值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber max(long number) {
        return this.max(of(number));
    }

    /**
     * 与目标值对比，取最大值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber max(double number) {
        return this.max(of(number));
    }

    /**
     * 与目标值对比，取最大值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber max(BigInteger number) {
        return this.max(of(number));
    }

    /**
     * 与目标值对比，取最大值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber max(BigDecimal number) {
        return this.max(of(number));
    }

    /**
     * 与目标值对比，取最小值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber min(long number) {
        return this.min(of(number));
    }

    /**
     * 与目标值对比，取最小值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber min(double number) {
        return this.min(of(number));
    }

    /**
     * 与目标值对比，取最小值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber min(BigInteger number) {
        return this.min(of(number));
    }

    /**
     * 与目标值对比，取最小值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber min(BigDecimal number) {
        return this.min(of(number));
    }

    /**
     * 加法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber add(EasyNumber number) {
        this.addCourse(SYMBOL_ADD, number);
        this.decimal = this.decimal.add(number.value());
        this.remainedDecimal = null;
        return this;
    }

    /**
     * 减法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber subtract(EasyNumber number) {
        this.addCourse(SYMBOL_SUBTRACT, number);
        this.decimal = this.decimal.subtract(number.value());
        this.remainedDecimal = null;
        return this;
    }

    /**
     * 乘法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber multiply(EasyNumber number) {
        this.addCourse(SYMBOL_MULTIPLY, number);
        this.decimal = this.decimal.multiply(number.value());
        this.remainedDecimal = null;
        return this;
    }

    /**
     * 除法运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber divide(EasyNumber number) {
        BigDecimal numberDecimal = number.value();
        this.remainedDecimal = this.decimal.remainder(numberDecimal);
        this.addCourse(SYMBOL_DIVIDE, number);
        this.decimal = this.decimal.divide(numberDecimal, roundRule.getScale(), roundRule.getMode());
        return this;
    }

    /**
     * 取余运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber remainder(EasyNumber number) {
        this.addCourse(SYMBOL_REMAINDER, number);
        this.decimal = this.decimal.remainder(number.value());
        this.remainedDecimal = null;
        return this;
    }

    /**
     * 与目标值对比，取最大值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber max(EasyNumber number) {
        switch (this.compareTo(number)) {
            case -1:
                return number;
            case 0:
            case 1:
                return this;
        }
        return this;
    }

    /**
     * 与目标值对比，取最小值
     * 如果相同则返回this
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber min(EasyNumber number) {
        switch (this.compareTo(number)) {
            case -1:
            case 0:
                return this;
            case 1:
                return number;
        }
        return this;
    }

    /**
     * 幂运算
     *
     * @param number 数值
     * @return EasyNumber
     */
    public EasyNumber pow(int number) {
        this.addCourse(SYMBOL_POW, EasyNumber.of(number).integerRule());
        this.decimal = this.decimal.pow(number);
        this.remainedDecimal = null;
        return this;
    }

    private void addCourse(String symbol, EasyNumber number) {
        boolean isAddSubtract = SYMBOL_ADD.equals(symbol) || SYMBOL_SUBTRACT.equals(symbol);
        String str = number.courseString != null ? String.format("(%s)", number.courseString) : number.value().setScale(this.roundRule.getScale(), this.roundRule.getMode()).toString();
        if (isAddSubtract && leftBracketIndex == -1) {
            leftBracketIndex = 0;
        }
        if (this.courseString == null) {
            this.courseString = new StringBuilder(this.toString());
        }
        if (!isAddSubtract && leftBracketIndex > -1) {
            this.courseString.insert(this.leftBracketIndex, "(");
            this.courseString.append(")");
            this.leftBracketIndex = -1;
        }
        this.courseString.append(symbol);
        this.courseString.append(str);
    }

    @Override
    public int compareTo(EasyNumber o) {
        return this.decimal.compareTo(o.decimal);
    }

    /**
     * 判断值与目标对象的值是否相等
     * 支持的类型：int、long、float、double、String、BigInteger、BigDecimal、EasyNumber
     *
     * @param obj 目标对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        double value;
        if (obj instanceof Integer) {
            value = (int) obj;
        } else if (obj instanceof Long) {
            value = (long) obj;
        } else if (obj instanceof Float) {
            value = (float) obj;
        } else if (obj instanceof Double) {
            value = (double) obj;
        } else if (obj instanceof String) {
            value = Double.parseDouble((String) obj);
        } else if (obj instanceof BigInteger) {
            value = ((BigInteger) obj).doubleValue();
        } else if (obj instanceof BigDecimal) {
            value = ((BigDecimal) obj).doubleValue();
        } else if (obj instanceof EasyNumber) {
            value = ((EasyNumber) obj).toDouble();
        } else {
            return false;
        }
        return this.toDouble() == value;
    }

}
