package org.example.JavaCore.HandleException;

public class Model {
    private  int code;
    private String message;

    public Model(int code, String message) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
