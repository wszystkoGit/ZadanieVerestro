package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String username;
    private String phoneNumber;
    private String email;
    private String preferredNotificationChannel;

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", preferredNotificationChannel='" + preferredNotificationChannel + '\'' +
                '}';
    }
}
