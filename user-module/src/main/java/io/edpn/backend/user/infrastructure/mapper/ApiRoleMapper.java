package io.edpn.backend.user.infrastructure.mapper;

import io.edpn.backend.user.domain.model.ApiRole;
import io.edpn.backend.user.infrastructure.entity.ApiRoleEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiRoleMapper {

    public ApiRole map(ApiRoleEntity entity) {
        return ApiRole.builder()
                .id(entity.getId())
                .name(entity.getName())
                .grants(entity.getGrants())
                .build();
    }
    public ApiRoleEntity map(ApiRole role) {
        return ApiRoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .grants(role.getGrants())
                .build();
    }
}
