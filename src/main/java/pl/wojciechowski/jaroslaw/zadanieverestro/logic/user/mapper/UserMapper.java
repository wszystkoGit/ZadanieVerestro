package pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.notification.NotificationChannel;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.security.UserAuth;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserDto;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.user.dto.UserRegistrationDto;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserDetails mapToUserDetails(UserRegistrationDto userRegistrationDto) {
        return UserDetails.builder()
                .username(userRegistrationDto.getUsername())
                .email(userRegistrationDto.getEmail())
                .phoneNumber(userRegistrationDto.getPhoneNumber())
                .preferredNotificationChannel(NotificationChannel.valueOf(userRegistrationDto.getPreferredNotificationChannel()))
                .build();
    }

    public UserAuth mapToUserAuth(UserRegistrationDto userRegistrationDto) {
        return UserAuth.builder()
                .username(userRegistrationDto.getUsername())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .build();
    }

    public UserDto mapToUserDto(UserDetails userDetails) {
        return UserDto.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .phoneNumber(userDetails.getPhoneNumber())
                .preferredNotificationChannel(userDetails.getPreferredNotificationChannel().toString())
                .build();
    }
}
