package org.example.JavaCore.HandleException;

public abstract class BaseException extends Exception {
    private final int code;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
