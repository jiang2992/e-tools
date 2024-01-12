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
    public void test(){
        // 获取昨日开始时间
        System.out.println(EasyDate.now().yesterday().startTime().value());

        // 获取7天前的结束时间
        System.out.println(EasyDate.now().addDays(-7).endTime().value());

        // 获取某一年的最后一天
        System.out.println(EasyDate.of("2020-05-01 12:00:00").yearEndDay().value());

        // 获取本月的第一天的12:00
        System.out.println(EasyDate.now().monthStartDay().time("12:00").value());

        // 获取格式化日期字符串
        System.out.println(EasyDate.now().endTime().stringValue("HH:mm:ss"));

        // 自定义额外操作
        System.out.println(EasyDate.now().extra(c -> c.set(Calendar.MONTH, 1)).endTime().value());
    }

}
