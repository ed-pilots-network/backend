package io.edpn.backend.exploration.application.domain;

public enum LandingPadSize {
    UNKNOWN,
    SMALL,
    MEDIUM,
    LARGE;

    public static LandingPadSize fromString(String landingPadSize) {
        return switch (landingPadSize.toUpperCase()) {
            case "SMALL" -> SMALL;
            case "MEDIUM" -> MEDIUM;
            case "LARGE" -> LARGE;
            default -> UNKNOWN;
        };
    }
}
