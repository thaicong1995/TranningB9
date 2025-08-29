package org.example.Assignment.HandleException;

public class CustomException {

    public static class FileNotFoundException extends BaseChecked {
        public FileNotFoundException(String message) {
            super(message);
        }
    }

    public static class IllegalStateException extends BaseChecked {
        public IllegalStateException(String message) {
            super(message);
        }
    }

    public static class NullPointerException extends BaseUnchecked {
        public NullPointerException(String message) {
            super(message);
        }
    }

    public static class IOException extends BaseChecked {
        public IOException(String message) {
            super(message);
        }
    }
}
