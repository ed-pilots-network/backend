package io.eddb.eddb2backend.application.dto.persistence;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StationEntity extends BaseEntity {

    private String name;
}
