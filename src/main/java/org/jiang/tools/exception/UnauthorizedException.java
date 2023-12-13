package org.jiang.tools.exception;

/**
 * 未授权异常
 *
 * @author Bin
 * @date 2020-01-08 17:17
 */
public class UnauthorizedException extends BadRequestException {

    public UnauthorizedException() {
        super("用户未授权");
    }

    public UnauthorizedException(String e) {
        super(e);
    }

}
