package org.jiang.tools.canvas.element;

import java.awt.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * 矩形
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class EasyRect extends EasyElement {

    public interface Mode {

        /**
         * 线条
         */
        int DRAW = 1;

        /**
         * 填充
         */
        int FILL = 2;

    }

    /**
     * 矩形宽度
     */
    private int width;

    /**
     * 矩形高度
     */
    private int height;

    /**
     * 画笔颜色
     */
    private Color color;

    /**
     * 绘制模式
     */
    private int mode;

    /**
     * 构造方法
     *
     * @param x      矩形位置x
     * @param y      矩形位置y
     * @param width  矩形宽度
     * @param height 矩形高度
     * @param color  画笔颜色
     * @param mode   绘制模式
     */
    public EasyRect(int x, int y, int width, int height, Color color, int mode) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.color = color;
        this.mode = mode;
    }

    /**
     * 构造线条模式矩形
     *
     * @param x      矩形位置x
     * @param y      矩形位置y
     * @param width  矩形宽度
     * @param height 矩形高度
     * @param color  画笔颜色
     * @return 矩形对象
     */
    public static EasyRect buildDraw(int x, int y, int width, int height, Color color) {
        return new EasyRect(x, y, width, height, color, Mode.DRAW);
    }

    /**
     * 构造填充模式矩形
     *
     * @param x      矩形位置x
     * @param y      矩形位置y
     * @param width  矩形宽度
     * @param height 矩形高度
     * @param color  画笔颜色
     * @return 矩形对象
     */
    public static EasyRect buildFill(int x, int y, int width, int height, Color color) {
        return new EasyRect(x, y, width, height, color, Mode.FILL);
    }

}
