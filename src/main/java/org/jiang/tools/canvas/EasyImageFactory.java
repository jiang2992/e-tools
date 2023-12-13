package org.jiang.tools.canvas;

import org.jiang.tools.image.ImageMode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 图片元素工厂类
 *
 * @author Bin
 * @date 2020/12/14 17:15
 */
public final class EasyImageFactory {

    /**
     * 从网络图片构造元素
     *
     * @param url 图片url
     * @param x   元素位置x
     * @param y   元素位置y
     * @return 图片元素
     * @throws IOException IO异常
     */
    public static EasyImage buildNetworkImage(String url, int x, int y) throws IOException {
        return buildNetworkImage(url, x, y, 0, 0, ImageMode.NONE);
    }

    /**
     * 从网络图片构造元素
     *
     * @param url    图片url
     * @param x      元素位置x
     * @param y      元素位置y
     * @param width  元素宽度
     * @param height 元素高度
     * @param mode   图片显示模式
     * @return 图片元素
     * @throws IOException IO异常
     */
    public static EasyImage buildNetworkImage(String url, int x, int y, int width, int height, int mode) throws IOException {
        Image image = ImageIO.read(new URL(url));
        return build(image, x, y, width, height, mode);
    }

    /**
     * 从本地图片构造元素
     *
     * @param url  本地路径
     * @param x    元素位置x
     * @param y    元素位置y
     * @param mode 图片显示模式
     * @return 图片元素
     * @throws IOException IO异常
     */
    public static EasyImage buildLocalImage(String url, int x, int y, int mode) throws IOException {
        return buildLocalImage(url, x, y, 0, 0, mode);
    }

    /**
     * 从本地图片构造元素
     *
     * @param url    本地路径
     * @param x      元素位置x
     * @param y      元素位置y
     * @param width  元素宽度
     * @param height 元素高度
     * @param mode   图片显示模式
     * @return 图片元素
     * @throws IOException IO异常
     */
    public static EasyImage buildLocalImage(String url, int x, int y, int width, int height, int mode) throws IOException {
        Image image = ImageIO.read(new File(url));
        return build(image, x, y, width, height, mode);
    }

    /**
     * 从图片对象构造元素
     *
     * @param image  图片对象
     * @param x      元素位置x
     * @param y      元素位置y
     * @param width  元素宽度
     * @param height 元素高度
     * @param mode   图片显示模式
     * @return 图片元素
     */
    public static EasyImage build(Image image, int x, int y, int width, int height, int mode) {
        EasyImage easyImage = new EasyImage();
        easyImage.setImage(image);
        easyImage.setX(x);
        easyImage.setY(y);
        easyImage.setWidth(width);
        easyImage.setHeight(height);
        easyImage.setMode(mode);
        return easyImage;
    }

}
