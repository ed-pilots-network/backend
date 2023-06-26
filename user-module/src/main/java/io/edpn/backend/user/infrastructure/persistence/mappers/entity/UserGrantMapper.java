package io.edpn.backend.user.infrastructure.persistence.mappers.entity;

import io.edpn.backend.user.domain.model.UserGrant;
import io.edpn.backend.user.infrastructure.persistence.entity.UserGrantEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserGrantMapper {

    public UserGrant map(UserGrantEntity entity) {
        return UserGrant.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public UserGrantEntity map(UserGrant key) {
        return UserGrantEntity.builder()
                .id(key.getId())
                .name(key.getName())
                .build();
    }
}
