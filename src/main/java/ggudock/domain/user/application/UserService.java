package ggudock.domain.user.application;

import ggudock.config.jwt.JwtTokenProvider;
import ggudock.config.jwt.TokenInfo;
import ggudock.domain.user.dto.Request.UserRequest;
import ggudock.domain.user.dto.Response.UserResponse;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public UserResponse signup(Long userId, UserRequest.SignUp request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        user.signupUser(request);
        return getUser(user.getId());
    }

    @Transactional
    public UserResponse updateUsername(Long userId, String username) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        user.updateUsername(username);
        return getUser(userId);
    }

    @Transactional
    public Long delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        userRepository.delete(user);
        return userId;
    }

    public Long getLoginUser(String email) {
        User loginUser = userRepository.findByEmail(email);
        return loginUser.getId();
    }

    public UserResponse getUserByEmail(String email) {
        return getUser(userRepository.findByEmail(email).getId());
    }

    public UserResponse getUserByNickname(String nickname) {
        return getUser(userRepository.findByNickname(nickname).getId());
    }

    public List<UserResponse> getUserList() {
        return userRepository.findAll().stream()
                .map(User::getId)
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getUserByUsername(String username) {
        return userRepository.findByUsername(username).stream()
                .map(User::getId)
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public UserResponse getUser(Long userId) {
        return createResponse(userId);
    }

    private UserResponse createResponse(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        return UserResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Transactional
    public Boolean logout(UserRequest.Logout logout) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            log.info("유효하지 않은 요청입니다.");
            return false;
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue().set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
        return true;
    }

    @Transactional
    public TokenInfo reissue(UserRequest.Reissue reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            throw new IllegalArgumentException("Refresh Token이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        Object o = redisTemplate.opsForValue().get("RT:" + authentication.getName());
        String refreshToken = (String) o;
        // (추가) 로그아웃되어 Redis에 RefreshToken이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(refreshToken)) {
            throw new IllegalArgumentException("Refresh Token이 유효하지 않습니다.");
        }
        if (!refreshToken.equals(reissue.getRefreshToken())) {
            throw new IllegalArgumentException("Refresh Token이 일치하지 않습니다.");
        }

        // 4. 새로운 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        log.info("토큰 재발급에 성공했습니다.");
        return tokenInfo;
    }
}
