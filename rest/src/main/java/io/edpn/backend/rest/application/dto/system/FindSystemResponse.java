package io.edpn.backend.rest.application.dto.system;

import io.edpn.backend.rest.domain.model.common.Allegiance;
import io.edpn.backend.rest.domain.model.common.Economy;
import io.edpn.backend.rest.domain.model.common.Faction;
import io.edpn.backend.rest.domain.model.common.Government;
import io.edpn.backend.rest.domain.model.system.*;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value(staticConstructor = "of")
@Builder
public class FindSystemResponse {
    
    UUID id;
    String name;
    Coordinate coordinate;
    Long population;
    Government government;
    Allegiance allegiance;
    Security security;
    Economy primaryEconomy;
    Power power;
    PowerState powerState;
    boolean needsPermit;
    LocalDateTime lastUpdated;
    Faction controllingMinorFaction;
    ReserveType reserveType;
    Long eliteId;
}