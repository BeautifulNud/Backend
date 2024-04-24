package ggudock.domain.category.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String icon;
}
