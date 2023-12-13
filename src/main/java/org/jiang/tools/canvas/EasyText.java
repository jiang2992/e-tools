package org.jiang.tools.canvas;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * 画板元素：文本
 *
 * @author Bin
 * @date 2020/12/14 16:53
 */
@Getter
@Setter
public class EasyText extends EasyElement {

    /**
     * 文本内容
     */
    private String content;

    /**
     * 文本颜色
     */
    private Color color;

    /**
     * 文本字体
     */
    private EasyFont font;

    /**
     * 构造方法
     * 文本颜色默认为黑色
     *
     * @param x       文本位置x
     * @param y       文本位置y
     * @param content 文本内容
     * @param font    文本字体
     */
    public EasyText(int x, int y, String content, EasyFont font) {
        this(x, y, content, Color.BLACK, font);
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
    public EasyText(int x, int y, String content, Color color, EasyFont font) {
        super(x, y);
        this.content = content;
        this.color = color;
        this.font = font;
    }

}
