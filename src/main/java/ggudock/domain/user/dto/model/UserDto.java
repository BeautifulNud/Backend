package ggudock.domain.user.dto.model;

import ggudock.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private String profile;
    private String phoneNumber;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profile(user.getProfile())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
