package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.notification.NotificationChannel;
import pl.wojciechowski.jaroslaw.zadanieverestro.validation.ValueOfEnum;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Pattern(regexp = "\\d{9}", message = "Should have exactly 9 numbers")
    private String phoneNumber;
    @Email
    private String email;
    @NotNull
    @ValueOfEnum(enumClass = NotificationChannel.class, message = "Must be any of [SMS, EMAIL]")
    //dostępne wartości powinny być częścią dokumentacji API
    private String preferredNotificationChannel;

    @Override
    public String toString() {
        return "UserRegistrationDto{" +
                "username='" + username + '\'' +
                ", password='" + "***" + '\'' + //dobrze byłoby zabezpieczyć to też na poziomie loggera
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", preferredNotificationChannel='" + preferredNotificationChannel + '\'' +
                '}';
    }
}
