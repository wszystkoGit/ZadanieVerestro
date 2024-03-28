package pl.wojciechowski.jaroslaw.zadanieverestro.domain.user;

import jakarta.persistence.*;
import lombok.*;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.notification.NotificationChannel;

import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Id
    @SequenceGenerator(sequenceName = "user_details_id_seq", name = "userDetailsIdGenerator")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userDetailsIdGenerator")
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING) //Użycie konwertera byłoby bezpieczniejsze długofalowo
    private NotificationChannel preferredNotificationChannel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
