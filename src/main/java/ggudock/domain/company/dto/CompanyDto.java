package ggudock.domain.company.dto;

import ggudock.domain.company.entity.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyDto {
    private Long id;
    private String name;
    private String telNumber;
    private String description;
    private String address;
    private int holiday;

    public static CompanyDto EntityToDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .telNumber(company.getTelNumber())
                .description(company.getDescription())
                .address(company.getAddress())
                .holiday(company.getHoliday())
                .build();
    }
}
