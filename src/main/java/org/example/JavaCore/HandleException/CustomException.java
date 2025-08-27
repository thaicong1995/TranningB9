package org.example.JavaCore.HandleException;

public class CustomException {

    public static class NotFoundException extends BaseException{
        public NotFoundException(String message) {
            super(1, message);
        }
    };

}
