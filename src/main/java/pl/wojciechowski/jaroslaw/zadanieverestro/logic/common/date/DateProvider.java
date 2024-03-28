package pl.wojciechowski.jaroslaw.zadanieverestro.logic.common.date;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DateProvider {
    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }
}
