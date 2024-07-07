package ggudock.domain.subscription.api.dto;

import ggudock.global.validator.customvalid.TitleValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SubscriptionPeriodRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DayOfWeek> days;

    public SubscriptionPeriodRequest() {
    }
}
