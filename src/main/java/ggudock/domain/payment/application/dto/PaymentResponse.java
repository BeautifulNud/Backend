package ggudock.domain.payment.application.dto;

import ggudock.domain.payment.entity.Payment;
import ggudock.domain.payment.model.PayStatus;
import ggudock.domain.payment.model.PayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class PaymentResponse {
    private PayType payType;
    private int amount;
    private String orderName;
    private String orderId;
    private String customerEmail;
    private String customerName;
    private String paymentKey;
    private LocalDate payDate;
    private PayStatus payStatus;

    public PaymentResponse(Payment payment){
        this.payType = payment.getPayType();
        this.amount = payment.getOrder().getTotalPrice();
        this.orderName = payment.getOrder().getItem().getName();
        this.orderId = payment.getOrder().getOrderId();
        this.customerEmail = payment.getUser().getEmail();
        this.customerName = payment.getUser().getUsername();
        this.paymentKey = payment.getPaymentKey();
        this.payDate = payment.getPayDate();
        this.payStatus = payment.getPayStatus();
    }
}
