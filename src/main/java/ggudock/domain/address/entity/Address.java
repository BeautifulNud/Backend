package ggudock.domain.address.entity;

import ggudock.domain.user.entity.User;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseTimeEntity {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String alias;

    @NotNull
    private String phoneNumber;
    @NotNull
    private String addressNumber;
    @NotNull
    private String address;

    @NotNull
    private String detailAddress;

    private boolean defaultAddress;

    private String exitCode;
    private String deliveryMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Address(String name, String alias, String phoneNumber, String addressNumber, String address, String detailAddress, String exitCode, String deliveryMessage, User user) {
        this.name = name;
        this.alias = alias;
        this.phoneNumber = phoneNumber;
        this.addressNumber = addressNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.defaultAddress = false;
        this.exitCode = exitCode;
        this.deliveryMessage = deliveryMessage;
        this.user = user;
    }

    public void changeAddress(String name, String alias, String phoneNumber, String addressNumber, String address, String detailAddress, String exitCode, String deliveryMessage) {
        this.name = name;
        this.alias = alias;
        this.phoneNumber = phoneNumber;
        this.addressNumber = addressNumber;
        this.address = address;
        this.detailAddress = detailAddress;
        this.exitCode = exitCode;
        this.deliveryMessage = deliveryMessage;
    }

    public void changeDefault(){
        this.defaultAddress= !this.defaultAddress;
    }

}