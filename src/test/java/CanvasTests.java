import org.jiang.tools.canvas.EasyCanvas;
import org.jiang.tools.canvas.EasyCanvasUtils;
import org.jiang.tools.canvas.EasyFont;
import org.jiang.tools.canvas.element.EasyRect;
import org.jiang.tools.canvas.element.EasyText;
import org.jiang.tools.canvas.image.EasyImageFactory;
import org.jiang.tools.canvas.image.ImageMode;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Canvas相关测试类
 *
 * @author Bin
 * @since 1.0.0
 */
public class CanvasTests {

    @Test
    public void test() throws IOException {
        EasyFont easyFont = new EasyFont(28);

        EasyCanvas easyCanvas = new EasyCanvas(1000, 1000);
        easyCanvas.add(EasyImageFactory.buildLocalImage(
                "/Users/bin/Downloads/background.jpg",
                10,
                10,
                1000,
                1000,
                ImageMode.FILL
        ));
        easyCanvas.add(new EasyText(
                320,
                38,
                "测试文本",
                Color.cyan,
                easyFont
        ));
        easyCanvas.add(new EasyRect(
                400,
                400,
                100,
                100,
                Color.cyan,
                EasyRect.Mode.DRAW
        ));
        File file = new File("/Users/bin/WorkSpace/Test/canvas.png");
        EasyCanvasUtils.write(easyCanvas, file, "png");
    }

}
