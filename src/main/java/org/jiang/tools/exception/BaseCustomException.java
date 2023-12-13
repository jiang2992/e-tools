package org.jiang.tools.exception;

/**
 * 自定义异常基类
 *
 * @author Bin
 * @date 2020-01-08 10:31
 */
public abstract class BaseCustomException extends RuntimeException {

    public BaseCustomException() {
        super();
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

    /**
     * 获取异常消息
     *
     * @return 消息文本
     */
    public String getInfo() {
        return super.getMessage();
    }

}
