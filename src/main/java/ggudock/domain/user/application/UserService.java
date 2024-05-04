package ggudock.domain.user.application;

import ggudock.domain.user.dto.SignUpRequest;
import ggudock.domain.user.dto.UserResponse;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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


    public List<UserResponse> findUserList() {
        return userRepository.findAll().stream()
                .map(User::getId)
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public UserResponse findUserByNickname(String nickname) {
        return getUser(userRepository.findByNickname(nickname).getId());
    }

    public UserResponse findUserByUsername(String username) {
        return getUser(userRepository.findByUsername(username).getId());
    }

    public List<UserResponse> findUserListByEmail(String email) {
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

    private UserResponse createResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
