package org.jiang.tools.date;

import java.util.Calendar;

/**
 * 日历工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class CalendarUtils {

    public static Calendar startTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar endTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    public static Calendar weekStartDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar;
    }

    public static Calendar weekEndDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar;
    }

    public static Calendar monthStartDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public static Calendar monthEndDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    public static Calendar yearStartDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar;
    }

    public static Calendar yearEndDay(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return calendar;
    }

    public static Calendar yearStartMonth(Calendar calendar) {
        calendar.set(Calendar.MONTH, 0);
        return calendar;
    }

    public static Calendar yearEndMonth(Calendar calendar) {
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        return calendar;
    }

    public static Calendar addSec(Calendar calendar, int offset) {
        calendar.add(Calendar.SECOND, offset);
        return calendar;
    }

    public static Calendar addMin(Calendar calendar, int offset) {
        calendar.add(Calendar.MINUTE, offset);
        return calendar;
    }

    public static Calendar addHour(Calendar calendar, int offset) {
        calendar.add(Calendar.HOUR_OF_DAY, offset);
        return calendar;
    }

    public static Calendar addDays(Calendar calendar, int offset) {
        calendar.add(Calendar.DAY_OF_MONTH, offset);
        return calendar;
    }

    public static Calendar addWeek(Calendar calendar, int offset) {
        calendar.add(Calendar.WEEK_OF_YEAR, offset);
        return calendar;
    }

    public static Calendar addMonth(Calendar calendar, int offset) {
        calendar.add(Calendar.MONTH, offset);
        return calendar;
    }

    public static Calendar addYear(Calendar calendar, int offset) {
        calendar.add(Calendar.YEAR, offset);
        return calendar;
    }

}
