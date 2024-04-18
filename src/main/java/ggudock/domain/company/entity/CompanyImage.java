package ggudock.domain.company.entity;

import ggudock.util.BaseTimeEntity;
import ggudock.validator.customvalid.S3Valid;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyImage extends BaseTimeEntity {
    @Id
    @Column(name = "company_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @S3Valid
    private String picture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder
    public CompanyImage(String picture, Company company) {
        this.picture = picture;
        this.company = company;
    }
}
