package org.jiang.tools.exception;

/**
 * 未授权异常
 *
 * @author Bin
 * @since 1.0.0
 */
public class UnauthorizedException extends BadRequestException {

    public UnauthorizedException() {
        super("unauthorized user");
    }

    public UnauthorizedException(String e) {
        super(e);
    }

}
