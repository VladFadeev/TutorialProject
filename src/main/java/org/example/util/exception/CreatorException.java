package org.example.util.exception;

public class CreatorException extends Exception {
    public CreatorException() {
        super();
    }

    public CreatorException(String message) {
        super(message);
    }

    public CreatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreatorException(Throwable cause) {
        super(cause);
    }

    protected CreatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
