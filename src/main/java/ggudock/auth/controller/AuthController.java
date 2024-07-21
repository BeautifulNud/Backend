package ggudock.auth.controller;


import ggudock.auth.dto.LoginResponse;
import ggudock.auth.jwt.TokenProvider;
import ggudock.auth.service.TokenService;
import ggudock.domain.user.application.UserService;
import ggudock.domain.user.dto.model.UserDto;
import ggudock.util.common.constants.TokenKey;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증", description = "인증 test API")
public class AuthController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @GetMapping("/auth/success")
    public ResponseEntity<LoginResponse> loginSuccess(@Valid LoginResponse loginResponse, HttpServletResponse response) {
        // 응답 헤더에 액세스 토큰 추가
        response.setHeader(AUTHORIZATION, TokenKey.TOKEN_PREFIX + loginResponse.accessToken());
        response.setHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/auth/time")
    public ResponseEntity<Long> getTokenExpireTime(@Valid LoginResponse loginResponse) {
        return ResponseEntity.ok(tokenProvider.getExpiration(loginResponse.accessToken()));
    }

    @GetMapping("/auth/login")
    public ResponseEntity<UserDto> login(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        return ResponseEntity.ok(userService.userInfo(userDetails.getUsername()));
    }

    @DeleteMapping("/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        tokenService.deleteRefreshToken(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}

