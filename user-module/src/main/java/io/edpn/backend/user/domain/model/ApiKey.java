package io.edpn.backend.user.domain.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiKey {

    UUID id;
    String prefix;
    String keyHash;
    String name;
    Set<ApiRole> roles;
    Set<String> grants;
    LocalDateTime expiryTimestamp;
    boolean enabled;


}
