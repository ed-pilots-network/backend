package io.edpn.backend.user.infrastructure.persistence.mappers.entity;

import io.edpn.backend.user.domain.model.ApiGrant;
import io.edpn.backend.user.infrastructure.persistence.entity.ApiGrantEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiGrantMapper {

    public ApiGrant map(ApiGrantEntity entity) {
        return ApiGrant.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public ApiGrantEntity map(ApiGrant key) {
        return ApiGrantEntity.builder()
                .id(key.getId())
                .name(key.getName())
                .build();
    }
}
