package ggudock.domain.cart.application.dto;

import ggudock.domain.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CartResponse {
    Long cartId;
    Long itemId;

    public CartResponse(Cart cart){
        cartId = cart.getId();
        itemId = cart.getItem().getId();
    }
}
