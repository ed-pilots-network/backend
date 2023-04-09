package io.eddb.eddb2backend.domain.model.system;

import io.eddb.eddb2backend.domain.model.body.Body;
import io.eddb.eddb2backend.domain.model.common.Allegiance;
import io.eddb.eddb2backend.domain.model.common.Economy;
import io.eddb.eddb2backend.domain.model.common.Faction;
import io.eddb.eddb2backend.domain.model.common.Government;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collection;

@Builder
//TODO: Investigate states. EDDB Data has it as an array of 3 objects for Happiness/Economy/Security
//TODO: Investigate minorFactionPresence array
public record System(Long id, String name, Coordinate coordinate, Long population, Government government,
                     Allegiance allegiance, Security security, Economy primaryEconomy, Power power,
                     PowerState powerState, boolean needsPermit, LocalDateTime lastUpdated,
                     Faction controllingMinorFaction, ReserveType reserveType, Long edSystemAddress,
                     Collection<Body> bodies) {
}