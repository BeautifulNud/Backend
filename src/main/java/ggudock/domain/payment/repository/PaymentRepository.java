package ggudock.domain.payment.repository;

import ggudock.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByOrder_OrderId(String orderId);
    Payment findByPaymentKey(String paymentKey);
}
