package pl.wojciechowski.jaroslaw.zadanieverestro.exception.user;

import lombok.Getter;
import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;

@Getter
public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
