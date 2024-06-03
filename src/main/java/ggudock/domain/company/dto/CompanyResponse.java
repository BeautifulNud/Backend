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
    private String name;
    private String telNumber;
    private String description;
    private String address;
    private DayOfWeek holiday;

    public CompanyResponse(Company company) {
        this.name = company.getName();
        this.telNumber = company.getTelNumber();
        this.description = company.getDescription();
        this.address = company.getAddress();
        this.holiday = company.getHoliday();
    }
}
