import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.jiang.tools.model.RequestResult;
import org.jiang.tools.object.ConvertUtils;
import org.jiang.tools.object.EasyResolver;
import org.junit.Test;

/**
 * 对象测试类
 *
 * @author Bin
 * @since 1.1.3
 */
public class ObjectTests {

    /**
     * 简单解析
     */
    @Test
    public void resolverTest() {
        System.out.println(EasyResolver.verifyExp("$.abc.ccc"));
        System.out.println(EasyResolver.verifyExp("$.1.0.$first"));
        System.out.println(EasyResolver.verifyExp("$"));
        System.out.println(EasyResolver.verifyExp("$."));

        List<Object> list = Arrays.asList("Hello", "World", Arrays.asList(RequestResult.success(), RequestResult.fail()));
        EasyResolver easyResolver = EasyResolver.of(list);
        System.out.println(easyResolver.get("$"));
        System.out.println(easyResolver.get("$.1"));
        System.out.println(easyResolver.get("$.$last.0.code"));
        System.out.println(easyResolver.get("$.$last.1.code"));
    }

    @Test
    public void test() {
//        System.out.println(ConvertUtils.to(new String[]{"aaa", "bbb","aaa"}, List.class));
//        System.out.println(ConvertUtils.to(new String[]{"aaa", "bbb","aaa"}, HashSet.class));
//        System.out.println(ConvertUtils.to(Arrays.asList("aaa","bbb","aaa"), HashSet.class));
        String[] strings = ConvertUtils.to(Arrays.asList(1,2,3), String[].class);
        System.out.println(strings);
    }

}
