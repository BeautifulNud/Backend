package ggudock.config.exception;

import ggudock.config.exception.constant.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public BusinessException() {
        super();
        this.errorCode = ErrorCode.UNKNOWN;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = message;
    }
}
