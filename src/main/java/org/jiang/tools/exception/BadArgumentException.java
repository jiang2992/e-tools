package org.jiang.tools.exception;

/**
 * 坏的参数异常
 *
 * @author Bin
 * @since 1.0.0
 */
public class BadArgumentException extends BaseCustomException {

    public BadArgumentException(){
        this("argument is not as expected");
    }

    public BadArgumentException(String message) {
        super(message);
    }

}
