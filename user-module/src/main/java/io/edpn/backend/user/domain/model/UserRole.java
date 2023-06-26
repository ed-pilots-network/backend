package io.edpn.backend.user.domain.model;

import java.util.Set;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRole {

    UUID id;
    String name;
    Set<UserGrant> grants;
}
