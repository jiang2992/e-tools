package org.jiang.tools.canvas;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * 字体
 *
 * @author Bin
 * @date 2020/12/14 18:21
 */
@Getter
@Setter
public class EasyFont {

    /**
     * 字体名称
     */
    private String name = "SimHei";

    /**
     * 字体大小
     */
    private int size;

    /**
     * 字体样式
     */
    private int style;

    /**
     * 角度
     */
    private int rotate;

    /**
     * 构造方法
     * 字体名称默认为SimHei，字体样式默认为普通样式
     *
     * @param size 字体大小
     */
    public EasyFont(int size) {
        this.size = size;
        this.style = Font.PLAIN;
    }

    /**
     * 构造方法
     *
     * @param name  字体名称
     * @param size  字体大小
     * @param style 字体样式
     */
    public EasyFont(String name, int size, int style) {
        this.name = name;
        this.size = size;
        this.style = style;
    }

    /**
     * 链式设置字体名称
     *
     * @param name 字体名称
     * @return 字体对象
     */
    public EasyFont name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 链式设置字体大小
     *
     * @param size 字体大小
     * @return 字体对象
     */
    public EasyFont size(int size) {
        this.size = size;
        return this;
    }

    /**
     * 链式设置字体样式
     *
     * @param style 字体样式
     * @return 字体对象
     */
    public EasyFont style(int style) {
        this.style = style;
        return this;
    }

    public Font toFont() {
        Font font = new Font(this.name, this.style, this.size);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(rotate), 0, 0);
        return font.deriveFont(affineTransform);
    }

}
