package org.jiang.tools.exception;

/**
 * 权限校验异常
 *
 * @author Bin
 * @date 2020-01-08 17:17
 */
public class PermissionsException extends BadRequestException {

    public PermissionsException() {
        super("权限不足");
    }

    public PermissionsException(String message) {
        super(message);
    }

}
