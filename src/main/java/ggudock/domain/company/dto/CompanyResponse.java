package ggudock.domain.company.dto;

import ggudock.domain.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;

@Data
@Builder
@AllArgsConstructor
public class CompanyResponse {
    private Long id;
    private String name;
    private String telNumber;
    private String description;
    private String address;
    private DayOfWeek holiday;

    public static CompanyResponse of(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .telNumber(company.getTelNumber())
                .description(company.getDescription())
                .address(company.getAddress())
                .holiday(company.getHoliday())
                .build();
    }
}
