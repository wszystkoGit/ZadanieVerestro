package pl.wojciechowski.jaroslaw.zadanieverestro.logic.norification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.norification.formatter.NotificationFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationFormatter notificationFormatter;

    @Override
    public void sendNotification(UserDetails userDetails, String message) {
        String formattedMessage = notificationFormatter.formatMessage(
                userDetails.getPreferredNotificationChannel(),
                resolveRecipientAddressType(userDetails),
                message);

        log.info(formattedMessage);
    }

    private String resolveRecipientAddressType(UserDetails userDetails) {
        return switch (userDetails.getPreferredNotificationChannel()) {
            case SMS -> userDetails.getPhoneNumber();
            case EMAIL -> userDetails.getEmail();
        };
    }
}
