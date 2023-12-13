package org.jiang.tools.date.dict;

/**
 * 星期相关字典
 *
 * @author Bin
 * @date 2021/6/18 09:47
 */
public abstract class TextDict {

    private String[] weekList;

    /**
     * 星期日
     *
     * @return 字典值
     */
    abstract public String getSun();

    /**
     * 星期一
     *
     * @return 字典值
     */
    abstract public String getMon();

    /**
     * 星期二
     *
     * @return 字典值
     */
    abstract public String getTue();

    /**
     * 星期三
     *
     * @return 字典值
     */
    abstract public String getWed();

    /**
     * 星期四
     *
     * @return 字典值
     */
    abstract public String getThu();

    /**
     * 星期五
     *
     * @return 字典值
     */
    abstract public String getFri();

    /**
     * 星期六
     *
     * @return 字典值
     */
    abstract public String getSat();

    /**
     * 在几秒钟之前
     * 使用 %s 占位符作为具体秒数
     *
     * @return 字典值
     */
    abstract public String getSecAgo();

    /**
     * 在几分钟之前
     * 使用 %s 占位符作为具体分钟
     *
     * @return 字典值
     */
    abstract public String getMinAgo();

    /**
     * 在几小时之前
     * 使用 %s 占位符作为具体小时
     *
     * @return 字典值
     */
    abstract public String getHourAgo();

    /**
     * 在几天之前
     * 使用 %s 占位符作为具体天数
     *
     * @return 字典值
     */
    abstract public String getDayAgo();

    /**
     * 在几周之前
     * 使用 %s 占位符作为具体周数
     *
     * @return 字典值
     */
    abstract public String getWeekAgo();

    /**
     * 在几个月之前
     * 使用 %s 占位符作为具体月数
     *
     * @return 字典值
     */
    abstract public String getMonthAgo();

    /**
     * 在几年之前
     * 使用 %s 占位符作为具体年数
     *
     * @return 字典值
     */
    abstract public String getYearAgo();

    /**
     * 星期列表（周日到周六）
     *
     * @return 星期列表
     */
    public String[] weekList() {
        if (weekList == null) {
            weekList = new String[]{getSun(), getMon(), getTue(), getWed(), getThu(), getFri(), getSat()};
        }
        return weekList;
    }

}
