package io.edpn.backend.rest.application.dto.system;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class FindSystemRequest {
    
    private final String name;
    
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
