package io.edpn.backend.user.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class ApiRole {

    UUID id;
    String name;
    Set<String> grants;
}
