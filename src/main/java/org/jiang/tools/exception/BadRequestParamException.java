package org.jiang.tools.exception;

/**
 * 请求参数错误异常
 * 不记为执行失败
 *
 * @author Bin
 * @since 1.0.0
 */
public class BadRequestParamException extends BadRequestException {

    public BadRequestParamException() {
        this("request parameter is error");
    }

    public BadRequestParamException(String message) {
        super(message);
    }

}
