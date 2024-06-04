package org.jiang.tools.exception;

/**
 * 需要验证用户身份
 *
 * @author Bin
 * @since 1.1.3
 */
public class NeedVerifyException extends BadRequestException {

    public NeedVerifyException() {
        this("this operation requires verification");
    }

    public NeedVerifyException(String message) {
        super(message);
    }

}
