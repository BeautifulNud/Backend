package ggudock.domain.subscription.entity;

import ggudock.domain.order.entity.CustomerOrder;
import ggudock.domain.subscription.model.State;
import ggudock.domain.subscription.model.SubType;
import ggudock.domain.user.entity.User;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription extends BaseTimeEntity {
    @Id
    @Column(name ="subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private SubType subType;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Subscription(State state, SubType subType, LocalDate startDate, LocalDate endDate, CustomerOrder order, User user) {
        this.state = state;
        this.subType = subType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.order = order;
        this.user = user;
    }

    public void offSubscription(){
        this.state = State.OFF;
    }
}
