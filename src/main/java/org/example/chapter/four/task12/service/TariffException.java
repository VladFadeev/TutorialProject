package org.example.chapter.four.task12.service;

public class TariffException extends RuntimeException {
    public TariffException(String message) {
        super(message);
    }

  public TariffException() {
    super();
  }

  public TariffException(String message, Throwable cause) {
    super(message, cause);
  }

  public TariffException(Throwable cause) {
    super(cause);
  }
}
