package ggudock.domain.payment.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatus {
    YES("결제 완료"),
    NO("결제 실패"),
    CANCEL("결제 취소");

    @JsonValue
    private final String name;
}
