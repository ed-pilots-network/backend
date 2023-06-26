package io.edpn.backend.user.domain.model;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiGrant {

    UUID id;
    String name;
}
