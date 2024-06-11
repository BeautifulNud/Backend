package ggudock.domain.order.application.dto;

import ggudock.domain.order.entity.CustomerOrder;
import ggudock.domain.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OrderResponse {
    private int totalPrice;
    private OrderStatus orderStatus;

    public OrderResponse(CustomerOrder customerOrder) {
        this.totalPrice = customerOrder.getTotalPrice();
        this.orderStatus = customerOrder.getOrderStatus();
    }
}
