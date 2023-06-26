package io.edpn.backend.user.infrastructure.persistence.entity;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRoleEntity {

    private UUID id;
    private String name;
    private Set<UserGrantEntity> grants;
}