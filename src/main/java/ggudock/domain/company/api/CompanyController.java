package ggudock.domain.company.api;

import ggudock.domain.company.application.CompanyService;
import ggudock.domain.company.dto.CompanyRequest;
import ggudock.domain.company.dto.CompanyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "업체", description = "업체 api")
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    @Operation(summary = "업체 저장", description = "정보를 받아 업체를 저장한다.")
    @PostMapping()
    public ResponseEntity<CompanyResponse> saveCompany(@RequestBody CompanyRequest companyRequest) {
        return new ResponseEntity<>(companyService.saveCompany(companyRequest), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "업체 삭제", description = "업체 Id를 받아 업체를 삭제한다.")
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Long> deleteCompany(@PathVariable("companyId") Long companyId) {
        return new ResponseEntity<>(companyService.deleteCompany(companyId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "업체 Id로 리뷰 정보 받아오기", description = "업체 Id를 통해 단일 업체의 정보를 받아온다.")
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponse> getDetail(@PathVariable("companyId") Long companyId) {
        return new ResponseEntity<>(companyService.getDetail(companyId), HttpStatusCode.valueOf(200));
    }


    @Operation(summary = "업체 리스트", description = "모든 업체들의 정보를 받아온다.")
    @GetMapping("/list")
    public ResponseEntity<Page<CompanyResponse>> getCompanyList(@RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(companyService.getCompanyPage(page), HttpStatusCode.valueOf(200));
    }

}
