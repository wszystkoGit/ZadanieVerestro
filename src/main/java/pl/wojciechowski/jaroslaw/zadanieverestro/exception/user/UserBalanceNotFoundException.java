package pl.wojciechowski.jaroslaw.zadanieverestro.exception.user;

import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;

public class UserBalanceNotFoundException extends BusinessException {
    public UserBalanceNotFoundException(String message) {
        super(message);
    }
}
