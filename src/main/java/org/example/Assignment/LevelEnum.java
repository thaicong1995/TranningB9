package org.example.Assignment;

public enum LevelEnum {
    INFO("[INFO]"),
    WARN("[WARN]"),
    ERROR("[ERROR]"),
    DEBUG("[DEBUG]");

    private final String pattern;

    LevelEnum(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}