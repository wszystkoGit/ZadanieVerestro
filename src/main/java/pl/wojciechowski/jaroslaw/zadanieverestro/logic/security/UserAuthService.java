package pl.wojciechowski.jaroslaw.zadanieverestro.logic.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.security.UserAuth;

public interface UserAuthService extends UserDetailsService {
    UserAuth registerUser(UserAuth userAuth);

    boolean userExistsByUsername(String username);

    String getAuthenticatedUserUsername();
}
