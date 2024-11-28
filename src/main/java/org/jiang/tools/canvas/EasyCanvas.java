package org.jiang.tools.canvas;

import java.awt.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * 画板
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class EasyCanvas extends EasyContainer {

    /**
     * 背景颜色
     */
    private Color backgroundColor;

    /**
     * 构造方法
     * 背景颜色默认为白色
     *
     * @param width  画板宽度
     * @param height 画板高度
     */
    public EasyCanvas(int width, int height) {
        this(width, height, Color.WHITE);
    }

    /**
     * 构造方法
     *
     * @param width           画板宽度
     * @param height          画板高度
     * @param backgroundColor 背景颜色
     */
    public EasyCanvas(int width, int height, Color backgroundColor) {
        super(width, height);
        this.backgroundColor = backgroundColor;
    }

}
