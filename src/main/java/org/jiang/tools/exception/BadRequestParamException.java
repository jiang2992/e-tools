package org.jiang.tools.exception;

/**
 * 请求参数错误异常
 * 不记为执行失败
 *
 * @author Bin
 * @date 2020-01-08 10:31
 */
public class BadRequestParamException extends BadRequestException {

    public BadRequestParamException() {
        super("请求参数异常");
    }

    public BadRequestParamException(String message) {
        super(message);
    }

}
