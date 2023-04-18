package io.eddb.eddb2backend.application.dto.persistence;

import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemEntity extends BaseEntity {

    private String name;

    private Set<UUID> stationIds;
}



