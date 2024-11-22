package org.jiang.tools.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * EasyDate：提供日期对象的流式转换
 *
 * @author Bin
 * @since 1.0.0
 */
public class EasyDate {

    private Calendar calendar;
    private final Queue<Function<Calendar, Calendar>> process;

    public EasyDate(Date date) {
        this.calendar = this.createCalendar(date);
        this.process = new LinkedList<>();
    }

    private Calendar createCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar;
    }

    public static EasyDate of(Date date) {
        return new EasyDate(date);
    }

    public static EasyDate of(LocalDate localDate) {
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return of(date);
    }

    public static EasyDate of(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return of(date);
    }

    public static EasyDate of(int ms) {
        return of(new Date(ms));
    }

    public static EasyDate of(String dateStr) {
        Date date = DateFormatUtils.toDate(dateStr);
        return of(date);
    }

    public static EasyDate of(String dateStr, String pattern) {
        Date date = DateFormatUtils.toDate(dateStr, pattern);
        return of(date);
    }

    public boolean isEmpty() {
        return this.calendar == null;
    }

    private Calendar execute() {
        if (this.isEmpty()) {
            return null;
        }
        Calendar calendar = this.calendar;
        while (!process.isEmpty()) {
            calendar = process.poll().apply(calendar);
        }
        this.calendar = calendar;
        return calendar;
    }

    /**
     * 将当前日期作为参数创建
     *
     * @return EasyDate
     */
    public static EasyDate now() {
        return of(new Date());
    }

    /**
     * 获取执行后的日期
     *
     * @return 日期对象
     */
    public Date value() {
        Calendar calendar = this.execute();
        if (calendar == null) {
            return null;
        }
        return new Date(calendar.getTime().getTime());
    }

    /**
     * 获取执行后的日期
     *
     * @return 日期对象
     */
    public LocalDateTime localValue() {
        Calendar calendar = this.execute();
        if (calendar == null) {
            return null;
        }
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取执行后的日期
     *
     * @return 日期对象
     */
    public LocalDate localDateValue() {
        return this.localValue().toLocalDate();
    }

    /**
     * 获取执行后的日期字符串
     *
     * @return 日期字符串
     */
    public String stringValue() {
        return DateFormatUtils.toString(this.value());
    }

    /**
     * 获取执行后的日期字符串
     *
     * @param pattern 字符串格式
     * @return 日期字符串
     */
    public String stringValue(String pattern) {
        return DateFormatUtils.toString(this.value(), pattern);
    }

    /**
     * 设置为：当天开始时间
     *
     * @return EasyDate
     */
    public EasyDate startTime() {
        process.offer(CalendarUtils::startTime);
        return this;
    }

    /**
     * 设置为：当天结束时间
     *
     * @return EasyDate
     */
    public EasyDate endTime() {
        process.offer(CalendarUtils::endTime);
        return this;
    }

    /**
     * 设置为：当周的第一天
     *
     * @return EasyDate
     */
    public EasyDate weekStartDay() {
        process.offer(CalendarUtils::weekStartDay);
        return this;
    }

    /**
     * 设置为：当周的最后一天
     *
     * @return EasyDate
     */
    public EasyDate weekEndDay() {
        process.offer(CalendarUtils::weekEndDay);
        return this;
    }

    /**
     * 设置为：当月的第一天
     *
     * @return EasyDate
     */
    public EasyDate monthStartDay() {
        process.offer(CalendarUtils::monthStartDay);
        return this;
    }

    /**
     * 设置为：当月的最后一天
     *
     * @return EasyDate
     */
    public EasyDate monthEndDay() {
        process.offer(CalendarUtils::monthEndDay);
        return this;
    }

    /**
     * 设置为：当年的第一天
     *
     * @return EasyDate
     */
    public EasyDate yearStartDay() {
        process.offer(CalendarUtils::yearStartDay);
        return this;
    }

    /**
     * 设置为：当年的最后一天
     *
     * @return EasyDate
     */
    public EasyDate yearEndDay() {
        process.offer(CalendarUtils::yearEndDay);
        return this;
    }

    /**
     * 设置为：当年的第一个月
     *
     * @return EasyDate
     */
    public EasyDate yearStartMonth() {
        process.offer(CalendarUtils::yearStartMonth);
        return this;
    }

    /**
     * 设置为：当年的最后一个月
     *
     * @return EasyDate
     */
    public EasyDate yearEndMonth() {
        process.offer(CalendarUtils::yearEndMonth);
        return this;
    }

    /**
     * 设置时间
     *
     * @param time 时间字符串（格式：10:24:00 / 10:24 ）
     * @return EasyDate
     */
    public EasyDate time(String time) {
        String[] split = time.split(":");
        return this.time(Integer.parseInt(split[0]), Integer.parseInt(split[1]), split.length >= 3 ? Integer.parseInt(split[2]) : 0);
    }

    /**
     * 设置时间
     *
     * @param hour 时
     * @param min  分
     * @param sec  秒
     * @return EasyDate
     */
    public EasyDate time(int hour, int min, int sec) {
        return this.extra(c -> {
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, min);
            c.set(Calendar.SECOND, sec);
        });
    }

    /**
     * 添加秒数
     *
     * @param offset 秒数，为负数时减少
     * @return EasyDate
     */
    public EasyDate addSec(int offset) {
        process.offer(c -> CalendarUtils.addSec(c, offset));
        return this;
    }

    /**
     * 添加分钟数
     *
     * @param offset 分钟数，为负数时减少
     * @return EasyDate
     */
    public EasyDate addMin(int offset) {
        process.offer(c -> CalendarUtils.addMin(c, offset));
        return this;
    }

    /**
     * 添加小时数
     *
     * @param offset 小时数，为负数时减少
     * @return EasyDate
     */
    public EasyDate addHour(int offset) {
        process.offer(c -> CalendarUtils.addHour(c, offset));
        return this;
    }

    /**
     * 添加日期天数
     *
     * @param offset 天数，为负数时减少
     * @return EasyDate
     */
    public EasyDate addDays(int offset) {
        process.offer(c -> CalendarUtils.addDays(c, offset));
        return this;
    }

    /**
     * 添加日期周数
     *
     * @param offset 周数，为负数时减少
     * @return EasyDate
     */
    public EasyDate addWeek(int offset) {
        process.offer(c -> CalendarUtils.addWeek(c, offset));
        return this;
    }

    /**
     * 添加日期月数
     *
     * @param offset 月数，为负数时减少
     * @return EasyDate
     */
    public EasyDate addMonth(int offset) {
        process.offer(c -> CalendarUtils.addMonth(c, offset));
        return this;
    }

    /**
     * 添加日期年数
     *
     * @param offset 年数，为负数时减少
     * @return EasyDate
     */
    public EasyDate addYear(int offset) {
        process.offer(c -> CalendarUtils.addYear(c, offset));
        return this;
    }

    /**
     * 设置为：昨天
     *
     * @return EasyDate
     */
    public EasyDate yesterday() {
        process.offer((c) -> CalendarUtils.addDays(c, -1));
        return this;
    }

    /**
     * 设置为：明天
     *
     * @return EasyDate
     */
    public EasyDate tomorrow() {
        process.offer((c) -> CalendarUtils.addDays(c, 1));
        return this;
    }

    /**
     * 自定义额外设置
     *
     * @param consumer 自定义函数
     * @return EasyDate
     */
    public EasyDate extra(Consumer<Calendar> consumer) {
        process.offer(c -> {
            consumer.accept(c);
            return c;
        });
        return this;
    }

}
