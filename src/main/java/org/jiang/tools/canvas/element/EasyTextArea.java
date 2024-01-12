package org.jiang.tools.canvas.element;

import lombok.Getter;
import lombok.Setter;
import org.jiang.tools.canvas.EasyFont;
import org.jiang.tools.canvas.TextAlign;

import java.awt.*;

/**
 * 画板元素：文本域
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class EasyTextArea extends EasyText {

    /**
     * 宽度
     */
    private int width;

    /**
     * 高度
     */
    private int height;

    /**
     * 水平排列
     */
    private int align = TextAlign.LEFT;

    /**
     * 构造方法
     * 文本颜色默认为黑色
     *
     * @param x       文本位置x
     * @param y       文本位置y
     * @param content 文本内容
     * @param font    文本字体
     */
    public EasyTextArea(int x, int y, int width, int height, String content, EasyFont font) {
        this(x, y, width, height, content, Color.BLACK, font);
    }

    /**
     * 构造方法
     *
     * @param x       文本位置x
     * @param y       文本位置y
     * @param content 文本内容
     * @param color   文本颜色
     * @param font    文本字体
     */
    public EasyTextArea(int x, int y, int width, int height, String content, Color color, EasyFont font) {
        super(x, y, content, color, font);
        this.width = width;
        this.height = height;
    }

}
