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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerOrder extends BaseTimeEntity {
    @Id
    @Column(name ="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private int totalPrice;

    private OrderStatus orderStatus;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public CustomerOrder(int totalPrice,Item item, User user,OrderStatus orderStatus) {
        this.totalPrice = totalPrice;
        this.item = item;
        this.user = user;
        this.orderStatus = orderStatus;
    }

    public void acceptStatus(OrderStatus orderStatus){
        this.orderStatus=orderStatus;
    }
}
