package ggudock.domain.user.repository;

import ggudock.config.oauth.entity.ProviderType;
import ggudock.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    User findByUsername(String username);

    User findByNickname(String nickname);

    List<User> findByEmail(String email);

    User findByEmailAndProviderType(String email, ProviderType providerType);

    Boolean existsByEmailAndProviderType(String email, ProviderType providerType);

    @EntityGraph(attributePaths = "authorities")	// Eager(즉시)조회로 authorites 정보를 같이 가져온다.
        // username을 기준으로 User 정보를 가져올때 권한 정보도 같이 가져온다.
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
