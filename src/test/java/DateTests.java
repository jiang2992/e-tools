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
 * @date 2020/12/11 16:24
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

}
