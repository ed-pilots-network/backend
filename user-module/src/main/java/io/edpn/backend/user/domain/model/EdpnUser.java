package io.edpn.backend.user.domain.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EdpnUser {

    String email;
    String password;
    LocalDateTime accountExpiryTimestamp;
    LocalDateTime passwordExpiryTimestamp;
    boolean enabled;
    boolean locked;
    Set<UserRole> roles;
    Set<String> grants;
}
