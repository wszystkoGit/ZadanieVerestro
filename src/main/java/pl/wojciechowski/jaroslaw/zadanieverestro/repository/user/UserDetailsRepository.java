package pl.wojciechowski.jaroslaw.zadanieverestro.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    Optional<UserDetails> findUserDetailsByUsername(String username);

    boolean existsByUsernameOrEmailOrPhoneNumber(String username, String email, String phoneNumber);
}
