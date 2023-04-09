package io.eddb.eddb2backend.domain.model.system;

import io.eddb.eddb2backend.domain.model.Allegiance;
import io.eddb.eddb2backend.domain.model.Economy;
import io.eddb.eddb2backend.domain.model.Faction;
import io.eddb.eddb2backend.domain.model.Government;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
//TODO: Investigate states. EDDB Data has it as an array of 3 objects for Happiness/Economy/Security
//TODO: Investigate minorFactionPresence array
public record System(Long id, String name, Coordinate coordinate, Long population, Government government,
                     Allegiance allegiance, Security security, Economy primaryEconomy, Power power,
                     PowerState powerState, boolean needsPermit, LocalDateTime lastUpdated,
                     Faction controllingMinorFaction, ReserveType reserveType, Long edSystemAddress) {
}