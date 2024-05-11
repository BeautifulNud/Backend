package ggudock.domain.user.api;

import ggudock.config.jwt.TokenInfo;
import ggudock.domain.user.application.UserService;
import ggudock.domain.user.dto.SignUpRequest;
import ggudock.domain.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@Tag(name = "유저 API", description = "유저 관련 Api (#7)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입 후 닉네임, 전화번호 설정")
    @PatchMapping("/join/{userId}")
    public ResponseEntity<UserResponse> signinUser(@PathVariable Long userId, @Valid @RequestBody SignUpRequest form) {
        return new ResponseEntity<>(userService.signup(userId, form), HttpStatusCode.valueOf(200));
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

    @Operation(summary = "이름과 이메일로 유저 찾기")
    @GetMapping("/usernameAndEmail")
    public ResponseEntity<UserResponse> getUserByUsernameAndEmail(@RequestParam(name = "username") String username,
                                                                  @RequestParam(name = "email") String email) {
        return new ResponseEntity<>(userService.getUserByUsernameAndEmail(username, email), HttpStatusCode.valueOf(200));
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

    @Operation(summary = "이메일로 유저 리스트 찾기")
    @GetMapping("/listByEmail")
    public ResponseEntity<List<UserResponse>> getUserListByEmail(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(userService.getUserListByEmail(email), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/reissue/test")
    public ResponseEntity<TokenInfo> reissue(@RequestBody TokenInfo reissue) {
        return userService.reissue(reissue);
    }
}
