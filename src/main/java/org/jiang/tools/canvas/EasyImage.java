package org.jiang.tools.canvas;

import lombok.Getter;
import lombok.Setter;
import org.jiang.tools.image.ImageMode;

import java.awt.*;

/**
 * 画板元素：图片
 *
 * @author Bin
 * @date 2020/12/14 16:36
 */
@Getter
@Setter
public class EasyImage extends EasyElement {

    /**
     * 图片宽度
     */
    private int width;

    /**
     * 图片高度
     */
    private int height;

    /**
     * 图片对象
     */
    private Image image;

    /**
     * 图片显示模式
     */
    private int mode = ImageMode.NONE;

    /**
     * 判断图片宽度和图片高度是否大于0
     *
     * @return 结果
     */
    public boolean hasSize() {
        return width > 0 && height > 0;
    }

}
