package ggudock.domain.cart.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {

    DIB("찜"),
    CART_LIST("장바구니");

    @JsonValue
    private final String name;
}
