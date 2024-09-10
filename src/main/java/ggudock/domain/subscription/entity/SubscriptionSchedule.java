package ggudock.domain.subscription.entity;

import ggudock.domain.address.entity.Address;
import ggudock.domain.subscription.model.ScheduleState;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubscriptionSchedule extends BaseTimeEntity {
    @Id
    @Column(name ="subscription_date_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;
    @NotNull
    private DayOfWeek day;

    @Enumerated(EnumType.STRING)
    private ScheduleState scheduleState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="address_id")
    private Address address;

    @Builder
    public SubscriptionSchedule(LocalDate date, Subscription subscription,Address address) {
        this.date = date;
        this.day = DayOfWeek.of(date.getDayOfWeek().getValue());
        this.scheduleState = ScheduleState.ON;
        this.subscription = subscription;
        this.address = address;
    }

    public void changeState(){
        this.scheduleState= ScheduleState.OFF;
    }
}
