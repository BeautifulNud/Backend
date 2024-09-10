package ggudock.domain.order.entity;

import ggudock.domain.item.entity.Item;
import ggudock.domain.order.model.OrderStatus;
import ggudock.domain.user.entity.User;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerOrder extends BaseTimeEntity {
    @Id
    @Column(name = "order_id")
    private String orderId;
    @NotNull
    private int totalPrice;
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public CustomerOrder(int totalPrice,Item item, User user,OrderStatus orderStatus) {
        orderId = UUID.randomUUID().toString();
        this.totalPrice = totalPrice;
        this.item = item;
        this.user = user;
        this.orderStatus = orderStatus;
        this.orderDate = LocalDate.now();
    }

    public void acceptStatus(OrderStatus orderStatus){
        this.orderStatus=orderStatus;
    }
}
