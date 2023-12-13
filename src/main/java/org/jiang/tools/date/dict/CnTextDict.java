package org.jiang.tools.date.dict;

/**
 * 星期相关字典（中文）
 *
 * @author Bin
 * @date 2021/6/18 09:56
 */
public class CnTextDict extends TextDict {

    @Override
    public String getSun() {
        return "星期日";
    }

    @Override
    public String getMon() {
        return "星期一";
    }

    @Override
    public String getTue() {
        return "星期二";
    }

    @Override
    public String getWed() {
        return "星期三";
    }

    @Override
    public String getThu() {
        return "星期四";
    }

    @Override
    public String getFri() {
        return "星期五";
    }

    @Override
    public String getSat() {
        return "星期六";
    }

    @Override
    public String getSecAgo() {
        return "%s秒前";
    }

    @Override
    public String getMinAgo() {
        return "%s分钟前";
    }

    @Override
    public String getHourAgo() {
        return "%s小时前";
    }

    @Override
    public String getDayAgo() {
        return "%s天前";
    }

    @Override
    public String getWeekAgo() {
        return "%s周前";
    }

    @Override
    public String getMonthAgo() {
        return "%s个月前";
    }

    @Override
    public String getYearAgo() {
        return "%s年前";
    }

}
