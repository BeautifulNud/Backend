package ggudock.domain.subscription.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScheduleState {
    ON("배송전"),
    OFF("배송후");

    @JsonValue
    private final String name;

    public static ScheduleState fromString(String text) {
        for (ScheduleState state : ScheduleState.values()) {
            if (state.name.equalsIgnoreCase(text)) {
                return state;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}