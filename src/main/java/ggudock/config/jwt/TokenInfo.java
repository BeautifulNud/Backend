package ggudock.config.jwt;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

    private String grantType;

    private String accessToken;

    private String refreshToken;

    private Long refreshTokenExpirationTime;
}
