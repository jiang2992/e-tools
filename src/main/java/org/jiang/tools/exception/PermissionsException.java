package org.jiang.tools.exception;

/**
 * 权限校验异常
 *
 * @author Bin
 * @since 1.0.0
 */
public class PermissionsException extends BadRequestException {

    public PermissionsException() {
        super("no operation permission");
    }

    public PermissionsException(String message) {
        super(message);
    }

}
