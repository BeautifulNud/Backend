package ggudock.domain.user.entity;

import ggudock.util.BaseTimeEntity;
import ggudock.validator.customvalid.EmailValid;
import ggudock.validator.customvalid.NicknameValid;
import ggudock.validator.customvalid.S3Valid;
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
    @Column(name ="user_name")
    @NotNull
    private String name;
    @NotNull
    @NicknameValid
    private String nickname;
    @S3Valid
    private String picture;
    @NotNull
    @EmailValid
    private String email;

    @Builder
    public User(String name, String nickname, String picture, String email) {
        this.name = name;
        this.nickname = nickname;
        this.picture = picture;
        this.email = email;
    }
}
