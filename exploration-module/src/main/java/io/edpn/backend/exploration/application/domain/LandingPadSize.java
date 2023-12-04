package io.edpn.backend.exploration.application.domain;

import java.util.Set;

public enum LandingPadSize {
    UNKNOWN,
    SMALL,
    MEDIUM,
    LARGE;

    public static final Set<LandingPadSize> KNOWN_LANDING_PAD_SIZES = Set.of(SMALL, MEDIUM, LARGE);

    public static LandingPadSize fromString(String landingPadSize) {
        try {
            return LandingPadSize.valueOf(landingPadSize);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
