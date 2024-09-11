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

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .name(category.getName())
                .icon(category.getIcon())
                .build();
    }
}