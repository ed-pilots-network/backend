package io.eddb.eddb2backend.application.dto.persistence;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Data
@Builder
public class SystemEntity {

    private Id id;
    private String name;

    @Value(staticConstructor = "of")
    public static class Id {
        UUID value;
    }
}



