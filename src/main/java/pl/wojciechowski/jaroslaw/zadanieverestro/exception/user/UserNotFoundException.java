package pl.wojciechowski.jaroslaw.zadanieverestro.exception.user;

import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
