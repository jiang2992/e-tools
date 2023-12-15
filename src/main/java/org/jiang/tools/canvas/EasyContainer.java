package org.jiang.tools.canvas;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 容器
 *
 * @author Bin
 * @since 1.0.0
 */
@Getter
@Setter
public class EasyContainer implements Serializable {

    /**
     * 容器宽度
     */
    private int width;

    /**
     * 容器高度
     */
    private int height;

    /**
     * 元素集合
     */
    private List<EasyElement> elements;

    /**
     * 构造方法
     *
     * @param width  容器宽度
     * @param height 容器高度
     */
    public EasyContainer(int width, int height) {
        this.width = width;
        this.height = height;
        this.elements = new ArrayList<>(4);
    }

    /**
     * 添加一个元素
     *
     * @param element 元素
     */
    public void add(EasyElement element) {
        this.elements.add(element);
    }

    /**
     * 清空元素集合
     */
    public void clear() {
        this.elements.clear();
    }

}
