package ggudock.domain.item.dto;

import ggudock.domain.category.application.response.CategoryResponse;
import ggudock.domain.company.dto.CompanyResponse;
import ggudock.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemDetailResponse {
    String name;
    CategoryResponse category; // category
    int price;
    int salePercent;
    int salePrice;   // item-price 에서 계산
    String thumbnail;
    String description;
    long view;
    boolean wish;    // cart
    float rating;
    CompanyResponse company; // company

    public static ItemDetailResponse of(Item item) {
        return ItemDetailResponse.builder()
                .name(item.getName())
                .price(item.getPrice())
                .salePercent(item.getSalePercent())
                .salePrice(item.getSalePrice())
                .thumbnail(item.getThumbnail())
                .description(item.getDescription())
                .view(0)
                .wish(false)
                .rating(item.getRating())
                .category(CategoryResponse.of(item.getCategory()))
                .company(CompanyResponse.of(item.getCompany()))
                .build();
    }

}
