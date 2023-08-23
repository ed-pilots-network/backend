package io.edpn.backend.trade.application.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class System {
    private UUID id;
    private Long eliteId;
    private String name;
    private Double xCoordinate;
    private Double yCoordinate;
    private Double zCoordinate;
    
}
