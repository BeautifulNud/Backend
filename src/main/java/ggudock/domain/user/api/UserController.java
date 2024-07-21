package ggudock.domain.user.api;

import ggudock.auth.annotaion.RoleUser;
import ggudock.domain.user.application.UserService;
import ggudock.domain.user.dto.UserEditRequest;
import ggudock.domain.user.dto.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련 Api (#7)")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @RoleUser
    @Operation(summary = "유저 정보", description = "유저의 정보를 받아오기.")
    @GetMapping
    public ResponseEntity<UserDto> userInfo(
            @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(userService.userInfo(userDetails.getUsername()), HttpStatusCode.valueOf(200));
    }



    @RoleUser
    @Operation(summary = "유저 수정", description = "유저의 전화번호를 수정한다.")
    @PatchMapping
    public ResponseEntity<UserDto> userEdit(
            @RequestBody @Valid UserEditRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.userEdit(request, userDetails.getUsername()));
    }
}
