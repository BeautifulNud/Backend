package ggudock.domain.user.repository;

import ggudock.config.oauth.entity.ProviderType;
import ggudock.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    User findByUsername(String username);

    User findByNickname(String nickname);

    User findByEmail(String email);

    Boolean existsByEmailAndProviderType(String email, ProviderType providerType);
}
