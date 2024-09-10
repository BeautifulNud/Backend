package ggudock.domain.payment.entity;

import ggudock.domain.order.entity.CustomerOrder;
import ggudock.domain.payment.model.PayStatus;
import ggudock.domain.payment.model.PayType;
import ggudock.domain.user.entity.User;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate payDate;
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    private PayType payType;
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Payment(PayType payType,CustomerOrder order, User user) {
        this.payType = payType;
        this.payDate = LocalDate.now();
        this.order = order;
        this.user = user;
    }

    public void createPaymentKey(String paymentKey){
        this.paymentKey = paymentKey;
        this.payStatus = PayStatus.YES;
    }

    public void failPayment(){
        this.payStatus = PayStatus.NO;
    }

    public void cancelPayment(){
        this.payStatus = PayStatus.CANCEL;
    }
}