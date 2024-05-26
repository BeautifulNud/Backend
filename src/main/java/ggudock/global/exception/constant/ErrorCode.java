package ggudock.global.exception.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    OK(HttpStatus.OK, "정상 처리 되었습니다."),
    DO_NOT_LOGIN(HttpStatus.NOT_FOUND, "현재 로그인중이 아닙니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    NOT_FOUND_ADDRESS(HttpStatus.NOT_FOUND, "주소를 찾을 수 없습니다."),
    NOT_FOUND_DEFAULT_ADDRESS(HttpStatus.NOT_FOUND, "기본 배송지가 설정되어 있지 않습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    DUPLICATED_USER(HttpStatus.CREATED, "이미 존재하는 회원입니다."),
    DUPLICATED_ADDRESS(HttpStatus.CREATED, "이미 저장되어 있는 배송지입니다."),
    DUPLICATED_DATE(HttpStatus.CREATED, "이미 상품 요청된 날짜입니다."), // 가정이 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "정상적인 요청이 아닙니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증 토큰이 필요합니다."),
    UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 에러 발생."),
    ;
    private final HttpStatus code;
    private final String message;
}
