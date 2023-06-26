package io.edpn.backend.user.infrastructure.persistence.entity;

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
public class ApiKeyEntity {

    private UUID id;
    private String prefix;
    private String keyHash;
    private String name;
    private Set<ApiRoleEntity> roles;
    private Set<ApiGrantEntity> grants;
    private LocalDateTime expiryTimestamp;
    private boolean enabled;

}
