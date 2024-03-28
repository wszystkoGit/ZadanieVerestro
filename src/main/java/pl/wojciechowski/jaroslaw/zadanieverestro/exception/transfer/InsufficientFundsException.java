package pl.wojciechowski.jaroslaw.zadanieverestro.exception.transfer;

import pl.wojciechowski.jaroslaw.zadanieverestro.exception.BusinessException;

public class InsufficientFundsException extends BusinessException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
