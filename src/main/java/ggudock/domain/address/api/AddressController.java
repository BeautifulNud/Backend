package ggudock.domain.address.api;

import ggudock.config.oauth.entity.UserPrincipal;
import ggudock.config.oauth.utils.SecurityUtil;
import ggudock.domain.address.api.request.AddressRequest;
import ggudock.domain.address.application.AddressService;
import ggudock.domain.address.application.response.AddressResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="배송지 주소",description = "배송지 주소 api")
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "배송지 주소 저장",description = "배송지를 저장한다.")
    @PostMapping()
    public ResponseEntity<AddressResponse> saveAddress(@Valid @RequestBody AddressRequest addressRequest) {
        return new ResponseEntity<>(addressService.saveAddress(addressRequest,SecurityUtil.getCurrentName()),HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "배송지 주소 삭제",description = "주소 id를 받아서 관련된 배송지를 삭제한다.")
    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("addressId") Long addressId){
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "배송지 주소 받기",description = "주소 Id로 배송지 주소를 받는다.")
    @GetMapping
    public ResponseEntity<AddressResponse> getDetail(@RequestParam("addressId") Long addressId){
        return new ResponseEntity<>(addressService.getDetail(addressId),HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "본인 배송지 주소 받기",description = "본인의 모든 배송지 주소를 받는다.")
    @GetMapping("/get-addressList")
    public ResponseEntity<List<AddressResponse>> getAddressList(){
        return new ResponseEntity<>(addressService.getAddressList(SecurityUtil.getCurrentName()),HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "본인 기본 배송지 주소 받기",description = "본인의 기본배송지 주소를 받는다.")
    @GetMapping("/get-default-address")
    public ResponseEntity<AddressResponse> getDefaultAddress(){
        return new ResponseEntity<>(addressService.getDefaultAddress(SecurityUtil.getCurrentName()),HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "배송지 정보 수정",description = "본인의 배송지정보 수정")
    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponse> changeAddress(@Valid @RequestBody AddressRequest addressRequest,@PathVariable("addressId")Long addressId){
        return new ResponseEntity<>(addressService.changeAddress(addressRequest,addressId),HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "기본 배송지 설정",description = "기본 배송지 설정")
    @PatchMapping("/{addressId}/default-address")
    public ResponseEntity<AddressResponse> changeAddress(@PathVariable("addressId")Long addressId){
        return new ResponseEntity<>(addressService.changeDefault(SecurityUtil.getCurrentName(),addressId),HttpStatusCode.valueOf(200));
    }
}