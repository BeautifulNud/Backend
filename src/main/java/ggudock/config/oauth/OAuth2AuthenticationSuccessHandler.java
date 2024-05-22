package ggudock.config.oauth;

import ggudock.config.jwt.JwtTokenProvider;
import ggudock.config.jwt.TokenInfo;
import ggudock.config.oauth.entity.ProviderType;
import ggudock.config.oauth.entity.Role;
import ggudock.config.oauth.entity.UserPrincipal;
import ggudock.config.oauth.utils.CookieUtil;
import ggudock.config.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static ggudock.config.jwt.JwtTokenProvider.getRefreshTokenExpireTimeCookie;
import static ggudock.config.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static ggudock.config.repository.OAuth2AuthorizationRequestBasedOnCookieRepository.REFRESH_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private String redirectUri = "localhost:8080/swagger-ui";
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 로그인 성공!");
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.error("Response has already been committed. Unable to redirect to {}", targetUrl);
            return;
        }
        // 인증 관련 설정값, 쿠키 제거
        clearAuthenticationAttributes(request, response);
        log.info("onAuthenticationSucces 메서드");
        // 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            log.error("determineTargetUrl - redirectUri : {} , 인증을 진행할 수 없습니다.", redirectUri);
            throw new IllegalArgumentException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        ProviderType providerType = ProviderType.valueOf(oauthToken.getAuthorizedClientRegistrationId().toUpperCase());
        UserPrincipal user = ((UserPrincipal) authentication.getPrincipal());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Role role = Role.USER;

        //액세스 토큰, 리프레쉬 토큰을 저장한 token info 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(userInfo.getEmail(), role.name());

        //redis에 refresh 토큰 저장
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, tokenInfo.getRefreshToken(), getRefreshTokenExpireTimeCookie());

        log.info("access token : {}", tokenInfo.getAccessToken());
        log.info("refresh token : {}", tokenInfo.getRefreshToken());

//        try {
//            setTokenResponse(response, tokenInfo.getAccessToken(), tokenInfo.getRefreshToken());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // 액세스 토큰,리프레시 토큰을 패스에 추가
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", tokenInfo.getAccessToken())
                .queryParam("refreshToken", tokenInfo.getRefreshToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizedUri = URI.create(redirectUri);

        return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                && authorizedUri.getPort() == clientRedirectUri.getPort();
    }

//    private void setTokenResponse(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("accessToken", accessToken);
//        result.put("refreshToken", refreshToken);
//
//        response.getWriter().println(new ObjectMapper().writeValueAsString(result));
//    }
}