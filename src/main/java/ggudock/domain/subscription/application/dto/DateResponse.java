package ggudock.domain.subscription.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DateResponse {
    String itemName;
    Long subscriptionId;
    List<LocalDate> dates;
}
