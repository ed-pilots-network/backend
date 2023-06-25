package io.edpn.backend.user.domain.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiKey {

    String prefix;
    String keyHash;
    String name;
    Set<UserRole> roles;
    Set<String> grants;
    LocalDateTime expiryTimestamp;
    boolean enabled;


}
