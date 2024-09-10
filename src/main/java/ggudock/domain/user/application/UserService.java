package ggudock.domain.user.application;

import ggudock.domain.user.dto.UserEditRequest;
import ggudock.domain.user.dto.model.UserDto;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.exception.UserException;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String email){
        return findByEmailOrThrow(email);
    }

    public UserDto userInfo(String eamil) {
        User user = findByEmailOrThrow(eamil);
        return UserDto.fromEntity(user);
    }

    @Transactional
    public UserDto userEdit(UserEditRequest request, String email) {
        User user = findByEmailOrThrow(email);
        user.updateUser(request);
        return UserDto.fromEntity(user);
    }

    private User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));
    }
}
