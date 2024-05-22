package org.jiang.tools.exception;

/**
 * 自定义异常基类
 *
 * @author Bin
 * @since 1.0.0
 */
public abstract class BaseCustomException extends RuntimeException {

    public BaseCustomException() {
        this("unknown custom exception");
    }

    public BaseCustomException(String message) {
        super(message);
    }

    public BaseCustomException(Exception e) {
        super(e);
    }

    public BaseCustomException(String message, Exception e) {
        super(message, e);
    }

}
