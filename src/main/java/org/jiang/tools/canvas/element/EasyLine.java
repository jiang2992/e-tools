package org.jiang.tools.canvas.element;

import java.awt.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * 线条
 *
 * @author Bin
 * @since 1.0.0
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
     * @param x     矩形开始位置x
     * @param y     矩形开始位置y
     * @param ex    矩形结束位置x
     * @param ey    矩形结束位置y
     * @param color 画笔颜色
     */
    public EasyLine(int x, int y, int ex, int ey, Color color) {
        super(x, y);
        this.ex = ex;
        this.ey = ey;
        this.color = color;
    }

}
