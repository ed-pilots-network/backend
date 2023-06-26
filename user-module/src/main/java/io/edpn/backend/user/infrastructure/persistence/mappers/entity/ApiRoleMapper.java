package io.edpn.backend.user.infrastructure.persistence.mappers.entity;

import io.edpn.backend.user.domain.model.ApiRole;
import io.edpn.backend.user.infrastructure.persistence.entity.ApiRoleEntity;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiRoleMapper {

    private final ApiGrantMapper apiGrantMapper;


    public ApiRole map(ApiRoleEntity entity) {
        return ApiRole.builder()
                .id(entity.getId())
                .name(entity.getName())
                .grants(entity.getGrants().stream().map(apiGrantMapper::map).collect(Collectors.toSet()))
                .build();
    }

    public ApiRoleEntity map(ApiRole role) {
        return ApiRoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .grants(role.getGrants().stream().map(apiGrantMapper::map).collect(Collectors.toSet()))
                .build();
    }
}
