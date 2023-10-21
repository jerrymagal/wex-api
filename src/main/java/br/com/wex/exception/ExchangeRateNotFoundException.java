package br.com.wex.exception;

import java.io.Serial;

public class ExchangeRateNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ExchangeRateNotFoundException() {
        super("The purchase cannot be converted to the target currency");
    }

    public ExchangeRateNotFoundException(String message) {
        super(message);
    }

    public ExchangeRateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}