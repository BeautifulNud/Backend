package ggudock.global.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ggudock.global.exception.constant.ErrorCode.UNKNOWN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    @Test
    @DisplayName("ExceptionTest")
    void test1(){
        //then
        assertThatThrownBy(this::createException)
                .isInstanceOf(BusinessException.class);
        Throwable exception = assertThrows(BusinessException.class, this::createException);
        assertEquals("에러 정보가 비어있습니다.", exception.getMessage());
    }

    BusinessException createException(){
        throw new BusinessException(UNKNOWN);
    }
}