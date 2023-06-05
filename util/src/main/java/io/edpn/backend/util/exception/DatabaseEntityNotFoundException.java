package io.edpn.backend.util.exception;

public class DatabaseEntityNotFoundException extends RuntimeException {
    public DatabaseEntityNotFoundException() {
        super();
    }

    public DatabaseEntityNotFoundException(String message) {
        super(message);
    }

    public DatabaseEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseEntityNotFoundException(Throwable cause) {
        super(cause);
    }
}

