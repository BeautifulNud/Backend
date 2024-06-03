package ggudock.domain.company.application;

import ggudock.domain.company.dto.CompanyRequest;
import ggudock.domain.company.dto.CompanyResponse;
import ggudock.domain.company.entity.Company;
import ggudock.domain.company.repository.CompanyRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyResponse saveCompany(CompanyRequest companyRequest) {
        Company company = createCompany(companyRequest);
        companyRepository.save(company);
        return getDetail(company.getId());
    }

    @Transactional
    public Long deleteCompany(Long companyId) {
        companyRepository.deleteById(companyId);
        return companyId;
    }

    public Company getCompany(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMPANY));
    }

    public Page<CompanyResponse> getCompanyPage(int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Company> companyPage = createCompanyPage(pageRequest);
        return createCompanyResponsePage(companyPage);
    }

    public CompanyResponse getDetail(Long companyId) {
        return createResponse(companyId);
    }

    private CompanyResponse createResponse(Long companyId) {
        Company company = getCompany(companyId);
        return CompanyResponse.builder()
                .name(company.getName())
                .telNumber(company.getTelNumber())
                .address(company.getAddress())
                .description(company.getDescription())
                .holiday(company.getHoliday())
                .build();
    }

    private static Company createCompany(CompanyRequest companyRequest) {
        return Company.builder()
                .name(companyRequest.getName())
                .address(companyRequest.getAddress())
                .description(companyRequest.getDescription())
                .telNumber(companyRequest.getTelNumber())
                .holiday(companyRequest.getHoliday())
                .build();
    }

    private Page<Company> createCompanyPage(PageRequest pageRequest) {
        return companyRepository.findAll(pageRequest);
    }

    private static Page<CompanyResponse> createCompanyResponsePage(Page<Company> companyPage) {
        return companyPage.map(CompanyResponse::new);
    }

    private static PageRequest createPageRequest(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return PageRequest.of(page, 10, sort);
    }
}
