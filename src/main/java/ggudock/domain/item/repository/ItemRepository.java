package ggudock.domain.item.repository;

import ggudock.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findById(Long itemId);

    Boolean existsAllByName(String name);

    List<Item> findAllByOrderByViewsDesc();
    @Query("SELECT i FROM Item i ORDER BY SIZE(i.reviewList) DESC")
    List<Item> findByReviewCountDesc();
}
