package org.jiang.tools.exception;

/**
 * 坏的请求，参数错误或状态错误
 *
 * @author Bin
 * @date 2020-07-05 18:39
 */
public class BadRequestException extends BaseCustomException {

    public BadRequestException(){
        super("请求异常");
    }

    public BadRequestException(String message) {
        super(message);
    }

}
