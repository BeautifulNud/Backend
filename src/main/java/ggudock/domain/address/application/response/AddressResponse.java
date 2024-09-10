package ggudock.domain.address.application.response;

import ggudock.domain.address.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressResponse {
    private String name;
    private String alias;
    private String phoneNumber;
    private String addressNumber;
    private String address;
    private String detailAddress;
    private boolean defaultAddress;
    private String exitCode;
    private String deliveryMessage;

    @Builder
    public AddressResponse(Address address){
        this.name = address.getName();
        this.alias = address.getAlias();
        this.phoneNumber = address.getPhoneNumber();
        this.addressNumber = address.getAddressNumber();
        this.address = address.getAddress();
        this.detailAddress = address.getDetailAddress();
        this.defaultAddress = address.isDefaultAddress();
        this.exitCode = address.getExitCode();
        this.deliveryMessage = address.getDeliveryMessage();
    }
}
