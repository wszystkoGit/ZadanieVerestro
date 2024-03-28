package pl.wojciechowski.jaroslaw.zadanieverestro.domain.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth {
    @Id
    @SequenceGenerator(sequenceName = "user_auth_id_seq", name = "userAuthIdGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userAuthIdGenerator")
    private Long id;
    private String username;
    private String password;
    private Long userDetailsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuth userAuth = (UserAuth) o;
        return Objects.equals(id, userAuth.id) && Objects.equals(username, userAuth.username) && Objects.equals(password, userAuth.password) && Objects.equals(userDetailsId, userAuth.userDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
