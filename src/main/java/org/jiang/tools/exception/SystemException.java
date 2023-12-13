package org.jiang.tools.exception;

/**
 * 系统异常
 *
 * @author Bin
 * @date 2020-01-08 10:32
 */
public class SystemException extends BaseCustomException {

    public SystemException(Exception e) {
        super(e);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Exception e) {
        super(message, e);
    }

    @Override
    public String getInfo() {
        return "系统异常";
    }

}
