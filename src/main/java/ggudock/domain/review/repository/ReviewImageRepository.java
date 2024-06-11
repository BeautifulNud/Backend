package ggudock.domain.review.repository;

import ggudock.domain.review.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findAllByReview_Id(Long reviewId);

    void deleteAllByReview_Id(Long reviewId);
}
