package ggudock.domain.user.application;

import ggudock.config.oauth.OAuth2UserInfo;
import ggudock.config.oauth.OAuth2UserInfoFactory;
import ggudock.config.oauth.entity.ProviderType;
import ggudock.config.oauth.entity.Role;
import ggudock.config.oauth.entity.UserPrincipal;
import ggudock.config.oauth.exception.OAuthProviderMissMatchException;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        log.info("userRequest.getAttributes : {}", super.loadUser(userRequest).getAttributes());

        try {
            return process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    //인증을 요청하는 사용자에 따라서 없는 회원이면 회원가입
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        //현재 진행중인 서비스를 구분하기 위해 문자열을 받음
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepository.findByEmail(userInfo.getEmail());

        if (savedUser != null) {
            log.info("{} 계정에 계정이 존재합니다.", savedUser.getProviderType());
            return UserPrincipal.create(savedUser, user.getAttributes());
            //            throw new OAuthProviderMissMatchException("already_signed_up :" + savedUser.getProviderType());
        } else {
            savedUser = createUser(userInfo, providerType);
            log.info("{}을 통해 계정을 생성했습니다.", savedUser.getProviderType());
        }
        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    //가져온 사용자 정보를 통해서 회원가입 실행
    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        User user = User.builder()
                .username(userInfo.getName())
                .email(userInfo.getEmail())
                .imageUrl(userInfo.getImageUrl())
                .providerType(providerType)
                .role(Role.USER)
                .build();

        return userRepository.saveAndFlush(user);
    }
}
