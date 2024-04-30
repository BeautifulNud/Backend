package ggudock.domain.item.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemCategory {

    WELL_BEING("웰빙푸드"),
    FAST_FOOD("패스트푸드");

    @JsonValue
    private final String name;
}
