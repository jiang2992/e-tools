package org.jiang.tools.date;

import org.jiang.tools.constants.TimeConstants;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期计算工具类
 *
 * @author Bin
 * @date 2023/12/12 09:44
 */
public class DateCalculateUtils {

    /**
     * 计算两个时间相差天数
     * 注意：其他更小的单位不参与计算
     * 例：昨天 23:59:00 与 今天 00:00:01 的计算结果为 1
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差天数
     */
    public static long getDayDiff(Date startDate, Date endDate) {
        LocalDate startDateLD = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateLD = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(startDateLD, endDateLD);
    }

    /**
     * 计算两个时间相差月数
     * 注意：其他更小的单位不参与计算
     * 例：2022-04-30 与 2022-05-01 的计算结果为 1
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差月数
     */
    public static long getMonthDiff(Date startDate, Date endDate) {
        LocalDate startDateLD = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateLD = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        YearMonth startMonth = YearMonth.from(startDateLD);
        YearMonth endMonth = YearMonth.from(endDateLD);
        return ChronoUnit.MONTHS.between(startMonth, endMonth);
    }

    /**
     * 计算两个时间相差年数
     * 注意：其他更小的单位不参与计算
     * 例：2022-12-29 与 2023-01-01 的计算结果为 1
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差年数
     */
    public static long getYearDiff(Date startDate, Date endDate) {
        LocalDate startDateLD = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateLD = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Year startMonth = Year.from(startDateLD);
        Year endMonth = Year.from(endDateLD);
        return ChronoUnit.YEARS.between(startMonth, endMonth);
    }

    /**
     * 计算两个时间相差秒数
     * 完整计算，不足1秒则忽略
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差秒数
     */
    public static long getFullSecDiff(Date startDate, Date endDate) {
        long diffMs = endDate.getTime() - startDate.getTime();
        long diff = diffMs / TimeConstants.SECOND_S;
        return (int) diff;
    }

    /**
     * 计算两个时间相差分钟数
     * 完整计算，不足1分钟则忽略
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差分钟数
     */
    public static long getFullMinDiff(Date startDate, Date endDate) {
        long diffMs = endDate.getTime() - startDate.getTime();
        long diff = diffMs / TimeConstants.MINUTE_M;
        return (int) diff;
    }

    /**
     * 计算两个时间相差小时数
     * 完整计算，不足1小时则忽略
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差小时数
     */
    public static long getFullHourDiff(Date startDate, Date endDate) {
        long diffMs = endDate.getTime() - startDate.getTime();
        long diff = diffMs / TimeConstants.HOUR_M;
        return (int) diff;
    }

    /**
     * 计算两个时间相差天数
     * 完整计算，不足1天则忽略
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差天数
     */
    public static long getFullDayDiff(Date startDate, Date endDate) {
        long diffMs = endDate.getTime() - startDate.getTime();
        long diff = diffMs / TimeConstants.DAY_M;
        return (int) diff;
    }

    /**
     * 计算两个时间相差周数
     * 完整计算，不足1周则忽略
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差周数
     */
    public static long getFullWeekDiff(Date startDate, Date endDate) {
        long diffMs = endDate.getTime() - startDate.getTime();
        long diff = diffMs / TimeConstants.WEEK_M;
        return (int) diff;
    }

    /**
     * 计算两个时间相差月数
     * 完整计算，不足1月则忽略
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差月数
     */
    public static long getFullMonthDiff(Date startDate, Date endDate) {
        long diffMs = endDate.getTime() - startDate.getTime();
        long diff = diffMs / TimeConstants.MONTH_M;
        return (int) diff;
    }

    /**
     * 计算两个时间相差年数
     * 完整计算，不足1年则忽略
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 相差年数
     */
    public static long getFullYearDiff(Date startDate, Date endDate) {
        long diffMs = endDate.getTime() - startDate.getTime();
        long diff = diffMs / TimeConstants.YEAR_M;
        return (int) diff;
    }

}
