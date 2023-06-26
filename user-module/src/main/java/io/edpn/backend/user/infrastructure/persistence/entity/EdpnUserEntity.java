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
public class EdpnUserEntity {

    private UUID id;
    private String email;
    private String password;
    private LocalDateTime accountExpiryTimestamp;
    private LocalDateTime passwordExpiryTimestamp;
    private boolean enabled;
    private boolean locked;
    private Set<UserRoleEntity> roles;
    private Set<UserGrantEntity> grants;
    private Set<ApiKeyEntity> apiKeys;
    private PricingPlanEntity pricingPlan;
}
