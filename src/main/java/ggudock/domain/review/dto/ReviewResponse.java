package ggudock.domain.review.dto;

import ggudock.domain.review.entity.Review;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long itemId;
    private Long userId;
    private String content;
    private float rating;
    private String date;
    private String imageUrl;

    @Builder
    public ReviewResponse(Review review) {
        this.itemId = review.getItem().getId();
        this.userId = review.getUser().getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.date = review.getDate();
        this.imageUrl = review.getImageUrl();
    }

    public static ReviewResponse of(Review review) {
        return new ReviewResponse(
                review.getItem().getId(),
                review.getUser().getId(),
                review.getContent(),
                review.getRating(),
                review.getDate(),
                review.getImageUrl()
        );
    }
}
