package ggudock.domain.user.repository;

import ggudock.domain.user.entity.User;
import ggudock.domain.user.model.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
