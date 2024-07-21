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

    public UserDto userInfo(String userKey) {
        User user = findByUserKeyOrThrow(userKey);
        return UserDto.fromEntity(user);
    }

    @Transactional
    public UserDto userEdit(UserEditRequest request, String userKey) {
        User user = findByUserKeyOrThrow(userKey);
        user.updateUser(request);
        return UserDto.fromEntity(user);
    }

    private User findByUserKeyOrThrow(String userKey) {
        return userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));
    }
}
