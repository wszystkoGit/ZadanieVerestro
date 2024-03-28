package pl.wojciechowski.jaroslaw.zadanieverestro.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.security.UserAuth;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    boolean existsByUsername(String username);

    Optional<UserAuth> findUserAuthByUsername(String username);
}
