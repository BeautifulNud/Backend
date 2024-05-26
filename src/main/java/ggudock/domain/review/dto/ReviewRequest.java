package ggudock.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReviewRequest {
    @NotNull
    private Long itemId;

    @NotNull
    private String content;

    @NotNull
    private float rating;

    @Builder
    public ReviewRequest(Long itemId, String content, float rating) {
        this.itemId = itemId;
        this.content = content;
        this.rating = rating;
    }
}
