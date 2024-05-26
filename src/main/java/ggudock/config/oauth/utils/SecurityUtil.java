package ggudock.config.oauth.utils;

import ggudock.config.oauth.entity.UserPrincipal;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {
    public static String getCurrentName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal().equals("anonymousUser"))
            throw new BusinessException(ErrorCode.DO_NOT_LOGIN);

        UserPrincipal oAuth2User = (UserPrincipal) authentication.getPrincipal();

        return oAuth2User.getName();
    }
}