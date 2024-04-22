package ggudock.domain.item.dto;

import ggudock.domain.company.dto.CompanyDto;
import lombok.Builder;

public record ItemDetailResponse(
    String name,
    String category, // category
    int price,
    int salePercent,
    int salePrice,   // item-price 에서 계산
    String thumbnail,
    String description,
    boolean wish,    // cart
    long rating,
    CompanyDto companyDto // company
) {
    @Builder
    public ItemDetailResponse {
    }
}
