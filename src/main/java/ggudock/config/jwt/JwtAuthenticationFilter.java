package ggudock.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> redisTemplate) {
        super();
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 1. Request Header 에서 JWT 토큰 추출
        String token = resolveToken(request);

        // log를 이용한 확인
        log.info("[This is Verifying token!!]");
        log.info(request.getRequestURL().toString());
        // 2. validateToken으로 토큰 유효성 검사
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            //Redis에 해당 accessToken logout 여부 확인
            String isLogout = redisTemplate.opsForValue().get(token);

            //로그아웃 하지 않은 경우
            if (ObjectUtils.isEmpty(isLogout)) {
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (StringUtils.hasText(headerValue) && headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(7);
        }

        return null;
    }
}
