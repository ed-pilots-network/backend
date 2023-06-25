package io.edpn.backend.user.domain.model;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import java.time.Duration;

public enum PricingPlan {
    ANONYMOUS {
        Bandwidth getLimit() {
            return Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        }
    },
    FREE {
        Bandwidth getLimit() {
            return Bandwidth.classic(60, Refill.intervally(60, Duration.ofMinutes(1)));
        }
    },
    INTERNAL {
        Bandwidth getLimit() {
            return Bandwidth.classic(6000, Refill.intervally(6000, Duration.ofMinutes(1)));
        }
    };

    https://www.baeldung.com/spring-bucket4j
    https://blog.hubspot.com/website/api-keys
}
