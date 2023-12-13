package org.jiang.tools.image;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片绘制位置信息
 *
 * @author Bin
 * @date 2020/12/15 09:44
 */
@Getter
@Setter
public class ImageDrawPosition {

    /**
     * 绘制开始位置X
     */
    private int startX;

    /**
     * 绘制开始位置Y
     */
    private int startY;

    /**
     * 绘制结束位置X
     */
    private int endX;

    /**
     * 绘制结束位置Y
     */
    private int endY;

    /**
     * 图片裁剪开始位置X
     */
    private int clipStartX;

    /**
     * 图片裁剪开始位置Y
     */
    private int clipStartY;

    /**
     * 图片裁剪结束位置X
     */
    private int clipEndX;

    /**
     * 图片裁剪结束位置Y
     */
    private int clipEndY;

}
