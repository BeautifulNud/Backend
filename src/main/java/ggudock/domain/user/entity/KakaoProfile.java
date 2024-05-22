package ggudock.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoProfile {
    private final Long id;
    @JsonProperty("connected_at")
    private final String connectedAt;
    private final Properties properties;
    @JsonProperty("kakao_account")
    private final KakaoAccount kakaoAccount;

    @Data
    public static class Properties {
        private final String nickname;
        @JsonProperty("profile_image")
        private final String profileImage;
        @JsonProperty("thumbnail_image")
        private final String thumbnailImage;
    }

    @Data
    public static class KakaoAccount {
        @JsonProperty("profile_nickname_needs_agreement")
        private final Boolean profileNicknameNeedsAgreement;
        private final Profile profile;
        @JsonProperty("has_email")
        private final Boolean hasEmail;
        @JsonProperty("email_needs_agreement")
        private final Boolean emailNeedsAgreement;
        @JsonProperty("is_email_valid")
        private final Boolean isEmailValid;
        @JsonProperty("is_email_verified")
        private final Boolean isEmailVerified;
        private final String email;

        @Data
        public static class Profile {
            private final String nickname;
            @JsonProperty("thumbnail_image_url")
            private final String thumbnailImageUrl;
            @JsonProperty("profile_image_url")
            private final String profileImageUrl;
        }
    }
}