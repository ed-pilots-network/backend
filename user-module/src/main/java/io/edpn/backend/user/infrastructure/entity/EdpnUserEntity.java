package io.edpn.backend.user.infrastructure.entity;

import io.edpn.backend.user.domain.model.ApiKey;
import io.edpn.backend.user.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EdpnUserEntity {

    private UUID id;
    private String email;
    private String password;
    private LocalDateTime accountExpiryTimestamp;
    private LocalDateTime passwordExpiryTimestamp;
    private boolean enabled;
    private boolean locked;
    private Set<UserRoleEntity> roles;
    private Set<String> grants;
    private Set<ApiKeyEntity> apiKeys;
}
