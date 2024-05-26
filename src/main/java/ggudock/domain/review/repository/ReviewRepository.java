package ggudock.domain.review.repository;

import ggudock.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser_EmailOrderByDateDesc(String userEmail);

    List<Review> findByItem_IdOrderByDateDesc(Long itemId);

    List<Review> findByItem_IdOrderByDateAsc(Long itemId);

    List<Review> findByItem_IdOrderByRatingDescDateDesc(Long itemId);

    List<Review> findByItem_IdOrderByRatingAscDateDesc(Long itemId);
}
