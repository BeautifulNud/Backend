package ggudock.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Getter
public class PaymentConfig {

    public static final String PAYMENT_ACCEPT_URL = "https://api.tosspayments.com/v1/payments/confirm";
    @Value("${toss.payments.client-key}")
    private String clientKey;

    @Value("${toss.payments.client-secret}")
    private String secretKey;

    @Value("${toss.payments.root-uri}")
    private String tossUrl;
}