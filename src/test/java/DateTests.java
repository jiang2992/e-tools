import org.jiang.tools.date.DateCalculateUtils;
import org.jiang.tools.date.DateFormatUtils;
import org.jiang.tools.date.EasyDate;
import org.jiang.tools.date.dict.CnTextDict;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期相关测试类
 *
 * @author Bin
 * @since 1.0.0
 */
public class DateTests {

    @Test
    public void monthStartTime() {
        String str = EasyDate.now().monthStartDay().startTime().stringValue();
        System.out.println(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void yearEndTime() {
        String str = EasyDate.now().yearEndDay().endTime().stringValue();
        System.out.println(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void weekEndDay() {
        String str = EasyDate.now().weekEndDay().stringValue();
        System.out.println(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void yesterdayStartTime() {
        String str = EasyDate.now().yesterday().startTime().stringValue();
        System.out.println(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void extraDayOnBefore() {
        String str = EasyDate.now()
                .extra(calendar -> calendar.add(Calendar.DAY_OF_MONTH, -3))
                .startTime()
                .stringValue();
        System.out.println(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void addDays() {
        String str = EasyDate.now().addYear(1).addMonth(1).addWeek(1).weekStartDay().stringValue("yyyy年MM月dd日");
        System.out.println(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void toWeek() {
        String str = DateFormatUtils.toWeek(new Date(), new CnTextDict());
        System.out.println(str);
        Assert.assertNotNull(str);
    }

    @Test
    public void getWeekDiff() {
        Date startDate = EasyDate.now().yesterday().endTime().value();
        Date endDate = EasyDate.now().startTime().value();
        System.out.println(DateFormatUtils.toString(startDate));
        System.out.println(DateFormatUtils.toString(endDate));
        long diff = DateCalculateUtils.getDayDiff(startDate, endDate);
        System.out.println(diff);
    }

    @Test
    public void getDiffText() {
        Date currentDate = EasyDate.now().value();
        Date date = EasyDate.of(currentDate).addHour(-59).value();
        System.out.println(DateFormatUtils.toDiffText(currentDate, date, new CnTextDict()));
    }

    @Test
    public void test() {
// 获取今天开始时间
        EasyDate.now().startTime().value();

        // 获取今天结束时间
        EasyDate.now().endTime().value();

        // 获取昨天开始时间
        EasyDate.now().yesterday().startTime().value();

        // 获取昨天结束时间
        EasyDate.now().yesterday().endTime().value();

        // 获取7天前开始时间
        EasyDate.now().addDays(-7).startTime().value();

        // 获取7天后结束时间
        EasyDate.now().addDays(7).endTime().value();

        // 获取下个月第一天的开始时间
        EasyDate.now().addMonth(1).startTime().value();

        // 获取下个月最后一天的结束时间
        EasyDate.now().addMonth(1).endTime().value();

        // 获取本周第一天开始时间
        EasyDate.now().weekStartDay().startTime().value();

        // 获取本周最后一天结束时间
        EasyDate.now().weekEndDay().endTime().value();

        // 获取本月第一天的开始时间
        EasyDate.now().monthStartDay().startTime().value();

        // 获取本月最后一天的结束时间
        EasyDate.now().monthEndDay().endTime().value();

        // 获取本年第一天的开始时间
        EasyDate.now().yearStartDay().startTime().value();

        // 获取本年最后一天的结束时间
        EasyDate.now().yearEndDay().endTime().value();

        // 获取去年最后一个月第一天的开始时间
        EasyDate.now().addYear(-1).yearEndMonth().monthStartDay().startTime().value();

        // 获取明天的指定时间
        EasyDate.now().tomorrow().time("12:00").value();

        // 自定义额外设置时间
        EasyDate.now().yesterday().extra(c -> c.set(Calendar.HOUR_OF_DAY, 12));

        // 格式化时间
        EasyDate.of(new Date()).stringValue("yyyy-MM-dd");
    }

}
