package ggudock.domain.subscription.application.dto;

import ggudock.domain.subscription.entity.Subscription;
import ggudock.domain.subscription.model.State;
import ggudock.domain.subscription.model.SubType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class SubscriptionResponse {

    private State state;
    private SubType subType;
    private long periodDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private int price;

    @Builder
    public SubscriptionResponse(Subscription subscription) {
        this.state = subscription.getState();
        this.subType = subscription.getSubType();
        this.periodDays = subscription.getPeriodDays();
        this.startDate = subscription.getStartDate();
        this.endDate = subscription.getEndDate();
        this.price = subscription.getPrice();
        this.title = subscription.getTitle();
    }
}
