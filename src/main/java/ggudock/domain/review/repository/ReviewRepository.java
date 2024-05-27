package ggudock.domain.review.repository;

import ggudock.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser_EmailOrderByDateDesc(String email);

    Page<Review> findByUser_EmailOrderByDateDesc(String email, Pageable pageable);

    Page<Review> findByItem_IdOrderByDateDesc(Long itemId, Pageable pageable);

    Page<Review> findByItem_IdOrderByDateAsc(Long itemId, Pageable pageable);

    Page<Review> findByItem_IdOrderByRatingDescDateDesc(Long itemId, Pageable pageable);

    Page<Review> findByItem_IdOrderByRatingAscDateDesc(Long itemId, Pageable pageable);
}
