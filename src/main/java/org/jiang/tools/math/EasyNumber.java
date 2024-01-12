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
public class EasyNumber {

    private final static String SYMBOL_ADD = "+";
    private final static String SYMBOL_SUBTRACT = "-";
    private final static String SYMBOL_MULTIPLY = "×";
    private final static String SYMBOL_DIVIDE = "÷";
    private final static String SYMBOL_REMAINDER = "%";

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

}
