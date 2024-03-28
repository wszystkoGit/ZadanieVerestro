package pl.wojciechowski.jaroslaw.zadanieverestro.domain.notification.event;

import lombok.Builder;
import lombok.Getter;
import pl.wojciechowski.jaroslaw.zadanieverestro.domain.user.UserDetails;
import pl.wojciechowski.jaroslaw.zadanieverestro.logic.transfer.dto.TransferDto;

@Getter
@Builder
public class TransferCompletedEvent {
    private UserDetails sourceUserDetails;
    private TransferDto transferDto;
}
