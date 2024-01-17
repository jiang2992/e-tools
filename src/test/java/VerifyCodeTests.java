import org.jiang.tools.verifycode.ImageCodeFactory;
import org.jiang.tools.verifycode.ImageVerifyCode;
import org.jiang.tools.verifycode.StringCodeUtils;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * 验证码相关测试类
 *
 * @author Bin
 * @since 1.0.0
 */
public class VerifyCodeTests {

    @Test
    public void test() throws IOException {
        ImageCodeFactory imageCodeFactory = new ImageCodeFactory();
        imageCodeFactory.setComplexity(60);
        ImageVerifyCode verifyCode = imageCodeFactory.generate(StringCodeUtils.generateNumber(4));
        File file = new File("/Users/bin/WorkSpace/Test/canvas.png");
        ImageIO.write(verifyCode.getImage(),"png",file);
    }

}
