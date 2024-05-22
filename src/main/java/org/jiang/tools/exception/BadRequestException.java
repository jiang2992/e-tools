package org.jiang.tools.exception;

/**
 * 坏的请求，参数错误或状态错误
 *
 * @author Bin
 * @since 1.0.0
 */
public class BadRequestException extends BaseCustomException {

    public BadRequestException(){
        this("request parameter is error or status is not as expected");
    }

    public BadRequestException(String message) {
        super(message);
    }

}
