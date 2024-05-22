package ggudock.domain.address.api.request;

import ggudock.global.validator.customvalid.AddressValid;
import ggudock.global.validator.customvalid.PhoneValid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AddressRequest {
    @NotNull
    private String name;

    @NotNull
    private String alias;

    @PhoneValid
    private String phoneNumber;
    @NotNull
    private String addressNumber;
    @AddressValid
    private String address;

    @NotNull
    private String detailAddress;
    private String exitCode;
    private String deliveryMessage;

    @Builder
    public AddressRequest(String name, String alias, String phoneNumber, String addressNumber, String address, String detailAddress, String exitCode, String deliveryMessage) {
        this.name = name;
        this.alias = alias;
        this.phoneNumber = phoneNumber;
        this.addressNumber = addressNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.exitCode = exitCode;
        this.deliveryMessage = deliveryMessage;
    }
}
