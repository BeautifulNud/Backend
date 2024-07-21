package ggudock.auth.exception;

import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;

public class TokenException extends BusinessException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
