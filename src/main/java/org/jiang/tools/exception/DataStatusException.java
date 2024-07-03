package org.jiang.tools.exception;

/**
 * 数据状态异常
 *
 * @author Bin
 * @since 1.1.4
 */
public class DataStatusException extends BaseCustomException {

    public DataStatusException() {
        this("data status exception");
    }

    public DataStatusException(String message) {
        super(message);
    }

}
