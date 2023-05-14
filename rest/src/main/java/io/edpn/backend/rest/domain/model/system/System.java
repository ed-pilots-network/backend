package io.edpn.backend.rest.domain.model.system;

import io.edpn.backend.rest.domain.model.body.Body;
import io.edpn.backend.rest.domain.model.common.Allegiance;
import io.edpn.backend.rest.domain.model.common.Economy;
import io.edpn.backend.rest.domain.model.common.Faction;
import io.edpn.backend.rest.domain.model.common.Government;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//TODO: Investigate states. EDPN Data has it as an array of 3 objects for Happiness/Economy/Security
//TODO: Investigate minorFactionPresence array
public class System {
    private UUID id;
    private String name;
    private Coordinate coordinate;
    private Long population;
    private Government government;
    private Allegiance allegiance;
    private Security security;
    private Economy primaryEconomy;
    private Power power;
    private PowerState powerState;
    private boolean needsPermit;
    private LocalDateTime lastUpdated;
    private Faction controllingMinorFaction;
    private ReserveType reserveType;
    private Long eliteId;
    private Collection<Body> bodies;
}
