package org.example.Assignment.HandleException;

public class BaseUnchecked  extends RuntimeException {
    private final String message;

    public BaseUnchecked(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
