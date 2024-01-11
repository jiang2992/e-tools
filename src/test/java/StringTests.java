import org.jiang.tools.text.StringUtils;
import org.junit.Test;

public class StringTests {

    @Test
    public void humpToLine() {
        String str = StringUtils.humpToLine("sysUserRole");
        System.out.println(str);
    }

}
