package ggudock.domain.review.dto;

import ggudock.domain.review.entity.Review;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReviewResponse {
    private Long itemId;
    private Long userId;
    private String content;
    private float rating;
    private String date;

    @Builder
    public ReviewResponse(Review review) {
        this.itemId = review.getItem().getId();
        this.userId = review.getUser().getId();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.date = review.getDate();
    }
}
