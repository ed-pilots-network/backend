package io.edpn.backend.modulith.commodityfinder.domain.entity;

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
public class System {

    private UUID id;
    private String name;
    private Long xCoordinate;
    private Long yCoordinate;
    private Long zCoordinate;
}
