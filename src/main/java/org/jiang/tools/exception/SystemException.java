package org.jiang.tools.exception;

/**
 * 系统异常
 *
 * @author Bin
 * @since 1.0.0
 */
public class SystemException extends BaseCustomException {

    public SystemException(){
        this("system unexpected exception");
    }

    public SystemException(Exception e) {
        super(e);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Exception e) {
        super(message, e);
    }

}
