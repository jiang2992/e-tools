package org.jiang.tools.canvas;

import org.jiang.tools.canvas.element.*;
import org.jiang.tools.exception.SystemException;
import org.jiang.tools.canvas.image.ImageDrawPosition;
import org.jiang.tools.canvas.image.ImageModeUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * EasyCanvas 工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public final class EasyCanvasUtils {

    /**
     * 将画板内容写入图片缓冲区
     *
     * @param easyCanvas 画板
     * @return 图片缓冲区
     */
    public static BufferedImage write(EasyCanvas easyCanvas) {
        BufferedImage bufferedImage = new BufferedImage(easyCanvas.getWidth(), easyCanvas.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 绘制背景
        EasyRect backgroundRect = EasyRect.buildFill(0, 0, easyCanvas.getWidth(), easyCanvas.getHeight(), easyCanvas.getBackgroundColor());
        drawRect(bufferedImage, backgroundRect);

        // 绘制元素
        for (EasyElement element : easyCanvas.getElements()) {
            bufferedImage = draw(bufferedImage, element);
        }

        return bufferedImage;
    }

    /**
     * 将画板内容写入输出流
     *
     * @param easyCanvas   画板
     * @param outputStream 输出流
     * @param formatName   格式名称(png,jpg)
     * @throws IOException IO异常
     */
    public static void write(EasyCanvas easyCanvas, OutputStream outputStream, String formatName) throws IOException {
        BufferedImage bufferedImage = write(easyCanvas);
        ImageIO.write(bufferedImage, formatName, outputStream);
    }

    /**
     * 将画板内容写入硬盘文件
     *
     * @param easyCanvas 画板
     * @param file       文件
     * @param formatName 格式名称(png,jpg)
     * @throws IOException IO异常
     */
    public static void write(EasyCanvas easyCanvas, File file, String formatName) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        write(easyCanvas, outputStream, formatName);
    }

    /**
     * 绘制元素
     *
     * @param bufferedImage 图片缓冲区
     * @param element       元素
     * @return 图片缓冲区
     */
    public static BufferedImage draw(BufferedImage bufferedImage, EasyElement element) {
        BufferedImage result;
        if (element instanceof EasyImage) {
            result = drawImage(bufferedImage, (EasyImage) element);
        } else if (element instanceof EasyText) {
            result = drawText(bufferedImage, (EasyText) element);
        } else if (element instanceof EasyRect) {
            result = drawRect(bufferedImage, (EasyRect) element);
        } else if (element instanceof EasyLine) {
            result = drawLine(bufferedImage, (EasyLine) element);
        } else {
            throw new SystemException("element type is unanticipated");
        }
        return result;
    }

    /**
     * 绘制图片
     *
     * @param bufferedImage 图片缓冲区
     * @param element       元素
     * @return 图片缓冲区
     */
    private static BufferedImage drawImage(BufferedImage bufferedImage, EasyImage element) {
        if (element.getImage() == null) {
            throw new SystemException("element image is null");
        }

        ImageDrawPosition position = ImageModeUtils.fit(element);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawImage(element.getImage(),
                position.getStartX(), position.getStartY(),
                position.getEndX(), position.getEndY(),
                position.getClipStartX(), position.getClipStartY(),
                position.getClipEndX(), position.getClipEndY(),
                null);

        return bufferedImage;
    }

    /**
     * 绘制文本
     *
     * @param bufferedImage 图片缓冲区
     * @param element       元素
     * @return 图片缓冲区
     */
    private static BufferedImage drawText(BufferedImage bufferedImage, EasyText element) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(element.getColor());
        Font font = element.getFont().toFont();
        graphics.setFont(font);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        String content = element.getContent() != null ? element.getContent() : "";

        if (element instanceof EasyTextArea) {
            EasyTextArea textArea = (EasyTextArea) element;
            char[] chars = content.toCharArray();

            int lineHeight = (int) (textArea.getFont().getSize() * 1.5);
            StringBuilder line = new StringBuilder();
            List<StringBuilder> lines = new ArrayList<>();
            lines.add(line);

            for (char c : chars) {
                line.append(c);
                int textWidth = graphics.getFontMetrics().stringWidth(line.toString());
                if (textWidth > textArea.getWidth()) {
                    line.deleteCharAt(line.length() - 1);
                    if ((lines.size() + 1) * lineHeight > textArea.getHeight()) {
                        line.delete(line.length() - 3, line.length());
                        line.append("...");
                        break;
                    }
                    line = new StringBuilder();
                    line.append(c);
                    lines.add(line);
                }
            }

            for (int i = 0; i < lines.size(); i++) {
                // 计算文本对齐方式
                int textWidth = graphics.getFontMetrics().stringWidth(line.toString());
                int x = textArea.getX();
                switch (textArea.getAlign()) {
                    case 1:
                        x = (textArea.getWidth() - textWidth) / 2;
                        break;
                    case 2:
                        x = textArea.getWidth() - textWidth;
                        break;
                    default:
                        break;
                }
                graphics.drawString(lines.get(i).toString(), x, (textArea.getY()) + (i * lineHeight) + element.getFont().getSize());
            }
        } else {
            graphics.drawString(content, element.getX(), element.getY() + element.getFont().getSize());
        }

        return bufferedImage;
    }

    /**
     * 绘制矩形
     *
     * @param bufferedImage 图片缓冲区
     * @param element       元素
     * @return 图片缓冲区
     */
    private static BufferedImage drawRect(BufferedImage bufferedImage, EasyRect element) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(element.getColor());
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (element.getMode() == EasyRect.Mode.DRAW) {
            graphics.drawRect(element.getX(), element.getY(), element.getWidth(), element.getHeight());
        } else {
            graphics.fillRect(element.getX(), element.getY(), element.getWidth(), element.getHeight());
        }
        return bufferedImage;
    }

    /**
     * 绘制线条
     *
     * @param bufferedImage 图片缓冲区
     * @param element       元素
     * @return 图片缓冲区
     */
    private static BufferedImage drawLine(BufferedImage bufferedImage, EasyLine element) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(element.getColor());
        graphics.drawLine(element.getX(), element.getY(), element.getEx(), element.getEy());
        return bufferedImage;
    }

}
