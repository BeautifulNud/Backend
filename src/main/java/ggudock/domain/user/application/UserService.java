package ggudock.domain.user.application;

import ggudock.domain.user.dto.SignUpDto;
import ggudock.domain.user.dto.UserResponse;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse signup(Long userId, SignUpDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다"));
        user.signupUser(dto);
        return getUser(userId);
    }

    @Transactional
    public Long delete(Long userId) {
        userRepository.delete(findUserById(userId));
        return userId;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId + "가 없습니다"));
    }

    public List<UserResponse> findUserList() {
        return userRepository.findAll().stream()
                .map(User::getId)
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    public UserResponse findUserByNickname(String nickname) {
        return getUser(userRepository.findByNickname(nickname));
    }

    public UserResponse findUserByUsername(String username) {
        return getUser(userRepository.findByUsername(username));
    }

    public UserResponse getUser(Long userId) {
        return createResponse(userId);
    }

    private UserResponse createResponse(Long userId) {
        User user = findUserById(userId);
        return UserResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
