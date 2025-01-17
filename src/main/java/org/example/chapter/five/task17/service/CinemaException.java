package org.example.chapter.five.task17.service;

public class CinemaException extends RuntimeException {
    public CinemaException(String message) {
        super(message);
    }

    public CinemaException() {
        super();
    }

    public CinemaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CinemaException(Throwable cause) {
        super(cause);
    }
}
