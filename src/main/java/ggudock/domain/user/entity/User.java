package ggudock.domain.user.entity;

import ggudock.domain.user.dto.UserEditRequest;
import ggudock.util.BaseTimeEntity;
import jakarta.persistence.*;
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

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 40, unique = true)
    private String email;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false, unique = true)
    private String userKey;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public User(Long id, String name, String email, String profile, String userKey, String phoneNumber, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.userKey = userKey;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public void updateUser(UserEditRequest request) {
        this.name = request.name();
        this.phoneNumber = request.phoneNumber();
    }
}
