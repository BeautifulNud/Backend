package ggudock.domain.order.repository;

import ggudock.domain.order.entity.CustomerOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
    Page<CustomerOrder> findAllByUser_Email(String email, Pageable pageable);
}
