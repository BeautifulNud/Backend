package ggudock.domain.item.entity;

import ggudock.domain.cart.entity.Cart;
import ggudock.domain.category.entity.Category;
import ggudock.domain.company.entity.Company;
import ggudock.domain.review.entity.Review;
import ggudock.domain.subscription.entity.Subscription;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;
    @NotNull
    private int price;
    private int salePercent;
    private String description;
    private String plan;
    private float rating;
    private String thumbnail;
    private long views;

    @OneToOne(fetch = FetchType.LAZY)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImageList;
    @OneToMany(mappedBy = "item")
    private List<Cart> cartList;
    @OneToMany(mappedBy = "item")
    private List<Subscription> subscriptionList;
    @OneToMany(mappedBy = "item")
    private List<Review> reviewList;

    @Builder
    public Item(Long id, String name, int price, int salePercent, String description, String plan, float rating, String thumbnail, long views, Category category, Company company, List<ItemImage> itemImageList, List<Cart> cartList, List<Subscription> subscriptionList, List<Review> reviewList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.salePercent = salePercent;
        this.description = description;
        this.plan = plan;
        this.rating = rating;
        this.thumbnail = thumbnail;
        this.views = views;
        this.category = category;
        this.company = company;
        this.itemImageList = itemImageList;
        this.cartList = cartList;
        this.subscriptionList = subscriptionList;
        this.reviewList = reviewList;
    }

    public int getSalePrice() {
        return this.price / 100 * (100 - this.salePercent);
    }

    public boolean isSameCategory(String category) {
        return this.category.getName().equals(category);
    }

    public void raiseViews() {
        this.views++;
    }
}
