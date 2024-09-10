package ggudock.domain.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDayRequest {
    private List<LocalDate> dates;

    public OrderDayRequest(){
    }
}
