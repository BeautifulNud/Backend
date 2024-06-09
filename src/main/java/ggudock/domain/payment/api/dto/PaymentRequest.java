package ggudock.domain.payment.api.dto;

import ggudock.domain.payment.model.PayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class PaymentRequest {
    private PayType payType;

    public PaymentRequest(){
    }
}
