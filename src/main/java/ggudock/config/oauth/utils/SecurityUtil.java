package ggudock.config.oauth.utils;

import ggudock.config.oauth.entity.UserPrincipal;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {
    public static String getCurrentName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal oAuth2User = (UserPrincipal) authentication.getPrincipal();

        return oAuth2User.getName();
    }
}