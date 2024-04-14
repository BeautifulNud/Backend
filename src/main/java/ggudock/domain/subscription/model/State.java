package ggudock.domain.subscription.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    ON("구독중"),
    OFF("구독종료");

    @JsonValue
    private final String name;
}
