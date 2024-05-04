package ggudock.domain.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @NotBlank
    private String nickname;
    @Column(name = "phone_number")
    private String phoneNumber;

    public SignUpRequest(String nickname, String phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
