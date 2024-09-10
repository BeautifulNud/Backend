package ggudock.domain.payment.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayType {
    CARD("카드");

    @JsonValue
    private final String name;
}
