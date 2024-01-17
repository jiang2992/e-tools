import org.jiang.tools.text.StringUtils;
import org.junit.Test;

/**
 * 字符串相关测试类
 *
 * @author Bin
 * @since 1.1.0
 */
public class StringTests {

    @Test
    public void humpToLine() {
        String str = StringUtils.humpToLine("sysUserRole");
        System.out.println(str);
    }

}
