package ggudock.auth.service;

import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public User getMemberOrThrow(String userKey) {
        return userRepository.findByUserKey(userKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    public void checkAccess(String userKey, User user) {
        if (!user.getUserKey().equals(userKey)) {
            throw new BusinessException(ErrorCode.NO_ACCESS);
        }
    }
}
