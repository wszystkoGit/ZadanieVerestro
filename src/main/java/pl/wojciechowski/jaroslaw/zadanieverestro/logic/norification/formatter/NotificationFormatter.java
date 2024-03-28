package pl.wojciechowski.jaroslaw.zadanieverestro.logic.norification.formatter;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.notification.NotificationChannel;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationFormatter {
    String template = "sending ${channelType} to ${deliveryMethod}: ${recipientAddress}, ${content}";
    Map<NotificationChannel, Map<String, String>> valuesByNotificationChannel = Map.of(
            NotificationChannel.EMAIL,
            Map.of(
                    "channelType", "email",
                    "deliveryMethod", "email address"
            ),
            NotificationChannel.SMS,
            Map.of(
                    "channelType", "sms",
                    "deliveryMethod", "phone number"
            )
    );

    public String formatMessage(NotificationChannel notificationChannel, String recipientAddress, String message) {
        Map<String, String> valuesMap = new HashMap<>(valuesByNotificationChannel.get(notificationChannel));
        valuesMap.put("recipientAddress", recipientAddress);
        valuesMap.put("content", message);

        StringSubstitutor stringSubstitutor = new StringSubstitutor(valuesMap);

        return stringSubstitutor.replace(template);
    }
}
