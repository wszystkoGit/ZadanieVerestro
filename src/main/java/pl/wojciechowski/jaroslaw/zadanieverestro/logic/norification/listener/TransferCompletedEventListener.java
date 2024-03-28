package pl.wojciechowski.jaroslaw.zadanieverestro.logic.norification.listener;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.notification.event.TransferCompletedEvent;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.norification.service.NotificationService;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class TransferCompletedEventListener {
    private final NotificationService notificationService;

    @TransactionalEventListener
    public void handleTransferCompletedEvent(TransferCompletedEvent transferCompletedEvent) {
        CompletableFuture.runAsync(() -> sendNotification(transferCompletedEvent));
    }

    @SneakyThrows //just for thread sleep
    private void sendNotification(TransferCompletedEvent transferCompletedEvent) {
        Thread.sleep(5000); //Simulate some delay in message sending
        UserDetails sourceUserDetails = transferCompletedEvent.getSourceUserDetails();
        notificationService.sendNotification(sourceUserDetails, transferCompletedEvent.getTransferDto().toString());
    }
}
