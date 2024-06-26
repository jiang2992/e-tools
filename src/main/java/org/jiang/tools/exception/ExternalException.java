package org.jiang.tools.exception;

/**
 * 第三方异常
 *
 * @author Bin
 * @since 1.0.0
 */
public class ExternalException extends BaseCustomException {

    public ExternalException(){
        this("an error occurred on the external service");
    }

    public ExternalException(Exception e) {
        super(e);
    }

    public ExternalException(String message) {
        super(message);
    }

    public ExternalException(String message, Exception e) {
        super(message, e);
    }

}
