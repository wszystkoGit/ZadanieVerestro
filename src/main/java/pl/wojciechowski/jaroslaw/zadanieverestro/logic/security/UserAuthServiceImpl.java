package pl.wojciechowski.jaroslaw.zadanieverestro.logic.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.security.UserAuth;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.security.UserAuthNotFoundException;
import pl.wojciechowski.jaroslaw.zadanieverestro.repository.security.UserAuthRepository;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {
    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = getUserAuth(username);
        return User.builder()
                .username(userAuth.getUsername())
                .password(userAuth.getPassword())
                .build();
    }

    @Override
    public UserAuth registerUser(UserAuth userAuth) {
        return userAuthRepository.save(userAuth);
    }

    @Override
    public boolean userExistsByUsername(String username) {
        return userAuthRepository.existsByUsername(username);
    }

    @Override
    public String getAuthenticatedUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        throw new UserAuthNotFoundException("No user is currently authorized.");
    }

    private UserAuth getUserAuth(String username) {
        return userAuthRepository.findUserAuthByUsername(username)
                .orElseThrow(() -> new UserAuthNotFoundException("Unable to find user: " + username));
    }

}
