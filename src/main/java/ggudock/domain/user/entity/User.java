package ggudock.domain.user.entity;

import ggudock.util.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="user_name")
    @NotNull
    private String name;
    @NotNull
    private String nickname;
    private String picture;
    @NotNull
    private String email;

    @Builder
    public User(String name, String nickname, String picture, String email) {
        this.name = name;
        this.nickname = nickname;
        this.picture = picture;
        this.email = email;
    }
}
