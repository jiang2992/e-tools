import org.jiang.tools.model.RequestResult;
import org.jiang.tools.object.EasyResolver;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        System.out.println(easyResolver.get("$.$last.0.code"));
    }

}
