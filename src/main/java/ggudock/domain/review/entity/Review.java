package ggudock.domain.review.entity;

import ggudock.domain.item.entity.Item;
import ggudock.domain.user.entity.User;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @NotNull
    private float rating;

    @NotNull
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "image_url")
    private String imageUrl;

    @Builder
    public Review(String content, float rating, String date, User user, Item item, String imageUrl) {
        this.content = content;
        this.rating = rating;
        this.date = date;
        this.user = user;
        this.item = item;
        this.imageUrl = imageUrl;
    }
}
