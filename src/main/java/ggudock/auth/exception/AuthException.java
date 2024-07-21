package ggudock.auth.exception;

import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;

public class AuthException extends BusinessException {

    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
