package ggudock.domain.user.repository;

import ggudock.config.oauth.entity.ProviderType;
import ggudock.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);

    User findByEmail(String email);

    List<User> findByUsername(String username);
}
