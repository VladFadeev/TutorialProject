package org.example.util.exception;

public class ChapterException extends RuntimeException {
    public ChapterException(String message) {
        super(message);
    }

    public ChapterException() {
        super();
    }

    public ChapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChapterException(Throwable cause) {
        super(cause);
    }

}
