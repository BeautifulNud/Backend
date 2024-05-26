package ggudock.domain.user.dto.Request;

import ggudock.global.validator.customvalid.EmailValid;
import ggudock.global.validator.customvalid.PhoneValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUp {
        private Long userId;

        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        @EmailValid
        private String nickname;

        @NotBlank(message = "전화번호는 필수 입력값입니다.")
        @Column(name = "phone_number")
        @PhoneValid
        private String phoneNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Reissue {
        @NotEmpty(message = "accessToken 을 입력해주세요.")
        private String accessToken;

        @NotEmpty(message = "refreshToken 을 입력해주세요.")
        private String refreshToken;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Logout {
        @NotEmpty(message = "잘못된 요청입니다.")
        private String accessToken;

        @NotEmpty(message = "잘못된 요청입니다.")
        private String refreshToken;
    }
}
