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
    private LocalDate startDate;
    private LocalDate endDate;
    private String itemName;
    private int price;

    @Builder
    public SubscriptionResponse(Subscription subscription) {
        this.state = subscription.getState();
        this.subType = subscription.getSubType();
        this.startDate = subscription.getStartDate();
        this.endDate = subscription.getEndDate();
        this.price = subscription.getOrder().getTotalPrice();
        this.itemName = subscription.getOrder().getItem().getName();
    }
}
