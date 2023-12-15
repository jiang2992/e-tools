package org.jiang.tools.date;

import org.jiang.tools.date.dict.TextDict;
import org.jiang.tools.exception.BadArgumentException;
import org.jiang.tools.exception.SystemException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 日期格式工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class DateFormatUtils {

    private static final String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final Map<BiFunction<Date, Date, Long>, Function<TextDict, String>> DIFF_TEXT_FUN_MAP = new LinkedHashMap<>(6);

    static {
        DIFF_TEXT_FUN_MAP.put(DateCalculateUtils::getFullYearDiff, TextDict::getYearAgo);
        DIFF_TEXT_FUN_MAP.put(DateCalculateUtils::getFullMonthDiff, TextDict::getMonthAgo);
        DIFF_TEXT_FUN_MAP.put(DateCalculateUtils::getFullDayDiff, TextDict::getDayAgo);
        DIFF_TEXT_FUN_MAP.put(DateCalculateUtils::getFullHourDiff, TextDict::getHourAgo);
        DIFF_TEXT_FUN_MAP.put(DateCalculateUtils::getFullMinDiff, TextDict::getMinAgo);
        DIFF_TEXT_FUN_MAP.put(DateCalculateUtils::getFullSecDiff, TextDict::getSecAgo);
    }

    /**
     * 日期字符串转日期对象
     * 使用默认格式（yyyy-MM-dd HH:mm:ss）进行转换
     *
     * @param dateStr 日期字符串
     * @return 日期对象
     */
    public static Date toDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        return parse(dateFormat, dateStr);
    }

    /**
     * 日期字符串转日期对象
     *
     * @param dateStr 日期字符串
     * @param pattern 转换格式
     * @return 日期对象
     */
    public static Date toDate(String dateStr, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return parse(dateFormat, dateStr);
    }

    /**
     * 日期对象转日期字符串
     * 使用默认格式（yyyy-MM-dd HH:mm:ss）进行转换
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String toString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT_PATTERN);
        return dateFormat.format(date);
    }

    /**
     * 日期对象转日期字符串
     *
     * @param date    日期对象
     * @param pattern 转换格式
     * @return 日期字符串
     */
    public static String toString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 日期对象转星期文本
     *
     * @param date     日期对象
     * @param textDict 文本字典
     * @return 星期文本
     */
    public static String toWeek(Date date, TextDict textDict) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return textDict.weekList()[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 获取日期差的可读文本
     *
     * @param currentDate 当前日期对象
     * @param date        目标日期对象
     * @param textDict    文本字典
     * @return 日期差可读文本
     */
    public static String toDiffText(Date currentDate, Date date, TextDict textDict) {
        for (Map.Entry<BiFunction<Date, Date, Long>, Function<TextDict, String>> entry : DIFF_TEXT_FUN_MAP.entrySet()) {
            Long diff = entry.getKey().apply(date, currentDate);
            if (diff > 0) {
                return String.format(entry.getValue().apply(textDict), diff);
            }
        }
        throw new BadArgumentException();
    }

    private static Date parse(SimpleDateFormat dateFormat, String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new SystemException(e);
        }
    }

}
