package ggudock.domain.subscription.api.dto;

import ggudock.global.validator.customvalid.TitleValid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SubscriptionDayRequest {
    private List<LocalDate> dates;

    public SubscriptionDayRequest() {
    }
}
