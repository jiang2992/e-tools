package org.jiang.tools.exception;

/**
 * 属性提取异常
 *
 * @author Bin
 * @since 1.1.3
 */
public class PropertyExtractException extends BaseCustomException {

    public PropertyExtractException() {
        this("unable to get object properties, check parsed objects or expressions");
    }

    public PropertyExtractException(String message) {
        super(message);
    }

}
