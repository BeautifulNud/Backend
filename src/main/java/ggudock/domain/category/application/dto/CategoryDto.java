package ggudock.domain.category.application.dto;

import ggudock.domain.category.entity.Category;
import ggudock.domain.subscription.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryDto {

    private String name;

    public CategoryDto(Category category) {
        this.name = category.getName();
    }
}