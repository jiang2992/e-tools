package org.jiang.tools.canvas.element;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 画板元素
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public abstract class EasyElement implements Serializable {

    /**
     * 元素位置x
     */
    private int x;

    /**
     * 元素位置y
     */
    private int y;

    /**
     * 旋转角度
     */
    private double rotation;

    /**
     * 构造方法
     * 默认 x = 0，y = 0
     */
    public EasyElement() {
    }

    /**
     * 构造方法
     *
     * @param x 元素位置x
     * @param y 元素位置y
     */
    public EasyElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
