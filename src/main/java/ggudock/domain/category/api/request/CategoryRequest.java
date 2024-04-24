package ggudock.domain.category.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String icon;

    @Builder
    public CategoryRequest(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
}
