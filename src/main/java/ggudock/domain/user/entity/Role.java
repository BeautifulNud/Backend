package ggudock.domain.user.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ANYONE("ROLE_ANYONE")
    ;

    private final String key;
}

