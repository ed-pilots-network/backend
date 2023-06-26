package io.edpn.backend.user.infrastructure.persistence.mappers.entity;

import io.edpn.backend.user.domain.model.UserRole;
import io.edpn.backend.user.infrastructure.persistence.entity.UserRoleEntity;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRoleMapper {

    private final UserGrantMapper userGrantMapper;

    public UserRole map(UserRoleEntity entity) {
        return UserRole.builder()
                .id(entity.getId())
                .name(entity.getName())
                .grants(entity.getGrants().stream().map(userGrantMapper::map).collect(Collectors.toSet()))
                .build();
    }

    public UserRoleEntity map(UserRole userRole) {
        return UserRoleEntity.builder()
                .id(userRole.getId())
                .name(userRole.getName())
                .grants(userRole.getGrants().stream().map(userGrantMapper::map).collect(Collectors.toSet()))
                .build();
    }
}
