package pl.wojciechowski.jaroslaw.zadanieverestro.exception.security;

public class UserAuthNotFoundException extends RuntimeException {
    public UserAuthNotFoundException(String message) {
        super(message);
    }

    public UserAuthNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
