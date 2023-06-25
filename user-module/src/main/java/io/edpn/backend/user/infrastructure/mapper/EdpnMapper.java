package io.edpn.backend.user.infrastructure.mapper;

import io.edpn.backend.user.domain.model.EdpnUser;
import io.edpn.backend.user.infrastructure.entity.EdpnUserEntity;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class EdpnMapper {

    private final UserRoleMapper userRoleMapper;
    private final ApiKeyMapper apiKeyMapper;

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
                .grants(entity.getGrants())
                .apiKeys(entity.getApiKeys().stream().map(apiKeyMapper::map).collect(Collectors.toSet()))
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
                .grants(user.getGrants())
                .apiKeys(user.getApiKeys().stream().map(apiKeyMapper::map).collect(Collectors.toSet()))
                .build();
    }
}
