package ggudock.domain.user.entity;

import ggudock.domain.user.model.Role;
import ggudock.domain.user.model.ProviderType;
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

    @NotNull
    private String nickname;

    private String picture;

    @NotNull
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_provider", nullable = false)
    private ProviderType providerType;

    @Builder
    public User(Long id, String username, String nickname, String picture, String email, String phoneNumber, Role role, ProviderType providerType) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.picture = picture;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.providerType = providerType;
    }
}
