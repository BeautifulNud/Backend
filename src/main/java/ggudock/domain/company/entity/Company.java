package ggudock.domain.company.entity;

import ggudock.global.validator.customvalid.PhoneValid;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

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

    @Column(name = "tel_number", unique = true)
    @PhoneValid
    private String telNumber;

    private String description;

    private String address;

    @Enumerated(EnumType.STRING)
    private DayOfWeek holiday;

    @Builder
    public Company(String name, String telNumber, String description, String address, DayOfWeek holiday) {
        this.name = name;
        this.telNumber = telNumber;
        this.description = description;
        this.address = address;
        this.holiday = holiday;
    }
}
