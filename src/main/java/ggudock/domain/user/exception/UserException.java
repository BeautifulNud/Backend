package ggudock.domain.user.exception;

import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;

public class UserException extends BusinessException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
