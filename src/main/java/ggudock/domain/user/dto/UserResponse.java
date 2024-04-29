package ggudock.domain.user.dto;

import lombok.Builder;

public record UserResponse(
        String username,
        String nickname,
        String email,
        String imageUrl,
        String phoneNumber
) {
    @Builder
    public UserResponse {
    }
}
