package ggudock.domain.user.repository;

import ggudock.config.oauth.entity.ProviderType;
import ggudock.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    User findByNickname(String nickname);

    User findByUsernameAndEmail(String username, String email);

    List<User> findByUsername(String username);

    List<User> findByEmail(String email);

    User findByEmailAndProviderType(String email, ProviderType providerType);

    Boolean existsByEmailAndProviderType(String email, ProviderType providerType);
}
