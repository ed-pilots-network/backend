package io.edpn.backend.exploration.infrastructure.persistence.entity;

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
public class SystemEntity {

    private UUID id;
    private String name;
    private Long eliteId;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
}
