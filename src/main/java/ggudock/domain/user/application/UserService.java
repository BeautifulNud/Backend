package ggudock.domain.user.application;

import ggudock.config.jwt.JwtTokenProvider;
import ggudock.config.jwt.TokenInfo;
import ggudock.domain.user.dto.SignUpRequest;
import ggudock.domain.user.dto.UserResponse;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public UserResponse signup(Long userId, SignUpRequest dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다"));
        user.signupUser(dto);
        return getUser(userId);
    }

    @Transactional
    public Long delete(Long userId) {
        User user = userRepository.findUserById(userId);
        userRepository.delete(user);
        return userId;
    }

    public UserResponse getUserByNickname(String nickname) {
        return getUser(userRepository.findByNickname(nickname).getId());
    }

    public UserResponse getUserByUsernameAndEmail(String username, String email) {
        return getUser(userRepository.findByUsernameAndEmail(username, email).getId());
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

    public List<UserResponse> getUserListByEmail(String email) {
        return userRepository.findByEmail(email).stream()
                .map(User::getId)
                .map(this::getUser)
                .collect(Collectors.toList());
    }

//    public UserResponse getUserWithAuthorities(String username) {
//        return createResponse((Objects.requireNonNull(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null))));
//    }
//
//    // 현재 유저(SecurityContext) 객체의 권한정보를 가져옴
//    public UserResponse getMyUserWithAuthorities() {
//        return createResponse(Objects.requireNonNull(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null)));
//    }

    public UserResponse getUser(Long userId) {
        return createResponse(userId);
    }

    private UserResponse createResponse(Long userId) {
        User user = userRepository.findUserById(userId);
        return UserResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public ResponseEntity<?> logout(TokenInfo logout) {
        // 1. Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            log.info("유효하지 않은 요청입니다.");
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
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

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<TokenInfo> reissue(TokenInfo reissue) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            log.info("RefreshToken이 유효하지 않습니다.");
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        Object o = redisTemplate.opsForValue().get("RT:" + authentication.getName());
        String refreshToken = (String) o;
        // (추가) 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if (ObjectUtils.isEmpty(refreshToken)) {
            log.info("RefreshToken이 유효하지 않습니다.");
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
        if (!refreshToken.equals(reissue.getRefreshToken())) {
            log.info("RefreshToken이 일치하지 않습니다.");
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

        // 4. 새로운 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        log.info("토큰 재발급에 성공했습니다.");
        return new ResponseEntity<>(tokenInfo, HttpStatusCode.valueOf(400));
    }
}
