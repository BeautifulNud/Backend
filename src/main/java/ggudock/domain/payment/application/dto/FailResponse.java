package ggudock.domain.payment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FailResponse {
    String errorCode;
    String errorMessage;
    String orderId;
}