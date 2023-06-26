package io.edpn.backend.user.infrastructure.persistence.mappers.entity;

import io.edpn.backend.user.domain.model.ApiKey;
import io.edpn.backend.user.infrastructure.persistence.entity.ApiKeyEntity;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ApiKeyMapper {

    private final ApiRoleMapper apiRoleMapper;
    private final ApiGrantMapper apiGrantMapper;

    public ApiKey map(ApiKeyEntity entity) {
        return ApiKey.builder()
                .id(entity.getId())
                .name(entity.getName())
                .prefix(entity.getPrefix())
                .keyHash(entity.getKeyHash())
                .roles(entity.getRoles().stream().map(apiRoleMapper::map).collect(Collectors.toSet()))
                .grants(entity.getGrants().stream().map(apiGrantMapper::map).collect(Collectors.toSet()))
                .expiryTimestamp(entity.getExpiryTimestamp())
                .build();
    }

    public ApiKeyEntity map(ApiKey key) {
        return ApiKeyEntity.builder()
                .id(key.getId())
                .name(key.getName())
                .prefix(key.getPrefix())
                .keyHash(key.getKeyHash())
                .roles(key.getRoles().stream().map(apiRoleMapper::map).collect(Collectors.toSet()))
                .grants(key.getGrants().stream().map(apiGrantMapper::map).collect(Collectors.toSet()))
                .expiryTimestamp(key.getExpiryTimestamp())
                .build();
    }
}
