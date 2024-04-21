package ggudock.domain.category.api.dto;

import ggudock.domain.subscription.entity.Subscription;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SaveForm {

    @NotBlank
    private String name;
}
