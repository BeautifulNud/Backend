package ggudock.domain.order.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    YES("주문 성공"),
    NO("주문 실패");

    @JsonValue
    private final String name;
}
