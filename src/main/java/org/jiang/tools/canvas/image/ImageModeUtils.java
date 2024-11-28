package org.jiang.tools.canvas.image;

import java.awt.Image;
import org.jiang.tools.canvas.element.EasyImage;
import org.jiang.tools.exception.SystemException;

/**
 * 图片显示模式工具类
 *
 * @author Bin
 * @since 1.0.0
 */
public class ImageModeUtils {

    /**
     * 按照指定图片显示模式计算相关参数
     *
     * @param easyImage 图片元素
     * @return 定位数据
     */
    public static ImageDrawPosition fit(EasyImage easyImage) {
        ImageDrawPosition position;
        switch (easyImage.getMode()) {
            case ImageMode.FILL:
                position = fillFit(easyImage);
                break;
            case ImageMode.CONTAIN:
                position = containFit(easyImage);
                break;
            case ImageMode.COVER:
                position = coverFit(easyImage);
                break;
            default:
                position = noneFit(easyImage);
                break;
        }
        return position;
    }

    /**
     * 完全按照图片原有尺寸显示
     *
     * @param easyImage 图片元素
     * @return 定位数据
     */
    public static ImageDrawPosition noneFit(EasyImage easyImage) {
        Image image = easyImage.getImage();
        ImageDrawPosition position = new ImageDrawPosition();

        position.setStartX(easyImage.getX());
        position.setStartY(easyImage.getY());
        position.setEndX(image.getWidth(null) + easyImage.getWidth());
        position.setEndY(image.getHeight(null) + easyImage.getHeight());
        position.setClipStartX(0);
        position.setClipStartY(0);
        position.setClipEndX(image.getWidth(null));
        position.setClipEndY(image.getHeight(null));

        return position;
    }

    /**
     * 完全填充整个容器，会导致图片出现拉伸
     *
     * @param easyImage 图片元素
     * @return 定位数据
     */
    public static ImageDrawPosition fillFit(EasyImage easyImage) {
        if (!easyImage.hasSize()) {
            throw new SystemException("size is incorrect");
        }

        Image image = easyImage.getImage();
        ImageDrawPosition position = new ImageDrawPosition();

        position.setStartX(easyImage.getX());
        position.setStartY(easyImage.getY());
        position.setEndX(easyImage.getX() + easyImage.getWidth());
        position.setEndY(easyImage.getY() + easyImage.getHeight());
        position.setClipStartX(0);
        position.setClipStartY(0);
        position.setClipEndX(image.getWidth(null));
        position.setClipEndY(image.getHeight(null));

        return position;
    }

    /**
     * 通过缩放来保证图片按照原有比例完全展示在容器内，不能保证填满整个容器
     *
     * @param easyImage 图片元素
     * @return 定位数据
     */
    public static ImageDrawPosition containFit(EasyImage easyImage) {
        if (!easyImage.hasSize()) {
            throw new SystemException("size is incorrect");
        }

        Image image = easyImage.getImage();
        ImageDrawPosition position = new ImageDrawPosition();

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int startX = easyImage.getX(), startY = easyImage.getY();

        // 按照宽度缩放至容器大小
        if (imageWidth != easyImage.getWidth()) {
            float ratio = (float) imageWidth / easyImage.getWidth();
            imageWidth = easyImage.getWidth();
            imageHeight = (int) (imageHeight / ratio);
        }

        // 如果高度超出容器，则按照高度缩放至容器大小
        if (imageHeight > easyImage.getHeight()) {
            float ratio = (float) imageHeight / easyImage.getHeight();
            imageHeight = easyImage.getHeight();
            imageWidth = (int) (imageWidth / ratio);
        }

        // 居中显示
        startX = startX + (imageWidth == easyImage.getWidth() ? 0 : ((easyImage.getWidth() - imageWidth) / 2));
        startY = startY + (imageHeight == easyImage.getHeight() ? 0 : ((easyImage.getHeight() - imageHeight) / 2));

        position.setStartX(startX);
        position.setStartY(startY);
        position.setEndX(startX + imageWidth);
        position.setEndY(startY + imageHeight);

        position.setClipStartX(0);
        position.setClipStartY(0);
        position.setClipEndX(image.getWidth(null));
        position.setClipEndY(image.getHeight(null));

        return position;
    }


    /**
     * 通过缩放来保证其中一边完全显示，超出部分将被裁剪，总是能填满整个容器
     *
     * @param easyImage 图片元素
     * @return 定位数据
     */
    public static ImageDrawPosition coverFit(EasyImage easyImage) {
        if (!easyImage.hasSize()) {
            throw new SystemException("size is incorrect");
        }

        Image image = easyImage.getImage();

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int clipX = 0, clipY = 0;
        int clipEndX = imageWidth, clipEndY = imageHeight;

        // 先获得contain模式计算结果
        ImageDrawPosition position = containFit(easyImage);

        // 计算出未铺满的一边的剩余比例后，将另一边的裁剪尺寸按照比例来减少
        if (position.getEndX() < easyImage.getWidth()) {
            float ratio = (float) (position.getEndX() - easyImage.getX()) / easyImage.getWidth();
            clipY = (int) (position.getClipEndY() - position.getClipEndY() * ratio);
            clipEndY -= clipY;
        } else if (position.getEndY() < easyImage.getHeight()) {
            float ratio = (float) (position.getEndY() - easyImage.getY()) / easyImage.getHeight();
            clipX = (int) (position.getClipEndX() - position.getClipEndX() * ratio);
            clipEndX -= clipX;
        }

        position.setStartX(easyImage.getX());
        position.setStartY(easyImage.getY());
        position.setEndX(easyImage.getX() + easyImage.getWidth());
        position.setEndY(easyImage.getY() + easyImage.getHeight());

        position.setClipStartX(clipX);
        position.setClipStartY(clipY);
        position.setClipEndX(clipEndX);
        position.setClipEndY(clipEndY);

        return position;
    }

}
