package ggudock.domain.item.entity;

import ggudock.domain.cart.entity.Cart;
import ggudock.domain.category.entity.Category;
import ggudock.domain.company.entity.Company;
import ggudock.domain.review.entity.Review;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImage> itemImageList = new ArrayList<>();
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Cart> cartList = new ArrayList<>();
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public Item(String name, int price, int salePercent, String description, String plan, float rating, String thumbnail, long views, Category category, Company company) {
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

