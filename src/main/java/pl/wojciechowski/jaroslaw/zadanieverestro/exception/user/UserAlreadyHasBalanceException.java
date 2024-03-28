package pl.wojciechowski.jaroslaw.zadanieverestro.exception.user;

import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;

public class UserAlreadyHasBalanceException extends BusinessException {
    public UserAlreadyHasBalanceException(String message) {
        super(message);
    }
}
