package ggudock.domain.subscription.application.dto;

import ggudock.domain.subscription.entity.SubscriptionSchedule;
import ggudock.domain.subscription.model.ScheduleState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class SubscriptionScheduleResponse {

    private LocalDate date;
    private DayOfWeek day;
    private String itemName;
    private ScheduleState scheduleState;

    @Builder
    public SubscriptionScheduleResponse(SubscriptionSchedule subscriptionSchedule) {
        this.date = subscriptionSchedule.getDate();
        this.day = subscriptionSchedule.getDay();
        this.itemName = subscriptionSchedule.getSubscription().getOrder().getItem().getName();
        this.scheduleState = subscriptionSchedule.getScheduleState();
    }
}
