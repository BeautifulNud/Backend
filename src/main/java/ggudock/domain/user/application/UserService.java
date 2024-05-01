package ggudock.domain.user.application;

import ggudock.domain.user.dto.SignUpDto;
import ggudock.domain.user.dto.UserResponse;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse signup(Long userId, SignUpDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다"));
        user.signupUser(dto);
        return getUser(user);
    }

    @Transactional
    public Long delete(Long userId) {
        userRepository.delete(findUserById(userId));
        return userId;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다"));
    }

    public UserResponse findUserByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname);
        return getUser(user);
    }

    public UserResponse findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return getUser(user);
    }

    public UserResponse getUser(User user) {
        return createResponse(user);
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
