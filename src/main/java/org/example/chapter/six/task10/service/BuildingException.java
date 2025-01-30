package org.example.chapter.six.task10.service;

public class BuildingException extends RuntimeException {
    public BuildingException(String message) {
        super(message);
    }

    public BuildingException() {
        super();
    }

    public BuildingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildingException(Throwable cause) {
        super(cause);
    }
}
