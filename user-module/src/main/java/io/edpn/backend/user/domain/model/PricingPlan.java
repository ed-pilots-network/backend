package io.edpn.backend.user.domain.model;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import lombok.Builder;
import lombok.Value;

import java.time.Duration;
import java.util.UUID;

@Value
@Builder
public class PricingPlan {

    UUID id;
    String name;
    long capacityPerMinute;

    public Bandwidth getLimit() {
        return Bandwidth.classic(capacityPerMinute, Refill.intervally(capacityPerMinute, Duration.ofMinutes(1)));
    }

    //https://www.baeldung.com/spring-bucket4j
    //https://blog.hubspot.com/website/api-keys
}
