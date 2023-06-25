package io.edpn.backend.user.domain.model;

import java.util.Set;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRole {

    String name;
    Set<String> grants;
}
