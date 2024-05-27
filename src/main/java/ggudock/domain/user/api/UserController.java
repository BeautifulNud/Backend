package ggudock.domain.user.api;

import ggudock.config.jwt.TokenInfo;
import ggudock.config.oauth.utils.SecurityUtil;
import ggudock.domain.user.application.UserService;
import ggudock.domain.user.dto.Request.UserRequest;
import ggudock.domain.user.dto.Response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련 Api (#7)")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입 후 설정", description = "회원가입 직후 닉네임과 전화번호를 설정한다.")
    @PatchMapping("/join/{userId}")
    public ResponseEntity<UserResponse> signinUser(@PathVariable(name = "userId") Long userId, @Valid @RequestBody UserRequest.SignUp request) {
        return new ResponseEntity<>(userService.signup(userId, request), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "로그인한 유저 Id 받아오기", description = "현재 로그인된 회원의 Id값을 받아온다.")
    @PatchMapping("/getId")
    public ResponseEntity<Long> getLoginUserId() {
        return new ResponseEntity<>(userService.getLoginUser(SecurityUtil.getCurrentName()), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "회원 이름 변경", description = "회원 Id를 통해 회원의 이름을 변경한다.")
    @PatchMapping("/updateUsername/{userId}")
    public ResponseEntity<UserResponse> updateUsername(@PathVariable(name = "userId") Long userId, @RequestParam(name = "username") String username) {
        return new ResponseEntity<>(userService.updateUsername(userId, username), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "로그아웃", description = "액세스 토큰과 리프레쉬 토큰을 통해 로그아웃한다.")
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@Valid @RequestBody UserRequest.Logout logout) {
        return new ResponseEntity<>(userService.logout(logout), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "회원 삭제", description = "회원 Id를 통해 회원 정보를 삭제한다.")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(userService.delete(userId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "Id로 회원 찾기", description = "회원 Id를 통해 회원 정보를 받아온다.")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "이메일로 회원 찾기", description = "이메일을 통해 회원 정보를 받아온다.")
    @GetMapping("/email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "닉네임으로 회원 찾기", description = "닉네임을 통해 회원 정보를 받아온다.")
    @GetMapping("/nickname")
    public ResponseEntity<UserResponse> getUserByNickname(@RequestParam(name = "nickname") String nickname) {
        return new ResponseEntity<>(userService.getUserByNickname(nickname), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "회원 리스트", description = "전체 회원 정보를 받아온다.")
    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> getUserList() {
        return new ResponseEntity<>(userService.getUserList(), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "이름으로 회원 찾기", description = "같은 이름의 회원들의 정보를 받아온다.")
    @GetMapping("/list/{username}")
    public ResponseEntity<List<UserResponse>> getUserListByUsername(@PathVariable(name = "username") String username) {
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "토큰 재발급", description = "액세스토큰과 리프레시 토큰을 통해 토큰을 재발급 받는다.")
    @PostMapping("/reissue")
    public ResponseEntity<TokenInfo> reissue(@Valid @RequestBody UserRequest.Reissue reissue) {
        return new ResponseEntity<>(userService.reissue(reissue), HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @Operation(summary = "Oauth2User 테스트")
    @GetMapping("/test/oauth1")
    public Map<String, Object> testOAuthLogin(@AuthenticationPrincipal OAuth2User oAuth2User) { //세션 정보 받아오기 (DI 의존성 주입)
        return oAuth2User.getAttributes();
    }

    @Operation(summary = "Oauth2User 테스트")
    @GetMapping("/test/oauth2")
    public ResponseEntity<String> testSecurityUtil() {
        return new ResponseEntity<>(SecurityUtil.getCurrentName(), HttpStatusCode.valueOf(200));
    }
}
