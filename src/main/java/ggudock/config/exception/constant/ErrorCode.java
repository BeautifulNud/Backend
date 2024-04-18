package ggudock.config.exception.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ErrorCode {
    OK(HttpStatus.OK, "정상 처리 되었습니다."),
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    DUPLICATED_DATE(HttpStatus.CREATED, "이미 상품 요청된 날짜입니다."), // 가정이 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "정상적인 요청이 아닙니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 토큰이 필요합니다."),
    ;
    private final HttpStatus code;
    private final String message;
}
