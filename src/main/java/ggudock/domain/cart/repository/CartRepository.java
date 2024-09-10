package ggudock.domain.cart.repository;

import ggudock.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser_Email(String email);
    boolean existsByUser_EmailAndItem_Id(String email,Long itemId);
}
