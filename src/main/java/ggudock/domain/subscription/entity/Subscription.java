package ggudock.domain.subscription.entity;

import ggudock.domain.item.entity.Item;
import ggudock.domain.user.entity.User;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription extends BaseTimeEntity {
    @Id
    @Column(name ="subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int period;
    private LocalDateTime subscribeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Subscription(int period, LocalDateTime subscribeDate, Item item, User user) {
        this.period = period;
        this.subscribeDate = subscribeDate;
        this.item = item;
        this.user = user;
    }
}
