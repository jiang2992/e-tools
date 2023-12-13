package org.jiang.tools.exception;

/**
 * 坏的参数异常
 *
 * @author Bin
 * @date 2023/12/11 15:40
 */
public class BadArgumentException extends BaseCustomException {

    public BadArgumentException(){
        super("参数异常");
    }

    public BadArgumentException(String message) {
        super(message);
    }

}
