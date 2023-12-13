package org.jiang.tools.canvas;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * 线条
 *
 * @author Bin
 * @date 2020/12/14 17:42
 */
@Getter
@Setter
public class EasyLine extends EasyElement {

    /**
     * 结束点x
     */
    private int ex;

    /**
     * 结束点y
     */
    private int ey;

    /**
     * 画笔颜色
     */
    private Color color;

    /**
     * 构造方法
     *
     * @param x     矩形位置x
     * @param y     矩形位置y
     * @param color 画笔颜色
     */
    public EasyLine(int x, int y, int ex, int ey, Color color) {
        super(x, y);
        this.ex = ex;
        this.ey = ey;
        this.color = color;
    }

}
