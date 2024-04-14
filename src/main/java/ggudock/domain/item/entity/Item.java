package ggudock.domain.item.entity;

import ggudock.domain.company.entity.Company;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    @NotNull
    private String name;
    @NotNull
    private int price;
    private String description;
    private String plan;
    private long rating;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public Item(String name, int price, String description, String plan, long rating, Company company) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.plan = plan;
        this.rating = rating;
        this.company = company;
    }
}
