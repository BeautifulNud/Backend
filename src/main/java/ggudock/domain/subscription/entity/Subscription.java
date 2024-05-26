package ggudock.domain.subscription.entity;

import ggudock.domain.item.entity.Item;
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
import java.time.temporal.ChronoUnit;

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

    private String title;

    private long periodDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Subscription(State state, SubType subType, LocalDate startDate, LocalDate endDate, Item item, User user,String title) {
        this.state = state;
        this.subType = subType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.item = item;
        this.user = user;
        this.periodDays = ChronoUnit.DAYS.between(startDate,endDate);
        this.title = title;
    }

    public void offSubscription(){
        this.state = State.OFF;
    }
    public void createPrice(int price){
        this.price = price;
    }
}
