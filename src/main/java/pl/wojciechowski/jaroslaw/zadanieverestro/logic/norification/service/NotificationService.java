package pl.wojciechowski.jaroslaw.zadanieverestro.logic.norification.service;

import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;

public interface NotificationService {
    void sendNotification(UserDetails userDetails, String message);
}
