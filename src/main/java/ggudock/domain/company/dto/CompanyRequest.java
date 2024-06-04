package ggudock.domain.company.dto;

import ggudock.global.validator.customvalid.AddressValid;
import ggudock.global.validator.customvalid.PhoneValid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CompanyRequest {
    @NotEmpty
    private String name;

    @PhoneValid
    private String telNumber;

    @NotNull
    private String description;

    @AddressValid
    private String address;

    private DayOfWeek holiday;

    @Builder
    public CompanyRequest(String name, String telNumber, String description, String address, DayOfWeek holiday) {
        this.name = name;
        this.telNumber = telNumber;
        this.description = description;
        this.address = address;
        this.holiday = holiday;
    }
}
