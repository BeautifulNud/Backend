package ggudock.domain.subscription.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubType {
    PERIOD("기간"),
    DAY("당일");

    @JsonValue
    private final String name;
}