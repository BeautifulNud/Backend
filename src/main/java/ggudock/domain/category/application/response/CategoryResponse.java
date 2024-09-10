package ggudock.domain.category.application.response;

import ggudock.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryResponse {

    private String name;
    private String icon;

    public CategoryResponse(Category category) {
        this.name = category.getName();
        this.icon = category.getIcon();
    }
}