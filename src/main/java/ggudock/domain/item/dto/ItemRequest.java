package ggudock.domain.item.dto;

import ggudock.domain.category.entity.Category;
import ggudock.domain.company.entity.Company;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @NotNull
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int salePercent;

    @NotNull
    private String description;

    @NotNull
    private String plan;

    @NotNull
    private String categoryName;

    @NotNull
    private String companyName;
}
