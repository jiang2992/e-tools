package org.jiang.tools.verifycode;

import lombok.Getter;
import lombok.Setter;
import org.jiang.tools.canvas.ColorUtils;
import org.jiang.tools.canvas.EasyCanvas;
import org.jiang.tools.canvas.EasyCanvasUtils;
import org.jiang.tools.canvas.EasyFont;
import org.jiang.tools.canvas.element.EasyLine;
import org.jiang.tools.canvas.element.EasyText;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片验证码工厂
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class ImageCodeFactory {

    private Integer width = 240;

    private Integer height = 80;

    /**
     * 复杂度：最高100
     */
    private Integer complexity = 50;

    public ImageCodeFactory() {
    }

    public ImageCodeFactory(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public ImageCodeFactory(Integer width, Integer height, Integer complexity) {
        this.width = width;
        this.height = height;
        this.complexity = complexity;
    }

    public ImageVerifyCode generate(String code) {
        Random random = new Random();
        EasyCanvas easyCanvas = new EasyCanvas(width, height, ColorUtils.getRandRandom(200, 250));
        int fontSize = width / code.length();
        for (int i = 0; i < code.length(); i++) {
            EasyFont easyFont = new EasyFont(fontSize);
            int rotate = random.nextInt(90) - 45;
            easyFont.setRotate(rotate);
            EasyText easyText = new EasyText((int) (fontSize * i * 0.8 + width * 0.1), height / 2 - fontSize / 2 - rotate / 3, String.valueOf(code.charAt(i)), easyFont);
            easyText.setColor(ColorUtils.getRandRandom(20, 130));
            easyCanvas.add(easyText);
        }
        //绘制干扰线
        float noisyPointLine = width * height * 0.0001f * complexity;
        Color lineColor = ColorUtils.getRandRandom(160, 200);
        for (int i = 0; i < noisyPointLine; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int ex = random.nextInt(width - 1);
            int ey = random.nextInt(height - 1);
            easyCanvas.add(new EasyLine(x, y, ex, ey, lineColor));
        }
        BufferedImage bufferedImage = EasyCanvasUtils.write(easyCanvas);
        // 绘制干扰点
        float noisyPointCount = width * height * 0.002f * complexity;
        for (int i = 0; i < noisyPointCount; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            bufferedImage.setRGB(x, y, ColorUtils.getRandomInt());
        }
        return new ImageVerifyCode(code, bufferedImage);
    }

}
