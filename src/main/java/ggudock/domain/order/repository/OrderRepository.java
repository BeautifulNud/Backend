package ggudock.domain.order.repository;

import ggudock.domain.order.entity.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<CustomerOrder, String> {
    Page<CustomerOrder> findAllByUser_Email(String email, Pageable pageable);
}
