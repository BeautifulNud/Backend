package ggudock.domain.user.entity;

import ggudock.config.oauth.entity.Role;
import ggudock.config.oauth.entity.ProviderType;
import ggudock.domain.user.dto.SignUpRequest;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @NotNull
    private String username;

    @Column(unique = true)
    @NotNull
    private String nickname;

    private String password;

    @NotNull
    private String email;

    private String imageUrl;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", nullable = false)
    private ProviderType providerType;

    @Builder
    public User(Long id, String username, String nickname, String password, String email, String imageUrl, String phoneNumber, Role role, ProviderType providerType) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.providerType = providerType;
    }

    public void signupUser(SignUpRequest request) {
        this.nickname = request.getNickname();
        this.phoneNumber = request.getPhoneNumber();
    }
}
