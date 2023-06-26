package io.edpn.backend.user.infrastructure.persistence.mappers.entity;

import io.edpn.backend.user.domain.model.EdpnUser;
import io.edpn.backend.user.infrastructure.persistence.entity.EdpnUserEntity;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EdpnUserMapper {

    private final UserRoleMapper userRoleMapper;
    private final ApiKeyMapper apiKeyMapper;
    private final UserGrantMapper userGrantMapper;
    private final PricingPlanMapper pricingPlanMapper;

    public EdpnUser map(EdpnUserEntity entity) {
        return EdpnUser.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(null) // DO NOT RETURN EMAIL
                .accountExpiryTimestamp(entity.getAccountExpiryTimestamp())
                .passwordExpiryTimestamp(entity.getPasswordExpiryTimestamp())
                .enabled(entity.isEnabled())
                .locked(entity.isLocked())
                .roles(entity.getRoles().stream().map(userRoleMapper::map).collect(Collectors.toSet()))
                .grants(entity.getGrants().stream().map(userGrantMapper::map).collect(Collectors.toSet()))
                .apiKeys(entity.getApiKeys().stream().map(apiKeyMapper::map).collect(Collectors.toSet()))
                .pricingPlan(pricingPlanMapper.map(entity.getPricingPlan()))
                .build();
    }

    public EdpnUserEntity map(EdpnUser user) {
        return EdpnUserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountExpiryTimestamp(user.getAccountExpiryTimestamp())
                .passwordExpiryTimestamp(user.getPasswordExpiryTimestamp())
                .enabled(user.isEnabled())
                .locked(user.isLocked())
                .roles(user.getRoles().stream().map(userRoleMapper::map).collect(Collectors.toSet()))
                .grants(user.getGrants().stream().map(userGrantMapper::map).collect(Collectors.toSet()))
                .apiKeys(user.getApiKeys().stream().map(apiKeyMapper::map).collect(Collectors.toSet()))
                .pricingPlan(pricingPlanMapper.map(user.getPricingPlan()))
                .build();
    }
}
