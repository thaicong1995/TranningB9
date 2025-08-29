package org.example.Assignment.HandleException;

public class BaseChecked extends Exception {
    private final String message;

    public BaseChecked(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
