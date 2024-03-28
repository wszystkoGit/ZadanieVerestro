package pl.wojciechowski.jaroslaw.zadanieverestro.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserBalance;

import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    Optional<UserBalance> findByIdentifier(String identifier);
}
