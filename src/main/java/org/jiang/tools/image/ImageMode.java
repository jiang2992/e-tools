package org.jiang.tools.image;

/**
 * 图片显示模式常量类
 *
 * @author Bin
 * @since 1.0.0
 */
public interface ImageMode {

    /**
     * 保持原有尺寸
     */
    int NONE = 0;

    /**
     * 完全填充整个容器
     */
    int FILL = 1;

    /**
     * 保证图片完全显示且不变形
     */
    int CONTAIN = 2;

    /**
     * 保证其中一边完全显示，超出部分将被裁剪
     */
    int COVER = 3;

}
