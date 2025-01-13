package org.example.util.exception;

public class ReaderException extends Exception {
    public ReaderException(String message) {
        super(message);
    }

    public ReaderException() {
        super();
    }

    public ReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReaderException(Throwable cause) {
        super(cause);
    }
}
