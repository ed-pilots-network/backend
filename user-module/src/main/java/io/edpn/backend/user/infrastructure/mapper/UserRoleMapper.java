package io.edpn.backend.user.infrastructure.mapper;

import io.edpn.backend.user.domain.model.UserRole;
import io.edpn.backend.user.infrastructure.entity.UserRoleEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRoleMapper {

    public UserRole map(UserRoleEntity entity) {
        return UserRole.builder()
                .id(entity.getId())
                .name(entity.getName())
                .grants(entity.getGrants())
                .build();
    }

    public UserRoleEntity map(UserRole userRole) {
        return UserRoleEntity.builder()
                .id(userRole.getId())
                .name(userRole.getName())
                .grants(userRole.getGrants())
                .build();
    }
}
