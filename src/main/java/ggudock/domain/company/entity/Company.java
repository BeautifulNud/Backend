package ggudock.domain.company.entity;

import ggudock.util.BaseTimeEntity;
import ggudock.validator.customvalid.AddressValid;
import ggudock.validator.customvalid.DescriptionValid;
import ggudock.validator.customvalid.PhoneValid;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company extends BaseTimeEntity {
    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    @NotNull
    private String name;
    @PhoneValid
    private String telNumber;
    @DescriptionValid
    private String description;
    @AddressValid
    private String address;
    private int holiday;

    @Builder
    public Company(String name, String telNumber, String description, String address, int holiday) {
        this.name = name;
        this.telNumber = telNumber;
        this.description = description;
        this.address = address;
        this.holiday = holiday;
    }
}
