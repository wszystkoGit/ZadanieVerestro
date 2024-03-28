package pl.wojciechowski.jaroslaw.zadanieverestro.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
