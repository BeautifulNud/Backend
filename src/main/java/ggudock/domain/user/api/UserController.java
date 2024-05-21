package ggudock.domain.user.api;

import ggudock.config.jwt.TokenInfo;
import ggudock.config.oauth.entity.UserPrincipal;
import ggudock.config.oauth.utils.SecurityUtil;
import ggudock.domain.user.application.UserService;
import ggudock.domain.user.dto.Request.UserRequest;
import ggudock.domain.user.dto.Response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User API", description = "유저 관련 Api (#7)")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @Operation(summary = "회원가입 후 닉네임, 전화번호 설정")
    @PatchMapping("/join/{userId}")
    public ResponseEntity<UserResponse> signinUser(@PathVariable Long userId, @Valid @RequestBody UserRequest.SignUp request) {
        return new ResponseEntity<>(userService.signup(userId, request), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "회원 이름 변경")
    @PatchMapping("/updateUsername/{userId}/{username}")
    public ResponseEntity<UserResponse> updateUsername(@PathVariable Long userId, @PathVariable String username) {
        return new ResponseEntity<>(userService.updateUsername(userId, username), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@Valid @RequestBody UserRequest.Logout logout) {
        return new ResponseEntity<>(userService.logout(logout), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "유저 삭제")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.delete(userId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "Id로 유저 찾기")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "이메일로 유저 찾기")
    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "닉네임으로 유저 찾기")
    @GetMapping("/nickname")
    public ResponseEntity<UserResponse> getUserByNickname(@RequestParam(name = "nickname") String nickname) {
        return new ResponseEntity<>(userService.getUserByNickname(nickname), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "유저 리스트")
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getUserList() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "이름으로 유저 찾기")
    @GetMapping("/listByUsername")
    public ResponseEntity<List<UserResponse>> getUserListByUsername(@RequestParam(name = "username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenInfo> reissue(@Valid @RequestBody UserRequest.Reissue reissue) {
        return new ResponseEntity<>(userService.reissue(reissue), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @Operation(summary = "Oauth2User 테스트")
    @GetMapping("/test/oauth1")
    public String testOAuthLogin(@AuthenticationPrincipal OAuth2User oAuth2User) { //세션 정보 받아오기 (DI 의존성 주입)
        System.out.println("authentication: " + oAuth2User.getAttributes());
        System.out.println("email : " + oAuth2User.getAttributes().get("email"));
        return "Oauth2User 테스트";
    }

    @Operation(summary = "Oauth2User 테스트")
    @GetMapping("/test/oauth2")
    public String testSecurityUtil() {
        Authentication authentication = SecurityUtil.getAuthentication();
        UserPrincipal oAuth2User = (UserPrincipal) authentication.getPrincipal();
        System.out.println("authentication: " + oAuth2User.getAttributes());
        System.out.println("email : " + oAuth2User.getAttributes().get("email"));
        return "Oauth2User 테스트";
    }
}
