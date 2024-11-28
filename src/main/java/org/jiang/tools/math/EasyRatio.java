package org.jiang.tools.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.jiang.tools.exception.DataStatusException;

/**
 * EasyRatio: 提供比率计算相关方法
 *
 * @author Bin
 * @since 1.1.4
 */
public class EasyRatio {

    /**
     * 比率总数
     */
    private static final BigDecimal RATIO_TOTAL = new BigDecimal(100);

    /**
     * 总数值
     */
    private BigDecimal total;

    /**
     * 剩余数值
     */
    private BigDecimal remained;

    /**
     * 剩余的比率
     */
    private BigDecimal remainedRatio;

    /**
     * 精度
     */
    private int scale = 2;

    public EasyRatio(BigDecimal total) {
        init(total);
    }

    /**
     * 初始化相关变量
     *
     * @param total 总数
     */
    private void init(BigDecimal total) {
        this.total = total;
        this.remained = new BigDecimal(total.toString());
        this.remainedRatio = new BigDecimal(RATIO_TOTAL.toString()).setScale(scale, RoundingMode.DOWN);
    }

    /**
     * 从long类型总数构造对象
     *
     * @param total 总数
     * @return EasyRatio
     */
    public static EasyRatio of(long total) {
        return of(new BigDecimal(total));
    }

    /**
     * 从int类型总数构造对象
     *
     * @param total 总数
     * @return EasyRatio
     */
    public static EasyRatio of(int total) {
        return of(new BigDecimal(total));
    }

    /**
     * 从double类型总数构造对象
     *
     * @param total 总数
     * @return EasyRatio
     */
    public static EasyRatio of(double total) {
        return of(new BigDecimal(total));
    }

    /**
     * 从float类型总数构造对象
     *
     * @param total 总数
     * @return EasyRatio
     */
    public static EasyRatio of(float total) {
        return of(new BigDecimal(total));
    }

    /**
     * 从BigDecimal类型总数构造对象
     *
     * @param total 总数
     * @return EasyRatio
     */
    public static EasyRatio of(BigDecimal total) {
        return new EasyRatio(total);
    }

    /**
     * 增加指定数值到总数
     *
     * @param total 总数
     * @return EasyRatio
     */
    public EasyRatio addTotal(long total) {
        return addTotal(new BigDecimal(total));
    }

    /**
     * 增加指定数值到总数
     *
     * @param total 总数
     * @return EasyRatio
     */
    public EasyRatio addTotal(int total) {
        return addTotal(new BigDecimal(total));
    }

    /**
     * 增加指定数值到总数
     *
     * @param total 总数
     * @return EasyRatio
     */
    public EasyRatio addTotal(double total) {
        return addTotal(new BigDecimal(total));
    }

    /**
     * 增加指定数值到总数
     *
     * @param total 总数
     * @return EasyRatio
     */
    public EasyRatio addTotal(float total) {
        return addTotal(new BigDecimal(total));
    }

    /**
     * 增加指定数值到总数
     *
     * @param total 总数
     * @return EasyRatio
     */
    public EasyRatio addTotal(BigDecimal total) {
        if (remainedRatio.compareTo(RATIO_TOTAL) < 0) {
            throw new DataStatusException("this ratio has already been used");
        }
        init(this.total.add(total));
        return this;
    }

    /**
     * 设置比率的精度，默认为2 支持的精度最大为4
     *
     * @param scale 精度
     * @return EasyRatio
     */
    public EasyRatio setScale(int scale) {
        this.scale = Math.min(scale, 4);
        return this;
    }

    /**
     * 取出一个比率
     *
     * @param value 数值
     * @return 比率
     * @see EasyRatio#take(BigDecimal)
     */
    public float take(long value) {
        return take(new BigDecimal(value));
    }

    /**
     * 取出一个比率
     *
     * @param value 数值
     * @return 比率
     * @see EasyRatio#take(BigDecimal)
     */
    public float take(int value) {
        return take(new BigDecimal(value));
    }

    /**
     * 取出一个比率
     *
     * @param value 数值
     * @return 比率
     * @see EasyRatio#take(BigDecimal)
     */
    public float take(double value) {
        return take(new BigDecimal(value));
    }

    /**
     * 取出一个比率
     *
     * @param value 数值
     * @return 比率
     * @see EasyRatio#take(BigDecimal)
     */
    public float take(float value) {
        return take(new BigDecimal(value));
    }

    /**
     * 将数值与总数计算出一个比率并取出，向下取舍 当最后一个比率被取出时，会返回所有剩余的比率，以保证总比率为 RATIO_TOTAL 如果调用该方法时，剩余的比率为0，则返回0
     *
     * @param value 数值
     * @return 比率
     */
    public float take(BigDecimal value) {
        if (remainedRatio.floatValue() == 0 || remained.floatValue() <= 0) {
            return 0;
        }
        if (value.compareTo(remained) >= 0) {
            float ratio = remainedRatio.floatValue();
            remainedRatio = new BigDecimal(0);
            return ratio;
        }
        BigDecimal ratioDecimal = value.multiply(RATIO_TOTAL).divide(total, scale, RoundingMode.DOWN);
        remained = remained.subtract(value);
        remainedRatio = remainedRatio.subtract(ratioDecimal).setScale(scale, RoundingMode.DOWN);
        return ratioDecimal.floatValue();
    }

    /**
     * 判断是否有剩下比率
     *
     * @return 是否存在
     */
    public boolean hasRemained() {
        return this.remainedRatio() > 0;
    }

    /**
     * 获取总数
     *
     * @return 数值
     */
    public BigDecimal total() {
        return this.total;
    }

    /**
     * 获取剩余的数值
     *
     * @return 数值
     */
    public BigDecimal remained() {
        return this.remained;
    }

    /**
     * 获取剩余的比率
     *
     * @return 比率
     */
    public float remainedRatio() {
        return this.remainedRatio.floatValue();
    }

}
