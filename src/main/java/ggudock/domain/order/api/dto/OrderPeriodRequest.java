package ggudock.domain.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderPeriodRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DayOfWeek> days;

    public OrderPeriodRequest(){

    }
}
