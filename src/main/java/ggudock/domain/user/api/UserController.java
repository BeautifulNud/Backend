package ggudock.domain.user.api;

import ggudock.domain.user.application.UserService;
import ggudock.domain.user.dto.SignUpRequest;
import ggudock.domain.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PatchMapping("/join/{userId}")
    public ResponseEntity<UserResponse> signinUser(@PathVariable Long userId, @Valid @RequestBody SignUpRequest form) {
        return new ResponseEntity<>(userService.signup(userId, form), HttpStatusCode.valueOf(200));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.delete(userId), HttpStatusCode.valueOf(200));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> searchUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUser(userId), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<UserResponse> searchUserByUsername(@RequestParam String username) {
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/findByNickname")
    public ResponseEntity<UserResponse> searchUserByNickname(@RequestParam String nickname) {
        return new ResponseEntity<>(userService.findUserByNickname(nickname), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> searchUserList() {
        return new ResponseEntity<>(userService.findUserList(), HttpStatusCode.valueOf(200));
    }

    @GetMapping("/listByEmail")
    public ResponseEntity<List<UserResponse>> searchUserListByEmail(String email) {
        return new ResponseEntity<>(userService.findUserListByEmail(email), HttpStatusCode.valueOf(200));
    }

//    @GetMapping("/")
//    public ResponseEntity<UserResponse> getUserInfo() {
//        return new ResponseEntity<>(userService.getMyUserWithAuthorities(), HttpStatusCode.valueOf(200));
//    }
//
//    @GetMapping("/{username}")
//    public ResponseEntity<UserResponse> getUserInfo(@PathVariable String username) {
//        return new ResponseEntity<>(userService.getUserWithAuthorities(username), HttpStatusCode.valueOf(200));
//    }
}
